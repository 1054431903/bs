package com.fwk.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TFoutputExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TFoutputExample() {
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
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

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andStandIsNull() {
            addCriterion("stand is null");
            return (Criteria) this;
        }

        public Criteria andStandIsNotNull() {
            addCriterion("stand is not null");
            return (Criteria) this;
        }

        public Criteria andStandEqualTo(String value) {
            addCriterion("stand =", value, "stand");
            return (Criteria) this;
        }

        public Criteria andStandNotEqualTo(String value) {
            addCriterion("stand <>", value, "stand");
            return (Criteria) this;
        }

        public Criteria andStandGreaterThan(String value) {
            addCriterion("stand >", value, "stand");
            return (Criteria) this;
        }

        public Criteria andStandGreaterThanOrEqualTo(String value) {
            addCriterion("stand >=", value, "stand");
            return (Criteria) this;
        }

        public Criteria andStandLessThan(String value) {
            addCriterion("stand <", value, "stand");
            return (Criteria) this;
        }

        public Criteria andStandLessThanOrEqualTo(String value) {
            addCriterion("stand <=", value, "stand");
            return (Criteria) this;
        }

        public Criteria andStandLike(String value) {
            addCriterion("stand like", value, "stand");
            return (Criteria) this;
        }

        public Criteria andStandNotLike(String value) {
            addCriterion("stand not like", value, "stand");
            return (Criteria) this;
        }

        public Criteria andStandIn(List<String> values) {
            addCriterion("stand in", values, "stand");
            return (Criteria) this;
        }

        public Criteria andStandNotIn(List<String> values) {
            addCriterion("stand not in", values, "stand");
            return (Criteria) this;
        }

        public Criteria andStandBetween(String value1, String value2) {
            addCriterion("stand between", value1, value2, "stand");
            return (Criteria) this;
        }

        public Criteria andStandNotBetween(String value1, String value2) {
            addCriterion("stand not between", value1, value2, "stand");
            return (Criteria) this;
        }

        public Criteria andStandunitIsNull() {
            addCriterion("standunit is null");
            return (Criteria) this;
        }

        public Criteria andStandunitIsNotNull() {
            addCriterion("standunit is not null");
            return (Criteria) this;
        }

        public Criteria andStandunitEqualTo(Integer value) {
            addCriterion("standunit =", value, "standunit");
            return (Criteria) this;
        }

        public Criteria andStandunitNotEqualTo(Integer value) {
            addCriterion("standunit <>", value, "standunit");
            return (Criteria) this;
        }

        public Criteria andStandunitGreaterThan(Integer value) {
            addCriterion("standunit >", value, "standunit");
            return (Criteria) this;
        }

        public Criteria andStandunitGreaterThanOrEqualTo(Integer value) {
            addCriterion("standunit >=", value, "standunit");
            return (Criteria) this;
        }

        public Criteria andStandunitLessThan(Integer value) {
            addCriterion("standunit <", value, "standunit");
            return (Criteria) this;
        }

        public Criteria andStandunitLessThanOrEqualTo(Integer value) {
            addCriterion("standunit <=", value, "standunit");
            return (Criteria) this;
        }

        public Criteria andStandunitIn(List<Integer> values) {
            addCriterion("standunit in", values, "standunit");
            return (Criteria) this;
        }

        public Criteria andStandunitNotIn(List<Integer> values) {
            addCriterion("standunit not in", values, "standunit");
            return (Criteria) this;
        }

        public Criteria andStandunitBetween(Integer value1, Integer value2) {
            addCriterion("standunit between", value1, value2, "standunit");
            return (Criteria) this;
        }

        public Criteria andStandunitNotBetween(Integer value1, Integer value2) {
            addCriterion("standunit not between", value1, value2, "standunit");
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

        public Criteria andNumEqualTo(Double value) {
            addCriterion("num =", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotEqualTo(Double value) {
            addCriterion("num <>", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThan(Double value) {
            addCriterion("num >", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThanOrEqualTo(Double value) {
            addCriterion("num >=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThan(Double value) {
            addCriterion("num <", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThanOrEqualTo(Double value) {
            addCriterion("num <=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumIn(List<Double> values) {
            addCriterion("num in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotIn(List<Double> values) {
            addCriterion("num not in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumBetween(Double value1, Double value2) {
            addCriterion("num between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotBetween(Double value1, Double value2) {
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

        public Criteria andNumunitEqualTo(Integer value) {
            addCriterion("numunit =", value, "numunit");
            return (Criteria) this;
        }

        public Criteria andNumunitNotEqualTo(Integer value) {
            addCriterion("numunit <>", value, "numunit");
            return (Criteria) this;
        }

        public Criteria andNumunitGreaterThan(Integer value) {
            addCriterion("numunit >", value, "numunit");
            return (Criteria) this;
        }

        public Criteria andNumunitGreaterThanOrEqualTo(Integer value) {
            addCriterion("numunit >=", value, "numunit");
            return (Criteria) this;
        }

        public Criteria andNumunitLessThan(Integer value) {
            addCriterion("numunit <", value, "numunit");
            return (Criteria) this;
        }

        public Criteria andNumunitLessThanOrEqualTo(Integer value) {
            addCriterion("numunit <=", value, "numunit");
            return (Criteria) this;
        }

        public Criteria andNumunitIn(List<Integer> values) {
            addCriterion("numunit in", values, "numunit");
            return (Criteria) this;
        }

        public Criteria andNumunitNotIn(List<Integer> values) {
            addCriterion("numunit not in", values, "numunit");
            return (Criteria) this;
        }

        public Criteria andNumunitBetween(Integer value1, Integer value2) {
            addCriterion("numunit between", value1, value2, "numunit");
            return (Criteria) this;
        }

        public Criteria andNumunitNotBetween(Integer value1, Integer value2) {
            addCriterion("numunit not between", value1, value2, "numunit");
            return (Criteria) this;
        }

        public Criteria andFacturerIsNull() {
            addCriterion("facturer is null");
            return (Criteria) this;
        }

        public Criteria andFacturerIsNotNull() {
            addCriterion("facturer is not null");
            return (Criteria) this;
        }

        public Criteria andFacturerEqualTo(String value) {
            addCriterion("facturer =", value, "facturer");
            return (Criteria) this;
        }

        public Criteria andFacturerNotEqualTo(String value) {
            addCriterion("facturer <>", value, "facturer");
            return (Criteria) this;
        }

        public Criteria andFacturerGreaterThan(String value) {
            addCriterion("facturer >", value, "facturer");
            return (Criteria) this;
        }

        public Criteria andFacturerGreaterThanOrEqualTo(String value) {
            addCriterion("facturer >=", value, "facturer");
            return (Criteria) this;
        }

        public Criteria andFacturerLessThan(String value) {
            addCriterion("facturer <", value, "facturer");
            return (Criteria) this;
        }

        public Criteria andFacturerLessThanOrEqualTo(String value) {
            addCriterion("facturer <=", value, "facturer");
            return (Criteria) this;
        }

        public Criteria andFacturerLike(String value) {
            addCriterion("facturer like", value, "facturer");
            return (Criteria) this;
        }

        public Criteria andFacturerNotLike(String value) {
            addCriterion("facturer not like", value, "facturer");
            return (Criteria) this;
        }

        public Criteria andFacturerIn(List<String> values) {
            addCriterion("facturer in", values, "facturer");
            return (Criteria) this;
        }

        public Criteria andFacturerNotIn(List<String> values) {
            addCriterion("facturer not in", values, "facturer");
            return (Criteria) this;
        }

        public Criteria andFacturerBetween(String value1, String value2) {
            addCriterion("facturer between", value1, value2, "facturer");
            return (Criteria) this;
        }

        public Criteria andFacturerNotBetween(String value1, String value2) {
            addCriterion("facturer not between", value1, value2, "facturer");
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