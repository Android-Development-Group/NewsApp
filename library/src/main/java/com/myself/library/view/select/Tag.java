package com.myself.library.view.select;

import java.io.Serializable;

/**
 * 标签实体
 * Created by guchenkai on 2015/12/4.
 */
public class Tag implements Serializable {
    private String id;
    private String parent_id;
    private String text;
    private String icon;
    private boolean isEnable = true;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id='" + id + '\'' +
                ", parent_id='" + parent_id + '\'' +
                ", text='" + text + '\'' +
                ", icon='" + icon + '\'' +
                ", isEnable=" + isEnable +
                '}';
    }
}
