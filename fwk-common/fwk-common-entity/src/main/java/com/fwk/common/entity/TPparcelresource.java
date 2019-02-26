package com.fwk.common.entity;

import java.util.Date;

public class TPparcelresource {
    private Integer id;

    private Date creattime;

    private Integer delstate;

    private Date edittime;

    private Integer parcelid;

    private Integer resourceid;

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

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }

    public Integer getParcelid() {
        return parcelid;
    }

    public void setParcelid(Integer parcelid) {
        this.parcelid = parcelid;
    }

    public Integer getResourceid() {
        return resourceid;
    }

    public void setResourceid(Integer resourceid) {
        this.resourceid = resourceid;
    }
}