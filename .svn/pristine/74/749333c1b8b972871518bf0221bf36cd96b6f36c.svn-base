package com.ceyi.project.dcjewelry.comment;

import com.ceyi.project.dcjewelry.article.ArticleComment;
import com.ceyi.project.dcjewelry.article.ArticleService;
import com.ceyi.project.dcjewelry.config.ConfigService;
import com.ceyi.project.dcjewelry.message.Message;
import com.ceyi.project.dcjewelry.user.UserService;
import com.google.gson.Gson;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pw.wecode.project.framework.jdbc.Dao;
import pw.wecode.project.framework.weixin.WeixinService;

import javax.inject.Inject;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Dell on 2017/7/3.
 */
@Component
public class CommentService {
    @Inject
    private Dao dao;
    @Inject
    private UserService userService;
    @Inject
    private ArticleService articleServie;
    public int comment(int uid, int aid, String content) {
        ArticleComment articleComment = new ArticleComment();
        articleComment.setUid(uid);
        articleComment.setAid(aid);
        articleComment.setContent(content);
        articleComment.setType(ArticleComment.TYPE_COMMENT);
        articleComment.setCreateTime(new Date());
        return dao.create(articleComment);
    }
}
