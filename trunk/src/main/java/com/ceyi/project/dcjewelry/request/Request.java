package com.ceyi.project.dcjewelry.request;

import com.ceyi.project.dcjewelry.user.User;
import pw.wecode.project.framework.jdbc.NonColumn;
import pw.wecode.project.framework.jdbc.Table;

import java.util.Date;

/**
 * Created by lingh on 2017/5/6.
 */
@Table("requests")
public class Request {
    private int id;
    private int uid;
    private String content;
    private String img;
    private Date createTime;

    @NonColumn
    private User user;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", uid=" + uid +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", img=" + img +
                '}';
    }
}
