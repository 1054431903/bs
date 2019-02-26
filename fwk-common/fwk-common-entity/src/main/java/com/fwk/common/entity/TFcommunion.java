package com.fwk.common.entity;

import java.util.Date;

public class TFcommunion {
    private Integer id;

    private Date creattime;

    private Date edittime;

    private Integer delstate;

    private Integer createuserid;

    private String location;

    private Integer commentcount;

    private String communioncontent;

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

    public Integer getCreateuserid() {
        return createuserid;
    }

    public void setCreateuserid(Integer createuserid) {
        this.createuserid = createuserid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Integer getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(Integer commentcount) {
        this.commentcount = commentcount;
    }

    public String getCommunioncontent() {
        return communioncontent;
    }

    public void setCommunioncontent(String communioncontent) {
        this.communioncontent = communioncontent == null ? null : communioncontent.trim();
    }
}