package com.fwk.common.entity;

import java.util.Date;

public class TApeasantprotocolresource {
    private Integer id;

    private Date creattime;

    private Integer delstate;

    private Integer resourceid;

    private Integer protocolid;

    private Date edittime;

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

    public Integer getResourceid() {
        return resourceid;
    }

    public void setResourceid(Integer resourceid) {
        this.resourceid = resourceid;
    }

    public Integer getProtocolid() {
        return protocolid;
    }

    public void setProtocolid(Integer protocolid) {
        this.protocolid = protocolid;
    }

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }
}