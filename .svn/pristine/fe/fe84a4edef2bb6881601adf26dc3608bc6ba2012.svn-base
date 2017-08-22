package com.ceyi.project.dcjewelry.article;

import com.ceyi.project.dcjewelry.user.User;
import pw.wecode.project.framework.jdbc.NonColumn;
import pw.wecode.project.framework.jdbc.Table;

import java.util.Date;

/**
 * Created by lingh on 2017/5/4.
 */
@Table("article_comment")
public class ArticleComment {
    public static final int TYPE_VIEW = 1;
    public static final int TYPE_COMMENT = 2;
    public static final int TYPE_LIKE = 3;
    public static final int TYPE_FAVORITE = 4;
    public static final int TYPE_SHARE = 5;
    private int id;
    private int uid;
    private int aid;
    private String content;
    private int type;
    private Date createTime;

    @NonColumn
    private User user;
    @NonColumn
    private Article article;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public String toString() {
        return "ArticleComment{" +
                "id=" + id +
                ", uid=" + uid +
                ", aid=" + aid +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                '}';
    }
}
