package com.fwk.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TFtaskwarnExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TFtaskwarnExample() {
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

        public Criteria andWarntimeIsNull() {
            addCriterion("warntime is null");
            return (Criteria) this;
        }

        public Criteria andWarntimeIsNotNull() {
            addCriterion("warntime is not null");
            return (Criteria) this;
        }

        public Criteria andWarntimeEqualTo(Date value) {
            addCriterion("warntime =", value, "warntime");
            return (Criteria) this;
        }

        public Criteria andWarntimeNotEqualTo(Date value) {
            addCriterion("warntime <>", value, "warntime");
            return (Criteria) this;
        }

        public Criteria andWarntimeGreaterThan(Date value) {
            addCriterion("warntime >", value, "warntime");
            return (Criteria) this;
        }

        public Criteria andWarntimeGreaterThanOrEqualTo(Date value) {
            addCriterion("warntime >=", value, "warntime");
            return (Criteria) this;
        }

        public Criteria andWarntimeLessThan(Date value) {
            addCriterion("warntime <", value, "warntime");
            return (Criteria) this;
        }

        public Criteria andWarntimeLessThanOrEqualTo(Date value) {
            addCriterion("warntime <=", value, "warntime");
            return (Criteria) this;
        }

        public Criteria andWarntimeIn(List<Date> values) {
            addCriterion("warntime in", values, "warntime");
            return (Criteria) this;
        }

        public Criteria andWarntimeNotIn(List<Date> values) {
            addCriterion("warntime not in", values, "warntime");
            return (Criteria) this;
        }

        public Criteria andWarntimeBetween(Date value1, Date value2) {
            addCriterion("warntime between", value1, value2, "warntime");
            return (Criteria) this;
        }

        public Criteria andWarntimeNotBetween(Date value1, Date value2) {
            addCriterion("warntime not between", value1, value2, "warntime");
            return (Criteria) this;
        }

        public Criteria andTaskidIsNull() {
            addCriterion("taskid is null");
            return (Criteria) this;
        }

        public Criteria andTaskidIsNotNull() {
            addCriterion("taskid is not null");
            return (Criteria) this;
        }

        public Criteria andTaskidEqualTo(Integer value) {
            addCriterion("taskid =", value, "taskid");
            return (Criteria) this;
        }

        public Criteria andTaskidNotEqualTo(Integer value) {
            addCriterion("taskid <>", value, "taskid");
            return (Criteria) this;
        }

        public Criteria andTaskidGreaterThan(Integer value) {
            addCriterion("taskid >", value, "taskid");
            return (Criteria) this;
        }

        public Criteria andTaskidGreaterThanOrEqualTo(Integer value) {
            addCriterion("taskid >=", value, "taskid");
            return (Criteria) this;
        }

        public Criteria andTaskidLessThan(Integer value) {
            addCriterion("taskid <", value, "taskid");
            return (Criteria) this;
        }

        public Criteria andTaskidLessThanOrEqualTo(Integer value) {
            addCriterion("taskid <=", value, "taskid");
            return (Criteria) this;
        }

        public Criteria andTaskidIn(List<Integer> values) {
            addCriterion("taskid in", values, "taskid");
            return (Criteria) this;
        }

        public Criteria andTaskidNotIn(List<Integer> values) {
            addCriterion("taskid not in", values, "taskid");
            return (Criteria) this;
        }

        public Criteria andTaskidBetween(Integer value1, Integer value2) {
            addCriterion("taskid between", value1, value2, "taskid");
            return (Criteria) this;
        }

        public Criteria andTaskidNotBetween(Integer value1, Integer value2) {
            addCriterion("taskid not between", value1, value2, "taskid");
            return (Criteria) this;
        }

        public Criteria andWarnpartIsNull() {
            addCriterion("warnpart is null");
            return (Criteria) this;
        }

        public Criteria andWarnpartIsNotNull() {
            addCriterion("warnpart is not null");
            return (Criteria) this;
        }

        public Criteria andWarnpartEqualTo(Integer value) {
            addCriterion("warnpart =", value, "warnpart");
            return (Criteria) this;
        }

        public Criteria andWarnpartNotEqualTo(Integer value) {
            addCriterion("warnpart <>", value, "warnpart");
            return (Criteria) this;
        }

        public Criteria andWarnpartGreaterThan(Integer value) {
            addCriterion("warnpart >", value, "warnpart");
            return (Criteria) this;
        }

        public Criteria andWarnpartGreaterThanOrEqualTo(Integer value) {
            addCriterion("warnpart >=", value, "warnpart");
            return (Criteria) this;
        }

        public Criteria andWarnpartLessThan(Integer value) {
            addCriterion("warnpart <", value, "warnpart");
            return (Criteria) this;
        }

        public Criteria andWarnpartLessThanOrEqualTo(Integer value) {
            addCriterion("warnpart <=", value, "warnpart");
            return (Criteria) this;
        }

        public Criteria andWarnpartIn(List<Integer> values) {
            addCriterion("warnpart in", values, "warnpart");
            return (Criteria) this;
        }

        public Criteria andWarnpartNotIn(List<Integer> values) {
            addCriterion("warnpart not in", values, "warnpart");
            return (Criteria) this;
        }

        public Criteria andWarnpartBetween(Integer value1, Integer value2) {
            addCriterion("warnpart between", value1, value2, "warnpart");
            return (Criteria) this;
        }

        public Criteria andWarnpartNotBetween(Integer value1, Integer value2) {
            addCriterion("warnpart not between", value1, value2, "warnpart");
            return (Criteria) this;
        }

        public Criteria andWarnexecuteIsNull() {
            addCriterion("warnexecute is null");
            return (Criteria) this;
        }

        public Criteria andWarnexecuteIsNotNull() {
            addCriterion("warnexecute is not null");
            return (Criteria) this;
        }

        public Criteria andWarnexecuteEqualTo(Integer value) {
            addCriterion("warnexecute =", value, "warnexecute");
            return (Criteria) this;
        }

        public Criteria andWarnexecuteNotEqualTo(Integer value) {
            addCriterion("warnexecute <>", value, "warnexecute");
            return (Criteria) this;
        }

        public Criteria andWarnexecuteGreaterThan(Integer value) {
            addCriterion("warnexecute >", value, "warnexecute");
            return (Criteria) this;
        }

        public Criteria andWarnexecuteGreaterThanOrEqualTo(Integer value) {
            addCriterion("warnexecute >=", value, "warnexecute");
            return (Criteria) this;
        }

        public Criteria andWarnexecuteLessThan(Integer value) {
            addCriterion("warnexecute <", value, "warnexecute");
            return (Criteria) this;
        }

        public Criteria andWarnexecuteLessThanOrEqualTo(Integer value) {
            addCriterion("warnexecute <=", value, "warnexecute");
            return (Criteria) this;
        }

        public Criteria andWarnexecuteIn(List<Integer> values) {
            addCriterion("warnexecute in", values, "warnexecute");
            return (Criteria) this;
        }

        public Criteria andWarnexecuteNotIn(List<Integer> values) {
            addCriterion("warnexecute not in", values, "warnexecute");
            return (Criteria) this;
        }

        public Criteria andWarnexecuteBetween(Integer value1, Integer value2) {
            addCriterion("warnexecute between", value1, value2, "warnexecute");
            return (Criteria) this;
        }

        public Criteria andWarnexecuteNotBetween(Integer value1, Integer value2) {
            addCriterion("warnexecute not between", value1, value2, "warnexecute");
            return (Criteria) this;
        }

        public Criteria andWarntimesIsNull() {
            addCriterion("warntimes is null");
            return (Criteria) this;
        }

        public Criteria andWarntimesIsNotNull() {
            addCriterion("warntimes is not null");
            return (Criteria) this;
        }

        public Criteria andWarntimesEqualTo(Integer value) {
            addCriterion("warntimes =", value, "warntimes");
            return (Criteria) this;
        }

        public Criteria andWarntimesNotEqualTo(Integer value) {
            addCriterion("warntimes <>", value, "warntimes");
            return (Criteria) this;
        }

        public Criteria andWarntimesGreaterThan(Integer value) {
            addCriterion("warntimes >", value, "warntimes");
            return (Criteria) this;
        }

        public Criteria andWarntimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("warntimes >=", value, "warntimes");
            return (Criteria) this;
        }

        public Criteria andWarntimesLessThan(Integer value) {
            addCriterion("warntimes <", value, "warntimes");
            return (Criteria) this;
        }

        public Criteria andWarntimesLessThanOrEqualTo(Integer value) {
            addCriterion("warntimes <=", value, "warntimes");
            return (Criteria) this;
        }

        public Criteria andWarntimesIn(List<Integer> values) {
            addCriterion("warntimes in", values, "warntimes");
            return (Criteria) this;
        }

        public Criteria andWarntimesNotIn(List<Integer> values) {
            addCriterion("warntimes not in", values, "warntimes");
            return (Criteria) this;
        }

        public Criteria andWarntimesBetween(Integer value1, Integer value2) {
            addCriterion("warntimes between", value1, value2, "warntimes");
            return (Criteria) this;
        }

        public Criteria andWarntimesNotBetween(Integer value1, Integer value2) {
            addCriterion("warntimes not between", value1, value2, "warntimes");
            return (Criteria) this;
        }

        public Criteria andTimeunitIsNull() {
            addCriterion("timeunit is null");
            return (Criteria) this;
        }

        public Criteria andTimeunitIsNotNull() {
            addCriterion("timeunit is not null");
            return (Criteria) this;
        }

        public Criteria andTimeunitEqualTo(String value) {
            addCriterion("timeunit =", value, "timeunit");
            return (Criteria) this;
        }

        public Criteria andTimeunitNotEqualTo(String value) {
            addCriterion("timeunit <>", value, "timeunit");
            return (Criteria) this;
        }

        public Criteria andTimeunitGreaterThan(String value) {
            addCriterion("timeunit >", value, "timeunit");
            return (Criteria) this;
        }

        public Criteria andTimeunitGreaterThanOrEqualTo(String value) {
            addCriterion("timeunit >=", value, "timeunit");
            return (Criteria) this;
        }

        public Criteria andTimeunitLessThan(String value) {
            addCriterion("timeunit <", value, "timeunit");
            return (Criteria) this;
        }

        public Criteria andTimeunitLessThanOrEqualTo(String value) {
            addCriterion("timeunit <=", value, "timeunit");
            return (Criteria) this;
        }

        public Criteria andTimeunitLike(String value) {
            addCriterion("timeunit like", value, "timeunit");
            return (Criteria) this;
        }

        public Criteria andTimeunitNotLike(String value) {
            addCriterion("timeunit not like", value, "timeunit");
            return (Criteria) this;
        }

        public Criteria andTimeunitIn(List<String> values) {
            addCriterion("timeunit in", values, "timeunit");
            return (Criteria) this;
        }

        public Criteria andTimeunitNotIn(List<String> values) {
            addCriterion("timeunit not in", values, "timeunit");
            return (Criteria) this;
        }

        public Criteria andTimeunitBetween(String value1, String value2) {
            addCriterion("timeunit between", value1, value2, "timeunit");
            return (Criteria) this;
        }

        public Criteria andTimeunitNotBetween(String value1, String value2) {
            addCriterion("timeunit not between", value1, value2, "timeunit");
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