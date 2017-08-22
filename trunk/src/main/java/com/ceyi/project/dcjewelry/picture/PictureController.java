package com.ceyi.project.dcjewelry.picture;

import com.ceyi.project.dcjewelry.ConfigHolder;
import com.ceyi.project.dcjewelry.article.Article;
import com.ceyi.project.dcjewelry.article.ArticleService;
import com.ceyi.project.dcjewelry.user.User;
import com.ceyi.project.dcjewelry.user.UserHis;
import com.ceyi.project.dcjewelry.user.UserHisService;
import com.ceyi.project.dcjewelry.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pw.wecode.project.framework.utils.FileUtils;
import pw.wecode.project.framework.utils.StringUtils;
import pw.wecode.project.framework.web.Mvc;
import pw.wecode.project.framework.web.Result;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * Created by lingh on 2017/4/9.
 */
@Controller
@RequestMapping("/wx/pic/")
public class PictureController {
    private static final Logger logger = LoggerFactory.getLogger(PictureController.class);
    @Inject
    private PictureService pictureService;
    @Inject
    private UserService userService;
    @Inject
    private ArticleService articleService;
    @Inject
    private UserHisService userHisService;
    @Inject
    private ConfigHolder configHolder;

    @ResponseBody
    @RequestMapping("getDownloadLink")
    public Result<Integer> getDownloadLink(@RequestParam("articleId") int articleId, @RequestParam("pic") String pic) {
        User user = Mvc.context().sGet(User.SESSION_KEY);
        Article article = articleService.get(articleId);
        logger.debug("用户查看原图 user:{} article:{} pic:{}", user, article, pic);
        if (article == null) {
            logger.error("资讯不存在, articleId:{}", articleId);
            return new Result<>(Result.CODE_ERROR, "图片不存在");
        }

        if (!StringUtils.hasText(pic)) {
            logger.error("图片不存在, pic:{}", pic);
            return new Result<>(Result.CODE_ERROR, "图片不存在");
        }

        int picId = 0;
        List<Picture> pictures = pictureService.getByIds(article.getPictureIds());
        for (Picture picture : pictures) {
            if (pic.indexOf(picture.getUrl()) != -1) {
                picId = picture.getId();
                break;
            }
        }
        if (picId == 0) {
            logger.error("图片不存在, pic:{} pictures:{}", pic, pictures);
            return new Result<>(Result.CODE_ERROR, "图片不存在");
        }

        UserHis userHis = userHisService.get(user.getId(), UserHis.TYPE_DOWNLOAD, picId);
        // 下载别人的图片才需要扣除积分
        if (user.getId() != article.getUid() && article.getPoint() > 0) {
            // 第一次下载需要扣积分
            if (userHis == null) {
                if (user.getPoint() < article.getPoint()) {
                    logger.warn("积分不足 user:{}, article:{}", user, article);
                    return new Result<>(Result.CODE_ERROR, "积分不足");
                }

                // 扣除积分
                Result result = userService.incPoint(user, 0 - article.getPoint(), UserHis.TYPE_DOWNLOAD, picId);
                if (!result.isSuccess()) {
                    logger.error("查看原图失败, user:{} result:{}", user, result);
                    return result;
                }
            }
        }
        if (userHis == null) {
            articleService.incDownload(articleId);
        }
        return new Result<>(picId);
    }

    // 微信无法下载,改为查看原图
    @RequestMapping("view-src")
    public void viewSrc(@RequestParam("picId") int picId, HttpServletResponse response) {
        User user = Mvc.context().sGet(User.SESSION_KEY);
        Picture picture = pictureService.get(picId);
        if (picture == null) {
            logger.error("图片不存在, id:{}", picId);
            return;
        }

        if (picture.getUid() != user.getId()) {
            // 需要先访问 /wx/pic/getDownloadLink 进行积分扣除
            UserHis userHis = userHisService.get(user.getId(), UserHis.TYPE_DOWNLOAD, picId);
            if (userHis == null) {
                logger.error("未扣过积分无法下载 user:{} picId:{}", user, picId);
                return;
            }
        }

        try {
            int pos = picture.getSrcUrl().lastIndexOf(".");
            String format = picture.getSrcUrl().substring(pos + 1);
            String contentType = "image/jpeg";
            if ("png".equalsIgnoreCase(format)) {
                contentType = "image/png";
            }
            response.setContentType(contentType);
            BufferedImage image = ImageIO.read(new File(configHolder.getWebroot() + picture.getSrcUrl()));
            ImageIO.write(image, format, response.getOutputStream());
            Mvc.context().response().flushBuffer();
            logger.debug("查看原图成功, user:{} picture:{}", user, picture);
        } catch (Exception e) {
            logger.error("输出图片失败, picture:{} e:{} uid:{} nickname:{}", picture, e, user.getId(), user.getNickname());
            return;
        }
    }

    @RequestMapping("download")
    public ResponseEntity<byte[]> download(@RequestParam("picId") int picId) {
        User user = Mvc.context().sGet(User.SESSION_KEY);

        Picture picture = pictureService.get(picId);
        if (picture == null) {
            logger.error("图片不存在, id:{}", picId);
            return new ResponseEntity<byte[]>(new byte[0], HttpStatus.NOT_IMPLEMENTED);
        }

        if (picture.getUid() != user.getId()) {
            // 需要先访问 /wx/pic/getDownloadLink 进行积分扣除
            UserHis userHis = userHisService.get(user.getId(), UserHis.TYPE_DOWNLOAD, picId);
            if (userHis == null) {
                logger.error("未扣过积分无法下载 user:{} picId:{}", user, picId);
                return new ResponseEntity<byte[]>(new byte[0], HttpStatus.NOT_IMPLEMENTED);
            }
        }

        int pos = picture.getSrcUrl().lastIndexOf(".");
        String format = picture.getSrcUrl().substring(pos + 1);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", System.currentTimeMillis() + "." + format);
        headers.setContentType(MediaType.IMAGE_JPEG);
        if ("png".equalsIgnoreCase(format)) {
            headers.setContentType(MediaType.IMAGE_PNG);
        }
        return new ResponseEntity<byte[]>(FileUtils.readAllAsBytes(configHolder.getWebroot() + picture.getSrcUrl()), headers, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping("getDownloadVideo")
    public Result getDownloadLink(@RequestParam("articleId") int articleId) {
        User user = Mvc.context().sGet(User.SESSION_KEY);
        Article article = articleService.get(articleId);
        logger.debug("用户查看视频 user:{} article:{} pic:{}", user, article);
        if (article == null) {
            logger.error("资讯不存在, articleId:{}", articleId);
            return new Result<>(Result.CODE_ERROR, "视频不存在");
        }

        UserHis userHis = userHisService.get(user.getId(), UserHis.TYPE_VIDEODOWNLOAD, article.getId());
        // 下载别人的图片才需要扣除积分
        if (user.getId() != article.getUid() && article.getPoint() > 0) {
            // 第一次下载需要扣积分
            if (userHis == null) {
                if (user.getPoint() < article.getPoint()) {
                    logger.warn("积分不足 user:{}, article:{}", user, article);
                    return new Result<>(Result.CODE_ERROR, "积分不足");
                }

                // 扣除积分
                Result result = userService.incPoint(user, 0 - article.getPoint(), UserHis.TYPE_VIDEODOWNLOAD, article.getId());
                if (!result.isSuccess()) {
                    logger.error("查看视频失败, user:{} result:{}", user, result);
                    return result;
                }
            }
        }
        if (userHis == null) {
            articleService.incDownload(articleId);
        }
        return new Result();
    }
}
