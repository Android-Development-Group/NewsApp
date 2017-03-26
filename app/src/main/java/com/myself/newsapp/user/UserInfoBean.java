package com.myself.newsapp.user;

import java.io.Serializable;

/**
 * UserInfo
 * Created by Jusenr on 2017/3/26.
 */

public class UserInfoBean implements Serializable {
    private String head_img;//头像图片名
    private String nick_name;//昵称
    private String gender;//性别
    private String region;//地区
    private String email;//Email
    private String password;//密码

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "head_img='" + head_img + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", gender='" + gender + '\'' +
                ", region='" + region + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
