package com.ceyi.project.dcjewelry.admin.user;

import com.ceyi.project.dcjewelry.admin.DatatablesVo;

/**
 * Created by lingh on 2017/4/6.
 */
public class UserSearchVo extends DatatablesVo {
    private String keyword;
    private int type;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserSearchVo{" +
                "keyword='" + keyword + '\'' +
                ", type=" + type +
                '}';
    }
}
