package com.fwk.common.entity;

import java.util.Date;

public class TFoutput {
    private Integer id;

    private Date creattime;

    private Integer delstate;

    private String name;

    private Integer type;

    private String stand;

    private Integer standunit;

    private Double num;

    private Integer numunit;

    private String facturer;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getStand() {
        return stand;
    }

    public void setStand(String stand) {
        this.stand = stand == null ? null : stand.trim();
    }

    public Integer getStandunit() {
        return standunit;
    }

    public void setStandunit(Integer standunit) {
        this.standunit = standunit;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public Integer getNumunit() {
        return numunit;
    }

    public void setNumunit(Integer numunit) {
        this.numunit = numunit;
    }

    public String getFacturer() {
        return facturer;
    }

    public void setFacturer(String facturer) {
        this.facturer = facturer == null ? null : facturer.trim();
    }

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }
}