package com.fwk.common.entity;

import java.util.Date;

public class TCcategory {
    private Integer id;

    private Date creattime;

    private Integer delstate;

    private String name;

    private Date edittime;

    private String supportid;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }

    public String getSupportid() {
        return supportid;
    }

    public void setSupportid(String supportid) {
        this.supportid = supportid == null ? null : supportid.trim();
    }
}