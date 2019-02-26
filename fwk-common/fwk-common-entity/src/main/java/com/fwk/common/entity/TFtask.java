package com.fwk.common.entity;

import java.util.Date;

public class TFtask {
    private Integer id;

    private Date creattime;

    private Integer delstate;

    private String type;

    private Date begintime;

    private Date endtime;

    private Integer isaffirmexecute;

    private Integer isaffirmpartics;

    private String meetingplace;

    private Integer createuserid;

    private String taskstatus;

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

    public Date getBegintime() {
        return begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getIsaffirmexecute() {
        return isaffirmexecute;
    }

    public void setIsaffirmexecute(Integer isaffirmexecute) {
        this.isaffirmexecute = isaffirmexecute;
    }

    public Integer getIsaffirmpartics() {
        return isaffirmpartics;
    }

    public void setIsaffirmpartics(Integer isaffirmpartics) {
        this.isaffirmpartics = isaffirmpartics;
    }

    public String getMeetingplace() {
        return meetingplace;
    }

    public void setMeetingplace(String meetingplace) {
        this.meetingplace = meetingplace == null ? null : meetingplace.trim();
    }

    public Integer getCreateuserid() {
        return createuserid;
    }

    public void setCreateuserid(Integer createuserid) {
        this.createuserid = createuserid;
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
}