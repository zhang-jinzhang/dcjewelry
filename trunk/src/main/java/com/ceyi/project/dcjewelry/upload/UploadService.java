package com.ceyi.project.dcjewelry.upload;

//import com.ceyi.project.dcjewelry.util.VideoUtil;
import com.ceyi.project.dcjewelry.banner.BannerService;
import com.ceyi.project.dcjewelry.util.VideoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import pw.wecode.project.framework.utils.FileUtils;
import pw.wecode.project.framework.utils.Md5Utils;
import pw.wecode.project.framework.utils.StringUtils;
import pw.wecode.project.framework.web.Mvc;

import javax.inject.Inject;
import java.io.File;


/**
 * Created by lingh on 2017/4/6.
 */
public class UploadService {
    private String path;
    private String dir;
    private static final Logger logger = LoggerFactory.getLogger(UploadService.class);
    public String upload(MultipartFile file) {
        if (!StringUtils.hasText(path)) {
            path = Mvc.getWebRoot();
        }
        if (!StringUtils.hasText(dir)) {
            dir = "/upload/";
        }
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            String format = FileUtils.guessImageType(file.getOriginalFilename(), file.getContentType());
            byte[] bytes = file.getBytes();
            String hash = Md5Utils.hash(bytes);
            String filename = dir + hash + "." + format;
            String filepath = path + filename;
            if (FileUtils.exists(filepath)) {
                logger.debug("文件已经存在:{}", filepath);
                return filename;
            }
            File parentFile = new File(filepath).getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            FileUtils.writeAll(filepath, bytes);
            return filename;
        } catch (Exception e) {
            logger.error("上传文件失败: name:{} originalFilename:{} e:{}", file.getName(), file.getOriginalFilename(), e);
            return null;
        }
    }

    public String uploadVideo(MultipartFile file) {
        if (!StringUtils.hasText(path)) {
            path = Mvc.getWebRoot();
        }
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            byte[] bytes = file.getBytes();
            if(bytes.length > 10*1024*1024){
                throw new Exception("文件不能大于10M");
            }
            String hash = Md5Utils.hash(bytes);
            String filename = "/upload/video/" + hash + ".mp4" ;
            String filepath = path + filename;
            if (FileUtils.exists(filepath)) {
                logger.debug("文件已经存在:{}", filepath);
                return filename.replace(".mp4",".jpg");
            }
            File parentFile = new File(filepath).getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            FileUtils.writeAll(filepath, bytes);
            String imgName=filename.replace(".mp4",".jpg");
            VideoUtil.cutImg(filepath, imgName);
            return imgName;
        } catch (Exception e) {
            logger.error("上传文件失败: name:{} originalFilename:{} e:{}", file.getName(), file.getOriginalFilename(), e);
            return null;
        }
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
}
