package com.ceyi.project.dcjewelry.comment;


import com.ceyi.project.dcjewelry.article.ArticleComment;
import com.ceyi.project.dcjewelry.article.ArticleService;
import com.ceyi.project.dcjewelry.message.Message;
import com.ceyi.project.dcjewelry.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pw.wecode.project.framework.web.Mvc;
import pw.wecode.project.framework.web.Result;

import javax.inject.Inject;
import java.util.Date;

/**
 * Created by Dell on 2017/7/3.
 */
@Controller
@RequestMapping("/wx/comment/")
public class CommentController {
    @Inject
    private CommentService commentService;
    @Inject
    private ArticleService articleServie;
    @RequestMapping("save")
    @ResponseBody
    public Boolean  save(@RequestParam("content") String content, @RequestParam("aid") String  articleId) {
        User user = Mvc.context().sGet(User.SESSION_KEY);
        int count=commentService.comment(user.getId(),Integer.parseInt(articleId),content);
        return articleServie.addComment(user.getId(),Integer.parseInt(articleId),content,count);
    }

}
