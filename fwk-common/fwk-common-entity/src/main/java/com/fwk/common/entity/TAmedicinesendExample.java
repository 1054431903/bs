package com.fwk.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TAmedicinesendExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TAmedicinesendExample() {
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

        public Criteria andNameidIsNull() {
            addCriterion("nameid is null");
            return (Criteria) this;
        }

        public Criteria andNameidIsNotNull() {
            addCriterion("nameid is not null");
            return (Criteria) this;
        }

        public Criteria andNameidEqualTo(Integer value) {
            addCriterion("nameid =", value, "nameid");
            return (Criteria) this;
        }

        public Criteria andNameidNotEqualTo(Integer value) {
            addCriterion("nameid <>", value, "nameid");
            return (Criteria) this;
        }

        public Criteria andNameidGreaterThan(Integer value) {
            addCriterion("nameid >", value, "nameid");
            return (Criteria) this;
        }

        public Criteria andNameidGreaterThanOrEqualTo(Integer value) {
            addCriterion("nameid >=", value, "nameid");
            return (Criteria) this;
        }

        public Criteria andNameidLessThan(Integer value) {
            addCriterion("nameid <", value, "nameid");
            return (Criteria) this;
        }

        public Criteria andNameidLessThanOrEqualTo(Integer value) {
            addCriterion("nameid <=", value, "nameid");
            return (Criteria) this;
        }

        public Criteria andNameidIn(List<Integer> values) {
            addCriterion("nameid in", values, "nameid");
            return (Criteria) this;
        }

        public Criteria andNameidNotIn(List<Integer> values) {
            addCriterion("nameid not in", values, "nameid");
            return (Criteria) this;
        }

        public Criteria andNameidBetween(Integer value1, Integer value2) {
            addCriterion("nameid between", value1, value2, "nameid");
            return (Criteria) this;
        }

        public Criteria andNameidNotBetween(Integer value1, Integer value2) {
            addCriterion("nameid not between", value1, value2, "nameid");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andSpecificIsNull() {
            addCriterion("specific is null");
            return (Criteria) this;
        }

        public Criteria andSpecificIsNotNull() {
            addCriterion("specific is not null");
            return (Criteria) this;
        }

        public Criteria andSpecificEqualTo(String value) {
            addCriterion("specific =", value, "specific");
            return (Criteria) this;
        }

        public Criteria andSpecificNotEqualTo(String value) {
            addCriterion("specific <>", value, "specific");
            return (Criteria) this;
        }

        public Criteria andSpecificGreaterThan(String value) {
            addCriterion("specific >", value, "specific");
            return (Criteria) this;
        }

        public Criteria andSpecificGreaterThanOrEqualTo(String value) {
            addCriterion("specific >=", value, "specific");
            return (Criteria) this;
        }

        public Criteria andSpecificLessThan(String value) {
            addCriterion("specific <", value, "specific");
            return (Criteria) this;
        }

        public Criteria andSpecificLessThanOrEqualTo(String value) {
            addCriterion("specific <=", value, "specific");
            return (Criteria) this;
        }

        public Criteria andSpecificLike(String value) {
            addCriterion("specific like", value, "specific");
            return (Criteria) this;
        }

        public Criteria andSpecificNotLike(String value) {
            addCriterion("specific not like", value, "specific");
            return (Criteria) this;
        }

        public Criteria andSpecificIn(List<String> values) {
            addCriterion("specific in", values, "specific");
            return (Criteria) this;
        }

        public Criteria andSpecificNotIn(List<String> values) {
            addCriterion("specific not in", values, "specific");
            return (Criteria) this;
        }

        public Criteria andSpecificBetween(String value1, String value2) {
            addCriterion("specific between", value1, value2, "specific");
            return (Criteria) this;
        }

        public Criteria andSpecificNotBetween(String value1, String value2) {
            addCriterion("specific not between", value1, value2, "specific");
            return (Criteria) this;
        }

        public Criteria andSpecificunitIsNull() {
            addCriterion("specificunit is null");
            return (Criteria) this;
        }

        public Criteria andSpecificunitIsNotNull() {
            addCriterion("specificunit is not null");
            return (Criteria) this;
        }

        public Criteria andSpecificunitEqualTo(String value) {
            addCriterion("specificunit =", value, "specificunit");
            return (Criteria) this;
        }

        public Criteria andSpecificunitNotEqualTo(String value) {
            addCriterion("specificunit <>", value, "specificunit");
            return (Criteria) this;
        }

        public Criteria andSpecificunitGreaterThan(String value) {
            addCriterion("specificunit >", value, "specificunit");
            return (Criteria) this;
        }

        public Criteria andSpecificunitGreaterThanOrEqualTo(String value) {
            addCriterion("specificunit >=", value, "specificunit");
            return (Criteria) this;
        }

        public Criteria andSpecificunitLessThan(String value) {
            addCriterion("specificunit <", value, "specificunit");
            return (Criteria) this;
        }

        public Criteria andSpecificunitLessThanOrEqualTo(String value) {
            addCriterion("specificunit <=", value, "specificunit");
            return (Criteria) this;
        }

        public Criteria andSpecificunitLike(String value) {
            addCriterion("specificunit like", value, "specificunit");
            return (Criteria) this;
        }

        public Criteria andSpecificunitNotLike(String value) {
            addCriterion("specificunit not like", value, "specificunit");
            return (Criteria) this;
        }

        public Criteria andSpecificunitIn(List<String> values) {
            addCriterion("specificunit in", values, "specificunit");
            return (Criteria) this;
        }

        public Criteria andSpecificunitNotIn(List<String> values) {
            addCriterion("specificunit not in", values, "specificunit");
            return (Criteria) this;
        }

        public Criteria andSpecificunitBetween(String value1, String value2) {
            addCriterion("specificunit between", value1, value2, "specificunit");
            return (Criteria) this;
        }

        public Criteria andSpecificunitNotBetween(String value1, String value2) {
            addCriterion("specificunit not between", value1, value2, "specificunit");
            return (Criteria) this;
        }

        public Criteria andNumIsNull() {
            addCriterion("num is null");
            return (Criteria) this;
        }

        public Criteria andNumIsNotNull() {
            addCriterion("num is not null");
            return (Criteria) this;
        }

        public Criteria andNumEqualTo(Integer value) {
            addCriterion("num =", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotEqualTo(Integer value) {
            addCriterion("num <>", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThan(Integer value) {
            addCriterion("num >", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("num >=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThan(Integer value) {
            addCriterion("num <", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThanOrEqualTo(Integer value) {
            addCriterion("num <=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumIn(List<Integer> values) {
            addCriterion("num in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotIn(List<Integer> values) {
            addCriterion("num not in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumBetween(Integer value1, Integer value2) {
            addCriterion("num between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotBetween(Integer value1, Integer value2) {
            addCriterion("num not between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andNumunitIsNull() {
            addCriterion("numunit is null");
            return (Criteria) this;
        }

        public Criteria andNumunitIsNotNull() {
            addCriterion("numunit is not null");
            return (Criteria) this;
        }

        public Criteria andNumunitEqualTo(String value) {
            addCriterion("numunit =", value, "numunit");
            return (Criteria) this;
        }

        public Criteria andNumunitNotEqualTo(String value) {
            addCriterion("numunit <>", value, "numunit");
            return (Criteria) this;
        }

        public Criteria andNumunitGreaterThan(String value) {
            addCriterion("numunit >", value, "numunit");
            return (Criteria) this;
        }

        public Criteria andNumunitGreaterThanOrEqualTo(String value) {
            addCriterion("numunit >=", value, "numunit");
            return (Criteria) this;
        }

        public Criteria andNumunitLessThan(String value) {
            addCriterion("numunit <", value, "numunit");
            return (Criteria) this;
        }

        public Criteria andNumunitLessThanOrEqualTo(String value) {
            addCriterion("numunit <=", value, "numunit");
            return (Criteria) this;
        }

        public Criteria andNumunitLike(String value) {
            addCriterion("numunit like", value, "numunit");
            return (Criteria) this;
        }

        public Criteria andNumunitNotLike(String value) {
            addCriterion("numunit not like", value, "numunit");
            return (Criteria) this;
        }

        public Criteria andNumunitIn(List<String> values) {
            addCriterion("numunit in", values, "numunit");
            return (Criteria) this;
        }

        public Criteria andNumunitNotIn(List<String> values) {
            addCriterion("numunit not in", values, "numunit");
            return (Criteria) this;
        }

        public Criteria andNumunitBetween(String value1, String value2) {
            addCriterion("numunit between", value1, value2, "numunit");
            return (Criteria) this;
        }

        public Criteria andNumunitNotBetween(String value1, String value2) {
            addCriterion("numunit not between", value1, value2, "numunit");
            return (Criteria) this;
        }

        public Criteria andDrowuseridIsNull() {
            addCriterion("drowuserid is null");
            return (Criteria) this;
        }

        public Criteria andDrowuseridIsNotNull() {
            addCriterion("drowuserid is not null");
            return (Criteria) this;
        }

        public Criteria andDrowuseridEqualTo(Integer value) {
            addCriterion("drowuserid =", value, "drowuserid");
            return (Criteria) this;
        }

        public Criteria andDrowuseridNotEqualTo(Integer value) {
            addCriterion("drowuserid <>", value, "drowuserid");
            return (Criteria) this;
        }

        public Criteria andDrowuseridGreaterThan(Integer value) {
            addCriterion("drowuserid >", value, "drowuserid");
            return (Criteria) this;
        }

        public Criteria andDrowuseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("drowuserid >=", value, "drowuserid");
            return (Criteria) this;
        }

        public Criteria andDrowuseridLessThan(Integer value) {
            addCriterion("drowuserid <", value, "drowuserid");
            return (Criteria) this;
        }

        public Criteria andDrowuseridLessThanOrEqualTo(Integer value) {
            addCriterion("drowuserid <=", value, "drowuserid");
            return (Criteria) this;
        }

        public Criteria andDrowuseridIn(List<Integer> values) {
            addCriterion("drowuserid in", values, "drowuserid");
            return (Criteria) this;
        }

        public Criteria andDrowuseridNotIn(List<Integer> values) {
            addCriterion("drowuserid not in", values, "drowuserid");
            return (Criteria) this;
        }

        public Criteria andDrowuseridBetween(Integer value1, Integer value2) {
            addCriterion("drowuserid between", value1, value2, "drowuserid");
            return (Criteria) this;
        }

        public Criteria andDrowuseridNotBetween(Integer value1, Integer value2) {
            addCriterion("drowuserid not between", value1, value2, "drowuserid");
            return (Criteria) this;
        }

        public Criteria andExplainIsNull() {
            addCriterion("explain is null");
            return (Criteria) this;
        }

        public Criteria andExplainIsNotNull() {
            addCriterion("explain is not null");
            return (Criteria) this;
        }

        public Criteria andExplainEqualTo(String value) {
            addCriterion("explain =", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainNotEqualTo(String value) {
            addCriterion("explain <>", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainGreaterThan(String value) {
            addCriterion("explain >", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainGreaterThanOrEqualTo(String value) {
            addCriterion("explain >=", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainLessThan(String value) {
            addCriterion("explain <", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainLessThanOrEqualTo(String value) {
            addCriterion("explain <=", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainLike(String value) {
            addCriterion("explain like", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainNotLike(String value) {
            addCriterion("explain not like", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainIn(List<String> values) {
            addCriterion("explain in", values, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainNotIn(List<String> values) {
            addCriterion("explain not in", values, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainBetween(String value1, String value2) {
            addCriterion("explain between", value1, value2, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainNotBetween(String value1, String value2) {
            addCriterion("explain not between", value1, value2, "explain");
            return (Criteria) this;
        }

        public Criteria andDrowaddressdetailIsNull() {
            addCriterion("drowaddressdetail is null");
            return (Criteria) this;
        }

        public Criteria andDrowaddressdetailIsNotNull() {
            addCriterion("drowaddressdetail is not null");
            return (Criteria) this;
        }

        public Criteria andDrowaddressdetailEqualTo(String value) {
            addCriterion("drowaddressdetail =", value, "drowaddressdetail");
            return (Criteria) this;
        }

        public Criteria andDrowaddressdetailNotEqualTo(String value) {
            addCriterion("drowaddressdetail <>", value, "drowaddressdetail");
            return (Criteria) this;
        }

        public Criteria andDrowaddressdetailGreaterThan(String value) {
            addCriterion("drowaddressdetail >", value, "drowaddressdetail");
            return (Criteria) this;
        }

        public Criteria andDrowaddressdetailGreaterThanOrEqualTo(String value) {
            addCriterion("drowaddressdetail >=", value, "drowaddressdetail");
            return (Criteria) this;
        }

        public Criteria andDrowaddressdetailLessThan(String value) {
            addCriterion("drowaddressdetail <", value, "drowaddressdetail");
            return (Criteria) this;
        }

        public Criteria andDrowaddressdetailLessThanOrEqualTo(String value) {
            addCriterion("drowaddressdetail <=", value, "drowaddressdetail");
            return (Criteria) this;
        }

        public Criteria andDrowaddressdetailLike(String value) {
            addCriterion("drowaddressdetail like", value, "drowaddressdetail");
            return (Criteria) this;
        }

        public Criteria andDrowaddressdetailNotLike(String value) {
            addCriterion("drowaddressdetail not like", value, "drowaddressdetail");
            return (Criteria) this;
        }

        public Criteria andDrowaddressdetailIn(List<String> values) {
            addCriterion("drowaddressdetail in", values, "drowaddressdetail");
            return (Criteria) this;
        }

        public Criteria andDrowaddressdetailNotIn(List<String> values) {
            addCriterion("drowaddressdetail not in", values, "drowaddressdetail");
            return (Criteria) this;
        }

        public Criteria andDrowaddressdetailBetween(String value1, String value2) {
            addCriterion("drowaddressdetail between", value1, value2, "drowaddressdetail");
            return (Criteria) this;
        }

        public Criteria andDrowaddressdetailNotBetween(String value1, String value2) {
            addCriterion("drowaddressdetail not between", value1, value2, "drowaddressdetail");
            return (Criteria) this;
        }

        public Criteria andDrowaddresscodeIsNull() {
            addCriterion("drowaddresscode is null");
            return (Criteria) this;
        }

        public Criteria andDrowaddresscodeIsNotNull() {
            addCriterion("drowaddresscode is not null");
            return (Criteria) this;
        }

        public Criteria andDrowaddresscodeEqualTo(String value) {
            addCriterion("drowaddresscode =", value, "drowaddresscode");
            return (Criteria) this;
        }

        public Criteria andDrowaddresscodeNotEqualTo(String value) {
            addCriterion("drowaddresscode <>", value, "drowaddresscode");
            return (Criteria) this;
        }

        public Criteria andDrowaddresscodeGreaterThan(String value) {
            addCriterion("drowaddresscode >", value, "drowaddresscode");
            return (Criteria) this;
        }

        public Criteria andDrowaddresscodeGreaterThanOrEqualTo(String value) {
            addCriterion("drowaddresscode >=", value, "drowaddresscode");
            return (Criteria) this;
        }

        public Criteria andDrowaddresscodeLessThan(String value) {
            addCriterion("drowaddresscode <", value, "drowaddresscode");
            return (Criteria) this;
        }

        public Criteria andDrowaddresscodeLessThanOrEqualTo(String value) {
            addCriterion("drowaddresscode <=", value, "drowaddresscode");
            return (Criteria) this;
        }

        public Criteria andDrowaddresscodeLike(String value) {
            addCriterion("drowaddresscode like", value, "drowaddresscode");
            return (Criteria) this;
        }

        public Criteria andDrowaddresscodeNotLike(String value) {
            addCriterion("drowaddresscode not like", value, "drowaddresscode");
            return (Criteria) this;
        }

        public Criteria andDrowaddresscodeIn(List<String> values) {
            addCriterion("drowaddresscode in", values, "drowaddresscode");
            return (Criteria) this;
        }

        public Criteria andDrowaddresscodeNotIn(List<String> values) {
            addCriterion("drowaddresscode not in", values, "drowaddresscode");
            return (Criteria) this;
        }

        public Criteria andDrowaddresscodeBetween(String value1, String value2) {
            addCriterion("drowaddresscode between", value1, value2, "drowaddresscode");
            return (Criteria) this;
        }

        public Criteria andDrowaddresscodeNotBetween(String value1, String value2) {
            addCriterion("drowaddresscode not between", value1, value2, "drowaddresscode");
            return (Criteria) this;
        }

        public Criteria andMsidIsNull() {
            addCriterion("msid is null");
            return (Criteria) this;
        }

        public Criteria andMsidIsNotNull() {
            addCriterion("msid is not null");
            return (Criteria) this;
        }

        public Criteria andMsidEqualTo(String value) {
            addCriterion("msid =", value, "msid");
            return (Criteria) this;
        }

        public Criteria andMsidNotEqualTo(String value) {
            addCriterion("msid <>", value, "msid");
            return (Criteria) this;
        }

        public Criteria andMsidGreaterThan(String value) {
            addCriterion("msid >", value, "msid");
            return (Criteria) this;
        }

        public Criteria andMsidGreaterThanOrEqualTo(String value) {
            addCriterion("msid >=", value, "msid");
            return (Criteria) this;
        }

        public Criteria andMsidLessThan(String value) {
            addCriterion("msid <", value, "msid");
            return (Criteria) this;
        }

        public Criteria andMsidLessThanOrEqualTo(String value) {
            addCriterion("msid <=", value, "msid");
            return (Criteria) this;
        }

        public Criteria andMsidLike(String value) {
            addCriterion("msid like", value, "msid");
            return (Criteria) this;
        }

        public Criteria andMsidNotLike(String value) {
            addCriterion("msid not like", value, "msid");
            return (Criteria) this;
        }

        public Criteria andMsidIn(List<String> values) {
            addCriterion("msid in", values, "msid");
            return (Criteria) this;
        }

        public Criteria andMsidNotIn(List<String> values) {
            addCriterion("msid not in", values, "msid");
            return (Criteria) this;
        }

        public Criteria andMsidBetween(String value1, String value2) {
            addCriterion("msid between", value1, value2, "msid");
            return (Criteria) this;
        }

        public Criteria andMsidNotBetween(String value1, String value2) {
            addCriterion("msid not between", value1, value2, "msid");
            return (Criteria) this;
        }

        public Criteria andDrowtimeIsNull() {
            addCriterion("drowtime is null");
            return (Criteria) this;
        }

        public Criteria andDrowtimeIsNotNull() {
            addCriterion("drowtime is not null");
            return (Criteria) this;
        }

        public Criteria andDrowtimeEqualTo(Date value) {
            addCriterion("drowtime =", value, "drowtime");
            return (Criteria) this;
        }

        public Criteria andDrowtimeNotEqualTo(Date value) {
            addCriterion("drowtime <>", value, "drowtime");
            return (Criteria) this;
        }

        public Criteria andDrowtimeGreaterThan(Date value) {
            addCriterion("drowtime >", value, "drowtime");
            return (Criteria) this;
        }

        public Criteria andDrowtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("drowtime >=", value, "drowtime");
            return (Criteria) this;
        }

        public Criteria andDrowtimeLessThan(Date value) {
            addCriterion("drowtime <", value, "drowtime");
            return (Criteria) this;
        }

        public Criteria andDrowtimeLessThanOrEqualTo(Date value) {
            addCriterion("drowtime <=", value, "drowtime");
            return (Criteria) this;
        }

        public Criteria andDrowtimeIn(List<Date> values) {
            addCriterion("drowtime in", values, "drowtime");
            return (Criteria) this;
        }

        public Criteria andDrowtimeNotIn(List<Date> values) {
            addCriterion("drowtime not in", values, "drowtime");
            return (Criteria) this;
        }

        public Criteria andDrowtimeBetween(Date value1, Date value2) {
            addCriterion("drowtime between", value1, value2, "drowtime");
            return (Criteria) this;
        }

        public Criteria andDrowtimeNotBetween(Date value1, Date value2) {
            addCriterion("drowtime not between", value1, value2, "drowtime");
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