package com.ceyi.project.dcjewelry.follow;

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
public class FollowController {
    @Inject
    private UserFollowService userFollowService;
    @Inject
    private BeetlHtmlRender beetlHtmlRender;

    @RequestMapping("/wx/follow/index")
    public ModelAndView my() {
        ModelAndView mav = new ModelAndView("weixin/follow/index");
        User user = Mvc.context().sGet(User.SESSION_KEY);
        mav.addObject("follows", userFollowService.query(user.getId(), 0, null));
        return mav;
    }

    @ResponseBody
    @RequestMapping("/wx/follow/more")
    public Result<HtmlPage> more(@RequestParam("id") int id, @RequestParam("createTime") Date createTime) {
        User user = Mvc.context().sGet(User.SESSION_KEY);
        List<UserFollow> follows = userFollowService.query(user.getId(), id, createTime);
        String html = beetlHtmlRender.render("weixin/follow/tpl_list", new ParamMap<>("follows", follows));
        HtmlPage htmlPage = new HtmlPage();
        if (follows.size() > 0) {
            htmlPage.setRecord(follows.get(follows.size() - 1));
        }
        htmlPage.setRecordCount(follows.size());
        htmlPage.setHtml(html);
        return new Result<>(htmlPage);
    }
}
