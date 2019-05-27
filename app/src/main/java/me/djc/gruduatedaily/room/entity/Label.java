package me.djc.gruduatedaily.room.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 标签
 */
@Entity(tableName = "tbl_label")
public class Label {
    /**
     * 主键ID
     */
    @PrimaryKey(autoGenerate = true)
    private long id;
    /**
     * 标签颜色
     */
    private int clolor;
    /**
     * 标签内容
     */
    private String content;

    private String res;

    private boolean enable;

    public Label() {
    }

    public Label(long id, int eClolor, String eContent, String eRes, boolean eEnable) {
        this.id = id;
        clolor = eClolor;
        content = eContent;
        res = eRes;
        enable = eEnable;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean eEnable) {
        enable = eEnable;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String eRes) {
        res = eRes;
    }

    public long getId() {
        return id;
    }

    public void setId(long eId) {
        id = eId;
    }

    public int getClolor() {
        return clolor;
    }

    public void setClolor(int eClolor) {
        clolor = eClolor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String eContent) {
        content = eContent;
    }

    @NonNull
    @Override
    public String toString() {
        return content;
    }
}
