package me.djc.gruduatedaily.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import me.djc.gruduatedaily.base.AppConst;

/**
 * 清单
 */
@Entity(tableName = "tbl_order")
public class Order {
    /**
     * 主键ID
     */
    @PrimaryKey(autoGenerate = true)
    private long id;
    /**
     * 开始时间
     */
    private long dateTimeStart;
    /**
     * 结束时间
     */
    private long dateTimeEnd;
    /**
     * 是否是重要的
     */
    private boolean isImpotant;
    /**
     * 是否是紧急的
     */
    private boolean isUrgent;
    /**
     * 清单内容
     */
    private String content;
    /**
     * 排序
     */
    private long listOrder;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int eType) {
        type = eType;
        convertType(type);
    }

    private void convertType(int eType) {
        if (eType == AppConst.OrderType.IMPORTANT) {
            isImpotant = true;
            isUrgent = false;
        } else if (eType == AppConst.OrderType.IMPORTANT_URGENT) {
            isUrgent = true;
            isImpotant = true;
        } else if (eType == AppConst.OrderType.URGENT) {
            isImpotant = false;
            isUrgent = true;
        } else {
            isUrgent = false;
            isImpotant = false;
        }
    }

    public long getListOrder() {
        return listOrder;
    }

    public void setListOrder(long eListOrder) {
        listOrder = eListOrder;
    }

    public long getId() {
        return id;
    }

    public void setId(long eId) {
        id = eId;
    }

    public long getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(long eDateTimeStart) {
        dateTimeStart = eDateTimeStart;
    }

    public long getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(long eDateTimeEnd) {
        dateTimeEnd = eDateTimeEnd;
    }

    public boolean isImpotant() {
        return isImpotant;
    }

    public void setImpotant(boolean eImpotant) {
        isImpotant = eImpotant;
    }

    public boolean isUrgent() {
        return isUrgent;
    }

    public void setUrgent(boolean eUrgent) {
        isUrgent = eUrgent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String eContent) {
        content = eContent;
    }
}
