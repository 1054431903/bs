package com.fwk.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TUorgExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TUorgExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andNatureIsNull() {
            addCriterion("nature is null");
            return (Criteria) this;
        }

        public Criteria andNatureIsNotNull() {
            addCriterion("nature is not null");
            return (Criteria) this;
        }

        public Criteria andNatureEqualTo(String value) {
            addCriterion("nature =", value, "nature");
            return (Criteria) this;
        }

        public Criteria andNatureNotEqualTo(String value) {
            addCriterion("nature <>", value, "nature");
            return (Criteria) this;
        }

        public Criteria andNatureGreaterThan(String value) {
            addCriterion("nature >", value, "nature");
            return (Criteria) this;
        }

        public Criteria andNatureGreaterThanOrEqualTo(String value) {
            addCriterion("nature >=", value, "nature");
            return (Criteria) this;
        }

        public Criteria andNatureLessThan(String value) {
            addCriterion("nature <", value, "nature");
            return (Criteria) this;
        }

        public Criteria andNatureLessThanOrEqualTo(String value) {
            addCriterion("nature <=", value, "nature");
            return (Criteria) this;
        }

        public Criteria andNatureLike(String value) {
            addCriterion("nature like", value, "nature");
            return (Criteria) this;
        }

        public Criteria andNatureNotLike(String value) {
            addCriterion("nature not like", value, "nature");
            return (Criteria) this;
        }

        public Criteria andNatureIn(List<String> values) {
            addCriterion("nature in", values, "nature");
            return (Criteria) this;
        }

        public Criteria andNatureNotIn(List<String> values) {
            addCriterion("nature not in", values, "nature");
            return (Criteria) this;
        }

        public Criteria andNatureBetween(String value1, String value2) {
            addCriterion("nature between", value1, value2, "nature");
            return (Criteria) this;
        }

        public Criteria andNatureNotBetween(String value1, String value2) {
            addCriterion("nature not between", value1, value2, "nature");
            return (Criteria) this;
        }

        public Criteria andLegalIsNull() {
            addCriterion("legal is null");
            return (Criteria) this;
        }

        public Criteria andLegalIsNotNull() {
            addCriterion("legal is not null");
            return (Criteria) this;
        }

        public Criteria andLegalEqualTo(String value) {
            addCriterion("legal =", value, "legal");
            return (Criteria) this;
        }

        public Criteria andLegalNotEqualTo(String value) {
            addCriterion("legal <>", value, "legal");
            return (Criteria) this;
        }

        public Criteria andLegalGreaterThan(String value) {
            addCriterion("legal >", value, "legal");
            return (Criteria) this;
        }

        public Criteria andLegalGreaterThanOrEqualTo(String value) {
            addCriterion("legal >=", value, "legal");
            return (Criteria) this;
        }

        public Criteria andLegalLessThan(String value) {
            addCriterion("legal <", value, "legal");
            return (Criteria) this;
        }

        public Criteria andLegalLessThanOrEqualTo(String value) {
            addCriterion("legal <=", value, "legal");
            return (Criteria) this;
        }

        public Criteria andLegalLike(String value) {
            addCriterion("legal like", value, "legal");
            return (Criteria) this;
        }

        public Criteria andLegalNotLike(String value) {
            addCriterion("legal not like", value, "legal");
            return (Criteria) this;
        }

        public Criteria andLegalIn(List<String> values) {
            addCriterion("legal in", values, "legal");
            return (Criteria) this;
        }

        public Criteria andLegalNotIn(List<String> values) {
            addCriterion("legal not in", values, "legal");
            return (Criteria) this;
        }

        public Criteria andLegalBetween(String value1, String value2) {
            addCriterion("legal between", value1, value2, "legal");
            return (Criteria) this;
        }

        public Criteria andLegalNotBetween(String value1, String value2) {
            addCriterion("legal not between", value1, value2, "legal");
            return (Criteria) this;
        }

        public Criteria andEastablishtimeIsNull() {
            addCriterion("eastablishtime is null");
            return (Criteria) this;
        }

        public Criteria andEastablishtimeIsNotNull() {
            addCriterion("eastablishtime is not null");
            return (Criteria) this;
        }

        public Criteria andEastablishtimeEqualTo(Date value) {
            addCriterionForJDBCDate("eastablishtime =", value, "eastablishtime");
            return (Criteria) this;
        }

        public Criteria andEastablishtimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("eastablishtime <>", value, "eastablishtime");
            return (Criteria) this;
        }

        public Criteria andEastablishtimeGreaterThan(Date value) {
            addCriterionForJDBCDate("eastablishtime >", value, "eastablishtime");
            return (Criteria) this;
        }

        public Criteria andEastablishtimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("eastablishtime >=", value, "eastablishtime");
            return (Criteria) this;
        }

        public Criteria andEastablishtimeLessThan(Date value) {
            addCriterionForJDBCDate("eastablishtime <", value, "eastablishtime");
            return (Criteria) this;
        }

        public Criteria andEastablishtimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("eastablishtime <=", value, "eastablishtime");
            return (Criteria) this;
        }

        public Criteria andEastablishtimeIn(List<Date> values) {
            addCriterionForJDBCDate("eastablishtime in", values, "eastablishtime");
            return (Criteria) this;
        }

        public Criteria andEastablishtimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("eastablishtime not in", values, "eastablishtime");
            return (Criteria) this;
        }

        public Criteria andEastablishtimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("eastablishtime between", value1, value2, "eastablishtime");
            return (Criteria) this;
        }

        public Criteria andEastablishtimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("eastablishtime not between", value1, value2, "eastablishtime");
            return (Criteria) this;
        }

        public Criteria andLogoIsNull() {
            addCriterion("logo is null");
            return (Criteria) this;
        }

        public Criteria andLogoIsNotNull() {
            addCriterion("logo is not null");
            return (Criteria) this;
        }

        public Criteria andLogoEqualTo(Integer value) {
            addCriterion("logo =", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotEqualTo(Integer value) {
            addCriterion("logo <>", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoGreaterThan(Integer value) {
            addCriterion("logo >", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoGreaterThanOrEqualTo(Integer value) {
            addCriterion("logo >=", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLessThan(Integer value) {
            addCriterion("logo <", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLessThanOrEqualTo(Integer value) {
            addCriterion("logo <=", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoIn(List<Integer> values) {
            addCriterion("logo in", values, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotIn(List<Integer> values) {
            addCriterion("logo not in", values, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoBetween(Integer value1, Integer value2) {
            addCriterion("logo between", value1, value2, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotBetween(Integer value1, Integer value2) {
            addCriterion("logo not between", value1, value2, "logo");
            return (Criteria) this;
        }

        public Criteria andRegisfundIsNull() {
            addCriterion("regisfund is null");
            return (Criteria) this;
        }

        public Criteria andRegisfundIsNotNull() {
            addCriterion("regisfund is not null");
            return (Criteria) this;
        }

        public Criteria andRegisfundEqualTo(Double value) {
            addCriterion("regisfund =", value, "regisfund");
            return (Criteria) this;
        }

        public Criteria andRegisfundNotEqualTo(Double value) {
            addCriterion("regisfund <>", value, "regisfund");
            return (Criteria) this;
        }

        public Criteria andRegisfundGreaterThan(Double value) {
            addCriterion("regisfund >", value, "regisfund");
            return (Criteria) this;
        }

        public Criteria andRegisfundGreaterThanOrEqualTo(Double value) {
            addCriterion("regisfund >=", value, "regisfund");
            return (Criteria) this;
        }

        public Criteria andRegisfundLessThan(Double value) {
            addCriterion("regisfund <", value, "regisfund");
            return (Criteria) this;
        }

        public Criteria andRegisfundLessThanOrEqualTo(Double value) {
            addCriterion("regisfund <=", value, "regisfund");
            return (Criteria) this;
        }

        public Criteria andRegisfundIn(List<Double> values) {
            addCriterion("regisfund in", values, "regisfund");
            return (Criteria) this;
        }

        public Criteria andRegisfundNotIn(List<Double> values) {
            addCriterion("regisfund not in", values, "regisfund");
            return (Criteria) this;
        }

        public Criteria andRegisfundBetween(Double value1, Double value2) {
            addCriterion("regisfund between", value1, value2, "regisfund");
            return (Criteria) this;
        }

        public Criteria andRegisfundNotBetween(Double value1, Double value2) {
            addCriterion("regisfund not between", value1, value2, "regisfund");
            return (Criteria) this;
        }

        public Criteria andScaleIsNull() {
            addCriterion("scale is null");
            return (Criteria) this;
        }

        public Criteria andScaleIsNotNull() {
            addCriterion("scale is not null");
            return (Criteria) this;
        }

        public Criteria andScaleEqualTo(String value) {
            addCriterion("scale =", value, "scale");
            return (Criteria) this;
        }

        public Criteria andScaleNotEqualTo(String value) {
            addCriterion("scale <>", value, "scale");
            return (Criteria) this;
        }

        public Criteria andScaleGreaterThan(String value) {
            addCriterion("scale >", value, "scale");
            return (Criteria) this;
        }

        public Criteria andScaleGreaterThanOrEqualTo(String value) {
            addCriterion("scale >=", value, "scale");
            return (Criteria) this;
        }

        public Criteria andScaleLessThan(String value) {
            addCriterion("scale <", value, "scale");
            return (Criteria) this;
        }

        public Criteria andScaleLessThanOrEqualTo(String value) {
            addCriterion("scale <=", value, "scale");
            return (Criteria) this;
        }

        public Criteria andScaleLike(String value) {
            addCriterion("scale like", value, "scale");
            return (Criteria) this;
        }

        public Criteria andScaleNotLike(String value) {
            addCriterion("scale not like", value, "scale");
            return (Criteria) this;
        }

        public Criteria andScaleIn(List<String> values) {
            addCriterion("scale in", values, "scale");
            return (Criteria) this;
        }

        public Criteria andScaleNotIn(List<String> values) {
            addCriterion("scale not in", values, "scale");
            return (Criteria) this;
        }

        public Criteria andScaleBetween(String value1, String value2) {
            addCriterion("scale between", value1, value2, "scale");
            return (Criteria) this;
        }

        public Criteria andScaleNotBetween(String value1, String value2) {
            addCriterion("scale not between", value1, value2, "scale");
            return (Criteria) this;
        }

        public Criteria andAddresscodeIsNull() {
            addCriterion("addresscode is null");
            return (Criteria) this;
        }

        public Criteria andAddresscodeIsNotNull() {
            addCriterion("addresscode is not null");
            return (Criteria) this;
        }

        public Criteria andAddresscodeEqualTo(String value) {
            addCriterion("addresscode =", value, "addresscode");
            return (Criteria) this;
        }

        public Criteria andAddresscodeNotEqualTo(String value) {
            addCriterion("addresscode <>", value, "addresscode");
            return (Criteria) this;
        }

        public Criteria andAddresscodeGreaterThan(String value) {
            addCriterion("addresscode >", value, "addresscode");
            return (Criteria) this;
        }

        public Criteria andAddresscodeGreaterThanOrEqualTo(String value) {
            addCriterion("addresscode >=", value, "addresscode");
            return (Criteria) this;
        }

        public Criteria andAddresscodeLessThan(String value) {
            addCriterion("addresscode <", value, "addresscode");
            return (Criteria) this;
        }

        public Criteria andAddresscodeLessThanOrEqualTo(String value) {
            addCriterion("addresscode <=", value, "addresscode");
            return (Criteria) this;
        }

        public Criteria andAddresscodeLike(String value) {
            addCriterion("addresscode like", value, "addresscode");
            return (Criteria) this;
        }

        public Criteria andAddresscodeNotLike(String value) {
            addCriterion("addresscode not like", value, "addresscode");
            return (Criteria) this;
        }

        public Criteria andAddresscodeIn(List<String> values) {
            addCriterion("addresscode in", values, "addresscode");
            return (Criteria) this;
        }

        public Criteria andAddresscodeNotIn(List<String> values) {
            addCriterion("addresscode not in", values, "addresscode");
            return (Criteria) this;
        }

        public Criteria andAddresscodeBetween(String value1, String value2) {
            addCriterion("addresscode between", value1, value2, "addresscode");
            return (Criteria) this;
        }

        public Criteria andAddresscodeNotBetween(String value1, String value2) {
            addCriterion("addresscode not between", value1, value2, "addresscode");
            return (Criteria) this;
        }

        public Criteria andAddressdetailIsNull() {
            addCriterion("addressdetail is null");
            return (Criteria) this;
        }

        public Criteria andAddressdetailIsNotNull() {
            addCriterion("addressdetail is not null");
            return (Criteria) this;
        }

        public Criteria andAddressdetailEqualTo(String value) {
            addCriterion("addressdetail =", value, "addressdetail");
            return (Criteria) this;
        }

        public Criteria andAddressdetailNotEqualTo(String value) {
            addCriterion("addressdetail <>", value, "addressdetail");
            return (Criteria) this;
        }

        public Criteria andAddressdetailGreaterThan(String value) {
            addCriterion("addressdetail >", value, "addressdetail");
            return (Criteria) this;
        }

        public Criteria andAddressdetailGreaterThanOrEqualTo(String value) {
            addCriterion("addressdetail >=", value, "addressdetail");
            return (Criteria) this;
        }

        public Criteria andAddressdetailLessThan(String value) {
            addCriterion("addressdetail <", value, "addressdetail");
            return (Criteria) this;
        }

        public Criteria andAddressdetailLessThanOrEqualTo(String value) {
            addCriterion("addressdetail <=", value, "addressdetail");
            return (Criteria) this;
        }

        public Criteria andAddressdetailLike(String value) {
            addCriterion("addressdetail like", value, "addressdetail");
            return (Criteria) this;
        }

        public Criteria andAddressdetailNotLike(String value) {
            addCriterion("addressdetail not like", value, "addressdetail");
            return (Criteria) this;
        }

        public Criteria andAddressdetailIn(List<String> values) {
            addCriterion("addressdetail in", values, "addressdetail");
            return (Criteria) this;
        }

        public Criteria andAddressdetailNotIn(List<String> values) {
            addCriterion("addressdetail not in", values, "addressdetail");
            return (Criteria) this;
        }

        public Criteria andAddressdetailBetween(String value1, String value2) {
            addCriterion("addressdetail between", value1, value2, "addressdetail");
            return (Criteria) this;
        }

        public Criteria andAddressdetailNotBetween(String value1, String value2) {
            addCriterion("addressdetail not between", value1, value2, "addressdetail");
            return (Criteria) this;
        }

        public Criteria andBelonguseridIsNull() {
            addCriterion("belonguserid is null");
            return (Criteria) this;
        }

        public Criteria andBelonguseridIsNotNull() {
            addCriterion("belonguserid is not null");
            return (Criteria) this;
        }

        public Criteria andBelonguseridEqualTo(Integer value) {
            addCriterion("belonguserid =", value, "belonguserid");
            return (Criteria) this;
        }

        public Criteria andBelonguseridNotEqualTo(Integer value) {
            addCriterion("belonguserid <>", value, "belonguserid");
            return (Criteria) this;
        }

        public Criteria andBelonguseridGreaterThan(Integer value) {
            addCriterion("belonguserid >", value, "belonguserid");
            return (Criteria) this;
        }

        public Criteria andBelonguseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("belonguserid >=", value, "belonguserid");
            return (Criteria) this;
        }

        public Criteria andBelonguseridLessThan(Integer value) {
            addCriterion("belonguserid <", value, "belonguserid");
            return (Criteria) this;
        }

        public Criteria andBelonguseridLessThanOrEqualTo(Integer value) {
            addCriterion("belonguserid <=", value, "belonguserid");
            return (Criteria) this;
        }

        public Criteria andBelonguseridIn(List<Integer> values) {
            addCriterion("belonguserid in", values, "belonguserid");
            return (Criteria) this;
        }

        public Criteria andBelonguseridNotIn(List<Integer> values) {
            addCriterion("belonguserid not in", values, "belonguserid");
            return (Criteria) this;
        }

        public Criteria andBelonguseridBetween(Integer value1, Integer value2) {
            addCriterion("belonguserid between", value1, value2, "belonguserid");
            return (Criteria) this;
        }

        public Criteria andBelonguseridNotBetween(Integer value1, Integer value2) {
            addCriterion("belonguserid not between", value1, value2, "belonguserid");
            return (Criteria) this;
        }

        public Criteria andPermitIsNull() {
            addCriterion("permit is null");
            return (Criteria) this;
        }

        public Criteria andPermitIsNotNull() {
            addCriterion("permit is not null");
            return (Criteria) this;
        }

        public Criteria andPermitEqualTo(Integer value) {
            addCriterion("permit =", value, "permit");
            return (Criteria) this;
        }

        public Criteria andPermitNotEqualTo(Integer value) {
            addCriterion("permit <>", value, "permit");
            return (Criteria) this;
        }

        public Criteria andPermitGreaterThan(Integer value) {
            addCriterion("permit >", value, "permit");
            return (Criteria) this;
        }

        public Criteria andPermitGreaterThanOrEqualTo(Integer value) {
            addCriterion("permit >=", value, "permit");
            return (Criteria) this;
        }

        public Criteria andPermitLessThan(Integer value) {
            addCriterion("permit <", value, "permit");
            return (Criteria) this;
        }

        public Criteria andPermitLessThanOrEqualTo(Integer value) {
            addCriterion("permit <=", value, "permit");
            return (Criteria) this;
        }

        public Criteria andPermitIn(List<Integer> values) {
            addCriterion("permit in", values, "permit");
            return (Criteria) this;
        }

        public Criteria andPermitNotIn(List<Integer> values) {
            addCriterion("permit not in", values, "permit");
            return (Criteria) this;
        }

        public Criteria andPermitBetween(Integer value1, Integer value2) {
            addCriterion("permit between", value1, value2, "permit");
            return (Criteria) this;
        }

        public Criteria andPermitNotBetween(Integer value1, Integer value2) {
            addCriterion("permit not between", value1, value2, "permit");
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

        public Criteria andSupportidIsNull() {
            addCriterion("supportid is null");
            return (Criteria) this;
        }

        public Criteria andSupportidIsNotNull() {
            addCriterion("supportid is not null");
            return (Criteria) this;
        }

        public Criteria andSupportidEqualTo(String value) {
            addCriterion("supportid =", value, "supportid");
            return (Criteria) this;
        }

        public Criteria andSupportidNotEqualTo(String value) {
            addCriterion("supportid <>", value, "supportid");
            return (Criteria) this;
        }

        public Criteria andSupportidGreaterThan(String value) {
            addCriterion("supportid >", value, "supportid");
            return (Criteria) this;
        }

        public Criteria andSupportidGreaterThanOrEqualTo(String value) {
            addCriterion("supportid >=", value, "supportid");
            return (Criteria) this;
        }

        public Criteria andSupportidLessThan(String value) {
            addCriterion("supportid <", value, "supportid");
            return (Criteria) this;
        }

        public Criteria andSupportidLessThanOrEqualTo(String value) {
            addCriterion("supportid <=", value, "supportid");
            return (Criteria) this;
        }

        public Criteria andSupportidLike(String value) {
            addCriterion("supportid like", value, "supportid");
            return (Criteria) this;
        }

        public Criteria andSupportidNotLike(String value) {
            addCriterion("supportid not like", value, "supportid");
            return (Criteria) this;
        }

        public Criteria andSupportidIn(List<String> values) {
            addCriterion("supportid in", values, "supportid");
            return (Criteria) this;
        }

        public Criteria andSupportidNotIn(List<String> values) {
            addCriterion("supportid not in", values, "supportid");
            return (Criteria) this;
        }

        public Criteria andSupportidBetween(String value1, String value2) {
            addCriterion("supportid between", value1, value2, "supportid");
            return (Criteria) this;
        }

        public Criteria andSupportidNotBetween(String value1, String value2) {
            addCriterion("supportid not between", value1, value2, "supportid");
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