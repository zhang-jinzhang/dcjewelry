package com.ceyi.project.dcjewelry.message;

import com.ceyi.project.dcjewelry.user.User;
import pw.wecode.project.framework.jdbc.NonColumn;
import pw.wecode.project.framework.jdbc.Table;

import java.util.Date;

/**
 * Created by lingh on 2017/5/2.
 */
@Table("private_message")
public class Message {
    public static final int STATUS_UNREAD = 0;
    public static final int STATUS_READ = 1;
    private int id;
    private int fromUid;
    private int toUid;
    private String content;
    private int status;
    private Date createTime;
    private Date updateTime;
    private String img;

    @NonColumn
    private User fromUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromUid() {
        return fromUid;
    }

    public void setFromUid(int fromUid) {
        this.fromUid = fromUid;
    }

    public int getToUid() {
        return toUid;
    }

    public void setToUid(int toUid) {
        this.toUid = toUid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", fromUid=" + fromUid +
                ", toUid=" + toUid +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", img=" + img +
                '}';
    }
}
