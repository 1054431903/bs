package com.fwk.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TClogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TClogExample() {
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

        public Criteria andUseridIsNull() {
            addCriterion("userid is null");
            return (Criteria) this;
        }

        public Criteria andUseridIsNotNull() {
            addCriterion("userid is not null");
            return (Criteria) this;
        }

        public Criteria andUseridEqualTo(Integer value) {
            addCriterion("userid =", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotEqualTo(Integer value) {
            addCriterion("userid <>", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThan(Integer value) {
            addCriterion("userid >", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("userid >=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThan(Integer value) {
            addCriterion("userid <", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThanOrEqualTo(Integer value) {
            addCriterion("userid <=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridIn(List<Integer> values) {
            addCriterion("userid in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotIn(List<Integer> values) {
            addCriterion("userid not in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridBetween(Integer value1, Integer value2) {
            addCriterion("userid between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotBetween(Integer value1, Integer value2) {
            addCriterion("userid not between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("ip not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andResultIsNull() {
            addCriterion("result is null");
            return (Criteria) this;
        }

        public Criteria andResultIsNotNull() {
            addCriterion("result is not null");
            return (Criteria) this;
        }

        public Criteria andResultEqualTo(String value) {
            addCriterion("result =", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotEqualTo(String value) {
            addCriterion("result <>", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThan(String value) {
            addCriterion("result >", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThanOrEqualTo(String value) {
            addCriterion("result >=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThan(String value) {
            addCriterion("result <", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThanOrEqualTo(String value) {
            addCriterion("result <=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLike(String value) {
            addCriterion("result like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotLike(String value) {
            addCriterion("result not like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultIn(List<String> values) {
            addCriterion("result in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotIn(List<String> values) {
            addCriterion("result not in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultBetween(String value1, String value2) {
            addCriterion("result between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotBetween(String value1, String value2) {
            addCriterion("result not between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andRemaincontentIsNull() {
            addCriterion("remaincontent is null");
            return (Criteria) this;
        }

        public Criteria andRemaincontentIsNotNull() {
            addCriterion("remaincontent is not null");
            return (Criteria) this;
        }

        public Criteria andRemaincontentEqualTo(String value) {
            addCriterion("remaincontent =", value, "remaincontent");
            return (Criteria) this;
        }

        public Criteria andRemaincontentNotEqualTo(String value) {
            addCriterion("remaincontent <>", value, "remaincontent");
            return (Criteria) this;
        }

        public Criteria andRemaincontentGreaterThan(String value) {
            addCriterion("remaincontent >", value, "remaincontent");
            return (Criteria) this;
        }

        public Criteria andRemaincontentGreaterThanOrEqualTo(String value) {
            addCriterion("remaincontent >=", value, "remaincontent");
            return (Criteria) this;
        }

        public Criteria andRemaincontentLessThan(String value) {
            addCriterion("remaincontent <", value, "remaincontent");
            return (Criteria) this;
        }

        public Criteria andRemaincontentLessThanOrEqualTo(String value) {
            addCriterion("remaincontent <=", value, "remaincontent");
            return (Criteria) this;
        }

        public Criteria andRemaincontentLike(String value) {
            addCriterion("remaincontent like", value, "remaincontent");
            return (Criteria) this;
        }

        public Criteria andRemaincontentNotLike(String value) {
            addCriterion("remaincontent not like", value, "remaincontent");
            return (Criteria) this;
        }

        public Criteria andRemaincontentIn(List<String> values) {
            addCriterion("remaincontent in", values, "remaincontent");
            return (Criteria) this;
        }

        public Criteria andRemaincontentNotIn(List<String> values) {
            addCriterion("remaincontent not in", values, "remaincontent");
            return (Criteria) this;
        }

        public Criteria andRemaincontentBetween(String value1, String value2) {
            addCriterion("remaincontent between", value1, value2, "remaincontent");
            return (Criteria) this;
        }

        public Criteria andRemaincontentNotBetween(String value1, String value2) {
            addCriterion("remaincontent not between", value1, value2, "remaincontent");
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

        public Criteria andRetcontentIsNull() {
            addCriterion("retcontent is null");
            return (Criteria) this;
        }

        public Criteria andRetcontentIsNotNull() {
            addCriterion("retcontent is not null");
            return (Criteria) this;
        }

        public Criteria andRetcontentEqualTo(String value) {
            addCriterion("retcontent =", value, "retcontent");
            return (Criteria) this;
        }

        public Criteria andRetcontentNotEqualTo(String value) {
            addCriterion("retcontent <>", value, "retcontent");
            return (Criteria) this;
        }

        public Criteria andRetcontentGreaterThan(String value) {
            addCriterion("retcontent >", value, "retcontent");
            return (Criteria) this;
        }

        public Criteria andRetcontentGreaterThanOrEqualTo(String value) {
            addCriterion("retcontent >=", value, "retcontent");
            return (Criteria) this;
        }

        public Criteria andRetcontentLessThan(String value) {
            addCriterion("retcontent <", value, "retcontent");
            return (Criteria) this;
        }

        public Criteria andRetcontentLessThanOrEqualTo(String value) {
            addCriterion("retcontent <=", value, "retcontent");
            return (Criteria) this;
        }

        public Criteria andRetcontentLike(String value) {
            addCriterion("retcontent like", value, "retcontent");
            return (Criteria) this;
        }

        public Criteria andRetcontentNotLike(String value) {
            addCriterion("retcontent not like", value, "retcontent");
            return (Criteria) this;
        }

        public Criteria andRetcontentIn(List<String> values) {
            addCriterion("retcontent in", values, "retcontent");
            return (Criteria) this;
        }

        public Criteria andRetcontentNotIn(List<String> values) {
            addCriterion("retcontent not in", values, "retcontent");
            return (Criteria) this;
        }

        public Criteria andRetcontentBetween(String value1, String value2) {
            addCriterion("retcontent between", value1, value2, "retcontent");
            return (Criteria) this;
        }

        public Criteria andRetcontentNotBetween(String value1, String value2) {
            addCriterion("retcontent not between", value1, value2, "retcontent");
            return (Criteria) this;
        }

        public Criteria andMethodIsNull() {
            addCriterion("method is null");
            return (Criteria) this;
        }

        public Criteria andMethodIsNotNull() {
            addCriterion("method is not null");
            return (Criteria) this;
        }

        public Criteria andMethodEqualTo(String value) {
            addCriterion("method =", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodNotEqualTo(String value) {
            addCriterion("method <>", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodGreaterThan(String value) {
            addCriterion("method >", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodGreaterThanOrEqualTo(String value) {
            addCriterion("method >=", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodLessThan(String value) {
            addCriterion("method <", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodLessThanOrEqualTo(String value) {
            addCriterion("method <=", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodLike(String value) {
            addCriterion("method like", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodNotLike(String value) {
            addCriterion("method not like", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodIn(List<String> values) {
            addCriterion("method in", values, "method");
            return (Criteria) this;
        }

        public Criteria andMethodNotIn(List<String> values) {
            addCriterion("method not in", values, "method");
            return (Criteria) this;
        }

        public Criteria andMethodBetween(String value1, String value2) {
            addCriterion("method between", value1, value2, "method");
            return (Criteria) this;
        }

        public Criteria andMethodNotBetween(String value1, String value2) {
            addCriterion("method not between", value1, value2, "method");
            return (Criteria) this;
        }

        public Criteria andExpenditureIsNull() {
            addCriterion("expenditure is null");
            return (Criteria) this;
        }

        public Criteria andExpenditureIsNotNull() {
            addCriterion("expenditure is not null");
            return (Criteria) this;
        }

        public Criteria andExpenditureEqualTo(Long value) {
            addCriterion("expenditure =", value, "expenditure");
            return (Criteria) this;
        }

        public Criteria andExpenditureNotEqualTo(Long value) {
            addCriterion("expenditure <>", value, "expenditure");
            return (Criteria) this;
        }

        public Criteria andExpenditureGreaterThan(Long value) {
            addCriterion("expenditure >", value, "expenditure");
            return (Criteria) this;
        }

        public Criteria andExpenditureGreaterThanOrEqualTo(Long value) {
            addCriterion("expenditure >=", value, "expenditure");
            return (Criteria) this;
        }

        public Criteria andExpenditureLessThan(Long value) {
            addCriterion("expenditure <", value, "expenditure");
            return (Criteria) this;
        }

        public Criteria andExpenditureLessThanOrEqualTo(Long value) {
            addCriterion("expenditure <=", value, "expenditure");
            return (Criteria) this;
        }

        public Criteria andExpenditureIn(List<Long> values) {
            addCriterion("expenditure in", values, "expenditure");
            return (Criteria) this;
        }

        public Criteria andExpenditureNotIn(List<Long> values) {
            addCriterion("expenditure not in", values, "expenditure");
            return (Criteria) this;
        }

        public Criteria andExpenditureBetween(Long value1, Long value2) {
            addCriterion("expenditure between", value1, value2, "expenditure");
            return (Criteria) this;
        }

        public Criteria andExpenditureNotBetween(Long value1, Long value2) {
            addCriterion("expenditure not between", value1, value2, "expenditure");
            return (Criteria) this;
        }

        public Criteria andMethodtypeIsNull() {
            addCriterion("methodtype is null");
            return (Criteria) this;
        }

        public Criteria andMethodtypeIsNotNull() {
            addCriterion("methodtype is not null");
            return (Criteria) this;
        }

        public Criteria andMethodtypeEqualTo(String value) {
            addCriterion("methodtype =", value, "methodtype");
            return (Criteria) this;
        }

        public Criteria andMethodtypeNotEqualTo(String value) {
            addCriterion("methodtype <>", value, "methodtype");
            return (Criteria) this;
        }

        public Criteria andMethodtypeGreaterThan(String value) {
            addCriterion("methodtype >", value, "methodtype");
            return (Criteria) this;
        }

        public Criteria andMethodtypeGreaterThanOrEqualTo(String value) {
            addCriterion("methodtype >=", value, "methodtype");
            return (Criteria) this;
        }

        public Criteria andMethodtypeLessThan(String value) {
            addCriterion("methodtype <", value, "methodtype");
            return (Criteria) this;
        }

        public Criteria andMethodtypeLessThanOrEqualTo(String value) {
            addCriterion("methodtype <=", value, "methodtype");
            return (Criteria) this;
        }

        public Criteria andMethodtypeLike(String value) {
            addCriterion("methodtype like", value, "methodtype");
            return (Criteria) this;
        }

        public Criteria andMethodtypeNotLike(String value) {
            addCriterion("methodtype not like", value, "methodtype");
            return (Criteria) this;
        }

        public Criteria andMethodtypeIn(List<String> values) {
            addCriterion("methodtype in", values, "methodtype");
            return (Criteria) this;
        }

        public Criteria andMethodtypeNotIn(List<String> values) {
            addCriterion("methodtype not in", values, "methodtype");
            return (Criteria) this;
        }

        public Criteria andMethodtypeBetween(String value1, String value2) {
            addCriterion("methodtype between", value1, value2, "methodtype");
            return (Criteria) this;
        }

        public Criteria andMethodtypeNotBetween(String value1, String value2) {
            addCriterion("methodtype not between", value1, value2, "methodtype");
            return (Criteria) this;
        }

        public Criteria andActionnameIsNull() {
            addCriterion("actionname is null");
            return (Criteria) this;
        }

        public Criteria andActionnameIsNotNull() {
            addCriterion("actionname is not null");
            return (Criteria) this;
        }

        public Criteria andActionnameEqualTo(String value) {
            addCriterion("actionname =", value, "actionname");
            return (Criteria) this;
        }

        public Criteria andActionnameNotEqualTo(String value) {
            addCriterion("actionname <>", value, "actionname");
            return (Criteria) this;
        }

        public Criteria andActionnameGreaterThan(String value) {
            addCriterion("actionname >", value, "actionname");
            return (Criteria) this;
        }

        public Criteria andActionnameGreaterThanOrEqualTo(String value) {
            addCriterion("actionname >=", value, "actionname");
            return (Criteria) this;
        }

        public Criteria andActionnameLessThan(String value) {
            addCriterion("actionname <", value, "actionname");
            return (Criteria) this;
        }

        public Criteria andActionnameLessThanOrEqualTo(String value) {
            addCriterion("actionname <=", value, "actionname");
            return (Criteria) this;
        }

        public Criteria andActionnameLike(String value) {
            addCriterion("actionname like", value, "actionname");
            return (Criteria) this;
        }

        public Criteria andActionnameNotLike(String value) {
            addCriterion("actionname not like", value, "actionname");
            return (Criteria) this;
        }

        public Criteria andActionnameIn(List<String> values) {
            addCriterion("actionname in", values, "actionname");
            return (Criteria) this;
        }

        public Criteria andActionnameNotIn(List<String> values) {
            addCriterion("actionname not in", values, "actionname");
            return (Criteria) this;
        }

        public Criteria andActionnameBetween(String value1, String value2) {
            addCriterion("actionname between", value1, value2, "actionname");
            return (Criteria) this;
        }

        public Criteria andActionnameNotBetween(String value1, String value2) {
            addCriterion("actionname not between", value1, value2, "actionname");
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