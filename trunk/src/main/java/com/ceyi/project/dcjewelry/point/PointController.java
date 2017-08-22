package com.ceyi.project.dcjewelry.point;

import com.ceyi.project.dcjewelry.user.User;
import com.ceyi.project.dcjewelry.user.UserHis;
import com.ceyi.project.dcjewelry.user.UserHisService;
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
 * Created by lingh on 2017/5/7.
 */
@Controller
public class PointController {
    @Inject
    private UserHisService userHisService;
    @Inject
    private BeetlHtmlRender beetlHtmlRender;

    @RequestMapping("/wx/point/index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("weixin/point/index");
        User user = Mvc.context().sGet(User.SESSION_KEY);
        mav.addObject("points", userHisService.query(user.getId(), 0, null));
        return mav;
    }

    @ResponseBody
    @RequestMapping("/wx/point/more")
    public Result<HtmlPage> more(@RequestParam("id") int id, @RequestParam("createTime") Date createTime) {
        User user = Mvc.context().sGet(User.SESSION_KEY);
        List<UserHis> points = userHisService.query(user.getId(), id, createTime);
        String html = beetlHtmlRender.render("weixin/point/tpl_list", new ParamMap<>("points", points));
        HtmlPage htmlPage = new HtmlPage();
        if (points.size() > 0) {
            htmlPage.setRecord(points.get(points.size() - 1));
        }
        htmlPage.setRecordCount(points.size());
        htmlPage.setHtml(html);
        return new Result<>(htmlPage);
    }
}
