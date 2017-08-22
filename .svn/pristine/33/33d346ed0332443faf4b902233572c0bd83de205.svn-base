package com.ceyi.project.dcjewelry.notice;

import pw.wecode.project.framework.jdbc.NonColumn;
import pw.wecode.project.framework.jdbc.Table;

import java.util.Date;

/**
 * Created by lingh on 2017/5/5.
 */
@Table("notice")
public class Notice {
    private int id;
    private String content;
    private Date createTime;
    private Date updateTime;
    @NonColumn
    private boolean read;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                ", id=" + id +
                '}';
    }
}
