package com.myself.newsapp.guidance.model.bean;

/**
 * Created by Administrator on 2016/8/8.
 */
public class GuidancePicture {
    private int picture;
    private String title;
    private String lable;

    public GuidancePicture(int picture, String title, String lable) {
        this.picture = picture;
        this.title = title;
        this.lable = lable;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }
}
