package com.fwk.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TFcommunioncategoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TFcommunioncategoryExample() {
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

        public Criteria andCommunionidIsNull() {
            addCriterion("communionid is null");
            return (Criteria) this;
        }

        public Criteria andCommunionidIsNotNull() {
            addCriterion("communionid is not null");
            return (Criteria) this;
        }

        public Criteria andCommunionidEqualTo(Integer value) {
            addCriterion("communionid =", value, "communionid");
            return (Criteria) this;
        }

        public Criteria andCommunionidNotEqualTo(Integer value) {
            addCriterion("communionid <>", value, "communionid");
            return (Criteria) this;
        }

        public Criteria andCommunionidGreaterThan(Integer value) {
            addCriterion("communionid >", value, "communionid");
            return (Criteria) this;
        }

        public Criteria andCommunionidGreaterThanOrEqualTo(Integer value) {
            addCriterion("communionid >=", value, "communionid");
            return (Criteria) this;
        }

        public Criteria andCommunionidLessThan(Integer value) {
            addCriterion("communionid <", value, "communionid");
            return (Criteria) this;
        }

        public Criteria andCommunionidLessThanOrEqualTo(Integer value) {
            addCriterion("communionid <=", value, "communionid");
            return (Criteria) this;
        }

        public Criteria andCommunionidIn(List<Integer> values) {
            addCriterion("communionid in", values, "communionid");
            return (Criteria) this;
        }

        public Criteria andCommunionidNotIn(List<Integer> values) {
            addCriterion("communionid not in", values, "communionid");
            return (Criteria) this;
        }

        public Criteria andCommunionidBetween(Integer value1, Integer value2) {
            addCriterion("communionid between", value1, value2, "communionid");
            return (Criteria) this;
        }

        public Criteria andCommunionidNotBetween(Integer value1, Integer value2) {
            addCriterion("communionid not between", value1, value2, "communionid");
            return (Criteria) this;
        }

        public Criteria andCategoryidIsNull() {
            addCriterion("categoryid is null");
            return (Criteria) this;
        }

        public Criteria andCategoryidIsNotNull() {
            addCriterion("categoryid is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryidEqualTo(String value) {
            addCriterion("categoryid =", value, "categoryid");
            return (Criteria) this;
        }

        public Criteria andCategoryidNotEqualTo(String value) {
            addCriterion("categoryid <>", value, "categoryid");
            return (Criteria) this;
        }

        public Criteria andCategoryidGreaterThan(String value) {
            addCriterion("categoryid >", value, "categoryid");
            return (Criteria) this;
        }

        public Criteria andCategoryidGreaterThanOrEqualTo(String value) {
            addCriterion("categoryid >=", value, "categoryid");
            return (Criteria) this;
        }

        public Criteria andCategoryidLessThan(String value) {
            addCriterion("categoryid <", value, "categoryid");
            return (Criteria) this;
        }

        public Criteria andCategoryidLessThanOrEqualTo(String value) {
            addCriterion("categoryid <=", value, "categoryid");
            return (Criteria) this;
        }

        public Criteria andCategoryidLike(String value) {
            addCriterion("categoryid like", value, "categoryid");
            return (Criteria) this;
        }

        public Criteria andCategoryidNotLike(String value) {
            addCriterion("categoryid not like", value, "categoryid");
            return (Criteria) this;
        }

        public Criteria andCategoryidIn(List<String> values) {
            addCriterion("categoryid in", values, "categoryid");
            return (Criteria) this;
        }

        public Criteria andCategoryidNotIn(List<String> values) {
            addCriterion("categoryid not in", values, "categoryid");
            return (Criteria) this;
        }

        public Criteria andCategoryidBetween(String value1, String value2) {
            addCriterion("categoryid between", value1, value2, "categoryid");
            return (Criteria) this;
        }

        public Criteria andCategoryidNotBetween(String value1, String value2) {
            addCriterion("categoryid not between", value1, value2, "categoryid");
            return (Criteria) this;
        }

        public Criteria andCategorynameIsNull() {
            addCriterion("categoryname is null");
            return (Criteria) this;
        }

        public Criteria andCategorynameIsNotNull() {
            addCriterion("categoryname is not null");
            return (Criteria) this;
        }

        public Criteria andCategorynameEqualTo(String value) {
            addCriterion("categoryname =", value, "categoryname");
            return (Criteria) this;
        }

        public Criteria andCategorynameNotEqualTo(String value) {
            addCriterion("categoryname <>", value, "categoryname");
            return (Criteria) this;
        }

        public Criteria andCategorynameGreaterThan(String value) {
            addCriterion("categoryname >", value, "categoryname");
            return (Criteria) this;
        }

        public Criteria andCategorynameGreaterThanOrEqualTo(String value) {
            addCriterion("categoryname >=", value, "categoryname");
            return (Criteria) this;
        }

        public Criteria andCategorynameLessThan(String value) {
            addCriterion("categoryname <", value, "categoryname");
            return (Criteria) this;
        }

        public Criteria andCategorynameLessThanOrEqualTo(String value) {
            addCriterion("categoryname <=", value, "categoryname");
            return (Criteria) this;
        }

        public Criteria andCategorynameLike(String value) {
            addCriterion("categoryname like", value, "categoryname");
            return (Criteria) this;
        }

        public Criteria andCategorynameNotLike(String value) {
            addCriterion("categoryname not like", value, "categoryname");
            return (Criteria) this;
        }

        public Criteria andCategorynameIn(List<String> values) {
            addCriterion("categoryname in", values, "categoryname");
            return (Criteria) this;
        }

        public Criteria andCategorynameNotIn(List<String> values) {
            addCriterion("categoryname not in", values, "categoryname");
            return (Criteria) this;
        }

        public Criteria andCategorynameBetween(String value1, String value2) {
            addCriterion("categoryname between", value1, value2, "categoryname");
            return (Criteria) this;
        }

        public Criteria andCategorynameNotBetween(String value1, String value2) {
            addCriterion("categoryname not between", value1, value2, "categoryname");
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