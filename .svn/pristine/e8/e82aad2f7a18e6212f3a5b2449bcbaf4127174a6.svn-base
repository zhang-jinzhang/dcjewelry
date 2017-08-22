package com.ceyi.project.dcjewelry.article;
import com.ceyi.project.dcjewelry.user.User;
import com.ceyi.project.dcjewelry.user.UserService;
import org.springframework.stereotype.Component;
import pw.wecode.project.framework.jdbc.Dao;
import pw.wecode.project.framework.jdbc.ParamMap;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by lingh on 2017/5/4.
 */
@Component
public class ArticleCommentService {
    @Inject
    private Dao dao;
    @Inject
    private UserService userService;
    @Inject
    private ArticleService articleService;

    public int view(int uid, int aid) {
        ArticleComment articleComment = dao.get(ArticleComment.class, new ParamMap<String, Object>("uid", uid).add("aid", aid).add("type", ArticleComment.TYPE_VIEW));
        if (articleComment != null) {
            return 0;
        }
        articleComment = new ArticleComment();
        articleComment.setUid(uid);
        articleComment.setAid(aid);
        articleComment.setType(ArticleComment.TYPE_VIEW);
        articleComment.setCreateTime(new Date());
        return dao.create(articleComment);
    }
    public int comment(int uid, int aid, String content) {
        ArticleComment articleComment = new ArticleComment();
        articleComment.setUid(uid);
        articleComment.setAid(aid);
        articleComment.setContent(content);
        articleComment.setType(ArticleComment.TYPE_COMMENT);
        articleComment.setCreateTime(new Date());
        return dao.create(articleComment);
    }

    public int like(int uid, int aid) {
        ArticleComment articleComment = dao.get(ArticleComment.class, new ParamMap<String, Object>("uid", uid).add("aid", aid).add("type", ArticleComment.TYPE_LIKE));
        if (articleComment != null) {
            return 0;
        }
        articleComment = new ArticleComment();
        articleComment.setUid(uid);
        articleComment.setAid(aid);
        articleComment.setType(ArticleComment.TYPE_LIKE);
        articleComment.setCreateTime(new Date());
        return dao.create(articleComment);
    }

    public int unlike(int uid, int aid) {
        ArticleComment articleComment = new ArticleComment();
        articleComment.setUid(uid);
        articleComment.setAid(aid);
        articleComment.setType(ArticleComment.TYPE_LIKE);
        return dao.delete(articleComment, true);
    }

    public int favorite(int uid, int aid) {
        ArticleComment articleComment = dao.get(ArticleComment.class, new ParamMap<String, Object>("uid", uid).add("aid", aid).add("type", ArticleComment.TYPE_FAVORITE));
        if (articleComment != null) {
            return 0;
        }
        articleComment = new ArticleComment();
        articleComment.setUid(uid);
        articleComment.setAid(aid);
        articleComment.setType(ArticleComment.TYPE_FAVORITE);
        articleComment.setCreateTime(new Date());
        return dao.create(articleComment);
    }

    public int share(int uid, int aid) {
        ArticleComment articleComment = dao.get(ArticleComment.class, new ParamMap<String, Object>("uid", uid).add("aid", aid).add("type", ArticleComment.TYPE_SHARE));
        if (articleComment != null) {
            return 0;
        }
        articleComment = new ArticleComment();
        articleComment.setUid(uid);
        articleComment.setAid(aid);
        articleComment.setType(ArticleComment.TYPE_SHARE);
        articleComment.setCreateTime(new Date());
        return dao.create(articleComment);
    }

    public int unFavorite(int uid, int aid) {
        ArticleComment articleComment = new ArticleComment();
        articleComment.setUid(uid);
        articleComment.setAid(aid);
        articleComment.setType(ArticleComment.TYPE_FAVORITE);
        return dao.delete(articleComment, true);
    }

    public List<ArticleComment> query(int aid, int type) {
        List<ArticleComment> articleComments = dao.query(ArticleComment.class, new ParamMap<String, Object>("aid", aid).add("type", type));
        if (articleComments.size() == 0) {
            return articleComments;
        }
        Set<String> uidSet = new HashSet<>();
        for (ArticleComment articleComment : articleComments) {
            uidSet.add(articleComment.getUid() + "");
        }
        Map<Integer, User> userMap = userService.loadByUids(uidSet);
        for (ArticleComment articleComment : articleComments) {
            articleComment.setUser(userMap.get(articleComment.getUid()));
        }
        return articleComments;
    }

    public int countFavorite(int uid) {
        return dao.count(ArticleComment.class, new ParamMap<String, Object>("uid", uid).add("type", ArticleComment.TYPE_FAVORITE));
    }

    public ArticleComment get(int aid, int uid, int type) {
        return dao.get(ArticleComment.class, new ParamMap<>("aid", aid).add("uid", uid).add("type", type));
    }

    public List<ArticleComment> listFavorite(int uid, int id, Date createTime) {
        ParamMap<String, Object> paramMap = new ParamMap("uid", uid);
        StringBuilder sql = new StringBuilder("SELECT * FROM article_comment WHERE uid = :uid AND type=4");
        if (id > 0) {
            sql.append(" AND ((id < :id AND createTime = :createTime) OR (createTime < :createTime))");
            paramMap.add("id", id).add("createTime", createTime);
        }
        sql.append(" ORDER BY createTime DESC, id DESC");
        List<ArticleComment> articleComments = dao.query(ArticleComment.class, sql.toString(), paramMap);
        Set<String> uidSet = new HashSet<>();
        Set<String> aidSet = new HashSet<>();
        for (ArticleComment articleComment : articleComments) {
            aidSet.add(articleComment.getAid() + "");
            uidSet.add(articleComment.getUid() + "");
        }
        Map<Integer, Article> articleMap = articleService.loadByIds(aidSet);
        Map<Integer, User> userMap = userService.loadByUids(uidSet);
        for (ArticleComment articleComment : articleComments) {
            articleComment.setArticle(articleMap.get(articleComment.getAid()));
            articleComment.setUser(userMap.get(articleComment.getUid()));
        }
        return articleComments;
    }

    public void deleteByAid(int aid) {
        ArticleComment articleComment = new ArticleComment();
        articleComment.setAid(aid);
        dao.delete(articleComment, true);
    }

}
