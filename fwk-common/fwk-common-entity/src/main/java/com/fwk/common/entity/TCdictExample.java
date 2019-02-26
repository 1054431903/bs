package com.fwk.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TCdictExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TCdictExample() {
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

        public Criteria andDicttypeIsNull() {
            addCriterion("dicttype is null");
            return (Criteria) this;
        }

        public Criteria andDicttypeIsNotNull() {
            addCriterion("dicttype is not null");
            return (Criteria) this;
        }

        public Criteria andDicttypeEqualTo(String value) {
            addCriterion("dicttype =", value, "dicttype");
            return (Criteria) this;
        }

        public Criteria andDicttypeNotEqualTo(String value) {
            addCriterion("dicttype <>", value, "dicttype");
            return (Criteria) this;
        }

        public Criteria andDicttypeGreaterThan(String value) {
            addCriterion("dicttype >", value, "dicttype");
            return (Criteria) this;
        }

        public Criteria andDicttypeGreaterThanOrEqualTo(String value) {
            addCriterion("dicttype >=", value, "dicttype");
            return (Criteria) this;
        }

        public Criteria andDicttypeLessThan(String value) {
            addCriterion("dicttype <", value, "dicttype");
            return (Criteria) this;
        }

        public Criteria andDicttypeLessThanOrEqualTo(String value) {
            addCriterion("dicttype <=", value, "dicttype");
            return (Criteria) this;
        }

        public Criteria andDicttypeLike(String value) {
            addCriterion("dicttype like", value, "dicttype");
            return (Criteria) this;
        }

        public Criteria andDicttypeNotLike(String value) {
            addCriterion("dicttype not like", value, "dicttype");
            return (Criteria) this;
        }

        public Criteria andDicttypeIn(List<String> values) {
            addCriterion("dicttype in", values, "dicttype");
            return (Criteria) this;
        }

        public Criteria andDicttypeNotIn(List<String> values) {
            addCriterion("dicttype not in", values, "dicttype");
            return (Criteria) this;
        }

        public Criteria andDicttypeBetween(String value1, String value2) {
            addCriterion("dicttype between", value1, value2, "dicttype");
            return (Criteria) this;
        }

        public Criteria andDicttypeNotBetween(String value1, String value2) {
            addCriterion("dicttype not between", value1, value2, "dicttype");
            return (Criteria) this;
        }

        public Criteria andDictvalueIsNull() {
            addCriterion("dictvalue is null");
            return (Criteria) this;
        }

        public Criteria andDictvalueIsNotNull() {
            addCriterion("dictvalue is not null");
            return (Criteria) this;
        }

        public Criteria andDictvalueEqualTo(String value) {
            addCriterion("dictvalue =", value, "dictvalue");
            return (Criteria) this;
        }

        public Criteria andDictvalueNotEqualTo(String value) {
            addCriterion("dictvalue <>", value, "dictvalue");
            return (Criteria) this;
        }

        public Criteria andDictvalueGreaterThan(String value) {
            addCriterion("dictvalue >", value, "dictvalue");
            return (Criteria) this;
        }

        public Criteria andDictvalueGreaterThanOrEqualTo(String value) {
            addCriterion("dictvalue >=", value, "dictvalue");
            return (Criteria) this;
        }

        public Criteria andDictvalueLessThan(String value) {
            addCriterion("dictvalue <", value, "dictvalue");
            return (Criteria) this;
        }

        public Criteria andDictvalueLessThanOrEqualTo(String value) {
            addCriterion("dictvalue <=", value, "dictvalue");
            return (Criteria) this;
        }

        public Criteria andDictvalueLike(String value) {
            addCriterion("dictvalue like", value, "dictvalue");
            return (Criteria) this;
        }

        public Criteria andDictvalueNotLike(String value) {
            addCriterion("dictvalue not like", value, "dictvalue");
            return (Criteria) this;
        }

        public Criteria andDictvalueIn(List<String> values) {
            addCriterion("dictvalue in", values, "dictvalue");
            return (Criteria) this;
        }

        public Criteria andDictvalueNotIn(List<String> values) {
            addCriterion("dictvalue not in", values, "dictvalue");
            return (Criteria) this;
        }

        public Criteria andDictvalueBetween(String value1, String value2) {
            addCriterion("dictvalue between", value1, value2, "dictvalue");
            return (Criteria) this;
        }

        public Criteria andDictvalueNotBetween(String value1, String value2) {
            addCriterion("dictvalue not between", value1, value2, "dictvalue");
            return (Criteria) this;
        }

        public Criteria andDictnameIsNull() {
            addCriterion("dictname is null");
            return (Criteria) this;
        }

        public Criteria andDictnameIsNotNull() {
            addCriterion("dictname is not null");
            return (Criteria) this;
        }

        public Criteria andDictnameEqualTo(String value) {
            addCriterion("dictname =", value, "dictname");
            return (Criteria) this;
        }

        public Criteria andDictnameNotEqualTo(String value) {
            addCriterion("dictname <>", value, "dictname");
            return (Criteria) this;
        }

        public Criteria andDictnameGreaterThan(String value) {
            addCriterion("dictname >", value, "dictname");
            return (Criteria) this;
        }

        public Criteria andDictnameGreaterThanOrEqualTo(String value) {
            addCriterion("dictname >=", value, "dictname");
            return (Criteria) this;
        }

        public Criteria andDictnameLessThan(String value) {
            addCriterion("dictname <", value, "dictname");
            return (Criteria) this;
        }

        public Criteria andDictnameLessThanOrEqualTo(String value) {
            addCriterion("dictname <=", value, "dictname");
            return (Criteria) this;
        }

        public Criteria andDictnameLike(String value) {
            addCriterion("dictname like", value, "dictname");
            return (Criteria) this;
        }

        public Criteria andDictnameNotLike(String value) {
            addCriterion("dictname not like", value, "dictname");
            return (Criteria) this;
        }

        public Criteria andDictnameIn(List<String> values) {
            addCriterion("dictname in", values, "dictname");
            return (Criteria) this;
        }

        public Criteria andDictnameNotIn(List<String> values) {
            addCriterion("dictname not in", values, "dictname");
            return (Criteria) this;
        }

        public Criteria andDictnameBetween(String value1, String value2) {
            addCriterion("dictname between", value1, value2, "dictname");
            return (Criteria) this;
        }

        public Criteria andDictnameNotBetween(String value1, String value2) {
            addCriterion("dictname not between", value1, value2, "dictname");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(String value) {
            addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(String value) {
            addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(String value) {
            addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(String value) {
            addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(String value) {
            addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(String value) {
            addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLike(String value) {
            addCriterion("sort like", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotLike(String value) {
            addCriterion("sort not like", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<String> values) {
            addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<String> values) {
            addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(String value1, String value2) {
            addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(String value1, String value2) {
            addCriterion("sort not between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andMemoIsNull() {
            addCriterion("memo is null");
            return (Criteria) this;
        }

        public Criteria andMemoIsNotNull() {
            addCriterion("memo is not null");
            return (Criteria) this;
        }

        public Criteria andMemoEqualTo(String value) {
            addCriterion("memo =", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotEqualTo(String value) {
            addCriterion("memo <>", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThan(String value) {
            addCriterion("memo >", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThanOrEqualTo(String value) {
            addCriterion("memo >=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThan(String value) {
            addCriterion("memo <", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThanOrEqualTo(String value) {
            addCriterion("memo <=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLike(String value) {
            addCriterion("memo like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotLike(String value) {
            addCriterion("memo not like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoIn(List<String> values) {
            addCriterion("memo in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotIn(List<String> values) {
            addCriterion("memo not in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoBetween(String value1, String value2) {
            addCriterion("memo between", value1, value2, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotBetween(String value1, String value2) {
            addCriterion("memo not between", value1, value2, "memo");
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