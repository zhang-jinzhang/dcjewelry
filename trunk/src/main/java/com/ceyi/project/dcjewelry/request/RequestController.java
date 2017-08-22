package com.ceyi.project.dcjewelry.request;

import com.ceyi.project.dcjewelry.merchant.Merchant;
import com.ceyi.project.dcjewelry.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pw.wecode.project.framework.jdbc.ParamMap;
import pw.wecode.project.framework.utils.StringUtils;
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
public class RequestController {
    @Inject
    private RequestService requestService;
    @Inject
    private BeetlHtmlRender beetlHtmlRender;

    @RequestMapping("/wx/request/index")
    public ModelAndView my() {
        User user = Mvc.context().sGet(User.SESSION_KEY);
        if (user.getMerchant() == null || user.getMerchant().getStatus() != Merchant.STATUS_OK) {
            return new ModelAndView("redirect:/wx/user/auth.html");
        }
        ModelAndView mav = new ModelAndView("weixin/request/index");
        mav.addObject("requests", requestService.query(user.getId(), 0, null));
        return mav;
    }

    @ResponseBody
    @RequestMapping("/wx/request/more")
    public Result<HtmlPage> more(@RequestParam("id") int id, @RequestParam("createTime") Date createTime) {
        User user = Mvc.context().sGet(User.SESSION_KEY);
        List<Request> requests = requestService.query(user.getId(), id, createTime);
        String html = beetlHtmlRender.render("weixin/request/tpl_request_list", new ParamMap<>("requests", requests));
        HtmlPage htmlPage = new HtmlPage();
        if (requests.size() > 0) {
            htmlPage.setRecord(requests.get(requests.size() - 1));
        }
        htmlPage.setRecordCount(requests.size());
        htmlPage.setHtml(html);
        return new Result<>(htmlPage);
    }

    @ResponseBody
    @RequestMapping("/wx/request/create")
    public Result create(Request request) {
        User user = Mvc.context().sGet(User.SESSION_KEY);
        request.setUid(user.getId());
        if (!StringUtils.hasText(request.getContent())) {
            return new Result(Result.CODE_ERROR, "请输入需求内容");
        }
        requestService.create(request);
        return new Result();
    }
}
