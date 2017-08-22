package com.ceyi.project.dcjewelry.comment;

import com.ceyi.project.dcjewelry.article.Article;
import com.ceyi.project.dcjewelry.user.User;
import pw.wecode.project.framework.jdbc.NonColumn;
import pw.wecode.project.framework.jdbc.Table;

import java.util.Date;

/**
 * Created by Dell on 2017/7/3.
 */
@Table("article_comment")
public class Comment {
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

    public int getUid() {
        return uid;
    }

    public int getAid() {
        return aid;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public User getUser() {
        return user;
    }

    public Article getArticle() {
        return article;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
