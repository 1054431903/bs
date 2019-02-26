package com.fwk.common.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TPparcelmeassoil {
    private Integer id;

    private Date creattime;

    private Integer delstate;

    private Integer parcelid;

    private String soiltexture;

    private Date sampltime;

    private Date meastime;

    private String orgname;

    private String samplnum;

    private String soilgenre;

    private BigDecimal nitrogen;

    private BigDecimal phosphorus;

    private BigDecimal potassium;

    private BigDecimal phvalue;

    private BigDecimal matter;

    private BigDecimal validzn;

    private BigDecimal validcu;

    private BigDecimal validfe;

    private BigDecimal validmn;

    private BigDecimal validmo;

    private BigDecimal valids;

    private BigDecimal solubilityca;

    private BigDecimal solubilitymg;

    private BigDecimal salt;

    private Integer mid;

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

    public Integer getParcelid() {
        return parcelid;
    }

    public void setParcelid(Integer parcelid) {
        this.parcelid = parcelid;
    }

    public String getSoiltexture() {
        return soiltexture;
    }

    public void setSoiltexture(String soiltexture) {
        this.soiltexture = soiltexture == null ? null : soiltexture.trim();
    }

    public Date getSampltime() {
        return sampltime;
    }

    public void setSampltime(Date sampltime) {
        this.sampltime = sampltime;
    }

    public Date getMeastime() {
        return meastime;
    }

    public void setMeastime(Date meastime) {
        this.meastime = meastime;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname == null ? null : orgname.trim();
    }

    public String getSamplnum() {
        return samplnum;
    }

    public void setSamplnum(String samplnum) {
        this.samplnum = samplnum == null ? null : samplnum.trim();
    }

    public String getSoilgenre() {
        return soilgenre;
    }

    public void setSoilgenre(String soilgenre) {
        this.soilgenre = soilgenre == null ? null : soilgenre.trim();
    }

    public BigDecimal getNitrogen() {
        return nitrogen;
    }

    public void setNitrogen(BigDecimal nitrogen) {
        this.nitrogen = nitrogen;
    }

    public BigDecimal getPhosphorus() {
        return phosphorus;
    }

    public void setPhosphorus(BigDecimal phosphorus) {
        this.phosphorus = phosphorus;
    }

    public BigDecimal getPotassium() {
        return potassium;
    }

    public void setPotassium(BigDecimal potassium) {
        this.potassium = potassium;
    }

    public BigDecimal getPhvalue() {
        return phvalue;
    }

    public void setPhvalue(BigDecimal phvalue) {
        this.phvalue = phvalue;
    }

    public BigDecimal getMatter() {
        return matter;
    }

    public void setMatter(BigDecimal matter) {
        this.matter = matter;
    }

    public BigDecimal getValidzn() {
        return validzn;
    }

    public void setValidzn(BigDecimal validzn) {
        this.validzn = validzn;
    }

    public BigDecimal getValidcu() {
        return validcu;
    }

    public void setValidcu(BigDecimal validcu) {
        this.validcu = validcu;
    }

    public BigDecimal getValidfe() {
        return validfe;
    }

    public void setValidfe(BigDecimal validfe) {
        this.validfe = validfe;
    }

    public BigDecimal getValidmn() {
        return validmn;
    }

    public void setValidmn(BigDecimal validmn) {
        this.validmn = validmn;
    }

    public BigDecimal getValidmo() {
        return validmo;
    }

    public void setValidmo(BigDecimal validmo) {
        this.validmo = validmo;
    }

    public BigDecimal getValids() {
        return valids;
    }

    public void setValids(BigDecimal valids) {
        this.valids = valids;
    }

    public BigDecimal getSolubilityca() {
        return solubilityca;
    }

    public void setSolubilityca(BigDecimal solubilityca) {
        this.solubilityca = solubilityca;
    }

    public BigDecimal getSolubilitymg() {
        return solubilitymg;
    }

    public void setSolubilitymg(BigDecimal solubilitymg) {
        this.solubilitymg = solubilitymg;
    }

    public BigDecimal getSalt() {
        return salt;
    }

    public void setSalt(BigDecimal salt) {
        this.salt = salt;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }
}