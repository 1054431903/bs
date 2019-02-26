package com.fwk.common.entity;

import java.util.Date;

public class TFplanwarn {
    private Integer id;

    private Date creattime;

    private Integer delstate;

    private String type;

    private Date warntime;

    private Integer planid;

    private Integer warncreat;

    private Integer warnpart;

    private Date edittime;

    private Integer warntimes;

    private String timeunit;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getWarntime() {
        return warntime;
    }

    public void setWarntime(Date warntime) {
        this.warntime = warntime;
    }

    public Integer getPlanid() {
        return planid;
    }

    public void setPlanid(Integer planid) {
        this.planid = planid;
    }

    public Integer getWarncreat() {
        return warncreat;
    }

    public void setWarncreat(Integer warncreat) {
        this.warncreat = warncreat;
    }

    public Integer getWarnpart() {
        return warnpart;
    }

    public void setWarnpart(Integer warnpart) {
        this.warnpart = warnpart;
    }

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }

    public Integer getWarntimes() {
        return warntimes;
    }

    public void setWarntimes(Integer warntimes) {
        this.warntimes = warntimes;
    }

    public String getTimeunit() {
        return timeunit;
    }

    public void setTimeunit(String timeunit) {
        this.timeunit = timeunit == null ? null : timeunit.trim();
    }
}