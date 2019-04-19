package me.djc.gruduatedaily.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Des:GruduateDaily - me.djc.gruduatedaily.bean
 *
 * @author xujichang
 * @date 2019-04-19 - 03:52
 * <p>
 * modify:
 */
@Entity(tableName = "tbl_plans")
public class Plan {
    @PrimaryKey
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int eId) {
        id = eId;
    }
}
