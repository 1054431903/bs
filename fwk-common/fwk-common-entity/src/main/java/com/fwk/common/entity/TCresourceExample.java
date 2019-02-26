package com.fwk.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TCresourceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TCresourceExample() {
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

        public Criteria andResourcepathIsNull() {
            addCriterion("resourcepath is null");
            return (Criteria) this;
        }

        public Criteria andResourcepathIsNotNull() {
            addCriterion("resourcepath is not null");
            return (Criteria) this;
        }

        public Criteria andResourcepathEqualTo(String value) {
            addCriterion("resourcepath =", value, "resourcepath");
            return (Criteria) this;
        }

        public Criteria andResourcepathNotEqualTo(String value) {
            addCriterion("resourcepath <>", value, "resourcepath");
            return (Criteria) this;
        }

        public Criteria andResourcepathGreaterThan(String value) {
            addCriterion("resourcepath >", value, "resourcepath");
            return (Criteria) this;
        }

        public Criteria andResourcepathGreaterThanOrEqualTo(String value) {
            addCriterion("resourcepath >=", value, "resourcepath");
            return (Criteria) this;
        }

        public Criteria andResourcepathLessThan(String value) {
            addCriterion("resourcepath <", value, "resourcepath");
            return (Criteria) this;
        }

        public Criteria andResourcepathLessThanOrEqualTo(String value) {
            addCriterion("resourcepath <=", value, "resourcepath");
            return (Criteria) this;
        }

        public Criteria andResourcepathLike(String value) {
            addCriterion("resourcepath like", value, "resourcepath");
            return (Criteria) this;
        }

        public Criteria andResourcepathNotLike(String value) {
            addCriterion("resourcepath not like", value, "resourcepath");
            return (Criteria) this;
        }

        public Criteria andResourcepathIn(List<String> values) {
            addCriterion("resourcepath in", values, "resourcepath");
            return (Criteria) this;
        }

        public Criteria andResourcepathNotIn(List<String> values) {
            addCriterion("resourcepath not in", values, "resourcepath");
            return (Criteria) this;
        }

        public Criteria andResourcepathBetween(String value1, String value2) {
            addCriterion("resourcepath between", value1, value2, "resourcepath");
            return (Criteria) this;
        }

        public Criteria andResourcepathNotBetween(String value1, String value2) {
            addCriterion("resourcepath not between", value1, value2, "resourcepath");
            return (Criteria) this;
        }

        public Criteria andReleatedtypeIsNull() {
            addCriterion("releatedtype is null");
            return (Criteria) this;
        }

        public Criteria andReleatedtypeIsNotNull() {
            addCriterion("releatedtype is not null");
            return (Criteria) this;
        }

        public Criteria andReleatedtypeEqualTo(String value) {
            addCriterion("releatedtype =", value, "releatedtype");
            return (Criteria) this;
        }

        public Criteria andReleatedtypeNotEqualTo(String value) {
            addCriterion("releatedtype <>", value, "releatedtype");
            return (Criteria) this;
        }

        public Criteria andReleatedtypeGreaterThan(String value) {
            addCriterion("releatedtype >", value, "releatedtype");
            return (Criteria) this;
        }

        public Criteria andReleatedtypeGreaterThanOrEqualTo(String value) {
            addCriterion("releatedtype >=", value, "releatedtype");
            return (Criteria) this;
        }

        public Criteria andReleatedtypeLessThan(String value) {
            addCriterion("releatedtype <", value, "releatedtype");
            return (Criteria) this;
        }

        public Criteria andReleatedtypeLessThanOrEqualTo(String value) {
            addCriterion("releatedtype <=", value, "releatedtype");
            return (Criteria) this;
        }

        public Criteria andReleatedtypeLike(String value) {
            addCriterion("releatedtype like", value, "releatedtype");
            return (Criteria) this;
        }

        public Criteria andReleatedtypeNotLike(String value) {
            addCriterion("releatedtype not like", value, "releatedtype");
            return (Criteria) this;
        }

        public Criteria andReleatedtypeIn(List<String> values) {
            addCriterion("releatedtype in", values, "releatedtype");
            return (Criteria) this;
        }

        public Criteria andReleatedtypeNotIn(List<String> values) {
            addCriterion("releatedtype not in", values, "releatedtype");
            return (Criteria) this;
        }

        public Criteria andReleatedtypeBetween(String value1, String value2) {
            addCriterion("releatedtype between", value1, value2, "releatedtype");
            return (Criteria) this;
        }

        public Criteria andReleatedtypeNotBetween(String value1, String value2) {
            addCriterion("releatedtype not between", value1, value2, "releatedtype");
            return (Criteria) this;
        }

        public Criteria andReleatedidIsNull() {
            addCriterion("releatedid is null");
            return (Criteria) this;
        }

        public Criteria andReleatedidIsNotNull() {
            addCriterion("releatedid is not null");
            return (Criteria) this;
        }

        public Criteria andReleatedidEqualTo(Integer value) {
            addCriterion("releatedid =", value, "releatedid");
            return (Criteria) this;
        }

        public Criteria andReleatedidNotEqualTo(Integer value) {
            addCriterion("releatedid <>", value, "releatedid");
            return (Criteria) this;
        }

        public Criteria andReleatedidGreaterThan(Integer value) {
            addCriterion("releatedid >", value, "releatedid");
            return (Criteria) this;
        }

        public Criteria andReleatedidGreaterThanOrEqualTo(Integer value) {
            addCriterion("releatedid >=", value, "releatedid");
            return (Criteria) this;
        }

        public Criteria andReleatedidLessThan(Integer value) {
            addCriterion("releatedid <", value, "releatedid");
            return (Criteria) this;
        }

        public Criteria andReleatedidLessThanOrEqualTo(Integer value) {
            addCriterion("releatedid <=", value, "releatedid");
            return (Criteria) this;
        }

        public Criteria andReleatedidIn(List<Integer> values) {
            addCriterion("releatedid in", values, "releatedid");
            return (Criteria) this;
        }

        public Criteria andReleatedidNotIn(List<Integer> values) {
            addCriterion("releatedid not in", values, "releatedid");
            return (Criteria) this;
        }

        public Criteria andReleatedidBetween(Integer value1, Integer value2) {
            addCriterion("releatedid between", value1, value2, "releatedid");
            return (Criteria) this;
        }

        public Criteria andReleatedidNotBetween(Integer value1, Integer value2) {
            addCriterion("releatedid not between", value1, value2, "releatedid");
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

        public Criteria andLocalpathIsNull() {
            addCriterion("localpath is null");
            return (Criteria) this;
        }

        public Criteria andLocalpathIsNotNull() {
            addCriterion("localpath is not null");
            return (Criteria) this;
        }

        public Criteria andLocalpathEqualTo(String value) {
            addCriterion("localpath =", value, "localpath");
            return (Criteria) this;
        }

        public Criteria andLocalpathNotEqualTo(String value) {
            addCriterion("localpath <>", value, "localpath");
            return (Criteria) this;
        }

        public Criteria andLocalpathGreaterThan(String value) {
            addCriterion("localpath >", value, "localpath");
            return (Criteria) this;
        }

        public Criteria andLocalpathGreaterThanOrEqualTo(String value) {
            addCriterion("localpath >=", value, "localpath");
            return (Criteria) this;
        }

        public Criteria andLocalpathLessThan(String value) {
            addCriterion("localpath <", value, "localpath");
            return (Criteria) this;
        }

        public Criteria andLocalpathLessThanOrEqualTo(String value) {
            addCriterion("localpath <=", value, "localpath");
            return (Criteria) this;
        }

        public Criteria andLocalpathLike(String value) {
            addCriterion("localpath like", value, "localpath");
            return (Criteria) this;
        }

        public Criteria andLocalpathNotLike(String value) {
            addCriterion("localpath not like", value, "localpath");
            return (Criteria) this;
        }

        public Criteria andLocalpathIn(List<String> values) {
            addCriterion("localpath in", values, "localpath");
            return (Criteria) this;
        }

        public Criteria andLocalpathNotIn(List<String> values) {
            addCriterion("localpath not in", values, "localpath");
            return (Criteria) this;
        }

        public Criteria andLocalpathBetween(String value1, String value2) {
            addCriterion("localpath between", value1, value2, "localpath");
            return (Criteria) this;
        }

        public Criteria andLocalpathNotBetween(String value1, String value2) {
            addCriterion("localpath not between", value1, value2, "localpath");
            return (Criteria) this;
        }

        public Criteria andResourcenameIsNull() {
            addCriterion("resourcename is null");
            return (Criteria) this;
        }

        public Criteria andResourcenameIsNotNull() {
            addCriterion("resourcename is not null");
            return (Criteria) this;
        }

        public Criteria andResourcenameEqualTo(String value) {
            addCriterion("resourcename =", value, "resourcename");
            return (Criteria) this;
        }

        public Criteria andResourcenameNotEqualTo(String value) {
            addCriterion("resourcename <>", value, "resourcename");
            return (Criteria) this;
        }

        public Criteria andResourcenameGreaterThan(String value) {
            addCriterion("resourcename >", value, "resourcename");
            return (Criteria) this;
        }

        public Criteria andResourcenameGreaterThanOrEqualTo(String value) {
            addCriterion("resourcename >=", value, "resourcename");
            return (Criteria) this;
        }

        public Criteria andResourcenameLessThan(String value) {
            addCriterion("resourcename <", value, "resourcename");
            return (Criteria) this;
        }

        public Criteria andResourcenameLessThanOrEqualTo(String value) {
            addCriterion("resourcename <=", value, "resourcename");
            return (Criteria) this;
        }

        public Criteria andResourcenameLike(String value) {
            addCriterion("resourcename like", value, "resourcename");
            return (Criteria) this;
        }

        public Criteria andResourcenameNotLike(String value) {
            addCriterion("resourcename not like", value, "resourcename");
            return (Criteria) this;
        }

        public Criteria andResourcenameIn(List<String> values) {
            addCriterion("resourcename in", values, "resourcename");
            return (Criteria) this;
        }

        public Criteria andResourcenameNotIn(List<String> values) {
            addCriterion("resourcename not in", values, "resourcename");
            return (Criteria) this;
        }

        public Criteria andResourcenameBetween(String value1, String value2) {
            addCriterion("resourcename between", value1, value2, "resourcename");
            return (Criteria) this;
        }

        public Criteria andResourcenameNotBetween(String value1, String value2) {
            addCriterion("resourcename not between", value1, value2, "resourcename");
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

        public Criteria andTimelengthIsNull() {
            addCriterion("timelength is null");
            return (Criteria) this;
        }

        public Criteria andTimelengthIsNotNull() {
            addCriterion("timelength is not null");
            return (Criteria) this;
        }

        public Criteria andTimelengthEqualTo(Integer value) {
            addCriterion("timelength =", value, "timelength");
            return (Criteria) this;
        }

        public Criteria andTimelengthNotEqualTo(Integer value) {
            addCriterion("timelength <>", value, "timelength");
            return (Criteria) this;
        }

        public Criteria andTimelengthGreaterThan(Integer value) {
            addCriterion("timelength >", value, "timelength");
            return (Criteria) this;
        }

        public Criteria andTimelengthGreaterThanOrEqualTo(Integer value) {
            addCriterion("timelength >=", value, "timelength");
            return (Criteria) this;
        }

        public Criteria andTimelengthLessThan(Integer value) {
            addCriterion("timelength <", value, "timelength");
            return (Criteria) this;
        }

        public Criteria andTimelengthLessThanOrEqualTo(Integer value) {
            addCriterion("timelength <=", value, "timelength");
            return (Criteria) this;
        }

        public Criteria andTimelengthIn(List<Integer> values) {
            addCriterion("timelength in", values, "timelength");
            return (Criteria) this;
        }

        public Criteria andTimelengthNotIn(List<Integer> values) {
            addCriterion("timelength not in", values, "timelength");
            return (Criteria) this;
        }

        public Criteria andTimelengthBetween(Integer value1, Integer value2) {
            addCriterion("timelength between", value1, value2, "timelength");
            return (Criteria) this;
        }

        public Criteria andTimelengthNotBetween(Integer value1, Integer value2) {
            addCriterion("timelength not between", value1, value2, "timelength");
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