package com.fwk.common.entity;

import java.util.Date;

public class TFcommunioncommentreversion {
    private Integer id;

    private Date creattime;

    private Date edittime;

    private Integer delstate;

    private Integer communionid;

    private Integer communioncommentid;

    private Integer reversionuserid;

    private Integer parentid;

    private Integer toreversionuserid;

    private Integer reversioncount;

    private String reversioncontent;

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

    public Integer getCommunioncommentid() {
        return communioncommentid;
    }

    public void setCommunioncommentid(Integer communioncommentid) {
        this.communioncommentid = communioncommentid;
    }

    public Integer getReversionuserid() {
        return reversionuserid;
    }

    public void setReversionuserid(Integer reversionuserid) {
        this.reversionuserid = reversionuserid;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getToreversionuserid() {
        return toreversionuserid;
    }

    public void setToreversionuserid(Integer toreversionuserid) {
        this.toreversionuserid = toreversionuserid;
    }

    public Integer getReversioncount() {
        return reversioncount;
    }

    public void setReversioncount(Integer reversioncount) {
        this.reversioncount = reversioncount;
    }

    public String getReversioncontent() {
        return reversioncontent;
    }

    public void setReversioncontent(String reversioncontent) {
        this.reversioncontent = reversioncontent == null ? null : reversioncontent.trim();
    }
}