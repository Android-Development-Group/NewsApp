package com.myself.library.base;

import java.io.Serializable;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Email      : jusenr@163.com
 * Company    : 葡萄科技
 * Author     : Jusenr
 * Date       : 2016/9/28 10:35.
 */

public class FirInfoBean implements Serializable {
//    {
//        "binary": {
//        "fsize": 8722240
//    },
//        "build": "5",
//        "changelog": "3.1.7",
//        "direct_install_url": "http://download.fir.im/v2/app/install/57e220adca87a8209a001cee?download_token=18f02ce4dbbcd7e252f3e7c508162880",
//        "installUrl": "http://download.fir.im/v2/app/install/57e220adca87a8209a001cee?download_token=18f02ce4dbbcd7e252f3e7c508162880",
//        "install_url": "http://download.fir.im/v2/app/install/57e220adca87a8209a001cee?download_token=18f02ce4dbbcd7e252f3e7c508162880",
//        "name": "RxJava Samsples",
//        "update_url": "http://fir.im/lf6p",
//        "updated_at": 1474892873,
//        "version": "5",
//        "versionShort": "3.1.7"
//    }

    private BinaryBean binary;              //更新文件的对象，仅有大小字段fsize
    private String build;                   //编译号
    private String changelog;               //更新日志
    private String direct_install_url;      //安装地址
    private String installUrl;              //安装地址
    private String install_url;             //安装地址
    private String name;                    //应用名称
    private String update_url;              //更新地址
    private long updated_at;                //更新时间
    private String version;                 //版本
    private String versionShort;            //版本编号

    public BinaryBean getBinary() {
        return binary;
    }

    public void setBinary(BinaryBean binary) {
        this.binary = binary;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public String getDirect_install_url() {
        return direct_install_url;
    }

    public void setDirect_install_url(String direct_install_url) {
        this.direct_install_url = direct_install_url;
    }

    public String getInstallUrl() {
        return installUrl;
    }

    public void setInstallUrl(String installUrl) {
        this.installUrl = installUrl;
    }

    public String getInstall_url() {
        return install_url;
    }

    public void setInstall_url(String install_url) {
        this.install_url = install_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdate_url() {
        return update_url;
    }

    public void setUpdate_url(String update_url) {
        this.update_url = update_url;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersionShort() {
        return versionShort;
    }

    public void setVersionShort(String versionShort) {
        this.versionShort = versionShort;
    }

    @Override
    public String toString() {
        return "FirInfoBean{" +
                "binary=" + binary +
                ", build='" + build + '\'' +
                ", changelog='" + changelog + '\'' +
                ", direct_install_url='" + direct_install_url + '\'' +
                ", installUrl='" + installUrl + '\'' +
                ", install_url='" + install_url + '\'' +
                ", name='" + name + '\'' +
                ", update_url='" + update_url + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", version='" + version + '\'' +
                ", versionShort='" + versionShort + '\'' +
                '}';
    }

    public class BinaryBean implements Serializable {
        private long fsize;

        public long getFsize() {
            return fsize;
        }

        public void setFsize(long fsize) {
            this.fsize = fsize;
        }

        @Override
        public String toString() {
            return "BinaryBean{" +
                    "fsize=" + fsize +
                    '}';
        }
    }
}
