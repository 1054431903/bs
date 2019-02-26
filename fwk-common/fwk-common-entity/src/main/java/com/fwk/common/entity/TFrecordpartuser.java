package com.fwk.common.entity;

import java.util.Date;

public class TFrecordpartuser {
    private Integer id;

    private Date creattime;

    private Integer delstate;

    private Integer recordid;

    private Integer userid;

    private Date edittime;

    private Byte hasreaded;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }

    public Integer getDelstate() {
        return delstate;
    }

    public void setDelstate(Integer delstate) {
        this.delstate = delstate;
    }

    public Integer getRecordid() {
        return recordid;
    }

    public void setRecordid(Integer recordid) {
        this.recordid = recordid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }

    public Byte getHasreaded() {
        return hasreaded;
    }

    public void setHasreaded(Byte hasreaded) {
        this.hasreaded = hasreaded;
    }
}