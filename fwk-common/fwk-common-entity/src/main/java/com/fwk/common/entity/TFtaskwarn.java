package com.fwk.common.entity;

import java.util.Date;

public class TFtaskwarn {
    private Integer id;

    private Date creattime;

    private Integer delstate;

    private String type;

    private Date warntime;

    private Integer taskid;

    private Integer warnpart;

    private Integer warnexecute;

    private Integer warntimes;

    private String timeunit;

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

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public Integer getWarnpart() {
        return warnpart;
    }

    public void setWarnpart(Integer warnpart) {
        this.warnpart = warnpart;
    }

    public Integer getWarnexecute() {
        return warnexecute;
    }

    public void setWarnexecute(Integer warnexecute) {
        this.warnexecute = warnexecute;
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

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }
}