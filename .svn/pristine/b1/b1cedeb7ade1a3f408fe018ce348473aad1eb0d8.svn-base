package com.ceyi.project.dcjewelry.banner;

import pw.wecode.project.framework.jdbc.Table;

import java.util.Date;

/**
 * Created by lingh on 2017/4/7.
 */
@Table("banner")
public class Banner {
    public static final int STATUS_NEW = 0;
    public static final int STATUS_ON = 1;
    public static final int STATUS_OFF = 2;
    private int id;
    private String title;
    private String pic;
    private String url;
    private int pos;
    private int status;
    private Date createTime;
    private Date updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
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

    @Override
    public String toString() {
        return "Banner{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", pic='" + pic + '\'' +
                ", url='" + url + '\'' +
                ", pos=" + pos +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
