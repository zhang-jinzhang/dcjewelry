package com.ceyi.project.dcjewelry.follow;

import com.ceyi.project.dcjewelry.user.User;
import pw.wecode.project.framework.jdbc.Column;
import pw.wecode.project.framework.jdbc.NonColumn;
import pw.wecode.project.framework.jdbc.Table;

import java.util.Date;

/**
 * Created by lingh on 2017/5/2.
 */
@Table("user_follow")
public class UserFollow {
    @Column(primaryKey = true, genrated = false)
    private int uid;
    @Column(primaryKey = true, genrated = false)
    private int followerUid;
    private Date createTime;
    @NonColumn
    private User user;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getFollowerUid() {
        return followerUid;
    }

    public void setFollowerUid(int followerUid) {
        this.followerUid = followerUid;
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

    @Override
    public String toString() {
        return "UserFollow{" +
                "uid=" + uid +
                ", followerUid=" + followerUid +
                ", createTime=" + createTime +
                '}';
    }
}
