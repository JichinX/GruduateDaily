package me.djc.gruduatedaily.room.entity;

import androidx.annotation.NonNull;
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

    /**
     * 补卡时间
     */
    private long patchTime;
    /**
     * 打卡当天的时间点
     */
    private long dayMs;

    public long getDayMs() {
        return dayMs;
    }

    public void setDayMs(long eDayMs) {
        dayMs = eDayMs;
    }

    public long getPatchTime() {
        return patchTime;
    }

    public void setPatchTime(long ePatchTime) {
        patchTime = ePatchTime;
    }

    public Ding(long eDateTime, String eMark) {
        dayMs = eDateTime;
        mark = eMark;
    }

    public Ding() {
    }

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

    @Override
    public String toString() {
        return "Ding{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", mark='" + mark + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
