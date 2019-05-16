package me.djc.gruduatedaily.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 打卡
 */
@Entity(tableName = "tbl_ding")
public class Ding {
    @PrimaryKey(autoGenerate = true)
    private long id;
    /**
     * 打卡时间
     */
    private long dateTime;
    /**
     * 打卡备注
     */
    private String mark;
    /**
     * 打卡 照片
     */
    private String picture;

    public long getId() {
        return id;
    }

    public void setId(long eId) {
        id = eId;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long eDateTime) {
        dateTime = eDateTime;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String eMark) {
        mark = eMark;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String ePicture) {
        picture = ePicture;
    }
}
