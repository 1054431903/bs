package com.fwk.common.entity;

import java.util.Date;

public class TAplant {
    private Integer id;

    private Date creattime;

    private Integer delstate;

    private Integer parcelid;

    private Integer prodocttypeid;

    private Integer plantage;

    private String plantdate;

    private Integer dutyuserid;

    private String explain;

    private String pid;

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

    public Integer getParcelid() {
        return parcelid;
    }

    public void setParcelid(Integer parcelid) {
        this.parcelid = parcelid;
    }

    public Integer getProdocttypeid() {
        return prodocttypeid;
    }

    public void setProdocttypeid(Integer prodocttypeid) {
        this.prodocttypeid = prodocttypeid;
    }

    public Integer getPlantage() {
        return plantage;
    }

    public void setPlantage(Integer plantage) {
        this.plantage = plantage;
    }

    public String getPlantdate() {
        return plantdate;
    }

    public void setPlantdate(String plantdate) {
        this.plantdate = plantdate == null ? null : plantdate.trim();
    }

    public Integer getDutyuserid() {
        return dutyuserid;
    }

    public void setDutyuserid(Integer dutyuserid) {
        this.dutyuserid = dutyuserid;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain == null ? null : explain.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }
}