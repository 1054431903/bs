package com.fwk.common.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TPparcelplantyieldExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TPparcelplantyieldExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCreattimeIsNull() {
            addCriterion("creattime is null");
            return (Criteria) this;
        }

        public Criteria andCreattimeIsNotNull() {
            addCriterion("creattime is not null");
            return (Criteria) this;
        }

        public Criteria andCreattimeEqualTo(Date value) {
            addCriterion("creattime =", value, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeNotEqualTo(Date value) {
            addCriterion("creattime <>", value, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeGreaterThan(Date value) {
            addCriterion("creattime >", value, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeGreaterThanOrEqualTo(Date value) {
            addCriterion("creattime >=", value, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeLessThan(Date value) {
            addCriterion("creattime <", value, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeLessThanOrEqualTo(Date value) {
            addCriterion("creattime <=", value, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeIn(List<Date> values) {
            addCriterion("creattime in", values, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeNotIn(List<Date> values) {
            addCriterion("creattime not in", values, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeBetween(Date value1, Date value2) {
            addCriterion("creattime between", value1, value2, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeNotBetween(Date value1, Date value2) {
            addCriterion("creattime not between", value1, value2, "creattime");
            return (Criteria) this;
        }

        public Criteria andDelstateIsNull() {
            addCriterion("delstate is null");
            return (Criteria) this;
        }

        public Criteria andDelstateIsNotNull() {
            addCriterion("delstate is not null");
            return (Criteria) this;
        }

        public Criteria andDelstateEqualTo(Integer value) {
            addCriterion("delstate =", value, "delstate");
            return (Criteria) this;
        }

        public Criteria andDelstateNotEqualTo(Integer value) {
            addCriterion("delstate <>", value, "delstate");
            return (Criteria) this;
        }

        public Criteria andDelstateGreaterThan(Integer value) {
            addCriterion("delstate >", value, "delstate");
            return (Criteria) this;
        }

        public Criteria andDelstateGreaterThanOrEqualTo(Integer value) {
            addCriterion("delstate >=", value, "delstate");
            return (Criteria) this;
        }

        public Criteria andDelstateLessThan(Integer value) {
            addCriterion("delstate <", value, "delstate");
            return (Criteria) this;
        }

        public Criteria andDelstateLessThanOrEqualTo(Integer value) {
            addCriterion("delstate <=", value, "delstate");
            return (Criteria) this;
        }

        public Criteria andDelstateIn(List<Integer> values) {
            addCriterion("delstate in", values, "delstate");
            return (Criteria) this;
        }

        public Criteria andDelstateNotIn(List<Integer> values) {
            addCriterion("delstate not in", values, "delstate");
            return (Criteria) this;
        }

        public Criteria andDelstateBetween(Integer value1, Integer value2) {
            addCriterion("delstate between", value1, value2, "delstate");
            return (Criteria) this;
        }

        public Criteria andDelstateNotBetween(Integer value1, Integer value2) {
            addCriterion("delstate not between", value1, value2, "delstate");
            return (Criteria) this;
        }

        public Criteria andEdittimeIsNull() {
            addCriterion("edittime is null");
            return (Criteria) this;
        }

        public Criteria andEdittimeIsNotNull() {
            addCriterion("edittime is not null");
            return (Criteria) this;
        }

        public Criteria andEdittimeEqualTo(Date value) {
            addCriterion("edittime =", value, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeNotEqualTo(Date value) {
            addCriterion("edittime <>", value, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeGreaterThan(Date value) {
            addCriterion("edittime >", value, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeGreaterThanOrEqualTo(Date value) {
            addCriterion("edittime >=", value, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeLessThan(Date value) {
            addCriterion("edittime <", value, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeLessThanOrEqualTo(Date value) {
            addCriterion("edittime <=", value, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeIn(List<Date> values) {
            addCriterion("edittime in", values, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeNotIn(List<Date> values) {
            addCriterion("edittime not in", values, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeBetween(Date value1, Date value2) {
            addCriterion("edittime between", value1, value2, "edittime");
            return (Criteria) this;
        }

        public Criteria andEdittimeNotBetween(Date value1, Date value2) {
            addCriterion("edittime not between", value1, value2, "edittime");
            return (Criteria) this;
        }

        public Criteria andPlantidIsNull() {
            addCriterion("plantid is null");
            return (Criteria) this;
        }

        public Criteria andPlantidIsNotNull() {
            addCriterion("plantid is not null");
            return (Criteria) this;
        }

        public Criteria andPlantidEqualTo(Integer value) {
            addCriterion("plantid =", value, "plantid");
            return (Criteria) this;
        }

        public Criteria andPlantidNotEqualTo(Integer value) {
            addCriterion("plantid <>", value, "plantid");
            return (Criteria) this;
        }

        public Criteria andPlantidGreaterThan(Integer value) {
            addCriterion("plantid >", value, "plantid");
            return (Criteria) this;
        }

        public Criteria andPlantidGreaterThanOrEqualTo(Integer value) {
            addCriterion("plantid >=", value, "plantid");
            return (Criteria) this;
        }

        public Criteria andPlantidLessThan(Integer value) {
            addCriterion("plantid <", value, "plantid");
            return (Criteria) this;
        }

        public Criteria andPlantidLessThanOrEqualTo(Integer value) {
            addCriterion("plantid <=", value, "plantid");
            return (Criteria) this;
        }

        public Criteria andPlantidIn(List<Integer> values) {
            addCriterion("plantid in", values, "plantid");
            return (Criteria) this;
        }

        public Criteria andPlantidNotIn(List<Integer> values) {
            addCriterion("plantid not in", values, "plantid");
            return (Criteria) this;
        }

        public Criteria andPlantidBetween(Integer value1, Integer value2) {
            addCriterion("plantid between", value1, value2, "plantid");
            return (Criteria) this;
        }

        public Criteria andPlantidNotBetween(Integer value1, Integer value2) {
            addCriterion("plantid not between", value1, value2, "plantid");
            return (Criteria) this;
        }

        public Criteria andOutputyearIsNull() {
            addCriterion("outputyear is null");
            return (Criteria) this;
        }

        public Criteria andOutputyearIsNotNull() {
            addCriterion("outputyear is not null");
            return (Criteria) this;
        }

        public Criteria andOutputyearEqualTo(String value) {
            addCriterion("outputyear =", value, "outputyear");
            return (Criteria) this;
        }

        public Criteria andOutputyearNotEqualTo(String value) {
            addCriterion("outputyear <>", value, "outputyear");
            return (Criteria) this;
        }

        public Criteria andOutputyearGreaterThan(String value) {
            addCriterion("outputyear >", value, "outputyear");
            return (Criteria) this;
        }

        public Criteria andOutputyearGreaterThanOrEqualTo(String value) {
            addCriterion("outputyear >=", value, "outputyear");
            return (Criteria) this;
        }

        public Criteria andOutputyearLessThan(String value) {
            addCriterion("outputyear <", value, "outputyear");
            return (Criteria) this;
        }

        public Criteria andOutputyearLessThanOrEqualTo(String value) {
            addCriterion("outputyear <=", value, "outputyear");
            return (Criteria) this;
        }

        public Criteria andOutputyearLike(String value) {
            addCriterion("outputyear like", value, "outputyear");
            return (Criteria) this;
        }

        public Criteria andOutputyearNotLike(String value) {
            addCriterion("outputyear not like", value, "outputyear");
            return (Criteria) this;
        }

        public Criteria andOutputyearIn(List<String> values) {
            addCriterion("outputyear in", values, "outputyear");
            return (Criteria) this;
        }

        public Criteria andOutputyearNotIn(List<String> values) {
            addCriterion("outputyear not in", values, "outputyear");
            return (Criteria) this;
        }

        public Criteria andOutputyearBetween(String value1, String value2) {
            addCriterion("outputyear between", value1, value2, "outputyear");
            return (Criteria) this;
        }

        public Criteria andOutputyearNotBetween(String value1, String value2) {
            addCriterion("outputyear not between", value1, value2, "outputyear");
            return (Criteria) this;
        }

        public Criteria andOutputIsNull() {
            addCriterion("output is null");
            return (Criteria) this;
        }

        public Criteria andOutputIsNotNull() {
            addCriterion("output is not null");
            return (Criteria) this;
        }

        public Criteria andOutputEqualTo(String value) {
            addCriterion("output =", value, "output");
            return (Criteria) this;
        }

        public Criteria andOutputNotEqualTo(String value) {
            addCriterion("output <>", value, "output");
            return (Criteria) this;
        }

        public Criteria andOutputGreaterThan(String value) {
            addCriterion("output >", value, "output");
            return (Criteria) this;
        }

        public Criteria andOutputGreaterThanOrEqualTo(String value) {
            addCriterion("output >=", value, "output");
            return (Criteria) this;
        }

        public Criteria andOutputLessThan(String value) {
            addCriterion("output <", value, "output");
            return (Criteria) this;
        }

        public Criteria andOutputLessThanOrEqualTo(String value) {
            addCriterion("output <=", value, "output");
            return (Criteria) this;
        }

        public Criteria andOutputLike(String value) {
            addCriterion("output like", value, "output");
            return (Criteria) this;
        }

        public Criteria andOutputNotLike(String value) {
            addCriterion("output not like", value, "output");
            return (Criteria) this;
        }

        public Criteria andOutputIn(List<String> values) {
            addCriterion("output in", values, "output");
            return (Criteria) this;
        }

        public Criteria andOutputNotIn(List<String> values) {
            addCriterion("output not in", values, "output");
            return (Criteria) this;
        }

        public Criteria andOutputBetween(String value1, String value2) {
            addCriterion("output between", value1, value2, "output");
            return (Criteria) this;
        }

        public Criteria andOutputNotBetween(String value1, String value2) {
            addCriterion("output not between", value1, value2, "output");
            return (Criteria) this;
        }

        public Criteria andOutputlevelIsNull() {
            addCriterion("outputlevel is null");
            return (Criteria) this;
        }

        public Criteria andOutputlevelIsNotNull() {
            addCriterion("outputlevel is not null");
            return (Criteria) this;
        }

        public Criteria andOutputlevelEqualTo(String value) {
            addCriterion("outputlevel =", value, "outputlevel");
            return (Criteria) this;
        }

        public Criteria andOutputlevelNotEqualTo(String value) {
            addCriterion("outputlevel <>", value, "outputlevel");
            return (Criteria) this;
        }

        public Criteria andOutputlevelGreaterThan(String value) {
            addCriterion("outputlevel >", value, "outputlevel");
            return (Criteria) this;
        }

        public Criteria andOutputlevelGreaterThanOrEqualTo(String value) {
            addCriterion("outputlevel >=", value, "outputlevel");
            return (Criteria) this;
        }

        public Criteria andOutputlevelLessThan(String value) {
            addCriterion("outputlevel <", value, "outputlevel");
            return (Criteria) this;
        }

        public Criteria andOutputlevelLessThanOrEqualTo(String value) {
            addCriterion("outputlevel <=", value, "outputlevel");
            return (Criteria) this;
        }

        public Criteria andOutputlevelLike(String value) {
            addCriterion("outputlevel like", value, "outputlevel");
            return (Criteria) this;
        }

        public Criteria andOutputlevelNotLike(String value) {
            addCriterion("outputlevel not like", value, "outputlevel");
            return (Criteria) this;
        }

        public Criteria andOutputlevelIn(List<String> values) {
            addCriterion("outputlevel in", values, "outputlevel");
            return (Criteria) this;
        }

        public Criteria andOutputlevelNotIn(List<String> values) {
            addCriterion("outputlevel not in", values, "outputlevel");
            return (Criteria) this;
        }

        public Criteria andOutputlevelBetween(String value1, String value2) {
            addCriterion("outputlevel between", value1, value2, "outputlevel");
            return (Criteria) this;
        }

        public Criteria andOutputlevelNotBetween(String value1, String value2) {
            addCriterion("outputlevel not between", value1, value2, "outputlevel");
            return (Criteria) this;
        }

        public Criteria andPreestimatecategoryIsNull() {
            addCriterion("preestimatecategory is null");
            return (Criteria) this;
        }

        public Criteria andPreestimatecategoryIsNotNull() {
            addCriterion("preestimatecategory is not null");
            return (Criteria) this;
        }

        public Criteria andPreestimatecategoryEqualTo(String value) {
            addCriterion("preestimatecategory =", value, "preestimatecategory");
            return (Criteria) this;
        }

        public Criteria andPreestimatecategoryNotEqualTo(String value) {
            addCriterion("preestimatecategory <>", value, "preestimatecategory");
            return (Criteria) this;
        }

        public Criteria andPreestimatecategoryGreaterThan(String value) {
            addCriterion("preestimatecategory >", value, "preestimatecategory");
            return (Criteria) this;
        }

        public Criteria andPreestimatecategoryGreaterThanOrEqualTo(String value) {
            addCriterion("preestimatecategory >=", value, "preestimatecategory");
            return (Criteria) this;
        }

        public Criteria andPreestimatecategoryLessThan(String value) {
            addCriterion("preestimatecategory <", value, "preestimatecategory");
            return (Criteria) this;
        }

        public Criteria andPreestimatecategoryLessThanOrEqualTo(String value) {
            addCriterion("preestimatecategory <=", value, "preestimatecategory");
            return (Criteria) this;
        }

        public Criteria andPreestimatecategoryLike(String value) {
            addCriterion("preestimatecategory like", value, "preestimatecategory");
            return (Criteria) this;
        }

        public Criteria andPreestimatecategoryNotLike(String value) {
            addCriterion("preestimatecategory not like", value, "preestimatecategory");
            return (Criteria) this;
        }

        public Criteria andPreestimatecategoryIn(List<String> values) {
            addCriterion("preestimatecategory in", values, "preestimatecategory");
            return (Criteria) this;
        }

        public Criteria andPreestimatecategoryNotIn(List<String> values) {
            addCriterion("preestimatecategory not in", values, "preestimatecategory");
            return (Criteria) this;
        }

        public Criteria andPreestimatecategoryBetween(String value1, String value2) {
            addCriterion("preestimatecategory between", value1, value2, "preestimatecategory");
            return (Criteria) this;
        }

        public Criteria andPreestimatecategoryNotBetween(String value1, String value2) {
            addCriterion("preestimatecategory not between", value1, value2, "preestimatecategory");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputIsNull() {
            addCriterion("preestimateoutput is null");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputIsNotNull() {
            addCriterion("preestimateoutput is not null");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputEqualTo(BigDecimal value) {
            addCriterion("preestimateoutput =", value, "preestimateoutput");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputNotEqualTo(BigDecimal value) {
            addCriterion("preestimateoutput <>", value, "preestimateoutput");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputGreaterThan(BigDecimal value) {
            addCriterion("preestimateoutput >", value, "preestimateoutput");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("preestimateoutput >=", value, "preestimateoutput");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputLessThan(BigDecimal value) {
            addCriterion("preestimateoutput <", value, "preestimateoutput");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputLessThanOrEqualTo(BigDecimal value) {
            addCriterion("preestimateoutput <=", value, "preestimateoutput");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputIn(List<BigDecimal> values) {
            addCriterion("preestimateoutput in", values, "preestimateoutput");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputNotIn(List<BigDecimal> values) {
            addCriterion("preestimateoutput not in", values, "preestimateoutput");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("preestimateoutput between", value1, value2, "preestimateoutput");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("preestimateoutput not between", value1, value2, "preestimateoutput");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputunitIsNull() {
            addCriterion("preestimateoutputunit is null");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputunitIsNotNull() {
            addCriterion("preestimateoutputunit is not null");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputunitEqualTo(String value) {
            addCriterion("preestimateoutputunit =", value, "preestimateoutputunit");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputunitNotEqualTo(String value) {
            addCriterion("preestimateoutputunit <>", value, "preestimateoutputunit");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputunitGreaterThan(String value) {
            addCriterion("preestimateoutputunit >", value, "preestimateoutputunit");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputunitGreaterThanOrEqualTo(String value) {
            addCriterion("preestimateoutputunit >=", value, "preestimateoutputunit");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputunitLessThan(String value) {
            addCriterion("preestimateoutputunit <", value, "preestimateoutputunit");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputunitLessThanOrEqualTo(String value) {
            addCriterion("preestimateoutputunit <=", value, "preestimateoutputunit");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputunitLike(String value) {
            addCriterion("preestimateoutputunit like", value, "preestimateoutputunit");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputunitNotLike(String value) {
            addCriterion("preestimateoutputunit not like", value, "preestimateoutputunit");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputunitIn(List<String> values) {
            addCriterion("preestimateoutputunit in", values, "preestimateoutputunit");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputunitNotIn(List<String> values) {
            addCriterion("preestimateoutputunit not in", values, "preestimateoutputunit");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputunitBetween(String value1, String value2) {
            addCriterion("preestimateoutputunit between", value1, value2, "preestimateoutputunit");
            return (Criteria) this;
        }

        public Criteria andPreestimateoutputunitNotBetween(String value1, String value2) {
            addCriterion("preestimateoutputunit not between", value1, value2, "preestimateoutputunit");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputIsNull() {
            addCriterion("preestimatelandoutput is null");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputIsNotNull() {
            addCriterion("preestimatelandoutput is not null");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputEqualTo(BigDecimal value) {
            addCriterion("preestimatelandoutput =", value, "preestimatelandoutput");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputNotEqualTo(BigDecimal value) {
            addCriterion("preestimatelandoutput <>", value, "preestimatelandoutput");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputGreaterThan(BigDecimal value) {
            addCriterion("preestimatelandoutput >", value, "preestimatelandoutput");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("preestimatelandoutput >=", value, "preestimatelandoutput");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputLessThan(BigDecimal value) {
            addCriterion("preestimatelandoutput <", value, "preestimatelandoutput");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputLessThanOrEqualTo(BigDecimal value) {
            addCriterion("preestimatelandoutput <=", value, "preestimatelandoutput");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputIn(List<BigDecimal> values) {
            addCriterion("preestimatelandoutput in", values, "preestimatelandoutput");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputNotIn(List<BigDecimal> values) {
            addCriterion("preestimatelandoutput not in", values, "preestimatelandoutput");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("preestimatelandoutput between", value1, value2, "preestimatelandoutput");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("preestimatelandoutput not between", value1, value2, "preestimatelandoutput");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputunitIsNull() {
            addCriterion("preestimatelandoutputunit is null");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputunitIsNotNull() {
            addCriterion("preestimatelandoutputunit is not null");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputunitEqualTo(String value) {
            addCriterion("preestimatelandoutputunit =", value, "preestimatelandoutputunit");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputunitNotEqualTo(String value) {
            addCriterion("preestimatelandoutputunit <>", value, "preestimatelandoutputunit");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputunitGreaterThan(String value) {
            addCriterion("preestimatelandoutputunit >", value, "preestimatelandoutputunit");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputunitGreaterThanOrEqualTo(String value) {
            addCriterion("preestimatelandoutputunit >=", value, "preestimatelandoutputunit");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputunitLessThan(String value) {
            addCriterion("preestimatelandoutputunit <", value, "preestimatelandoutputunit");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputunitLessThanOrEqualTo(String value) {
            addCriterion("preestimatelandoutputunit <=", value, "preestimatelandoutputunit");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputunitLike(String value) {
            addCriterion("preestimatelandoutputunit like", value, "preestimatelandoutputunit");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputunitNotLike(String value) {
            addCriterion("preestimatelandoutputunit not like", value, "preestimatelandoutputunit");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputunitIn(List<String> values) {
            addCriterion("preestimatelandoutputunit in", values, "preestimatelandoutputunit");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputunitNotIn(List<String> values) {
            addCriterion("preestimatelandoutputunit not in", values, "preestimatelandoutputunit");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputunitBetween(String value1, String value2) {
            addCriterion("preestimatelandoutputunit between", value1, value2, "preestimatelandoutputunit");
            return (Criteria) this;
        }

        public Criteria andPreestimatelandoutputunitNotBetween(String value1, String value2) {
            addCriterion("preestimatelandoutputunit not between", value1, value2, "preestimatelandoutputunit");
            return (Criteria) this;
        }

        public Criteria andActualoutputIsNull() {
            addCriterion("actualoutput is null");
            return (Criteria) this;
        }

        public Criteria andActualoutputIsNotNull() {
            addCriterion("actualoutput is not null");
            return (Criteria) this;
        }

        public Criteria andActualoutputEqualTo(BigDecimal value) {
            addCriterion("actualoutput =", value, "actualoutput");
            return (Criteria) this;
        }

        public Criteria andActualoutputNotEqualTo(BigDecimal value) {
            addCriterion("actualoutput <>", value, "actualoutput");
            return (Criteria) this;
        }

        public Criteria andActualoutputGreaterThan(BigDecimal value) {
            addCriterion("actualoutput >", value, "actualoutput");
            return (Criteria) this;
        }

        public Criteria andActualoutputGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("actualoutput >=", value, "actualoutput");
            return (Criteria) this;
        }

        public Criteria andActualoutputLessThan(BigDecimal value) {
            addCriterion("actualoutput <", value, "actualoutput");
            return (Criteria) this;
        }

        public Criteria andActualoutputLessThanOrEqualTo(BigDecimal value) {
            addCriterion("actualoutput <=", value, "actualoutput");
            return (Criteria) this;
        }

        public Criteria andActualoutputIn(List<BigDecimal> values) {
            addCriterion("actualoutput in", values, "actualoutput");
            return (Criteria) this;
        }

        public Criteria andActualoutputNotIn(List<BigDecimal> values) {
            addCriterion("actualoutput not in", values, "actualoutput");
            return (Criteria) this;
        }

        public Criteria andActualoutputBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("actualoutput between", value1, value2, "actualoutput");
            return (Criteria) this;
        }

        public Criteria andActualoutputNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("actualoutput not between", value1, value2, "actualoutput");
            return (Criteria) this;
        }

        public Criteria andActualoutputunitIsNull() {
            addCriterion("actualoutputunit is null");
            return (Criteria) this;
        }

        public Criteria andActualoutputunitIsNotNull() {
            addCriterion("actualoutputunit is not null");
            return (Criteria) this;
        }

        public Criteria andActualoutputunitEqualTo(String value) {
            addCriterion("actualoutputunit =", value, "actualoutputunit");
            return (Criteria) this;
        }

        public Criteria andActualoutputunitNotEqualTo(String value) {
            addCriterion("actualoutputunit <>", value, "actualoutputunit");
            return (Criteria) this;
        }

        public Criteria andActualoutputunitGreaterThan(String value) {
            addCriterion("actualoutputunit >", value, "actualoutputunit");
            return (Criteria) this;
        }

        public Criteria andActualoutputunitGreaterThanOrEqualTo(String value) {
            addCriterion("actualoutputunit >=", value, "actualoutputunit");
            return (Criteria) this;
        }

        public Criteria andActualoutputunitLessThan(String value) {
            addCriterion("actualoutputunit <", value, "actualoutputunit");
            return (Criteria) this;
        }

        public Criteria andActualoutputunitLessThanOrEqualTo(String value) {
            addCriterion("actualoutputunit <=", value, "actualoutputunit");
            return (Criteria) this;
        }

        public Criteria andActualoutputunitLike(String value) {
            addCriterion("actualoutputunit like", value, "actualoutputunit");
            return (Criteria) this;
        }

        public Criteria andActualoutputunitNotLike(String value) {
            addCriterion("actualoutputunit not like", value, "actualoutputunit");
            return (Criteria) this;
        }

        public Criteria andActualoutputunitIn(List<String> values) {
            addCriterion("actualoutputunit in", values, "actualoutputunit");
            return (Criteria) this;
        }

        public Criteria andActualoutputunitNotIn(List<String> values) {
            addCriterion("actualoutputunit not in", values, "actualoutputunit");
            return (Criteria) this;
        }

        public Criteria andActualoutputunitBetween(String value1, String value2) {
            addCriterion("actualoutputunit between", value1, value2, "actualoutputunit");
            return (Criteria) this;
        }

        public Criteria andActualoutputunitNotBetween(String value1, String value2) {
            addCriterion("actualoutputunit not between", value1, value2, "actualoutputunit");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}