package com.ceyi.project.dcjewelry.admin.user;

/**
 * Created by lingh on 2017/5/7.
 */
public class Level {
    private int level;
    private int point;

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

    @Override
    public String toString() {
        return "Level{" +
                "level=" + level +
                ", point=" + point +
                '}';
    }
}
