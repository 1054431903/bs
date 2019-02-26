package com.fwk.common.entity;

import java.util.Date;

public class TFcommunionresource {
    private Integer id;

    private Date creattime;

    private Date edittime;

    private Integer delstate;

    private Integer communionid;

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

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }

    public Integer getDelstate() {
        return delstate;
    }

    public void setDelstate(Integer delstate) {
        this.delstate = delstate;
    }

    public Integer getCommunionid() {
        return communionid;
    }

    public void setCommunionid(Integer communionid) {
        this.communionid = communionid;
    }

    public Integer getResourceid() {
        return resourceid;
    }

    public void setResourceid(Integer resourceid) {
        this.resourceid = resourceid;
    }
}