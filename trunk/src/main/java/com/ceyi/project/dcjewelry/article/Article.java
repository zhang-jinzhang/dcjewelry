package com.ceyi.project.dcjewelry.article;

import com.ceyi.project.dcjewelry.picture.Picture;
import com.ceyi.project.dcjewelry.user.User;
import pw.wecode.project.framework.jdbc.NonColumn;
import pw.wecode.project.framework.jdbc.Table;
import pw.wecode.project.framework.lucene.NonLucene;
import pw.wecode.project.framework.utils.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lingh on 2017/3/29.
 */
@Table("articles")
public class Article {
    public static final String TAG_TT = "头条";
    public static final String TAG_REC = "推荐";
    public static final String TAG_TOP = "置顶";
    public static final String TAG_AD = "广告";
    public static final String TAG_PUSH = "推送";
    public static final int TYPE_PICTEXT = 0;
    public static final int TYPE_TEXT = 1;

    private int id;
    private int uid;
    private int pid;
    private int cid;
    private String title;
    private String content;
    private String firstPic;
    private String pictureIds;
    private int wmId; // 水印编号
    private int wmPos; // 水印位置
    private int viewCount;
    private int likeCount;
    private int favoriteCount;
    private int commentCount;
    private int downloadCount;
    private int type;
    private int point;
    private String videoUrl;
    private String videoImg;
    private int functionType;

    // 标签不分词
    private String tags;
    private Date createTime;
    private Date updateTime;
    @NonColumn
    private User user;
    @NonColumn
    private List<Picture> pictures;
    @NonColumn
    private boolean liked;
    @NonColumn
    private boolean favorited;

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

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstPic() {
        return firstPic;
    }

    public void setFirstPic(String firstPic) {
        this.firstPic = firstPic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPictureIds() {
        return pictureIds;
    }

    public void setPictureIds(String pictureIds) {
        this.pictureIds = pictureIds;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public int getWmId() {
        return wmId;
    }

    public void setWmId(int wmId) {
        this.wmId = wmId;
    }

    public int getWmPos() {
        return wmPos;
    }

    public void setWmPos(int wmPos) {
        this.wmPos = wmPos;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoImg() {
        return videoImg;
    }

    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg;
    }

    public int getFunctionType() { return functionType; }

    public void setFunctionType(int functionType) { this.functionType = functionType; }

    public List<String> getTagList() {
        if (StringUtils.hasText(this.tags)) {
            return StringUtils.split(this.tags, ",");
        } else {
            return new ArrayList<>();
        }
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", uid=" + uid +
                ", cid=" + cid +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", pictureIds='" + pictureIds + '\'' +
                ", wmId=" + wmId +
                ", wmPos=" + wmPos +
                ", viewCount=" + viewCount +
                ", likeCount=" + likeCount +
                ", favoriteCount=" + favoriteCount +
                ", commentCount=" + commentCount +
                ", downloadCount=" + downloadCount +
                ", type=" + type +
                ", point=" + point +
                ", tags='" + tags + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", pid=" + pid +
                ", user=" + user +
                ", videoUrl=" + videoUrl +
                ", videoImg=" + videoImg +
                ", functionType=" + functionType +
                '}';
    }
}
