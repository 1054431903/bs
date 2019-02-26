package com.fwk.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TAmedicineintoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TAmedicineintoExample() {
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

        public Criteria andExecuteuseridIsNull() {
            addCriterion("executeuserid is null");
            return (Criteria) this;
        }

        public Criteria andExecuteuseridIsNotNull() {
            addCriterion("executeuserid is not null");
            return (Criteria) this;
        }

        public Criteria andExecuteuseridEqualTo(Integer value) {
            addCriterion("executeuserid =", value, "executeuserid");
            return (Criteria) this;
        }

        public Criteria andExecuteuseridNotEqualTo(Integer value) {
            addCriterion("executeuserid <>", value, "executeuserid");
            return (Criteria) this;
        }

        public Criteria andExecuteuseridGreaterThan(Integer value) {
            addCriterion("executeuserid >", value, "executeuserid");
            return (Criteria) this;
        }

        public Criteria andExecuteuseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("executeuserid >=", value, "executeuserid");
            return (Criteria) this;
        }

        public Criteria andExecuteuseridLessThan(Integer value) {
            addCriterion("executeuserid <", value, "executeuserid");
            return (Criteria) this;
        }

        public Criteria andExecuteuseridLessThanOrEqualTo(Integer value) {
            addCriterion("executeuserid <=", value, "executeuserid");
            return (Criteria) this;
        }

        public Criteria andExecuteuseridIn(List<Integer> values) {
            addCriterion("executeuserid in", values, "executeuserid");
            return (Criteria) this;
        }

        public Criteria andExecuteuseridNotIn(List<Integer> values) {
            addCriterion("executeuserid not in", values, "executeuserid");
            return (Criteria) this;
        }

        public Criteria andExecuteuseridBetween(Integer value1, Integer value2) {
            addCriterion("executeuserid between", value1, value2, "executeuserid");
            return (Criteria) this;
        }

        public Criteria andExecuteuseridNotBetween(Integer value1, Integer value2) {
            addCriterion("executeuserid not between", value1, value2, "executeuserid");
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

        public Criteria andExecutetimeIsNull() {
            addCriterion("executetime is null");
            return (Criteria) this;
        }

        public Criteria andExecutetimeIsNotNull() {
            addCriterion("executetime is not null");
            return (Criteria) this;
        }

        public Criteria andExecutetimeEqualTo(Date value) {
            addCriterion("executetime =", value, "executetime");
            return (Criteria) this;
        }

        public Criteria andExecutetimeNotEqualTo(Date value) {
            addCriterion("executetime <>", value, "executetime");
            return (Criteria) this;
        }

        public Criteria andExecutetimeGreaterThan(Date value) {
            addCriterion("executetime >", value, "executetime");
            return (Criteria) this;
        }

        public Criteria andExecutetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("executetime >=", value, "executetime");
            return (Criteria) this;
        }

        public Criteria andExecutetimeLessThan(Date value) {
            addCriterion("executetime <", value, "executetime");
            return (Criteria) this;
        }

        public Criteria andExecutetimeLessThanOrEqualTo(Date value) {
            addCriterion("executetime <=", value, "executetime");
            return (Criteria) this;
        }

        public Criteria andExecutetimeIn(List<Date> values) {
            addCriterion("executetime in", values, "executetime");
            return (Criteria) this;
        }

        public Criteria andExecutetimeNotIn(List<Date> values) {
            addCriterion("executetime not in", values, "executetime");
            return (Criteria) this;
        }

        public Criteria andExecutetimeBetween(Date value1, Date value2) {
            addCriterion("executetime between", value1, value2, "executetime");
            return (Criteria) this;
        }

        public Criteria andExecutetimeNotBetween(Date value1, Date value2) {
            addCriterion("executetime not between", value1, value2, "executetime");
            return (Criteria) this;
        }

        public Criteria andMiidIsNull() {
            addCriterion("miid is null");
            return (Criteria) this;
        }

        public Criteria andMiidIsNotNull() {
            addCriterion("miid is not null");
            return (Criteria) this;
        }

        public Criteria andMiidEqualTo(String value) {
            addCriterion("miid =", value, "miid");
            return (Criteria) this;
        }

        public Criteria andMiidNotEqualTo(String value) {
            addCriterion("miid <>", value, "miid");
            return (Criteria) this;
        }

        public Criteria andMiidGreaterThan(String value) {
            addCriterion("miid >", value, "miid");
            return (Criteria) this;
        }

        public Criteria andMiidGreaterThanOrEqualTo(String value) {
            addCriterion("miid >=", value, "miid");
            return (Criteria) this;
        }

        public Criteria andMiidLessThan(String value) {
            addCriterion("miid <", value, "miid");
            return (Criteria) this;
        }

        public Criteria andMiidLessThanOrEqualTo(String value) {
            addCriterion("miid <=", value, "miid");
            return (Criteria) this;
        }

        public Criteria andMiidLike(String value) {
            addCriterion("miid like", value, "miid");
            return (Criteria) this;
        }

        public Criteria andMiidNotLike(String value) {
            addCriterion("miid not like", value, "miid");
            return (Criteria) this;
        }

        public Criteria andMiidIn(List<String> values) {
            addCriterion("miid in", values, "miid");
            return (Criteria) this;
        }

        public Criteria andMiidNotIn(List<String> values) {
            addCriterion("miid not in", values, "miid");
            return (Criteria) this;
        }

        public Criteria andMiidBetween(String value1, String value2) {
            addCriterion("miid between", value1, value2, "miid");
            return (Criteria) this;
        }

        public Criteria andMiidNotBetween(String value1, String value2) {
            addCriterion("miid not between", value1, value2, "miid");
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