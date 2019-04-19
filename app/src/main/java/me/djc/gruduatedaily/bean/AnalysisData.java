package me.djc.gruduatedaily.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Des:GruduateDaily - me.djc.gruduatedaily.bean
 *
 * @author xujichang
 * @date 2019-04-18 - 18:12
 * <p>
 * modify:
 */
@Entity(tableName = "tbl_analysis_data")
public class AnalysisData {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private int type;

    public AnalysisData(String eTitle, int eType) {
        title = eTitle;
        type = eType;
    }

    public int getType() {
        return type;
    }

    public void setType(int eType) {
        type = eType;
    }

    public AnalysisData() {
    }

    public AnalysisData(String eTitle) {
        title = eTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String eTitle) {
        title = eTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int eId) {
        id = eId;
    }
}
