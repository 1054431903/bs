package com.fwk.common.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TFrecord {
    private Integer id;

    private Date creattime;

    private Integer delstate;

    private String content;

    private Integer farmtype;

    private Date begintime;

    private Date endtime;

    private BigDecimal cost;

    private String sourcedictvalue;

    private Date edittime;

    private String remark;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getFarmtype() {
        return farmtype;
    }

    public void setFarmtype(Integer farmtype) {
        this.farmtype = farmtype;
    }

    public Date getBegintime() {
        return begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getSourcedictvalue() {
        return sourcedictvalue;
    }

    public void setSourcedictvalue(String sourcedictvalue) {
        this.sourcedictvalue = sourcedictvalue == null ? null : sourcedictvalue.trim();
    }

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}