package com.ceyi.project.dcjewelry.user;

import pw.wecode.project.framework.jdbc.Column;
import pw.wecode.project.framework.jdbc.Table;

import java.util.Date;

/**
 * Created by lingh on 2017/4/10.
 */
@Table("user_his")
public class UserHis {
    public static final String TYPE_DOWNLOAD = "下载原图";
    public static final String TYPE_CREATE = "发布资讯";
    public static final String TYPE_VIEW = "阅读资讯";
    public static final String TYPE_LIKE = "点赞资讯";
    public static final String TYPE_COMMENT = "评论资讯";
    public static final String TYPE_FAVORITE = "收藏资讯";
    public static final String TYPE_SHARE = "分享资讯";
    public static final String TYPE_INVITE = "邀请用户";
    public static final String TYPE_VIDEODOWNLOAD ="下载视频";

    @Column(primaryKey = true, genrated = true)
    private int id;
    private int uid;
    private int point;
    private int amount;
    private String type;
    private int tid;
    private Date createTime;

    public UserHis() {
        this.createTime = new Date();
    }

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

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserHis{" +
                "id=" + id +
                ", uid=" + uid +
                ", point=" + point +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", tid=" + tid +
                ", createTime=" + createTime +
                '}';
    }
}
