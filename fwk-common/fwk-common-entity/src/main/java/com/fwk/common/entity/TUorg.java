package com.fwk.common.entity;

import java.util.Date;

public class TUorg {
    private Integer id;

    private Date creattime;

    private Integer delstate;

    private String name;

    private String nature;

    private String legal;

    private Date eastablishtime;

    private Integer logo;

    private Double regisfund;

    private String scale;

    private String addresscode;

    private String addressdetail;

    private Integer belonguserid;

    private Integer permit;

    private Date edittime;

    private String supportid;

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

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature == null ? null : nature.trim();
    }

    public String getLegal() {
        return legal;
    }

    public void setLegal(String legal) {
        this.legal = legal == null ? null : legal.trim();
    }

    public Date getEastablishtime() {
        return eastablishtime;
    }

    public void setEastablishtime(Date eastablishtime) {
        this.eastablishtime = eastablishtime;
    }

    public Integer getLogo() {
        return logo;
    }

    public void setLogo(Integer logo) {
        this.logo = logo;
    }

    public Double getRegisfund() {
        return regisfund;
    }

    public void setRegisfund(Double regisfund) {
        this.regisfund = regisfund;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale == null ? null : scale.trim();
    }

    public String getAddresscode() {
        return addresscode;
    }

    public void setAddresscode(String addresscode) {
        this.addresscode = addresscode == null ? null : addresscode.trim();
    }

    public String getAddressdetail() {
        return addressdetail;
    }

    public void setAddressdetail(String addressdetail) {
        this.addressdetail = addressdetail == null ? null : addressdetail.trim();
    }

    public Integer getBelonguserid() {
        return belonguserid;
    }

    public void setBelonguserid(Integer belonguserid) {
        this.belonguserid = belonguserid;
    }

    public Integer getPermit() {
        return permit;
    }

    public void setPermit(Integer permit) {
        this.permit = permit;
    }

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }

    public String getSupportid() {
        return supportid;
    }

    public void setSupportid(String supportid) {
        this.supportid = supportid == null ? null : supportid.trim();
    }
}