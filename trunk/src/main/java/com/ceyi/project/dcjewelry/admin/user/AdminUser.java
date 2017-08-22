package com.ceyi.project.dcjewelry.admin.user;

import pw.wecode.project.framework.jdbc.Table;
import pw.wecode.project.framework.utils.RandUtils;

import java.util.Date;

/**
 * Created by lingh on 2017/4/5.
 */
@Table("admin_users")
public class AdminUser {
    public static String SESSION_KEY = "login_admin";
    private int id;
    private String username;
    private String phone;
    private String password;
    private String salt;
    private Date createTime;
    private Date updateTime;

    public AdminUser() {
        this.salt = RandUtils.randStr(4);
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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
        return "AdminUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
