package com.fwk.common.entity;

import java.util.Date;

public class TAagricname {
    private Integer id;

    private Date creattime;

    private Integer delstate;

    private String name;

    private Integer typeid;

    private Double specif;

    private Integer specifunit;

    private String buildfacture;

    private String discribe;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public Double getSpecif() {
        return specif;
    }

    public void setSpecif(Double specif) {
        this.specif = specif;
    }

    public Integer getSpecifunit() {
        return specifunit;
    }

    public void setSpecifunit(Integer specifunit) {
        this.specifunit = specifunit;
    }

    public String getBuildfacture() {
        return buildfacture;
    }

    public void setBuildfacture(String buildfacture) {
        this.buildfacture = buildfacture == null ? null : buildfacture.trim();
    }

    public String getDiscribe() {
        return discribe;
    }

    public void setDiscribe(String discribe) {
        this.discribe = discribe == null ? null : discribe.trim();
    }

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }
}