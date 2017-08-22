package com.ceyi.project.dcjewelry.user;

import com.ceyi.project.dcjewelry.merchant.Merchant;
import pw.wecode.project.framework.jdbc.Column;
import pw.wecode.project.framework.jdbc.NonColumn;
import pw.wecode.project.framework.jdbc.Table;
import pw.wecode.project.framework.utils.Md5Utils;
import pw.wecode.project.framework.utils.RandUtils;

import java.util.Date;

/**
 * Created by lingh on 2017/3/29.
 */
@Table("users")
public class User {
    public static final String SESSION_KEY = "login_user";
    public static final String COOKIE_TOKEN = "ltoken";
    public static final int SEX_UNKNOWN = 0;
    public static final int SEX_MALE = 1;
    public static final int SEX_FEMALE = 2;
    public static final int SUBSCRIBE_NO = 0;
    public static final int UBSCRIBE_YES = 1;
    public static final int TYPE_ALL = 0;
    public static final int TYPE_USER = 1;
    public static final int TYPE_MERCHANT = 2;
    public static final int ADMIN_UID = 1;

    @Column(primaryKey = true, genrated = true)
    private int id;
    private String openid;
    private String phone;
    private String salt;
    private String nickname;
    private String headimg;
    private String bgUrl;
    private int level;
    private int point;
    private String jobTitle;
    private String signature;
    private String address;
    private int sex;
    private int subscribe;
    private Date createTime;
    private Date updateTime;
    private String wechatId;

    @NonColumn
    private Merchant merchant;
    @NonColumn
    private boolean followed;
    @NonColumn
    private int followCount;
    @NonColumn
    private int favoriteCount;

    public User() {
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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getBgUrl() {
        return bgUrl;
    }

    public void setBgUrl(String bgUrl) {
        this.bgUrl = bgUrl;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(int subscribe) {
        this.subscribe = subscribe;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getLoginToken() {
        Date now = new Date();
        return now.getTime() + "-" + this.id + "-" + Md5Utils.hash(now.getTime() + "-" + this.id + "-" + this.salt);
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public String getWechatId() { return wechatId; }

    public void setWechatId(String wechatId) { this.wechatId = wechatId; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", openid='" + openid + '\'' +
                ", phone='" + phone + '\'' +
                ", salt='" + salt + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headimg='" + headimg + '\'' +
                ", bgUrl='" + bgUrl + '\'' +
                ", level=" + level +
                ", point=" + point +
                ", jobTitle='" + jobTitle + '\'' +
                ", signature='" + signature + '\'' +
                ", address='" + address + '\'' +
                ", sex=" + sex +
                ", subscribe=" + subscribe +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", merchant=" + merchant +
                ", followed=" + followed +
                ", followCount=" + followCount +
                ", favoriteCount=" + favoriteCount +
                ", wechatId=" + wechatId +
                '}';
    }
}
