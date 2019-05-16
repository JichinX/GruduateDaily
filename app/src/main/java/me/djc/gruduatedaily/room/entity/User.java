package me.djc.gruduatedaily.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_user")
public class User {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String pwd;
    private String phone;
    private String code;
    private long codeMs;

    public long getId() {
        return id;
    }

    public void setId(long eId) {
        id = eId;
    }

    public String getName() {
        return name;
    }

    public void setName(String eName) {
        name = eName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String ePwd) {
        pwd = ePwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String ePhone) {
        phone = ePhone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String eCode) {
        code = eCode;
    }

    public long getCodeMs() {
        return codeMs;
    }

    public void setCodeMs(long eCodeMs) {
        codeMs = eCodeMs;
    }
}
