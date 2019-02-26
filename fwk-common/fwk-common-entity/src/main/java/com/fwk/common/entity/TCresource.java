package com.fwk.common.entity;

import java.util.Date;

public class TCresource {
    private Integer id;

    private String resourcepath;

    private String releatedtype;

    private Integer releatedid;

    private String type;

    private String localpath;

    private String resourcename;

    private Date creattime;

    private Integer delstate;

    private Integer timelength;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResourcepath() {
        return resourcepath;
    }

    public void setResourcepath(String resourcepath) {
        this.resourcepath = resourcepath == null ? null : resourcepath.trim();
    }

    public String getReleatedtype() {
        return releatedtype;
    }

    public void setReleatedtype(String releatedtype) {
        this.releatedtype = releatedtype == null ? null : releatedtype.trim();
    }

    public Integer getReleatedid() {
        return releatedid;
    }

    public void setReleatedid(Integer releatedid) {
        this.releatedid = releatedid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getLocalpath() {
        return localpath;
    }

    public void setLocalpath(String localpath) {
        this.localpath = localpath == null ? null : localpath.trim();
    }

    public String getResourcename() {
        return resourcename;
    }

    public void setResourcename(String resourcename) {
        this.resourcename = resourcename == null ? null : resourcename.trim();
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

    public Integer getTimelength() {
        return timelength;
    }

    public void setTimelength(Integer timelength) {
        this.timelength = timelength;
    }
}