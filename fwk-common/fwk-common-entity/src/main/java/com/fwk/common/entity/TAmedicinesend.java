package com.fwk.common.entity;

import java.util.Date;

public class TAmedicinesend {
    private Integer id;

    private Date creattime;

    private Integer delstate;

    private Integer parcelid;

    private Integer nameid;

    private String type;

    private String specific;

    private String specificunit;

    private Integer num;

    private String numunit;

    private Integer drowuserid;

    private String explain;

    private String drowaddressdetail;

    private String drowaddresscode;

    private String msid;

    private Date drowtime;

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

    public Integer getNameid() {
        return nameid;
    }

    public void setNameid(Integer nameid) {
        this.nameid = nameid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getSpecific() {
        return specific;
    }

    public void setSpecific(String specific) {
        this.specific = specific == null ? null : specific.trim();
    }

    public String getSpecificunit() {
        return specificunit;
    }

    public void setSpecificunit(String specificunit) {
        this.specificunit = specificunit == null ? null : specificunit.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getNumunit() {
        return numunit;
    }

    public void setNumunit(String numunit) {
        this.numunit = numunit == null ? null : numunit.trim();
    }

    public Integer getDrowuserid() {
        return drowuserid;
    }

    public void setDrowuserid(Integer drowuserid) {
        this.drowuserid = drowuserid;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain == null ? null : explain.trim();
    }

    public String getDrowaddressdetail() {
        return drowaddressdetail;
    }

    public void setDrowaddressdetail(String drowaddressdetail) {
        this.drowaddressdetail = drowaddressdetail == null ? null : drowaddressdetail.trim();
    }

    public String getDrowaddresscode() {
        return drowaddresscode;
    }

    public void setDrowaddresscode(String drowaddresscode) {
        this.drowaddresscode = drowaddresscode == null ? null : drowaddresscode.trim();
    }

    public String getMsid() {
        return msid;
    }

    public void setMsid(String msid) {
        this.msid = msid == null ? null : msid.trim();
    }

    public Date getDrowtime() {
        return drowtime;
    }

    public void setDrowtime(Date drowtime) {
        this.drowtime = drowtime;
    }

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }
}