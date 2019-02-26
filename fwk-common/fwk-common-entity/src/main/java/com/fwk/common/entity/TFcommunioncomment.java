package com.fwk.common.entity;

import java.util.Date;

public class TFcommunioncomment {
    private Integer id;

    private Date creattime;

    private Date edittime;

    private Integer delstate;

    private Integer communionid;

    private Integer commentuserid;

    private Integer commentreversioncount;

    private String commentcontent;

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

    public Integer getCommentuserid() {
        return commentuserid;
    }

    public void setCommentuserid(Integer commentuserid) {
        this.commentuserid = commentuserid;
    }

    public Integer getCommentreversioncount() {
        return commentreversioncount;
    }

    public void setCommentreversioncount(Integer commentreversioncount) {
        this.commentreversioncount = commentreversioncount;
    }

    public String getCommentcontent() {
        return commentcontent;
    }

    public void setCommentcontent(String commentcontent) {
        this.commentcontent = commentcontent == null ? null : commentcontent.trim();
    }
}