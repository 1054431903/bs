package com.fwk.common.entity;

import java.util.Date;

public class TFcommunionuserpermisions {
    private Integer id;

    private Date creattime;

    private Date edittime;

    private Integer delstate;

    private Integer communionid;

    private Integer createuserid;

    private String createuserorgid;

    private String createuserdepartmentid;

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

    public Integer getCreateuserid() {
        return createuserid;
    }

    public void setCreateuserid(Integer createuserid) {
        this.createuserid = createuserid;
    }

    public String getCreateuserorgid() {
        return createuserorgid;
    }

    public void setCreateuserorgid(String createuserorgid) {
        this.createuserorgid = createuserorgid == null ? null : createuserorgid.trim();
    }

    public String getCreateuserdepartmentid() {
        return createuserdepartmentid;
    }

    public void setCreateuserdepartmentid(String createuserdepartmentid) {
        this.createuserdepartmentid = createuserdepartmentid == null ? null : createuserdepartmentid.trim();
    }
}