package com.ceyi.project.dcjewelry.merchant;

import com.ceyi.project.dcjewelry.user.User;
import pw.wecode.project.framework.jdbc.Column;
import pw.wecode.project.framework.jdbc.NonColumn;
import pw.wecode.project.framework.jdbc.Table;

import java.util.Date;

/**
 * Created by lingh on 2017/4/5.
 */
@Table("user_merchant")
public class Merchant {
    public static final int STATUS_NEW = 0;
    public static final int STATUS_OK = 1;
    public static final int STATUS_FAIL = 2;
    @Column(primaryKey = true, genrated = false)
    private int uid;
    private int cid;
    private String name;
    private String area;
    private String boss;
    private String email;
    private String businessPic;
    private int status;
    private Date createTime;
    private Date updateTime;
    @NonColumn
    private User user;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBoss() {
        return boss;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusinessPic() {
        return businessPic;
    }

    public void setBusinessPic(String businessPic) {
        this.businessPic = businessPic;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "uid=" + uid +
                ", cid=" + cid +
                ", name='" + name + '\'' +
                ", area='" + area + '\'' +
                ", boss='" + boss + '\'' +
                ", email='" + email + '\'' +
                ", businessPic='" + businessPic + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
