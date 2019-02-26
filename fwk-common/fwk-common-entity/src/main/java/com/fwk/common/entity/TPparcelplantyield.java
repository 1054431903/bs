package com.fwk.common.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TPparcelplantyield {
    private Integer id;

    private Date creattime;

    private Integer delstate;

    private Date edittime;

    private Integer plantid;

    private String outputyear;

    private String output;

    private String outputlevel;

    private String preestimatecategory;

    private BigDecimal preestimateoutput;

    private String preestimateoutputunit;

    private BigDecimal preestimatelandoutput;

    private String preestimatelandoutputunit;

    private BigDecimal actualoutput;

    private String actualoutputunit;

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

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }

    public Integer getPlantid() {
        return plantid;
    }

    public void setPlantid(Integer plantid) {
        this.plantid = plantid;
    }

    public String getOutputyear() {
        return outputyear;
    }

    public void setOutputyear(String outputyear) {
        this.outputyear = outputyear == null ? null : outputyear.trim();
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output == null ? null : output.trim();
    }

    public String getOutputlevel() {
        return outputlevel;
    }

    public void setOutputlevel(String outputlevel) {
        this.outputlevel = outputlevel == null ? null : outputlevel.trim();
    }

    public String getPreestimatecategory() {
        return preestimatecategory;
    }

    public void setPreestimatecategory(String preestimatecategory) {
        this.preestimatecategory = preestimatecategory == null ? null : preestimatecategory.trim();
    }

    public BigDecimal getPreestimateoutput() {
        return preestimateoutput;
    }

    public void setPreestimateoutput(BigDecimal preestimateoutput) {
        this.preestimateoutput = preestimateoutput;
    }

    public String getPreestimateoutputunit() {
        return preestimateoutputunit;
    }

    public void setPreestimateoutputunit(String preestimateoutputunit) {
        this.preestimateoutputunit = preestimateoutputunit == null ? null : preestimateoutputunit.trim();
    }

    public BigDecimal getPreestimatelandoutput() {
        return preestimatelandoutput;
    }

    public void setPreestimatelandoutput(BigDecimal preestimatelandoutput) {
        this.preestimatelandoutput = preestimatelandoutput;
    }

    public String getPreestimatelandoutputunit() {
        return preestimatelandoutputunit;
    }

    public void setPreestimatelandoutputunit(String preestimatelandoutputunit) {
        this.preestimatelandoutputunit = preestimatelandoutputunit == null ? null : preestimatelandoutputunit.trim();
    }

    public BigDecimal getActualoutput() {
        return actualoutput;
    }

    public void setActualoutput(BigDecimal actualoutput) {
        this.actualoutput = actualoutput;
    }

    public String getActualoutputunit() {
        return actualoutputunit;
    }

    public void setActualoutputunit(String actualoutputunit) {
        this.actualoutputunit = actualoutputunit == null ? null : actualoutputunit.trim();
    }
}