package com.fwk.common.entity;

import java.util.Date;

public class TUdepartment {
    private Integer id;

    private Date creattime;

    private Integer delstate;

    private Integer orgid;

    private Integer parentid;

    private String name;

    private String departno;

    private Integer chargeuserid;

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

    public Integer getOrgid() {
        return orgid;
    }

    public void setOrgid(Integer orgid) {
        this.orgid = orgid;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDepartno() {
        return departno;
    }

    public void setDepartno(String departno) {
        this.departno = departno == null ? null : departno.trim();
    }

    public Integer getChargeuserid() {
        return chargeuserid;
    }

    public void setChargeuserid(Integer chargeuserid) {
        this.chargeuserid = chargeuserid;
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