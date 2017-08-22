package com.ceyi.project.dcjewelry.article;

import com.ceyi.project.dcjewelry.admin.user.PointConfigService;
import com.ceyi.project.dcjewelry.comment.CommentService;
import com.ceyi.project.dcjewelry.config.Config;
import com.ceyi.project.dcjewelry.config.ConfigService;
import com.ceyi.project.dcjewelry.follow.UserFollow;
import com.ceyi.project.dcjewelry.follow.UserFollowService;
import com.ceyi.project.dcjewelry.index.AboutUs;
import com.ceyi.project.dcjewelry.merchant.Merchant;
import com.ceyi.project.dcjewelry.picture.PictureService;
import com.ceyi.project.dcjewelry.user.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pw.wecode.project.framework.common.SyncInfo;
import pw.wecode.project.framework.jdbc.Dao;
import pw.wecode.project.framework.jdbc.OrderList;
import pw.wecode.project.framework.jdbc.Page;
import pw.wecode.project.framework.jdbc.ParamMap;
import pw.wecode.project.framework.utils.StringUtils;
import pw.wecode.project.framework.weixin.TemplateMessage;
import pw.wecode.project.framework.weixin.WeixinService;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by lingh on 2017/4/6.
 */
@Component
public class ArticleService {
    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);
    private static final String CONFIG_NEW_ARTICLE_NOTIFY = "新资讯提醒:";
    @Value("${tid}")
    private String tid;
    @Value("${host}")
    private String host;
    @Inject
    private Dao dao;
    @Inject
    private UserService userService;
    @Inject
    private UserFollowService userFollowService;
    @Inject
    private ArticleCommentService articleCommentService;
    @Inject
    private ArticleCategoryService articleCategoryService;
    @Inject
    private PointConfigService pointConfigService;
    @Inject
    private UserHisService userHisService;
    @Inject
    private UserCategoryService userCategoryService;
    @Inject
    private PictureService pictureService;
    @Inject
    private ConfigService configService;
    @Inject
    private Gson gson;
    @Inject
    private WeixinService weixinService;
    @Inject
    private CommentService commentService;

    public Page<Article> search(int uid, SearchRequest searchRequest) {
        return this.search(uid, searchRequest, false);
    }

    public Page<Article> search(int uid, SearchRequest searchRequest, boolean my) {
        ParamMap<String, Object> paramMap = new ParamMap<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM articles WHERE 1=1");
        if (my) {
            sql.append(" AND uid = :uid");
            paramMap.put("uid", uid);
        }
        if (searchRequest.getCid() > 0) {
            ArticleCategory articleCategory = articleCategoryService.get(searchRequest.getCid());
            if (articleCategory.getPid() == 0) {
                sql.append(" AND pid = :pid");
                paramMap.put("pid", articleCategory.getId());
            } else {
                sql.append(" AND pid = :pid AND cid = :cid");
                paramMap.put("pid", articleCategory.getPid());
                paramMap.put("cid", articleCategory.getId());
            }
            if (!StringUtils.hasText(searchRequest.getKeyword()) && !StringUtils.hasText(searchRequest.getTag())) {
                Set<Integer> cidSet = userCategoryService.getSubscribed(uid);
                Set<String> cidStrSet = new HashSet<>(cidSet.size());
                for (int cid : cidSet) {
                    cidStrSet.add(cid + "");
                }
                String cids = StringUtils.join(cidStrSet);
                if (StringUtils.hasText(cids)) {
                    sql.append(" AND cid IN (" + cids + ")");
                }
            }
        }
        if (StringUtils.hasText(searchRequest.getKeyword())) {
            paramMap.put("keyword", "%" + searchRequest.getKeyword() + "%");
            sql.append(" AND (title LIKE :keyword OR content LIKE :keyword)");
        }
        if (StringUtils.hasText(searchRequest.getTag())) {
            paramMap.put("tag", "%," + searchRequest.getTag() + ",%");
            sql.append(" AND tags LIKE :tag");
        }
        if (searchRequest.getId() > 0) {
            if (searchRequest.isOld()) {
                sql.append(" AND ((createTime = :updateTime AND id < :id) OR (createTime < :updateTime))");
            } else {
                sql.append(" AND ((createTime = :updateTime AND id > :id) OR (createTime > :updateTime))");
            }
            paramMap.put("updateTime", searchRequest.getUpdateTime());
            paramMap.put("id", searchRequest.getId());
        }
        String type = searchRequest.getFunctionType();
        if(type != null){
            if(type.equals("资讯")){
                sql.append(" AND functionType = 0 ");
            }else if(type.equals("图库")){
                sql.append(" AND functionType = 1 ");
            }
        }
        sql.append(" ORDER BY createTime DESC, id DESC LIMIT :offset, :pageSize");
        paramMap.put("offset", searchRequest.getOffset());
        paramMap.put("pageSize", searchRequest.getPageSize());
        List<Article> articles = dao.query(Article.class, sql.toString(), paramMap);
        refresh(uid, articles);
        return new Page<Article>(articles.size(), articles);
    }

    public Article get(int id) {
        Article article = dao.get(Article.class, id);
        if (article != null) {
            article.setUser(dao.get(User.class, article.getUid()));
        }
        return article;
    }

    public Page<Article> query(Page<Article> page, int type) {
        int total = dao.count(Article.class);
        page.setRecordCount(total);
        if (total == 0) {
            return page;
        }
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("functionType",type);
        page.setData(dao.query(Article.class, param, new OrderList("updateTime", OrderList.DESC), page));
        Set<String> uidSet = new HashSet<>(page.getData().size());
        for (Article article : page.getData()) {
            uidSet.add(article.getUid() + "");
        }
        Map<Integer, User> userMap = userService.loadByUids(uidSet);
        for (Article article : page.getData()) {
            article.setUser(userMap.get(article.getUid()));
        }
        return page;
    }

    public void querys(Page<Article> page, String nickname, String actitle,int type){
        Map<String, Object> paramMap = new ParamMap<String, Object>("offset", page.getOffset()).add("pageSize", page.getPageSize());
        StringBuilder countSql = new StringBuilder("SELECT COUNT(1) FROM articles a LEFT JOIN users u on a.uid = u.id WHERE 1=1");
        StringBuilder sql = new StringBuilder("SELECT a.*,u.nickname FROM articles a LEFT JOIN users u on a.uid = u.id WHERE 1=1");
        if (StringUtils.hasText(nickname)) {
            countSql.append(" AND (u.nickname LIKE '%"+ nickname + "%')");
            sql.append(" AND (u.nickname LIKE '%"+ nickname + "%')");
        }
        if (StringUtils.hasText(actitle)) {
            countSql.append(" AND (a.title LIKE '%"+ actitle + "%')");
            sql.append(" AND (a.title LIKE '%"+ actitle + "%')");
        }
        if (type == 0) {
            countSql.append(" AND a.functionType = 0");
            sql.append(" AND a.functionType = 0");
        }else if(type == 1){
            countSql.append(" AND a.functionType = 1");
            sql.append(" AND a.functionType = 1");
        }
        sql.append(" Order By a.updateTime Desc"+" LIMIT :offset,:pageSize");
        int total = dao.get(int.class, countSql.toString(), paramMap);
        page.setRecordCount(total);
        if (total == 0) {
            return;
        }

        List<Article> articles = dao.query(sql.toString(), paramMap, new RowMapper<Article>() {
            @Override
            public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
                Article article = new Article();
                article.setId(rs.getInt("id"));
                article.setCid(rs.getInt("cid"));
                article.setCommentCount(rs.getInt("commentCount"));
                article.setContent(rs.getString("content"));
                article.setDownloadCount(rs.getInt("downloadCount"));
                article.setFavoriteCount(rs.getInt("favoriteCount"));
                article.setFirstPic(rs.getString("firstPic"));
                article.setFunctionType(rs.getInt("functionType"));
                article.setLikeCount(rs.getInt("likeCount"));
                article.setPictureIds(rs.getString("pictureIds"));
                article.setPid(rs.getInt("pid"));
                article.setPoint(rs.getInt("point"));
                article.setTags(rs.getString("tags"));
                article.setVideoImg(rs.getString("videoImg"));
                article.setVideoUrl(rs.getString("videoUrl"));
                article.setViewCount(rs.getInt("viewCount"));
                article.setWmId(rs.getInt("wmId"));
                article.setWmPos(rs.getInt("wmPos"));
                article.setCreateTime(rs.getTimestamp("createTime"));
                article.setUpdateTime(rs.getTimestamp("updateTime"));
                article.setTitle(rs.getString("title"));
                User user = new User();
                user.setNickname(rs.getString("nickname"));
                article.setUser(user);

                return article;
            }
        });
        page.setData(articles);
    }

    public void save(Article article) {
        if (article.getId() > 0) {
            article.setUpdateTime(new Date());
            dao.update(article);
        } else {
            dao.create(article);
            // 增加积分
            int point = pointConfigService.get().getArticle();
            if (point > 0) {
                User user = userService.get(article.getUid());
                UserHis userHis = userHisService.get(user.getId(), UserHis.TYPE_CREATE, article.getId());
                if (userHis == null) {
                    userService.incPoint(user, point, UserHis.TYPE_CREATE, article.getId());
                }
            }
        }
    }

    public boolean view(int uid, int aid) {
        if (articleCommentService.view(uid, aid) > 0) {
            this.incCount(aid, "viewCount", "+1");
            return true;
        }
        return false;
    }

    public boolean comment(int uid, int aid, String content) {
        if (articleCommentService.comment(uid, aid, content) > 0) {
            this.incCount(aid, "commentCount", "+1");
            return true;
        }

        return false;
    }

    public boolean like(int uid, int aid) {
        if (articleCommentService.like(uid, aid) > 0) {
            this.incCount(aid, "likeCount", "+1");
            return true;
        }
        return false;
    }

    public boolean unlike(int uid, int aid) {
        if (articleCommentService.unlike(uid, aid) > 0) {
            this.incCount(aid, "likeCount", "-1");
            return true;
        }
        return false;
    }

    public boolean favorite(int uid, int aid) {
        if (articleCommentService.favorite(uid, aid) > 0) {
            this.incCount(aid, "favoriteCount", "+1");
            return true;
        }
        return false;
    }

    public boolean unfavorite(int uid, int aid) {
        if (articleCommentService.unFavorite(uid, aid) > 0) {
            this.incCount(aid, "favoriteCount", "-1");
            return true;
        }
        return false;
    }

    public Map<Integer, Article> loadByIds(Set<String> aidSet) {
        Map<Integer, Article> articleMap = new HashMap<>();
        String aids = StringUtils.join(aidSet);
        if (!StringUtils.hasText(aids)) {
            return articleMap;
        }
        String sql = "SELECT * FROM articles WHERE id IN (" + aids + ")";
        List<Article> articles = dao.query(Article.class, sql);
        for (Article article : articles) {
            articleMap.put(article.getId(), article);
        }
        return articleMap;
    }

    public void delete(int id) {
        articleCommentService.deleteByAid(id);
        Article article = new Article();
        article.setId(id);
        dao.delete(article, true);
    }

    private int incCount(int aid, String field, String number) {
        return dao.update("UPDATE articles SET " + field + " = " + field + " " + number + " WHERE id = :id", new ParamMap<String, Object>("id", aid));
    }

    private void refresh(int uid, List<Article> articles) {
        Set<String> uidSet = new HashSet<>();
        for (Article article : articles) {
            uidSet.add(article.getUid() + "");
        }
        Map<Integer, User> userMap = userService.loadByUids(uidSet);
        for (Article article : articles) {
            article.setUser(userMap.get(article.getUid()));
            article.setPictures(pictureService.getByIds(article.getPictureIds()));
            ArticleComment articleComment = articleCommentService.get(article.getId(), uid, ArticleComment.TYPE_FAVORITE);
            if (articleComment != null) {
                article.setFavorited(true);
            }
            articleComment = articleCommentService.get(article.getId(), uid, ArticleComment.TYPE_LIKE);
            if (articleComment != null) {
                article.setLiked(true);
            }
        }
        Map<Integer, UserFollow> userFollowMap = userFollowService.loadByUids(uid, uidSet);
        for (Article article : articles) {
            User user = article.getUser();
            if (user == null) {
                logger.error("资讯的用户不存在, article:{}", article);
                continue;
            }
            user.setFollowed(userFollowMap.keySet().contains(user.getId()));
        }
    }

    public void incDownload(int id) {
        String sql = "UPDATE articles SET downloadCount = downloadCount + 1 WHERE id = :id";
        dao.update(sql, new ParamMap<String, Object>("id", id));
    }

    public int countDownload() {
        return dao.get(Integer.class, "SELECT SUM(downloadCount) FROM articles");
    }

    public int countAdView() {
        Integer count = dao.get(Integer.class, "SELECT SUM(viewCount) FROM articles WHERE tags LIKE '%,广告,%'");
        if (count == null) {
            count = 0;
        }
        return count;
    }

    public void notifyNewArticle() {
        Date now = new Date();
        List<Article> articles = dao.query(Article.class, new ParamMap<String, Object>(), new OrderList("createTime", OrderList.DESC).add("id", OrderList.DESC), new Page(1, 1000));
        Map<Integer, Article> articleMap = new HashMap<>();
        for (Article article : articles) {
            if (!articleMap.containsKey(article.getUid())) {
                articleMap.put(article.getUid(), article);
            }
        }
        Map<Integer, User> notifyMap = new HashMap<>();
        for (Article article : articleMap.values()) {
            SyncInfo syncInfo = new SyncInfo(article.getId(), new Date(article.getCreateTime().getTime() - 1));
            List<User> users = userService.getFollowers(article.getUid());
            for (User user : users) {
                Config config = configService.get(CONFIG_NEW_ARTICLE_NOTIFY + user.getId());
                if (config != null) {
                    syncInfo = gson.fromJson(config.getValue(), SyncInfo.class);
                }
                if (syncInfo.getTime().getTime() >= article.getCreateTime().getTime()) {
                    continue;
                }
                ArticleComment articleComment = articleCommentService.get(article.getId(), user.getId(), ArticleComment.TYPE_VIEW);
                if (articleComment != null) {
                    continue;
                }
                if (!notifyMap.containsKey(user.getId())) {
                    notifyMap.put(user.getId(), user);
                }
            }
        }

        logger.debug("需要通知{}个用户有新资讯", notifyMap.size());
        for (User user : notifyMap.values()) {
            TemplateMessage templateMessage = new TemplateMessage();
            templateMessage.setTouser(user.getOpenid());
            templateMessage.setTemplate_id(tid);
            templateMessage.setUrl(host + "/wx/index.html");
            Map<String, Object> paramMap = new ParamMap<String, Object>("first", new ParamMap<String, Object>("value", "您关注的发布者，发布新的资讯啦，快去看看").add("color", "#173177"))
                    .add("keyword1", new ParamMap<String, Object>("value", "系统").add("color", "#173177"))
                    .add("keyword2", new ParamMap<String, Object>("value", now).add("color", "#173177"))
                    .add("remark", new ParamMap<String, Object>("value", "点击详情并及时处理").add("color", "#173177"));
            templateMessage.setData(paramMap);
            weixinService.sendTempalteMessage(templateMessage);
            SyncInfo syncInfo = new SyncInfo();
            syncInfo.setTime(now);
            configService.save(new Config(CONFIG_NEW_ARTICLE_NOTIFY + user.getId(), gson.toJson(syncInfo)));
            logger.debug("通知用户有新资讯, templateMessage:{}", templateMessage);
        }
    }

    /**
     * htg 2017.7.2
     * @param uid
     * @param searchRequest
     * @return
     */
    //查询单条资讯记录中的图片张数
    public Map<Integer, Integer> getPicCount(int uid, SearchRequest searchRequest){
        Page<Article> page=search( uid, searchRequest);
        //key为用户id，value为图片张数
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(Article article : page.getData()){
            String str=article.getPictureIds();
            String[] strs=str.split(",");
            int size=strs.length;
            map.put(article.getId(),size);
        }
        return map;
    }
    public boolean addComment(int uid, int aid,String content,int count) {
        if (count > 0) {
            this.incCount(aid, "commentCount", "+1");
            return true;
        }
        return false;
    }

    public void pushArticle(List<Article> articles){
        logger.debug("条数："+articles.size());
        Date now = new Date();
        Map<Integer, Article> articleMap = new HashMap<>();
        for (Article article : articles) {
            if (!articleMap.containsKey(article.getUid())) {
                articleMap.put(article.getUid(), article);
            }
        }
        Map<Integer, User> notifyMap = new HashMap<>();
        for (Article article : articleMap.values()) {
            SyncInfo syncInfo = new SyncInfo(article.getId(), new Date(article.getCreateTime().getTime() - 1));
            List<User> users = userService.getUsers();
            for (User user : users) {
                if(user.getOpenid() == null || user.getOpenid().equals("")){
                    continue;
                }
                Config config = configService.get(CONFIG_NEW_ARTICLE_NOTIFY + user.getId());
                if (config != null) {
                    syncInfo = gson.fromJson(config.getValue(), SyncInfo.class);
                }
                if (syncInfo.getTime().getTime() >= article.getCreateTime().getTime()) {
                    continue;
                }
                ArticleComment articleComment = articleCommentService.get(article.getId(), user.getId(), ArticleComment.TYPE_VIEW);
                if (articleComment != null) {
                    continue;
                }
                if (!notifyMap.containsKey(user.getId())) {
                    notifyMap.put(user.getId(), user);
                }
            }
        }

        logger.debug("需要通知{}个用户有新资讯", notifyMap.size());
        for (User user : notifyMap.values()) {
            TemplateMessage templateMessage = new TemplateMessage();
            templateMessage.setTouser(user.getOpenid());
            templateMessage.setTemplate_id(tid);
            templateMessage.setUrl(host + "/wx/index.html");
            Map<String, Object> paramMap = new ParamMap<String, Object>("first", new ParamMap<String, Object>("value", "系统更新了 "+articles.size()+"条新的资讯，快去看看").add("color", "#173177"))
                    .add("keyword1", new ParamMap<String, Object>("value", "系统").add("color", "#173177"))
                    .add("keyword2", new ParamMap<String, Object>("value", now).add("color", "#173177"))
                    .add("remark", new ParamMap<String, Object>("value", "点击详情并及时处理").add("color", "#173177"));
            templateMessage.setData(paramMap);
            weixinService.sendTempalteMessage(templateMessage);
            SyncInfo syncInfo = new SyncInfo();
            syncInfo.setTime(now);
            configService.save(new Config(CONFIG_NEW_ARTICLE_NOTIFY + user.getId(), gson.toJson(syncInfo)));
            logger.debug("通知用户有新资讯, templateMessage:{}", templateMessage);
        }
    }

    public  void queryStatement(Page<Article> page,int functionType, int type){
        Map<String, Object> paramMap = new ParamMap<String, Object>("offset", page.getOffset()).add("pageSize", page.getPageSize());
        StringBuilder countSql = new StringBuilder("SELECT COUNT(1) FROM articles a LEFT JOIN users u on a.uid = u.id WHERE 1=1");
        StringBuilder sql = new StringBuilder("SELECT a.*,u.nickname FROM articles a LEFT JOIN users u on a.uid = u.id WHERE 1=1");
        if (functionType == 1) {
            countSql.append(" AND a.functionType = 0");
            sql.append(" AND a.functionType = 0");
        }else if(functionType == 2){
            countSql.append(" AND a.functionType = 1");
            sql.append(" AND a.functionType = 1");
        }

        switch (type){
            case 1: sql.append(" Order By a.downloadCount Desc");break;
            case 2: sql.append(" Order By a.viewCount Desc");break;
            case 3: sql.append(" Order By a.commentCount Desc");break;
            case 4: sql.append(" Order By a.likeCount Desc");break;
            case 5: sql.append(" Order By a.favoriteCount Desc");break;
        }

        sql.append(" LIMIT :offset,:pageSize");
        int total = dao.get(int.class, countSql.toString(), paramMap);
        page.setRecordCount(total);
        if (total == 0) {
            return;
        }

        List<Article> articles = dao.query(sql.toString(), paramMap, new RowMapper<Article>() {
            @Override
            public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
                Article article = new Article();
                article.setId(rs.getInt("id"));
                article.setCid(rs.getInt("cid"));
                article.setCommentCount(rs.getInt("commentCount"));
                article.setContent(rs.getString("content"));
                article.setDownloadCount(rs.getInt("downloadCount"));
                article.setFavoriteCount(rs.getInt("favoriteCount"));
                article.setFirstPic(rs.getString("firstPic"));
                article.setFunctionType(rs.getInt("functionType"));
                article.setLikeCount(rs.getInt("likeCount"));
                article.setPictureIds(rs.getString("pictureIds"));
                article.setPid(rs.getInt("pid"));
                article.setPoint(rs.getInt("point"));
                article.setTags(rs.getString("tags"));
                article.setVideoImg(rs.getString("videoImg"));
                article.setVideoUrl(rs.getString("videoUrl"));
                article.setViewCount(rs.getInt("viewCount"));
                article.setWmId(rs.getInt("wmId"));
                article.setWmPos(rs.getInt("wmPos"));
                article.setCreateTime(rs.getTimestamp("createTime"));
                article.setUpdateTime(rs.getTimestamp("updateTime"));
                article.setTitle(rs.getString("title"));
                User user = new User();
                user.setNickname(rs.getString("nickname"));
                article.setUser(user);

                return article;
            }
        });
        page.setData(articles);
    }
}
