package com.fwk.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TUusersExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TUusersExample() {
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

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("password like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("password not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("password not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
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

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andLastloginIsNull() {
            addCriterion("lastLogin is null");
            return (Criteria) this;
        }

        public Criteria andLastloginIsNotNull() {
            addCriterion("lastLogin is not null");
            return (Criteria) this;
        }

        public Criteria andLastloginEqualTo(Date value) {
            addCriterion("lastLogin =", value, "lastlogin");
            return (Criteria) this;
        }

        public Criteria andLastloginNotEqualTo(Date value) {
            addCriterion("lastLogin <>", value, "lastlogin");
            return (Criteria) this;
        }

        public Criteria andLastloginGreaterThan(Date value) {
            addCriterion("lastLogin >", value, "lastlogin");
            return (Criteria) this;
        }

        public Criteria andLastloginGreaterThanOrEqualTo(Date value) {
            addCriterion("lastLogin >=", value, "lastlogin");
            return (Criteria) this;
        }

        public Criteria andLastloginLessThan(Date value) {
            addCriterion("lastLogin <", value, "lastlogin");
            return (Criteria) this;
        }

        public Criteria andLastloginLessThanOrEqualTo(Date value) {
            addCriterion("lastLogin <=", value, "lastlogin");
            return (Criteria) this;
        }

        public Criteria andLastloginIn(List<Date> values) {
            addCriterion("lastLogin in", values, "lastlogin");
            return (Criteria) this;
        }

        public Criteria andLastloginNotIn(List<Date> values) {
            addCriterion("lastLogin not in", values, "lastlogin");
            return (Criteria) this;
        }

        public Criteria andLastloginBetween(Date value1, Date value2) {
            addCriterion("lastLogin between", value1, value2, "lastlogin");
            return (Criteria) this;
        }

        public Criteria andLastloginNotBetween(Date value1, Date value2) {
            addCriterion("lastLogin not between", value1, value2, "lastlogin");
            return (Criteria) this;
        }

        public Criteria andLoginaddressIsNull() {
            addCriterion("loginAddress is null");
            return (Criteria) this;
        }

        public Criteria andLoginaddressIsNotNull() {
            addCriterion("loginAddress is not null");
            return (Criteria) this;
        }

        public Criteria andLoginaddressEqualTo(String value) {
            addCriterion("loginAddress =", value, "loginaddress");
            return (Criteria) this;
        }

        public Criteria andLoginaddressNotEqualTo(String value) {
            addCriterion("loginAddress <>", value, "loginaddress");
            return (Criteria) this;
        }

        public Criteria andLoginaddressGreaterThan(String value) {
            addCriterion("loginAddress >", value, "loginaddress");
            return (Criteria) this;
        }

        public Criteria andLoginaddressGreaterThanOrEqualTo(String value) {
            addCriterion("loginAddress >=", value, "loginaddress");
            return (Criteria) this;
        }

        public Criteria andLoginaddressLessThan(String value) {
            addCriterion("loginAddress <", value, "loginaddress");
            return (Criteria) this;
        }

        public Criteria andLoginaddressLessThanOrEqualTo(String value) {
            addCriterion("loginAddress <=", value, "loginaddress");
            return (Criteria) this;
        }

        public Criteria andLoginaddressLike(String value) {
            addCriterion("loginAddress like", value, "loginaddress");
            return (Criteria) this;
        }

        public Criteria andLoginaddressNotLike(String value) {
            addCriterion("loginAddress not like", value, "loginaddress");
            return (Criteria) this;
        }

        public Criteria andLoginaddressIn(List<String> values) {
            addCriterion("loginAddress in", values, "loginaddress");
            return (Criteria) this;
        }

        public Criteria andLoginaddressNotIn(List<String> values) {
            addCriterion("loginAddress not in", values, "loginaddress");
            return (Criteria) this;
        }

        public Criteria andLoginaddressBetween(String value1, String value2) {
            addCriterion("loginAddress between", value1, value2, "loginaddress");
            return (Criteria) this;
        }

        public Criteria andLoginaddressNotBetween(String value1, String value2) {
            addCriterion("loginAddress not between", value1, value2, "loginaddress");
            return (Criteria) this;
        }

        public Criteria andLatestidentifyingcodeIsNull() {
            addCriterion("latestidentifyingcode is null");
            return (Criteria) this;
        }

        public Criteria andLatestidentifyingcodeIsNotNull() {
            addCriterion("latestidentifyingcode is not null");
            return (Criteria) this;
        }

        public Criteria andLatestidentifyingcodeEqualTo(String value) {
            addCriterion("latestidentifyingcode =", value, "latestidentifyingcode");
            return (Criteria) this;
        }

        public Criteria andLatestidentifyingcodeNotEqualTo(String value) {
            addCriterion("latestidentifyingcode <>", value, "latestidentifyingcode");
            return (Criteria) this;
        }

        public Criteria andLatestidentifyingcodeGreaterThan(String value) {
            addCriterion("latestidentifyingcode >", value, "latestidentifyingcode");
            return (Criteria) this;
        }

        public Criteria andLatestidentifyingcodeGreaterThanOrEqualTo(String value) {
            addCriterion("latestidentifyingcode >=", value, "latestidentifyingcode");
            return (Criteria) this;
        }

        public Criteria andLatestidentifyingcodeLessThan(String value) {
            addCriterion("latestidentifyingcode <", value, "latestidentifyingcode");
            return (Criteria) this;
        }

        public Criteria andLatestidentifyingcodeLessThanOrEqualTo(String value) {
            addCriterion("latestidentifyingcode <=", value, "latestidentifyingcode");
            return (Criteria) this;
        }

        public Criteria andLatestidentifyingcodeLike(String value) {
            addCriterion("latestidentifyingcode like", value, "latestidentifyingcode");
            return (Criteria) this;
        }

        public Criteria andLatestidentifyingcodeNotLike(String value) {
            addCriterion("latestidentifyingcode not like", value, "latestidentifyingcode");
            return (Criteria) this;
        }

        public Criteria andLatestidentifyingcodeIn(List<String> values) {
            addCriterion("latestidentifyingcode in", values, "latestidentifyingcode");
            return (Criteria) this;
        }

        public Criteria andLatestidentifyingcodeNotIn(List<String> values) {
            addCriterion("latestidentifyingcode not in", values, "latestidentifyingcode");
            return (Criteria) this;
        }

        public Criteria andLatestidentifyingcodeBetween(String value1, String value2) {
            addCriterion("latestidentifyingcode between", value1, value2, "latestidentifyingcode");
            return (Criteria) this;
        }

        public Criteria andLatestidentifyingcodeNotBetween(String value1, String value2) {
            addCriterion("latestidentifyingcode not between", value1, value2, "latestidentifyingcode");
            return (Criteria) this;
        }

        public Criteria andLastlogincodeIsNull() {
            addCriterion("lastlogincode is null");
            return (Criteria) this;
        }

        public Criteria andLastlogincodeIsNotNull() {
            addCriterion("lastlogincode is not null");
            return (Criteria) this;
        }

        public Criteria andLastlogincodeEqualTo(String value) {
            addCriterion("lastlogincode =", value, "lastlogincode");
            return (Criteria) this;
        }

        public Criteria andLastlogincodeNotEqualTo(String value) {
            addCriterion("lastlogincode <>", value, "lastlogincode");
            return (Criteria) this;
        }

        public Criteria andLastlogincodeGreaterThan(String value) {
            addCriterion("lastlogincode >", value, "lastlogincode");
            return (Criteria) this;
        }

        public Criteria andLastlogincodeGreaterThanOrEqualTo(String value) {
            addCriterion("lastlogincode >=", value, "lastlogincode");
            return (Criteria) this;
        }

        public Criteria andLastlogincodeLessThan(String value) {
            addCriterion("lastlogincode <", value, "lastlogincode");
            return (Criteria) this;
        }

        public Criteria andLastlogincodeLessThanOrEqualTo(String value) {
            addCriterion("lastlogincode <=", value, "lastlogincode");
            return (Criteria) this;
        }

        public Criteria andLastlogincodeLike(String value) {
            addCriterion("lastlogincode like", value, "lastlogincode");
            return (Criteria) this;
        }

        public Criteria andLastlogincodeNotLike(String value) {
            addCriterion("lastlogincode not like", value, "lastlogincode");
            return (Criteria) this;
        }

        public Criteria andLastlogincodeIn(List<String> values) {
            addCriterion("lastlogincode in", values, "lastlogincode");
            return (Criteria) this;
        }

        public Criteria andLastlogincodeNotIn(List<String> values) {
            addCriterion("lastlogincode not in", values, "lastlogincode");
            return (Criteria) this;
        }

        public Criteria andLastlogincodeBetween(String value1, String value2) {
            addCriterion("lastlogincode between", value1, value2, "lastlogincode");
            return (Criteria) this;
        }

        public Criteria andLastlogincodeNotBetween(String value1, String value2) {
            addCriterion("lastlogincode not between", value1, value2, "lastlogincode");
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

        public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(String value) {
            addCriterion("sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(String value) {
            addCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(String value) {
            addCriterion("sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(String value) {
            addCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(String value) {
            addCriterion("sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(String value) {
            addCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLike(String value) {
            addCriterion("sex like", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotLike(String value) {
            addCriterion("sex not like", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<String> values) {
            addCriterion("sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<String> values) {
            addCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(String value1, String value2) {
            addCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(String value1, String value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNull() {
            addCriterion("birthday is null");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNotNull() {
            addCriterion("birthday is not null");
            return (Criteria) this;
        }

        public Criteria andBirthdayEqualTo(Date value) {
            addCriterionForJDBCDate("birthday =", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotEqualTo(Date value) {
            addCriterionForJDBCDate("birthday <>", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThan(Date value) {
            addCriterionForJDBCDate("birthday >", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birthday >=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThan(Date value) {
            addCriterionForJDBCDate("birthday <", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birthday <=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayIn(List<Date> values) {
            addCriterionForJDBCDate("birthday in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotIn(List<Date> values) {
            addCriterionForJDBCDate("birthday not in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birthday between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birthday not between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andNicknameIsNull() {
            addCriterion("nickname is null");
            return (Criteria) this;
        }

        public Criteria andNicknameIsNotNull() {
            addCriterion("nickname is not null");
            return (Criteria) this;
        }

        public Criteria andNicknameEqualTo(String value) {
            addCriterion("nickname =", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotEqualTo(String value) {
            addCriterion("nickname <>", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameGreaterThan(String value) {
            addCriterion("nickname >", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameGreaterThanOrEqualTo(String value) {
            addCriterion("nickname >=", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLessThan(String value) {
            addCriterion("nickname <", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLessThanOrEqualTo(String value) {
            addCriterion("nickname <=", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLike(String value) {
            addCriterion("nickname like", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotLike(String value) {
            addCriterion("nickname not like", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameIn(List<String> values) {
            addCriterion("nickname in", values, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotIn(List<String> values) {
            addCriterion("nickname not in", values, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameBetween(String value1, String value2) {
            addCriterion("nickname between", value1, value2, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotBetween(String value1, String value2) {
            addCriterion("nickname not between", value1, value2, "nickname");
            return (Criteria) this;
        }

        public Criteria andPhotoIsNull() {
            addCriterion("photo is null");
            return (Criteria) this;
        }

        public Criteria andPhotoIsNotNull() {
            addCriterion("photo is not null");
            return (Criteria) this;
        }

        public Criteria andPhotoEqualTo(Integer value) {
            addCriterion("photo =", value, "photo");
            return (Criteria) this;
        }

        public Criteria andPhotoNotEqualTo(Integer value) {
            addCriterion("photo <>", value, "photo");
            return (Criteria) this;
        }

        public Criteria andPhotoGreaterThan(Integer value) {
            addCriterion("photo >", value, "photo");
            return (Criteria) this;
        }

        public Criteria andPhotoGreaterThanOrEqualTo(Integer value) {
            addCriterion("photo >=", value, "photo");
            return (Criteria) this;
        }

        public Criteria andPhotoLessThan(Integer value) {
            addCriterion("photo <", value, "photo");
            return (Criteria) this;
        }

        public Criteria andPhotoLessThanOrEqualTo(Integer value) {
            addCriterion("photo <=", value, "photo");
            return (Criteria) this;
        }

        public Criteria andPhotoIn(List<Integer> values) {
            addCriterion("photo in", values, "photo");
            return (Criteria) this;
        }

        public Criteria andPhotoNotIn(List<Integer> values) {
            addCriterion("photo not in", values, "photo");
            return (Criteria) this;
        }

        public Criteria andPhotoBetween(Integer value1, Integer value2) {
            addCriterion("photo between", value1, value2, "photo");
            return (Criteria) this;
        }

        public Criteria andPhotoNotBetween(Integer value1, Integer value2) {
            addCriterion("photo not between", value1, value2, "photo");
            return (Criteria) this;
        }

        public Criteria andJobnumberIsNull() {
            addCriterion("jobnumber is null");
            return (Criteria) this;
        }

        public Criteria andJobnumberIsNotNull() {
            addCriterion("jobnumber is not null");
            return (Criteria) this;
        }

        public Criteria andJobnumberEqualTo(String value) {
            addCriterion("jobnumber =", value, "jobnumber");
            return (Criteria) this;
        }

        public Criteria andJobnumberNotEqualTo(String value) {
            addCriterion("jobnumber <>", value, "jobnumber");
            return (Criteria) this;
        }

        public Criteria andJobnumberGreaterThan(String value) {
            addCriterion("jobnumber >", value, "jobnumber");
            return (Criteria) this;
        }

        public Criteria andJobnumberGreaterThanOrEqualTo(String value) {
            addCriterion("jobnumber >=", value, "jobnumber");
            return (Criteria) this;
        }

        public Criteria andJobnumberLessThan(String value) {
            addCriterion("jobnumber <", value, "jobnumber");
            return (Criteria) this;
        }

        public Criteria andJobnumberLessThanOrEqualTo(String value) {
            addCriterion("jobnumber <=", value, "jobnumber");
            return (Criteria) this;
        }

        public Criteria andJobnumberLike(String value) {
            addCriterion("jobnumber like", value, "jobnumber");
            return (Criteria) this;
        }

        public Criteria andJobnumberNotLike(String value) {
            addCriterion("jobnumber not like", value, "jobnumber");
            return (Criteria) this;
        }

        public Criteria andJobnumberIn(List<String> values) {
            addCriterion("jobnumber in", values, "jobnumber");
            return (Criteria) this;
        }

        public Criteria andJobnumberNotIn(List<String> values) {
            addCriterion("jobnumber not in", values, "jobnumber");
            return (Criteria) this;
        }

        public Criteria andJobnumberBetween(String value1, String value2) {
            addCriterion("jobnumber between", value1, value2, "jobnumber");
            return (Criteria) this;
        }

        public Criteria andJobnumberNotBetween(String value1, String value2) {
            addCriterion("jobnumber not between", value1, value2, "jobnumber");
            return (Criteria) this;
        }

        public Criteria andLoginorgidIsNull() {
            addCriterion("loginorgid is null");
            return (Criteria) this;
        }

        public Criteria andLoginorgidIsNotNull() {
            addCriterion("loginorgid is not null");
            return (Criteria) this;
        }

        public Criteria andLoginorgidEqualTo(String value) {
            addCriterion("loginorgid =", value, "loginorgid");
            return (Criteria) this;
        }

        public Criteria andLoginorgidNotEqualTo(String value) {
            addCriterion("loginorgid <>", value, "loginorgid");
            return (Criteria) this;
        }

        public Criteria andLoginorgidGreaterThan(String value) {
            addCriterion("loginorgid >", value, "loginorgid");
            return (Criteria) this;
        }

        public Criteria andLoginorgidGreaterThanOrEqualTo(String value) {
            addCriterion("loginorgid >=", value, "loginorgid");
            return (Criteria) this;
        }

        public Criteria andLoginorgidLessThan(String value) {
            addCriterion("loginorgid <", value, "loginorgid");
            return (Criteria) this;
        }

        public Criteria andLoginorgidLessThanOrEqualTo(String value) {
            addCriterion("loginorgid <=", value, "loginorgid");
            return (Criteria) this;
        }

        public Criteria andLoginorgidLike(String value) {
            addCriterion("loginorgid like", value, "loginorgid");
            return (Criteria) this;
        }

        public Criteria andLoginorgidNotLike(String value) {
            addCriterion("loginorgid not like", value, "loginorgid");
            return (Criteria) this;
        }

        public Criteria andLoginorgidIn(List<String> values) {
            addCriterion("loginorgid in", values, "loginorgid");
            return (Criteria) this;
        }

        public Criteria andLoginorgidNotIn(List<String> values) {
            addCriterion("loginorgid not in", values, "loginorgid");
            return (Criteria) this;
        }

        public Criteria andLoginorgidBetween(String value1, String value2) {
            addCriterion("loginorgid between", value1, value2, "loginorgid");
            return (Criteria) this;
        }

        public Criteria andLoginorgidNotBetween(String value1, String value2) {
            addCriterion("loginorgid not between", value1, value2, "loginorgid");
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