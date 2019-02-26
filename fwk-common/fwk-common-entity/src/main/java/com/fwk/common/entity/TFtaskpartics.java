package com.fwk.common.entity;

import java.util.Date;

public class TFtaskpartics {
    private Integer id;

    private Date creattime;

    private Integer delstate;

    private Integer userid;

    private Integer taskid;

    private String taskparticsstatus;

    private String taskstatus;

    private Date edittime;

    private Integer issavedtorecord;

    private Byte hasreaded;

    private String recordeid;

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

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public String getTaskparticsstatus() {
        return taskparticsstatus;
    }

    public void setTaskparticsstatus(String taskparticsstatus) {
        this.taskparticsstatus = taskparticsstatus == null ? null : taskparticsstatus.trim();
    }

    public String getTaskstatus() {
        return taskstatus;
    }

    public void setTaskstatus(String taskstatus) {
        this.taskstatus = taskstatus == null ? null : taskstatus.trim();
    }

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }

    public Integer getIssavedtorecord() {
        return issavedtorecord;
    }

    public void setIssavedtorecord(Integer issavedtorecord) {
        this.issavedtorecord = issavedtorecord;
    }

    public Byte getHasreaded() {
        return hasreaded;
    }

    public void setHasreaded(Byte hasreaded) {
        this.hasreaded = hasreaded;
    }

    public String getRecordeid() {
        return recordeid;
    }

    public void setRecordeid(String recordeid) {
        this.recordeid = recordeid == null ? null : recordeid.trim();
    }
}