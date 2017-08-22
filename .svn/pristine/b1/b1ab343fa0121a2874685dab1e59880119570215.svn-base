package com.ceyi.project.dcjewelry.article;

import pw.wecode.project.framework.jdbc.Column;
import pw.wecode.project.framework.jdbc.NonColumn;
import pw.wecode.project.framework.jdbc.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lingh on 2017/4/6.
 */
@Table("article_category")
public class ArticleCategory {
    @Column(primaryKey = true, genrated = true)
    private int id;
    private int pid;
    private String name;
    private String icon;
    private int pos;
    @NonColumn
    private List<ArticleCategory> articleCategoryList = new ArrayList<>();
    @NonColumn
    private boolean subscribed;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public List<ArticleCategory> getArticleCategoryList() {
        return articleCategoryList;
    }

    public void setArticleCategoryList(List<ArticleCategory> articleCategoryList) {
        this.articleCategoryList = articleCategoryList;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    @Override
    public String toString() {
        return "ArticleCategory{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", pos=" + pos +
                ", articleCategoryList=" + articleCategoryList +
                ", type=" + type +
                '}';
    }
}
