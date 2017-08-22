package com.ceyi.project.dcjewelry.admin.user;

/**
 * Created by lingh on 2017/5/7.
 */
public class PointConfig {
    private int article; // 发布资讯
    private int view; // 浏览
    private int share; // 分享资讯
    private int like;  // 点赞
    private int comment;  // 评论
    private int favorite; // 收藏
    private int invite; // 推荐注册

    public int getArticle() {
        return article;
    }

    public void setArticle(int article) {
        this.article = article;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getInvite() {
        return invite;
    }

    public void setInvite(int invite) {
        this.invite = invite;
    }

    @Override
    public String toString() {
        return "PointConfig{" +
                "article=" + article +
                ", share=" + share +
                ", like=" + like +
                ", comment=" + comment +
                ", favorite=" + favorite +
                ", view=" + view +
                ", invite=" + invite +
                '}';
    }
}
