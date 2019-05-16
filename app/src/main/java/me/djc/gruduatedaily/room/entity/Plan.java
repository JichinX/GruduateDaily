package me.djc.gruduatedaily.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 计划
 */
@Entity(tableName = "tbl_plan")
public class Plan {
    @PrimaryKey
    private long id;
    private long timeStart;
    private long timeEnd;
    private long label;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String eDate) {
        date = eDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long eId) {
        id = eId;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long eTimeStart) {
        timeStart = eTimeStart;
    }

    public long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(long eTimeEnd) {
        timeEnd = eTimeEnd;
    }

    public long getLabel() {
        return label;
    }

    public void setLabel(long eLabel) {
        label = eLabel;
    }
}
