package com.ceyi.project.dcjewelry.favorite;

import com.ceyi.project.dcjewelry.article.ArticleComment;
import com.ceyi.project.dcjewelry.article.ArticleCommentService;
import com.ceyi.project.dcjewelry.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pw.wecode.project.framework.jdbc.ParamMap;
import pw.wecode.project.framework.web.HtmlPage;
import pw.wecode.project.framework.web.Mvc;
import pw.wecode.project.framework.web.Result;
import pw.wecode.project.framework.web.beetl.BeetlHtmlRender;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Created by lingh on 2017/5/6.
 */
@Controller
public class FavoriteController {
    @Inject
    private ArticleCommentService articleCommentService;
    @Inject
    private BeetlHtmlRender beetlHtmlRender;

    @RequestMapping("/wx/favorite/index")
    public ModelAndView listFavorite() {
        ModelAndView mav = new ModelAndView("weixin/favorite/index");
        User user = Mvc.context().sGet(User.SESSION_KEY);
        mav.addObject("favorites", articleCommentService.listFavorite(user.getId(), 0, null));
        return mav;
    }

    @ResponseBody
    @RequestMapping("/wx/favorite/more")
    public Result<HtmlPage> moreFavorite(@RequestParam("id") int id, @RequestParam("createTime") Date createTime) {
        User user = Mvc.context().sGet(User.SESSION_KEY);
        List<ArticleComment> favorites = articleCommentService.listFavorite(user.getId(), id, createTime);
        String html = beetlHtmlRender.render("weixin/favorite/tpl_list", new ParamMap<>("favorites", favorites));
        HtmlPage htmlPage = new HtmlPage();
        if (favorites.size() > 0) {
            htmlPage.setRecord(favorites.get(favorites.size() - 1));
        }
        htmlPage.setRecordCount(favorites.size());
        htmlPage.setHtml(html);
        return new Result<>(htmlPage);
    }
}
