package com.fwk.common.entity;

import java.util.Date;

public class TSinformation {
    private Integer id;

    private Date creattime;

    private Integer delstate;

    private String title;

    private String categoryid;

    private String pubuserid;

    private Integer ispub;

    private Date pubtime;

    private String puborgid;

    private String orgid;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid == null ? null : categoryid.trim();
    }

    public String getPubuserid() {
        return pubuserid;
    }

    public void setPubuserid(String pubuserid) {
        this.pubuserid = pubuserid == null ? null : pubuserid.trim();
    }

    public Integer getIspub() {
        return ispub;
    }

    public void setIspub(Integer ispub) {
        this.ispub = ispub;
    }

    public Date getPubtime() {
        return pubtime;
    }

    public void setPubtime(Date pubtime) {
        this.pubtime = pubtime;
    }

    public String getPuborgid() {
        return puborgid;
    }

    public void setPuborgid(String puborgid) {
        this.puborgid = puborgid == null ? null : puborgid.trim();
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid == null ? null : orgid.trim();
    }

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }
}