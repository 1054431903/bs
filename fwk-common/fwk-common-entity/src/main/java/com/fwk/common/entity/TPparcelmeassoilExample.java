package com.fwk.common.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TPparcelmeassoilExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TPparcelmeassoilExample() {
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

        public Criteria andParcelidIsNull() {
            addCriterion("parcelid is null");
            return (Criteria) this;
        }

        public Criteria andParcelidIsNotNull() {
            addCriterion("parcelid is not null");
            return (Criteria) this;
        }

        public Criteria andParcelidEqualTo(Integer value) {
            addCriterion("parcelid =", value, "parcelid");
            return (Criteria) this;
        }

        public Criteria andParcelidNotEqualTo(Integer value) {
            addCriterion("parcelid <>", value, "parcelid");
            return (Criteria) this;
        }

        public Criteria andParcelidGreaterThan(Integer value) {
            addCriterion("parcelid >", value, "parcelid");
            return (Criteria) this;
        }

        public Criteria andParcelidGreaterThanOrEqualTo(Integer value) {
            addCriterion("parcelid >=", value, "parcelid");
            return (Criteria) this;
        }

        public Criteria andParcelidLessThan(Integer value) {
            addCriterion("parcelid <", value, "parcelid");
            return (Criteria) this;
        }

        public Criteria andParcelidLessThanOrEqualTo(Integer value) {
            addCriterion("parcelid <=", value, "parcelid");
            return (Criteria) this;
        }

        public Criteria andParcelidIn(List<Integer> values) {
            addCriterion("parcelid in", values, "parcelid");
            return (Criteria) this;
        }

        public Criteria andParcelidNotIn(List<Integer> values) {
            addCriterion("parcelid not in", values, "parcelid");
            return (Criteria) this;
        }

        public Criteria andParcelidBetween(Integer value1, Integer value2) {
            addCriterion("parcelid between", value1, value2, "parcelid");
            return (Criteria) this;
        }

        public Criteria andParcelidNotBetween(Integer value1, Integer value2) {
            addCriterion("parcelid not between", value1, value2, "parcelid");
            return (Criteria) this;
        }

        public Criteria andSoiltextureIsNull() {
            addCriterion("soiltexture is null");
            return (Criteria) this;
        }

        public Criteria andSoiltextureIsNotNull() {
            addCriterion("soiltexture is not null");
            return (Criteria) this;
        }

        public Criteria andSoiltextureEqualTo(String value) {
            addCriterion("soiltexture =", value, "soiltexture");
            return (Criteria) this;
        }

        public Criteria andSoiltextureNotEqualTo(String value) {
            addCriterion("soiltexture <>", value, "soiltexture");
            return (Criteria) this;
        }

        public Criteria andSoiltextureGreaterThan(String value) {
            addCriterion("soiltexture >", value, "soiltexture");
            return (Criteria) this;
        }

        public Criteria andSoiltextureGreaterThanOrEqualTo(String value) {
            addCriterion("soiltexture >=", value, "soiltexture");
            return (Criteria) this;
        }

        public Criteria andSoiltextureLessThan(String value) {
            addCriterion("soiltexture <", value, "soiltexture");
            return (Criteria) this;
        }

        public Criteria andSoiltextureLessThanOrEqualTo(String value) {
            addCriterion("soiltexture <=", value, "soiltexture");
            return (Criteria) this;
        }

        public Criteria andSoiltextureLike(String value) {
            addCriterion("soiltexture like", value, "soiltexture");
            return (Criteria) this;
        }

        public Criteria andSoiltextureNotLike(String value) {
            addCriterion("soiltexture not like", value, "soiltexture");
            return (Criteria) this;
        }

        public Criteria andSoiltextureIn(List<String> values) {
            addCriterion("soiltexture in", values, "soiltexture");
            return (Criteria) this;
        }

        public Criteria andSoiltextureNotIn(List<String> values) {
            addCriterion("soiltexture not in", values, "soiltexture");
            return (Criteria) this;
        }

        public Criteria andSoiltextureBetween(String value1, String value2) {
            addCriterion("soiltexture between", value1, value2, "soiltexture");
            return (Criteria) this;
        }

        public Criteria andSoiltextureNotBetween(String value1, String value2) {
            addCriterion("soiltexture not between", value1, value2, "soiltexture");
            return (Criteria) this;
        }

        public Criteria andSampltimeIsNull() {
            addCriterion("sampltime is null");
            return (Criteria) this;
        }

        public Criteria andSampltimeIsNotNull() {
            addCriterion("sampltime is not null");
            return (Criteria) this;
        }

        public Criteria andSampltimeEqualTo(Date value) {
            addCriterion("sampltime =", value, "sampltime");
            return (Criteria) this;
        }

        public Criteria andSampltimeNotEqualTo(Date value) {
            addCriterion("sampltime <>", value, "sampltime");
            return (Criteria) this;
        }

        public Criteria andSampltimeGreaterThan(Date value) {
            addCriterion("sampltime >", value, "sampltime");
            return (Criteria) this;
        }

        public Criteria andSampltimeGreaterThanOrEqualTo(Date value) {
            addCriterion("sampltime >=", value, "sampltime");
            return (Criteria) this;
        }

        public Criteria andSampltimeLessThan(Date value) {
            addCriterion("sampltime <", value, "sampltime");
            return (Criteria) this;
        }

        public Criteria andSampltimeLessThanOrEqualTo(Date value) {
            addCriterion("sampltime <=", value, "sampltime");
            return (Criteria) this;
        }

        public Criteria andSampltimeIn(List<Date> values) {
            addCriterion("sampltime in", values, "sampltime");
            return (Criteria) this;
        }

        public Criteria andSampltimeNotIn(List<Date> values) {
            addCriterion("sampltime not in", values, "sampltime");
            return (Criteria) this;
        }

        public Criteria andSampltimeBetween(Date value1, Date value2) {
            addCriterion("sampltime between", value1, value2, "sampltime");
            return (Criteria) this;
        }

        public Criteria andSampltimeNotBetween(Date value1, Date value2) {
            addCriterion("sampltime not between", value1, value2, "sampltime");
            return (Criteria) this;
        }

        public Criteria andMeastimeIsNull() {
            addCriterion("meastime is null");
            return (Criteria) this;
        }

        public Criteria andMeastimeIsNotNull() {
            addCriterion("meastime is not null");
            return (Criteria) this;
        }

        public Criteria andMeastimeEqualTo(Date value) {
            addCriterion("meastime =", value, "meastime");
            return (Criteria) this;
        }

        public Criteria andMeastimeNotEqualTo(Date value) {
            addCriterion("meastime <>", value, "meastime");
            return (Criteria) this;
        }

        public Criteria andMeastimeGreaterThan(Date value) {
            addCriterion("meastime >", value, "meastime");
            return (Criteria) this;
        }

        public Criteria andMeastimeGreaterThanOrEqualTo(Date value) {
            addCriterion("meastime >=", value, "meastime");
            return (Criteria) this;
        }

        public Criteria andMeastimeLessThan(Date value) {
            addCriterion("meastime <", value, "meastime");
            return (Criteria) this;
        }

        public Criteria andMeastimeLessThanOrEqualTo(Date value) {
            addCriterion("meastime <=", value, "meastime");
            return (Criteria) this;
        }

        public Criteria andMeastimeIn(List<Date> values) {
            addCriterion("meastime in", values, "meastime");
            return (Criteria) this;
        }

        public Criteria andMeastimeNotIn(List<Date> values) {
            addCriterion("meastime not in", values, "meastime");
            return (Criteria) this;
        }

        public Criteria andMeastimeBetween(Date value1, Date value2) {
            addCriterion("meastime between", value1, value2, "meastime");
            return (Criteria) this;
        }

        public Criteria andMeastimeNotBetween(Date value1, Date value2) {
            addCriterion("meastime not between", value1, value2, "meastime");
            return (Criteria) this;
        }

        public Criteria andOrgnameIsNull() {
            addCriterion("orgname is null");
            return (Criteria) this;
        }

        public Criteria andOrgnameIsNotNull() {
            addCriterion("orgname is not null");
            return (Criteria) this;
        }

        public Criteria andOrgnameEqualTo(String value) {
            addCriterion("orgname =", value, "orgname");
            return (Criteria) this;
        }

        public Criteria andOrgnameNotEqualTo(String value) {
            addCriterion("orgname <>", value, "orgname");
            return (Criteria) this;
        }

        public Criteria andOrgnameGreaterThan(String value) {
            addCriterion("orgname >", value, "orgname");
            return (Criteria) this;
        }

        public Criteria andOrgnameGreaterThanOrEqualTo(String value) {
            addCriterion("orgname >=", value, "orgname");
            return (Criteria) this;
        }

        public Criteria andOrgnameLessThan(String value) {
            addCriterion("orgname <", value, "orgname");
            return (Criteria) this;
        }

        public Criteria andOrgnameLessThanOrEqualTo(String value) {
            addCriterion("orgname <=", value, "orgname");
            return (Criteria) this;
        }

        public Criteria andOrgnameLike(String value) {
            addCriterion("orgname like", value, "orgname");
            return (Criteria) this;
        }

        public Criteria andOrgnameNotLike(String value) {
            addCriterion("orgname not like", value, "orgname");
            return (Criteria) this;
        }

        public Criteria andOrgnameIn(List<String> values) {
            addCriterion("orgname in", values, "orgname");
            return (Criteria) this;
        }

        public Criteria andOrgnameNotIn(List<String> values) {
            addCriterion("orgname not in", values, "orgname");
            return (Criteria) this;
        }

        public Criteria andOrgnameBetween(String value1, String value2) {
            addCriterion("orgname between", value1, value2, "orgname");
            return (Criteria) this;
        }

        public Criteria andOrgnameNotBetween(String value1, String value2) {
            addCriterion("orgname not between", value1, value2, "orgname");
            return (Criteria) this;
        }

        public Criteria andSamplnumIsNull() {
            addCriterion("samplnum is null");
            return (Criteria) this;
        }

        public Criteria andSamplnumIsNotNull() {
            addCriterion("samplnum is not null");
            return (Criteria) this;
        }

        public Criteria andSamplnumEqualTo(String value) {
            addCriterion("samplnum =", value, "samplnum");
            return (Criteria) this;
        }

        public Criteria andSamplnumNotEqualTo(String value) {
            addCriterion("samplnum <>", value, "samplnum");
            return (Criteria) this;
        }

        public Criteria andSamplnumGreaterThan(String value) {
            addCriterion("samplnum >", value, "samplnum");
            return (Criteria) this;
        }

        public Criteria andSamplnumGreaterThanOrEqualTo(String value) {
            addCriterion("samplnum >=", value, "samplnum");
            return (Criteria) this;
        }

        public Criteria andSamplnumLessThan(String value) {
            addCriterion("samplnum <", value, "samplnum");
            return (Criteria) this;
        }

        public Criteria andSamplnumLessThanOrEqualTo(String value) {
            addCriterion("samplnum <=", value, "samplnum");
            return (Criteria) this;
        }

        public Criteria andSamplnumLike(String value) {
            addCriterion("samplnum like", value, "samplnum");
            return (Criteria) this;
        }

        public Criteria andSamplnumNotLike(String value) {
            addCriterion("samplnum not like", value, "samplnum");
            return (Criteria) this;
        }

        public Criteria andSamplnumIn(List<String> values) {
            addCriterion("samplnum in", values, "samplnum");
            return (Criteria) this;
        }

        public Criteria andSamplnumNotIn(List<String> values) {
            addCriterion("samplnum not in", values, "samplnum");
            return (Criteria) this;
        }

        public Criteria andSamplnumBetween(String value1, String value2) {
            addCriterion("samplnum between", value1, value2, "samplnum");
            return (Criteria) this;
        }

        public Criteria andSamplnumNotBetween(String value1, String value2) {
            addCriterion("samplnum not between", value1, value2, "samplnum");
            return (Criteria) this;
        }

        public Criteria andSoilgenreIsNull() {
            addCriterion("soilgenre is null");
            return (Criteria) this;
        }

        public Criteria andSoilgenreIsNotNull() {
            addCriterion("soilgenre is not null");
            return (Criteria) this;
        }

        public Criteria andSoilgenreEqualTo(String value) {
            addCriterion("soilgenre =", value, "soilgenre");
            return (Criteria) this;
        }

        public Criteria andSoilgenreNotEqualTo(String value) {
            addCriterion("soilgenre <>", value, "soilgenre");
            return (Criteria) this;
        }

        public Criteria andSoilgenreGreaterThan(String value) {
            addCriterion("soilgenre >", value, "soilgenre");
            return (Criteria) this;
        }

        public Criteria andSoilgenreGreaterThanOrEqualTo(String value) {
            addCriterion("soilgenre >=", value, "soilgenre");
            return (Criteria) this;
        }

        public Criteria andSoilgenreLessThan(String value) {
            addCriterion("soilgenre <", value, "soilgenre");
            return (Criteria) this;
        }

        public Criteria andSoilgenreLessThanOrEqualTo(String value) {
            addCriterion("soilgenre <=", value, "soilgenre");
            return (Criteria) this;
        }

        public Criteria andSoilgenreLike(String value) {
            addCriterion("soilgenre like", value, "soilgenre");
            return (Criteria) this;
        }

        public Criteria andSoilgenreNotLike(String value) {
            addCriterion("soilgenre not like", value, "soilgenre");
            return (Criteria) this;
        }

        public Criteria andSoilgenreIn(List<String> values) {
            addCriterion("soilgenre in", values, "soilgenre");
            return (Criteria) this;
        }

        public Criteria andSoilgenreNotIn(List<String> values) {
            addCriterion("soilgenre not in", values, "soilgenre");
            return (Criteria) this;
        }

        public Criteria andSoilgenreBetween(String value1, String value2) {
            addCriterion("soilgenre between", value1, value2, "soilgenre");
            return (Criteria) this;
        }

        public Criteria andSoilgenreNotBetween(String value1, String value2) {
            addCriterion("soilgenre not between", value1, value2, "soilgenre");
            return (Criteria) this;
        }

        public Criteria andNitrogenIsNull() {
            addCriterion("nitrogen is null");
            return (Criteria) this;
        }

        public Criteria andNitrogenIsNotNull() {
            addCriterion("nitrogen is not null");
            return (Criteria) this;
        }

        public Criteria andNitrogenEqualTo(BigDecimal value) {
            addCriterion("nitrogen =", value, "nitrogen");
            return (Criteria) this;
        }

        public Criteria andNitrogenNotEqualTo(BigDecimal value) {
            addCriterion("nitrogen <>", value, "nitrogen");
            return (Criteria) this;
        }

        public Criteria andNitrogenGreaterThan(BigDecimal value) {
            addCriterion("nitrogen >", value, "nitrogen");
            return (Criteria) this;
        }

        public Criteria andNitrogenGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("nitrogen >=", value, "nitrogen");
            return (Criteria) this;
        }

        public Criteria andNitrogenLessThan(BigDecimal value) {
            addCriterion("nitrogen <", value, "nitrogen");
            return (Criteria) this;
        }

        public Criteria andNitrogenLessThanOrEqualTo(BigDecimal value) {
            addCriterion("nitrogen <=", value, "nitrogen");
            return (Criteria) this;
        }

        public Criteria andNitrogenIn(List<BigDecimal> values) {
            addCriterion("nitrogen in", values, "nitrogen");
            return (Criteria) this;
        }

        public Criteria andNitrogenNotIn(List<BigDecimal> values) {
            addCriterion("nitrogen not in", values, "nitrogen");
            return (Criteria) this;
        }

        public Criteria andNitrogenBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("nitrogen between", value1, value2, "nitrogen");
            return (Criteria) this;
        }

        public Criteria andNitrogenNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("nitrogen not between", value1, value2, "nitrogen");
            return (Criteria) this;
        }

        public Criteria andPhosphorusIsNull() {
            addCriterion("phosphorus is null");
            return (Criteria) this;
        }

        public Criteria andPhosphorusIsNotNull() {
            addCriterion("phosphorus is not null");
            return (Criteria) this;
        }

        public Criteria andPhosphorusEqualTo(BigDecimal value) {
            addCriterion("phosphorus =", value, "phosphorus");
            return (Criteria) this;
        }

        public Criteria andPhosphorusNotEqualTo(BigDecimal value) {
            addCriterion("phosphorus <>", value, "phosphorus");
            return (Criteria) this;
        }

        public Criteria andPhosphorusGreaterThan(BigDecimal value) {
            addCriterion("phosphorus >", value, "phosphorus");
            return (Criteria) this;
        }

        public Criteria andPhosphorusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("phosphorus >=", value, "phosphorus");
            return (Criteria) this;
        }

        public Criteria andPhosphorusLessThan(BigDecimal value) {
            addCriterion("phosphorus <", value, "phosphorus");
            return (Criteria) this;
        }

        public Criteria andPhosphorusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("phosphorus <=", value, "phosphorus");
            return (Criteria) this;
        }

        public Criteria andPhosphorusIn(List<BigDecimal> values) {
            addCriterion("phosphorus in", values, "phosphorus");
            return (Criteria) this;
        }

        public Criteria andPhosphorusNotIn(List<BigDecimal> values) {
            addCriterion("phosphorus not in", values, "phosphorus");
            return (Criteria) this;
        }

        public Criteria andPhosphorusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("phosphorus between", value1, value2, "phosphorus");
            return (Criteria) this;
        }

        public Criteria andPhosphorusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("phosphorus not between", value1, value2, "phosphorus");
            return (Criteria) this;
        }

        public Criteria andPotassiumIsNull() {
            addCriterion("potassium is null");
            return (Criteria) this;
        }

        public Criteria andPotassiumIsNotNull() {
            addCriterion("potassium is not null");
            return (Criteria) this;
        }

        public Criteria andPotassiumEqualTo(BigDecimal value) {
            addCriterion("potassium =", value, "potassium");
            return (Criteria) this;
        }

        public Criteria andPotassiumNotEqualTo(BigDecimal value) {
            addCriterion("potassium <>", value, "potassium");
            return (Criteria) this;
        }

        public Criteria andPotassiumGreaterThan(BigDecimal value) {
            addCriterion("potassium >", value, "potassium");
            return (Criteria) this;
        }

        public Criteria andPotassiumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("potassium >=", value, "potassium");
            return (Criteria) this;
        }

        public Criteria andPotassiumLessThan(BigDecimal value) {
            addCriterion("potassium <", value, "potassium");
            return (Criteria) this;
        }

        public Criteria andPotassiumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("potassium <=", value, "potassium");
            return (Criteria) this;
        }

        public Criteria andPotassiumIn(List<BigDecimal> values) {
            addCriterion("potassium in", values, "potassium");
            return (Criteria) this;
        }

        public Criteria andPotassiumNotIn(List<BigDecimal> values) {
            addCriterion("potassium not in", values, "potassium");
            return (Criteria) this;
        }

        public Criteria andPotassiumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("potassium between", value1, value2, "potassium");
            return (Criteria) this;
        }

        public Criteria andPotassiumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("potassium not between", value1, value2, "potassium");
            return (Criteria) this;
        }

        public Criteria andPhvalueIsNull() {
            addCriterion("PHvalue is null");
            return (Criteria) this;
        }

        public Criteria andPhvalueIsNotNull() {
            addCriterion("PHvalue is not null");
            return (Criteria) this;
        }

        public Criteria andPhvalueEqualTo(BigDecimal value) {
            addCriterion("PHvalue =", value, "phvalue");
            return (Criteria) this;
        }

        public Criteria andPhvalueNotEqualTo(BigDecimal value) {
            addCriterion("PHvalue <>", value, "phvalue");
            return (Criteria) this;
        }

        public Criteria andPhvalueGreaterThan(BigDecimal value) {
            addCriterion("PHvalue >", value, "phvalue");
            return (Criteria) this;
        }

        public Criteria andPhvalueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PHvalue >=", value, "phvalue");
            return (Criteria) this;
        }

        public Criteria andPhvalueLessThan(BigDecimal value) {
            addCriterion("PHvalue <", value, "phvalue");
            return (Criteria) this;
        }

        public Criteria andPhvalueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PHvalue <=", value, "phvalue");
            return (Criteria) this;
        }

        public Criteria andPhvalueIn(List<BigDecimal> values) {
            addCriterion("PHvalue in", values, "phvalue");
            return (Criteria) this;
        }

        public Criteria andPhvalueNotIn(List<BigDecimal> values) {
            addCriterion("PHvalue not in", values, "phvalue");
            return (Criteria) this;
        }

        public Criteria andPhvalueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PHvalue between", value1, value2, "phvalue");
            return (Criteria) this;
        }

        public Criteria andPhvalueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PHvalue not between", value1, value2, "phvalue");
            return (Criteria) this;
        }

        public Criteria andMatterIsNull() {
            addCriterion("matter is null");
            return (Criteria) this;
        }

        public Criteria andMatterIsNotNull() {
            addCriterion("matter is not null");
            return (Criteria) this;
        }

        public Criteria andMatterEqualTo(BigDecimal value) {
            addCriterion("matter =", value, "matter");
            return (Criteria) this;
        }

        public Criteria andMatterNotEqualTo(BigDecimal value) {
            addCriterion("matter <>", value, "matter");
            return (Criteria) this;
        }

        public Criteria andMatterGreaterThan(BigDecimal value) {
            addCriterion("matter >", value, "matter");
            return (Criteria) this;
        }

        public Criteria andMatterGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("matter >=", value, "matter");
            return (Criteria) this;
        }

        public Criteria andMatterLessThan(BigDecimal value) {
            addCriterion("matter <", value, "matter");
            return (Criteria) this;
        }

        public Criteria andMatterLessThanOrEqualTo(BigDecimal value) {
            addCriterion("matter <=", value, "matter");
            return (Criteria) this;
        }

        public Criteria andMatterIn(List<BigDecimal> values) {
            addCriterion("matter in", values, "matter");
            return (Criteria) this;
        }

        public Criteria andMatterNotIn(List<BigDecimal> values) {
            addCriterion("matter not in", values, "matter");
            return (Criteria) this;
        }

        public Criteria andMatterBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("matter between", value1, value2, "matter");
            return (Criteria) this;
        }

        public Criteria andMatterNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("matter not between", value1, value2, "matter");
            return (Criteria) this;
        }

        public Criteria andValidznIsNull() {
            addCriterion("validzn is null");
            return (Criteria) this;
        }

        public Criteria andValidznIsNotNull() {
            addCriterion("validzn is not null");
            return (Criteria) this;
        }

        public Criteria andValidznEqualTo(BigDecimal value) {
            addCriterion("validzn =", value, "validzn");
            return (Criteria) this;
        }

        public Criteria andValidznNotEqualTo(BigDecimal value) {
            addCriterion("validzn <>", value, "validzn");
            return (Criteria) this;
        }

        public Criteria andValidznGreaterThan(BigDecimal value) {
            addCriterion("validzn >", value, "validzn");
            return (Criteria) this;
        }

        public Criteria andValidznGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("validzn >=", value, "validzn");
            return (Criteria) this;
        }

        public Criteria andValidznLessThan(BigDecimal value) {
            addCriterion("validzn <", value, "validzn");
            return (Criteria) this;
        }

        public Criteria andValidznLessThanOrEqualTo(BigDecimal value) {
            addCriterion("validzn <=", value, "validzn");
            return (Criteria) this;
        }

        public Criteria andValidznIn(List<BigDecimal> values) {
            addCriterion("validzn in", values, "validzn");
            return (Criteria) this;
        }

        public Criteria andValidznNotIn(List<BigDecimal> values) {
            addCriterion("validzn not in", values, "validzn");
            return (Criteria) this;
        }

        public Criteria andValidznBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("validzn between", value1, value2, "validzn");
            return (Criteria) this;
        }

        public Criteria andValidznNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("validzn not between", value1, value2, "validzn");
            return (Criteria) this;
        }

        public Criteria andValidcuIsNull() {
            addCriterion("validcu is null");
            return (Criteria) this;
        }

        public Criteria andValidcuIsNotNull() {
            addCriterion("validcu is not null");
            return (Criteria) this;
        }

        public Criteria andValidcuEqualTo(BigDecimal value) {
            addCriterion("validcu =", value, "validcu");
            return (Criteria) this;
        }

        public Criteria andValidcuNotEqualTo(BigDecimal value) {
            addCriterion("validcu <>", value, "validcu");
            return (Criteria) this;
        }

        public Criteria andValidcuGreaterThan(BigDecimal value) {
            addCriterion("validcu >", value, "validcu");
            return (Criteria) this;
        }

        public Criteria andValidcuGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("validcu >=", value, "validcu");
            return (Criteria) this;
        }

        public Criteria andValidcuLessThan(BigDecimal value) {
            addCriterion("validcu <", value, "validcu");
            return (Criteria) this;
        }

        public Criteria andValidcuLessThanOrEqualTo(BigDecimal value) {
            addCriterion("validcu <=", value, "validcu");
            return (Criteria) this;
        }

        public Criteria andValidcuIn(List<BigDecimal> values) {
            addCriterion("validcu in", values, "validcu");
            return (Criteria) this;
        }

        public Criteria andValidcuNotIn(List<BigDecimal> values) {
            addCriterion("validcu not in", values, "validcu");
            return (Criteria) this;
        }

        public Criteria andValidcuBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("validcu between", value1, value2, "validcu");
            return (Criteria) this;
        }

        public Criteria andValidcuNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("validcu not between", value1, value2, "validcu");
            return (Criteria) this;
        }

        public Criteria andValidfeIsNull() {
            addCriterion("validfe is null");
            return (Criteria) this;
        }

        public Criteria andValidfeIsNotNull() {
            addCriterion("validfe is not null");
            return (Criteria) this;
        }

        public Criteria andValidfeEqualTo(BigDecimal value) {
            addCriterion("validfe =", value, "validfe");
            return (Criteria) this;
        }

        public Criteria andValidfeNotEqualTo(BigDecimal value) {
            addCriterion("validfe <>", value, "validfe");
            return (Criteria) this;
        }

        public Criteria andValidfeGreaterThan(BigDecimal value) {
            addCriterion("validfe >", value, "validfe");
            return (Criteria) this;
        }

        public Criteria andValidfeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("validfe >=", value, "validfe");
            return (Criteria) this;
        }

        public Criteria andValidfeLessThan(BigDecimal value) {
            addCriterion("validfe <", value, "validfe");
            return (Criteria) this;
        }

        public Criteria andValidfeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("validfe <=", value, "validfe");
            return (Criteria) this;
        }

        public Criteria andValidfeIn(List<BigDecimal> values) {
            addCriterion("validfe in", values, "validfe");
            return (Criteria) this;
        }

        public Criteria andValidfeNotIn(List<BigDecimal> values) {
            addCriterion("validfe not in", values, "validfe");
            return (Criteria) this;
        }

        public Criteria andValidfeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("validfe between", value1, value2, "validfe");
            return (Criteria) this;
        }

        public Criteria andValidfeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("validfe not between", value1, value2, "validfe");
            return (Criteria) this;
        }

        public Criteria andValidmnIsNull() {
            addCriterion("validmn is null");
            return (Criteria) this;
        }

        public Criteria andValidmnIsNotNull() {
            addCriterion("validmn is not null");
            return (Criteria) this;
        }

        public Criteria andValidmnEqualTo(BigDecimal value) {
            addCriterion("validmn =", value, "validmn");
            return (Criteria) this;
        }

        public Criteria andValidmnNotEqualTo(BigDecimal value) {
            addCriterion("validmn <>", value, "validmn");
            return (Criteria) this;
        }

        public Criteria andValidmnGreaterThan(BigDecimal value) {
            addCriterion("validmn >", value, "validmn");
            return (Criteria) this;
        }

        public Criteria andValidmnGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("validmn >=", value, "validmn");
            return (Criteria) this;
        }

        public Criteria andValidmnLessThan(BigDecimal value) {
            addCriterion("validmn <", value, "validmn");
            return (Criteria) this;
        }

        public Criteria andValidmnLessThanOrEqualTo(BigDecimal value) {
            addCriterion("validmn <=", value, "validmn");
            return (Criteria) this;
        }

        public Criteria andValidmnIn(List<BigDecimal> values) {
            addCriterion("validmn in", values, "validmn");
            return (Criteria) this;
        }

        public Criteria andValidmnNotIn(List<BigDecimal> values) {
            addCriterion("validmn not in", values, "validmn");
            return (Criteria) this;
        }

        public Criteria andValidmnBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("validmn between", value1, value2, "validmn");
            return (Criteria) this;
        }

        public Criteria andValidmnNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("validmn not between", value1, value2, "validmn");
            return (Criteria) this;
        }

        public Criteria andValidmoIsNull() {
            addCriterion("validmo is null");
            return (Criteria) this;
        }

        public Criteria andValidmoIsNotNull() {
            addCriterion("validmo is not null");
            return (Criteria) this;
        }

        public Criteria andValidmoEqualTo(BigDecimal value) {
            addCriterion("validmo =", value, "validmo");
            return (Criteria) this;
        }

        public Criteria andValidmoNotEqualTo(BigDecimal value) {
            addCriterion("validmo <>", value, "validmo");
            return (Criteria) this;
        }

        public Criteria andValidmoGreaterThan(BigDecimal value) {
            addCriterion("validmo >", value, "validmo");
            return (Criteria) this;
        }

        public Criteria andValidmoGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("validmo >=", value, "validmo");
            return (Criteria) this;
        }

        public Criteria andValidmoLessThan(BigDecimal value) {
            addCriterion("validmo <", value, "validmo");
            return (Criteria) this;
        }

        public Criteria andValidmoLessThanOrEqualTo(BigDecimal value) {
            addCriterion("validmo <=", value, "validmo");
            return (Criteria) this;
        }

        public Criteria andValidmoIn(List<BigDecimal> values) {
            addCriterion("validmo in", values, "validmo");
            return (Criteria) this;
        }

        public Criteria andValidmoNotIn(List<BigDecimal> values) {
            addCriterion("validmo not in", values, "validmo");
            return (Criteria) this;
        }

        public Criteria andValidmoBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("validmo between", value1, value2, "validmo");
            return (Criteria) this;
        }

        public Criteria andValidmoNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("validmo not between", value1, value2, "validmo");
            return (Criteria) this;
        }

        public Criteria andValidsIsNull() {
            addCriterion("valids is null");
            return (Criteria) this;
        }

        public Criteria andValidsIsNotNull() {
            addCriterion("valids is not null");
            return (Criteria) this;
        }

        public Criteria andValidsEqualTo(BigDecimal value) {
            addCriterion("valids =", value, "valids");
            return (Criteria) this;
        }

        public Criteria andValidsNotEqualTo(BigDecimal value) {
            addCriterion("valids <>", value, "valids");
            return (Criteria) this;
        }

        public Criteria andValidsGreaterThan(BigDecimal value) {
            addCriterion("valids >", value, "valids");
            return (Criteria) this;
        }

        public Criteria andValidsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("valids >=", value, "valids");
            return (Criteria) this;
        }

        public Criteria andValidsLessThan(BigDecimal value) {
            addCriterion("valids <", value, "valids");
            return (Criteria) this;
        }

        public Criteria andValidsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("valids <=", value, "valids");
            return (Criteria) this;
        }

        public Criteria andValidsIn(List<BigDecimal> values) {
            addCriterion("valids in", values, "valids");
            return (Criteria) this;
        }

        public Criteria andValidsNotIn(List<BigDecimal> values) {
            addCriterion("valids not in", values, "valids");
            return (Criteria) this;
        }

        public Criteria andValidsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("valids between", value1, value2, "valids");
            return (Criteria) this;
        }

        public Criteria andValidsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("valids not between", value1, value2, "valids");
            return (Criteria) this;
        }

        public Criteria andSolubilitycaIsNull() {
            addCriterion("solubilityca is null");
            return (Criteria) this;
        }

        public Criteria andSolubilitycaIsNotNull() {
            addCriterion("solubilityca is not null");
            return (Criteria) this;
        }

        public Criteria andSolubilitycaEqualTo(BigDecimal value) {
            addCriterion("solubilityca =", value, "solubilityca");
            return (Criteria) this;
        }

        public Criteria andSolubilitycaNotEqualTo(BigDecimal value) {
            addCriterion("solubilityca <>", value, "solubilityca");
            return (Criteria) this;
        }

        public Criteria andSolubilitycaGreaterThan(BigDecimal value) {
            addCriterion("solubilityca >", value, "solubilityca");
            return (Criteria) this;
        }

        public Criteria andSolubilitycaGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("solubilityca >=", value, "solubilityca");
            return (Criteria) this;
        }

        public Criteria andSolubilitycaLessThan(BigDecimal value) {
            addCriterion("solubilityca <", value, "solubilityca");
            return (Criteria) this;
        }

        public Criteria andSolubilitycaLessThanOrEqualTo(BigDecimal value) {
            addCriterion("solubilityca <=", value, "solubilityca");
            return (Criteria) this;
        }

        public Criteria andSolubilitycaIn(List<BigDecimal> values) {
            addCriterion("solubilityca in", values, "solubilityca");
            return (Criteria) this;
        }

        public Criteria andSolubilitycaNotIn(List<BigDecimal> values) {
            addCriterion("solubilityca not in", values, "solubilityca");
            return (Criteria) this;
        }

        public Criteria andSolubilitycaBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("solubilityca between", value1, value2, "solubilityca");
            return (Criteria) this;
        }

        public Criteria andSolubilitycaNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("solubilityca not between", value1, value2, "solubilityca");
            return (Criteria) this;
        }

        public Criteria andSolubilitymgIsNull() {
            addCriterion("solubilitymg is null");
            return (Criteria) this;
        }

        public Criteria andSolubilitymgIsNotNull() {
            addCriterion("solubilitymg is not null");
            return (Criteria) this;
        }

        public Criteria andSolubilitymgEqualTo(BigDecimal value) {
            addCriterion("solubilitymg =", value, "solubilitymg");
            return (Criteria) this;
        }

        public Criteria andSolubilitymgNotEqualTo(BigDecimal value) {
            addCriterion("solubilitymg <>", value, "solubilitymg");
            return (Criteria) this;
        }

        public Criteria andSolubilitymgGreaterThan(BigDecimal value) {
            addCriterion("solubilitymg >", value, "solubilitymg");
            return (Criteria) this;
        }

        public Criteria andSolubilitymgGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("solubilitymg >=", value, "solubilitymg");
            return (Criteria) this;
        }

        public Criteria andSolubilitymgLessThan(BigDecimal value) {
            addCriterion("solubilitymg <", value, "solubilitymg");
            return (Criteria) this;
        }

        public Criteria andSolubilitymgLessThanOrEqualTo(BigDecimal value) {
            addCriterion("solubilitymg <=", value, "solubilitymg");
            return (Criteria) this;
        }

        public Criteria andSolubilitymgIn(List<BigDecimal> values) {
            addCriterion("solubilitymg in", values, "solubilitymg");
            return (Criteria) this;
        }

        public Criteria andSolubilitymgNotIn(List<BigDecimal> values) {
            addCriterion("solubilitymg not in", values, "solubilitymg");
            return (Criteria) this;
        }

        public Criteria andSolubilitymgBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("solubilitymg between", value1, value2, "solubilitymg");
            return (Criteria) this;
        }

        public Criteria andSolubilitymgNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("solubilitymg not between", value1, value2, "solubilitymg");
            return (Criteria) this;
        }

        public Criteria andSaltIsNull() {
            addCriterion("salt is null");
            return (Criteria) this;
        }

        public Criteria andSaltIsNotNull() {
            addCriterion("salt is not null");
            return (Criteria) this;
        }

        public Criteria andSaltEqualTo(BigDecimal value) {
            addCriterion("salt =", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltNotEqualTo(BigDecimal value) {
            addCriterion("salt <>", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltGreaterThan(BigDecimal value) {
            addCriterion("salt >", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("salt >=", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltLessThan(BigDecimal value) {
            addCriterion("salt <", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltLessThanOrEqualTo(BigDecimal value) {
            addCriterion("salt <=", value, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltIn(List<BigDecimal> values) {
            addCriterion("salt in", values, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltNotIn(List<BigDecimal> values) {
            addCriterion("salt not in", values, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("salt between", value1, value2, "salt");
            return (Criteria) this;
        }

        public Criteria andSaltNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("salt not between", value1, value2, "salt");
            return (Criteria) this;
        }

        public Criteria andMidIsNull() {
            addCriterion("mid is null");
            return (Criteria) this;
        }

        public Criteria andMidIsNotNull() {
            addCriterion("mid is not null");
            return (Criteria) this;
        }

        public Criteria andMidEqualTo(Integer value) {
            addCriterion("mid =", value, "mid");
            return (Criteria) this;
        }

        public Criteria andMidNotEqualTo(Integer value) {
            addCriterion("mid <>", value, "mid");
            return (Criteria) this;
        }

        public Criteria andMidGreaterThan(Integer value) {
            addCriterion("mid >", value, "mid");
            return (Criteria) this;
        }

        public Criteria andMidGreaterThanOrEqualTo(Integer value) {
            addCriterion("mid >=", value, "mid");
            return (Criteria) this;
        }

        public Criteria andMidLessThan(Integer value) {
            addCriterion("mid <", value, "mid");
            return (Criteria) this;
        }

        public Criteria andMidLessThanOrEqualTo(Integer value) {
            addCriterion("mid <=", value, "mid");
            return (Criteria) this;
        }

        public Criteria andMidIn(List<Integer> values) {
            addCriterion("mid in", values, "mid");
            return (Criteria) this;
        }

        public Criteria andMidNotIn(List<Integer> values) {
            addCriterion("mid not in", values, "mid");
            return (Criteria) this;
        }

        public Criteria andMidBetween(Integer value1, Integer value2) {
            addCriterion("mid between", value1, value2, "mid");
            return (Criteria) this;
        }

        public Criteria andMidNotBetween(Integer value1, Integer value2) {
            addCriterion("mid not between", value1, value2, "mid");
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