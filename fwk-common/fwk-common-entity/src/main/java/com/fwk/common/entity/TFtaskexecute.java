package com.fwk.common.entity;

import java.util.Date;

public class TFtaskexecute {
    private Integer id;

    private Date creattime;

    private Integer delstate;

    private Integer userid;

    private Integer taskid;

    private Date edittime;

    private Byte hasreaded;

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

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }

    public Byte getHasreaded() {
        return hasreaded;
    }

    public void setHasreaded(Byte hasreaded) {
        this.hasreaded = hasreaded;
    }
}