package com.ceyi.project.dcjewelry.notice;

import com.ceyi.project.dcjewelry.admin.DatatablesPage;
import com.ceyi.project.dcjewelry.admin.DatatablesVo;
import com.ceyi.project.dcjewelry.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pw.wecode.project.framework.jdbc.Page;
import pw.wecode.project.framework.jdbc.ParamMap;
import pw.wecode.project.framework.web.HtmlPage;
import pw.wecode.project.framework.web.Mvc;
import pw.wecode.project.framework.web.Result;
import pw.wecode.project.framework.web.beetl.BeetlHtmlRender;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Created by lingh on 2017/5/5.
 */
@Controller
public class NoticeController {
    @Inject
    private NoticeService noticeService;
    @Inject
    private BeetlHtmlRender beetlHtmlRender;

    @RequestMapping("/admin/notice/index")
    public String index() {
        return "admin/notice/index";
    }

    @RequestMapping("/admin/notice/query")
    @ResponseBody
    public DatatablesPage<Notice> query(DatatablesVo datatablesVo) {
        Page page = new Page();
        if (datatablesVo.getLength() > 0) {
            page.setPageSize(datatablesVo.getLength());
        }
        if (datatablesVo.getStart() > 0) {
            page.setPageNumber((datatablesVo.getStart() + page.getPageSize() - 1) / page.getPageSize() + 1);
        }
        return new DatatablesPage<>(noticeService.query(page));
    }

    @RequestMapping("/admin/notice/save")
    @ResponseBody
    public Result save(@RequestParam("content") String content) {
        Notice notice = new Notice();
        notice.setContent(content);
        noticeService.save(notice);
        return new Result();
    }

    @RequestMapping("/admin/notice/create")
    public String create() {
        return "admin/notice/create";
    }

    @ResponseBody
    @RequestMapping("/admin/notice/delete")
    public Result delete(@RequestParam("ids[]") int[] ids) {
        noticeService.delete(ids);
        return new Result();
    }

    @RequestMapping("/wx/notice/index")
    public ModelAndView my() {
        ModelAndView mav = new ModelAndView("weixin/notice/index");
        User user = Mvc.context().sGet(User.SESSION_KEY);
        mav.addObject("notices", noticeService.query(user.getId(), 0, null));
        return mav;
    }

    @ResponseBody
    @RequestMapping("/wx/notice/read")
    public Result read(@RequestParam("id") int id) {
        User user = Mvc.context().sGet(User.SESSION_KEY);
        noticeService.read(user.getId(), id);
        return new Result();
    }

    @ResponseBody
    @RequestMapping("/wx/notice/more")
    public Result<HtmlPage> more(@RequestParam("id") int id, @RequestParam("updateTime") Date updateTime) {
        User user = Mvc.context().sGet(User.SESSION_KEY);
        List<Notice> notices = noticeService.query(user.getId(), id, updateTime);
        String html = beetlHtmlRender.render("weixin/notice/tpl_notice_list", new ParamMap<>("notices", notices));
        HtmlPage htmlPage = new HtmlPage();
        if (notices.size() > 0) {
            htmlPage.setRecord(notices.get(notices.size() - 1));
        }
        htmlPage.setRecordCount(notices.size());
        htmlPage.setHtml(html);
        return new Result<>(htmlPage);
    }
    @ResponseBody
    @RequestMapping("/admin/notice/checkboxDelete")
    public Result checkboxDelete(@RequestParam("ids") String ids) {
        String[] str = ids.split(",");
        int id[] = new int[str.length];
        for(int i=0;i<str.length;i++){
            id[i]=Integer.parseInt(str[i]);
        }
        noticeService.delete(id);
        return new Result();
    }
}
