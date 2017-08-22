package com.ceyi.project.dcjewelry.message;

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
public class MessageController {
    @Inject
    private MessageService messageService;
    @Inject
    private BeetlHtmlRender beetlHtmlRender;

    @RequestMapping("/wx/message/send")
    @ResponseBody
    public Result send(Message message) {
        User user = Mvc.context().sGet(User.SESSION_KEY);
        message.setFromUid(user.getId());
        messageService.create(message);
        return new Result();
    }

    @RequestMapping("/wx/message/index")
    public ModelAndView my() {
        ModelAndView mav = new ModelAndView("weixin/message/index");
        User user = Mvc.context().sGet(User.SESSION_KEY);
        mav.addObject("messages", messageService.query(user.getId(), 0, null));
        return mav;
    }

    @ResponseBody
    @RequestMapping("/wx/message/read")
    public Result read(@RequestParam("id") int id) {
        User user = Mvc.context().sGet(User.SESSION_KEY);
        messageService.read(id);
        return new Result();
    }

    @ResponseBody
    @RequestMapping("/wx/message/more")
    public Result<HtmlPage> more(@RequestParam("id") int id, @RequestParam("createTime") Date createTime) {
        User user = Mvc.context().sGet(User.SESSION_KEY);
        List<Message> messages = messageService.query(user.getId(), id, createTime);
        String html = beetlHtmlRender.render("weixin/message/tpl_message_list", new ParamMap<>("messages", messages));
        HtmlPage htmlPage = new HtmlPage();
        if (messages.size() > 0) {
            htmlPage.setRecord(messages.get(messages.size() - 1));
        }
        htmlPage.setRecordCount(messages.size());
        htmlPage.setHtml(html);
        return new Result<>(htmlPage);
    }
}
