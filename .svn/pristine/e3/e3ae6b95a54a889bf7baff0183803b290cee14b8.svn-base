package com.ceyi.project.dcjewelry.index;

import com.ceyi.project.dcjewelry.admin.DatatablesPage;
import com.ceyi.project.dcjewelry.article.Article;
import com.ceyi.project.dcjewelry.article.ArticleCategoryService;
import com.ceyi.project.dcjewelry.article.ArticleService;
import com.ceyi.project.dcjewelry.article.SearchRequest;
import com.ceyi.project.dcjewelry.banner.BannerService;
import com.ceyi.project.dcjewelry.merchant.Merchant;
import com.ceyi.project.dcjewelry.merchant.MerchantService;
import com.ceyi.project.dcjewelry.message.MessageService;
import com.ceyi.project.dcjewelry.picture.Watermark;
import com.ceyi.project.dcjewelry.upload.UploadService;
import com.ceyi.project.dcjewelry.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import pw.wecode.project.framework.jdbc.Page;
import pw.wecode.project.framework.jdbc.ParamMap;
import pw.wecode.project.framework.utils.StringUtils;
import pw.wecode.project.framework.web.HtmlPage;
import pw.wecode.project.framework.web.Mvc;
import pw.wecode.project.framework.web.Result;
import pw.wecode.project.framework.web.beetl.BeetlHtmlRender;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lingh on 2017/3/31.
 */
@Controller
@RequestMapping("/wx/")
public class IndexController {
    @Inject
    private MessageService messageService;
    @Inject
    private ArticleCategoryService articleCategoryService;
    @Inject
    private BannerService bannerService;
    @Inject
    private ArticleService articleService;
    @Inject
    private BeetlHtmlRender beetlHtmlRender;
    @Inject
    private MerchantService merchantService;
    @Inject
    private IndexService indexService;
    @Inject
    private UploadService uploadService;
    @RequestMapping("index")
    public ModelAndView index() {
        User user = Mvc.context().sGet(User.SESSION_KEY);
        ModelAndView mav = new ModelAndView("weixin/index");
        String cids = merchantService.queryByUid(user.getId());
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setTag(Article.TAG_TT);
        searchRequest.setPageNumber(1);
        searchRequest.setPageSize(3);
        mav.addObject("ttArticles", articleService.search(user.getId(), searchRequest).getData());
        searchRequest = new SearchRequest();
        searchRequest.setTag(Article.TAG_REC);
        searchRequest.setPageNumber(1);
        searchRequest.setPageSize(5);
        mav.addObject("articlePage", articleService.search(user.getId(), searchRequest));
        mav.addObject("unreadCount", messageService.countUnread(user.getId()));
        mav.addObject("topCategories", articleCategoryService.getTopCategoryOnImg().values());
        mav.addObject("banners", bannerService.top());
        mav.addObject("cids", cids);
        return mav;
    }
    @RequestMapping("aboutUs")
    public ModelAndView aboutUs(){
        ModelAndView mav = new ModelAndView("weixin/aboutUs");
        List<AboutUs> aboutUss = indexService.queryAboutUs();
        AboutUs aboutUs = new AboutUs();
        if(aboutUss.size() != 0){
            aboutUs = aboutUss.get(0);
        }
        mav.addObject("aboutUs", aboutUs);
        return mav;
    }
    @RequestMapping("queryAbouts")
    public Result<AboutUs> queryAbouts(){
        List<AboutUs> aboutUss = indexService.queryAboutUs();
        AboutUs aboutUs = new AboutUs();
        if(aboutUss.size() != 0){
            aboutUs = aboutUss.get(0);
        }
        return new Result(aboutUs);
    }

    @RequestMapping("aboutUsIndex")
    public String aboutUsIndex() {
        return "admin/aboutUs/index";
    }

    @RequestMapping("queryAboutUs")
    @ResponseBody
    public DatatablesPage<AboutUs> queryAboutUs(){
        Page page = new Page();
        List<AboutUs> aboutUss = indexService.queryAboutUs();
        page.setRecordCount(aboutUss.size());
        page.setData(aboutUss);
        return new DatatablesPage<>(page);
    }

    @ResponseBody
    @RequestMapping("search")
    public Result<HtmlPage> search(SearchRequest searchRequest) {
        User user = Mvc.context().sGet(User.SESSION_KEY);
        String cids = merchantService.queryByUid(user.getId());
        searchRequest.setTag(Article.TAG_REC);
        Page<Article> articlePage = articleService.search(user.getId(), searchRequest);
        String html = beetlHtmlRender.render("weixin/tpl_article_list_index", new ParamMap<String,Object>("articlePage", articlePage).add("cids",cids));
        HtmlPage htmlPage = new HtmlPage();
        htmlPage.setHtml(html);
        htmlPage.setRecordCount(articlePage.getData().size());
        if (articlePage.getData().size() > 0) {
            if (searchRequest.isOld()) {
                htmlPage.setRecord(articlePage.getData().get(articlePage.getData().size() - 1));
            } else {
                htmlPage.setRecord(articlePage.getData().get(0));
            }
        }
        return new Result<>(htmlPage);
    }

    @RequestMapping("editAboutUs")
    @ResponseBody
    public Result<AboutUs> queryAboutUs(AboutUs aboutUs){
        indexService.save(aboutUs);
        return new Result<>(aboutUs);
    }

    @RequestMapping("saveFile")
    @ResponseBody
    public Result saveFile(AboutUs aboutUs, @RequestParam(value = "file") MultipartFile file){
        if (file.isEmpty()) {
            return new Result(Result.CODE_ERROR, "请上传公司图片");
        }
        String pic = uploadService.upload(file);
        if (!StringUtils.hasText(pic)) {
            return new Result(Result.CODE_ERROR, "上传文件失败");
        }
        List<AboutUs> aboutUss = indexService.queryAboutUs();
        if(aboutUss.size() != 0){
            aboutUs = aboutUss.get(0);
        }
        aboutUs.setSrc(pic);
        indexService.save(aboutUs);
        return new Result();
    }
}
