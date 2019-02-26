package com.fwk.common.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TPparcelplant {
    private Integer id;

    private Date creattime;

    private Integer delstate;

    private String categoryid;

    private String breed;

    private Integer varieties;

    private String growingbatch;

    private Date plantbegintime;

    private Date plantendtime;

    private Integer preestimategrowthcycle;

    private String preestimategrowthcycleunit;

    private Integer plantage;

    private String plantageunit;

    private BigDecimal area;

    private Integer number;

    private String unit;

    private String introduce;

    private Integer parcelid;

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

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid == null ? null : categoryid.trim();
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed == null ? null : breed.trim();
    }

    public Integer getVarieties() {
        return varieties;
    }

    public void setVarieties(Integer varieties) {
        this.varieties = varieties;
    }

    public String getGrowingbatch() {
        return growingbatch;
    }

    public void setGrowingbatch(String growingbatch) {
        this.growingbatch = growingbatch == null ? null : growingbatch.trim();
    }

    public Date getPlantbegintime() {
        return plantbegintime;
    }

    public void setPlantbegintime(Date plantbegintime) {
        this.plantbegintime = plantbegintime;
    }

    public Date getPlantendtime() {
        return plantendtime;
    }

    public void setPlantendtime(Date plantendtime) {
        this.plantendtime = plantendtime;
    }

    public Integer getPreestimategrowthcycle() {
        return preestimategrowthcycle;
    }

    public void setPreestimategrowthcycle(Integer preestimategrowthcycle) {
        this.preestimategrowthcycle = preestimategrowthcycle;
    }

    public String getPreestimategrowthcycleunit() {
        return preestimategrowthcycleunit;
    }

    public void setPreestimategrowthcycleunit(String preestimategrowthcycleunit) {
        this.preestimategrowthcycleunit = preestimategrowthcycleunit == null ? null : preestimategrowthcycleunit.trim();
    }

    public Integer getPlantage() {
        return plantage;
    }

    public void setPlantage(Integer plantage) {
        this.plantage = plantage;
    }

    public String getPlantageunit() {
        return plantageunit;
    }

    public void setPlantageunit(String plantageunit) {
        this.plantageunit = plantageunit == null ? null : plantageunit.trim();
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }

    public Integer getParcelid() {
        return parcelid;
    }

    public void setParcelid(Integer parcelid) {
        this.parcelid = parcelid;
    }

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }
}