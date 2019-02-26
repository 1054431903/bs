package com.fwk.common.entity;

import java.util.Date;

public class TApeasantprotocol {
    private Integer id;

    private Date creattime;

    private Integer delstate;

    private String document;

    private String name;

    private Date signtime;

    private Date begintime;

    private Date endtime;

    private String actstate;

    private Integer peasantid;

    private String operator;

    private String operatorphone;

    private String explain;

    private String ppid;

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

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document == null ? null : document.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getSigntime() {
        return signtime;
    }

    public void setSigntime(Date signtime) {
        this.signtime = signtime;
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

    public String getActstate() {
        return actstate;
    }

    public void setActstate(String actstate) {
        this.actstate = actstate == null ? null : actstate.trim();
    }

    public Integer getPeasantid() {
        return peasantid;
    }

    public void setPeasantid(Integer peasantid) {
        this.peasantid = peasantid;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getOperatorphone() {
        return operatorphone;
    }

    public void setOperatorphone(String operatorphone) {
        this.operatorphone = operatorphone == null ? null : operatorphone.trim();
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain == null ? null : explain.trim();
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid == null ? null : ppid.trim();
    }

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }
}