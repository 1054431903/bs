package com.fwk.common.entity;

import java.util.Date;

public class TCresourcehistory {
    private Integer id;

    private String maname;

    private String description;

    private Integer orderno;

    private String resourceid;

    private Integer currentresource;

    private Date creattime;

    private Integer delstate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getManame() {
        return maname;
    }

    public void setManame(String maname) {
        this.maname = maname == null ? null : maname.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getOrderno() {
        return orderno;
    }

    public void setOrderno(Integer orderno) {
        this.orderno = orderno;
    }

    public String getResourceid() {
        return resourceid;
    }

    public void setResourceid(String resourceid) {
        this.resourceid = resourceid == null ? null : resourceid.trim();
    }

    public Integer getCurrentresource() {
        return currentresource;
    }

    public void setCurrentresource(Integer currentresource) {
        this.currentresource = currentresource;
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
}