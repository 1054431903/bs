package com.fwk.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TPparcelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TPparcelExample() {
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

        public Criteria andParcelnoIsNull() {
            addCriterion("parcelno is null");
            return (Criteria) this;
        }

        public Criteria andParcelnoIsNotNull() {
            addCriterion("parcelno is not null");
            return (Criteria) this;
        }

        public Criteria andParcelnoEqualTo(String value) {
            addCriterion("parcelno =", value, "parcelno");
            return (Criteria) this;
        }

        public Criteria andParcelnoNotEqualTo(String value) {
            addCriterion("parcelno <>", value, "parcelno");
            return (Criteria) this;
        }

        public Criteria andParcelnoGreaterThan(String value) {
            addCriterion("parcelno >", value, "parcelno");
            return (Criteria) this;
        }

        public Criteria andParcelnoGreaterThanOrEqualTo(String value) {
            addCriterion("parcelno >=", value, "parcelno");
            return (Criteria) this;
        }

        public Criteria andParcelnoLessThan(String value) {
            addCriterion("parcelno <", value, "parcelno");
            return (Criteria) this;
        }

        public Criteria andParcelnoLessThanOrEqualTo(String value) {
            addCriterion("parcelno <=", value, "parcelno");
            return (Criteria) this;
        }

        public Criteria andParcelnoLike(String value) {
            addCriterion("parcelno like", value, "parcelno");
            return (Criteria) this;
        }

        public Criteria andParcelnoNotLike(String value) {
            addCriterion("parcelno not like", value, "parcelno");
            return (Criteria) this;
        }

        public Criteria andParcelnoIn(List<String> values) {
            addCriterion("parcelno in", values, "parcelno");
            return (Criteria) this;
        }

        public Criteria andParcelnoNotIn(List<String> values) {
            addCriterion("parcelno not in", values, "parcelno");
            return (Criteria) this;
        }

        public Criteria andParcelnoBetween(String value1, String value2) {
            addCriterion("parcelno between", value1, value2, "parcelno");
            return (Criteria) this;
        }

        public Criteria andParcelnoNotBetween(String value1, String value2) {
            addCriterion("parcelno not between", value1, value2, "parcelno");
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

        public Criteria andProvincecodeIsNull() {
            addCriterion("provincecode is null");
            return (Criteria) this;
        }

        public Criteria andProvincecodeIsNotNull() {
            addCriterion("provincecode is not null");
            return (Criteria) this;
        }

        public Criteria andProvincecodeEqualTo(String value) {
            addCriterion("provincecode =", value, "provincecode");
            return (Criteria) this;
        }

        public Criteria andProvincecodeNotEqualTo(String value) {
            addCriterion("provincecode <>", value, "provincecode");
            return (Criteria) this;
        }

        public Criteria andProvincecodeGreaterThan(String value) {
            addCriterion("provincecode >", value, "provincecode");
            return (Criteria) this;
        }

        public Criteria andProvincecodeGreaterThanOrEqualTo(String value) {
            addCriterion("provincecode >=", value, "provincecode");
            return (Criteria) this;
        }

        public Criteria andProvincecodeLessThan(String value) {
            addCriterion("provincecode <", value, "provincecode");
            return (Criteria) this;
        }

        public Criteria andProvincecodeLessThanOrEqualTo(String value) {
            addCriterion("provincecode <=", value, "provincecode");
            return (Criteria) this;
        }

        public Criteria andProvincecodeLike(String value) {
            addCriterion("provincecode like", value, "provincecode");
            return (Criteria) this;
        }

        public Criteria andProvincecodeNotLike(String value) {
            addCriterion("provincecode not like", value, "provincecode");
            return (Criteria) this;
        }

        public Criteria andProvincecodeIn(List<String> values) {
            addCriterion("provincecode in", values, "provincecode");
            return (Criteria) this;
        }

        public Criteria andProvincecodeNotIn(List<String> values) {
            addCriterion("provincecode not in", values, "provincecode");
            return (Criteria) this;
        }

        public Criteria andProvincecodeBetween(String value1, String value2) {
            addCriterion("provincecode between", value1, value2, "provincecode");
            return (Criteria) this;
        }

        public Criteria andProvincecodeNotBetween(String value1, String value2) {
            addCriterion("provincecode not between", value1, value2, "provincecode");
            return (Criteria) this;
        }

        public Criteria andCitycodeIsNull() {
            addCriterion("citycode is null");
            return (Criteria) this;
        }

        public Criteria andCitycodeIsNotNull() {
            addCriterion("citycode is not null");
            return (Criteria) this;
        }

        public Criteria andCitycodeEqualTo(String value) {
            addCriterion("citycode =", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeNotEqualTo(String value) {
            addCriterion("citycode <>", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeGreaterThan(String value) {
            addCriterion("citycode >", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeGreaterThanOrEqualTo(String value) {
            addCriterion("citycode >=", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeLessThan(String value) {
            addCriterion("citycode <", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeLessThanOrEqualTo(String value) {
            addCriterion("citycode <=", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeLike(String value) {
            addCriterion("citycode like", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeNotLike(String value) {
            addCriterion("citycode not like", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeIn(List<String> values) {
            addCriterion("citycode in", values, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeNotIn(List<String> values) {
            addCriterion("citycode not in", values, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeBetween(String value1, String value2) {
            addCriterion("citycode between", value1, value2, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeNotBetween(String value1, String value2) {
            addCriterion("citycode not between", value1, value2, "citycode");
            return (Criteria) this;
        }

        public Criteria andCountycodeIsNull() {
            addCriterion("countycode is null");
            return (Criteria) this;
        }

        public Criteria andCountycodeIsNotNull() {
            addCriterion("countycode is not null");
            return (Criteria) this;
        }

        public Criteria andCountycodeEqualTo(String value) {
            addCriterion("countycode =", value, "countycode");
            return (Criteria) this;
        }

        public Criteria andCountycodeNotEqualTo(String value) {
            addCriterion("countycode <>", value, "countycode");
            return (Criteria) this;
        }

        public Criteria andCountycodeGreaterThan(String value) {
            addCriterion("countycode >", value, "countycode");
            return (Criteria) this;
        }

        public Criteria andCountycodeGreaterThanOrEqualTo(String value) {
            addCriterion("countycode >=", value, "countycode");
            return (Criteria) this;
        }

        public Criteria andCountycodeLessThan(String value) {
            addCriterion("countycode <", value, "countycode");
            return (Criteria) this;
        }

        public Criteria andCountycodeLessThanOrEqualTo(String value) {
            addCriterion("countycode <=", value, "countycode");
            return (Criteria) this;
        }

        public Criteria andCountycodeLike(String value) {
            addCriterion("countycode like", value, "countycode");
            return (Criteria) this;
        }

        public Criteria andCountycodeNotLike(String value) {
            addCriterion("countycode not like", value, "countycode");
            return (Criteria) this;
        }

        public Criteria andCountycodeIn(List<String> values) {
            addCriterion("countycode in", values, "countycode");
            return (Criteria) this;
        }

        public Criteria andCountycodeNotIn(List<String> values) {
            addCriterion("countycode not in", values, "countycode");
            return (Criteria) this;
        }

        public Criteria andCountycodeBetween(String value1, String value2) {
            addCriterion("countycode between", value1, value2, "countycode");
            return (Criteria) this;
        }

        public Criteria andCountycodeNotBetween(String value1, String value2) {
            addCriterion("countycode not between", value1, value2, "countycode");
            return (Criteria) this;
        }

        public Criteria andTowncodeIsNull() {
            addCriterion("towncode is null");
            return (Criteria) this;
        }

        public Criteria andTowncodeIsNotNull() {
            addCriterion("towncode is not null");
            return (Criteria) this;
        }

        public Criteria andTowncodeEqualTo(String value) {
            addCriterion("towncode =", value, "towncode");
            return (Criteria) this;
        }

        public Criteria andTowncodeNotEqualTo(String value) {
            addCriterion("towncode <>", value, "towncode");
            return (Criteria) this;
        }

        public Criteria andTowncodeGreaterThan(String value) {
            addCriterion("towncode >", value, "towncode");
            return (Criteria) this;
        }

        public Criteria andTowncodeGreaterThanOrEqualTo(String value) {
            addCriterion("towncode >=", value, "towncode");
            return (Criteria) this;
        }

        public Criteria andTowncodeLessThan(String value) {
            addCriterion("towncode <", value, "towncode");
            return (Criteria) this;
        }

        public Criteria andTowncodeLessThanOrEqualTo(String value) {
            addCriterion("towncode <=", value, "towncode");
            return (Criteria) this;
        }

        public Criteria andTowncodeLike(String value) {
            addCriterion("towncode like", value, "towncode");
            return (Criteria) this;
        }

        public Criteria andTowncodeNotLike(String value) {
            addCriterion("towncode not like", value, "towncode");
            return (Criteria) this;
        }

        public Criteria andTowncodeIn(List<String> values) {
            addCriterion("towncode in", values, "towncode");
            return (Criteria) this;
        }

        public Criteria andTowncodeNotIn(List<String> values) {
            addCriterion("towncode not in", values, "towncode");
            return (Criteria) this;
        }

        public Criteria andTowncodeBetween(String value1, String value2) {
            addCriterion("towncode between", value1, value2, "towncode");
            return (Criteria) this;
        }

        public Criteria andTowncodeNotBetween(String value1, String value2) {
            addCriterion("towncode not between", value1, value2, "towncode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeIsNull() {
            addCriterion("villagecode is null");
            return (Criteria) this;
        }

        public Criteria andVillagecodeIsNotNull() {
            addCriterion("villagecode is not null");
            return (Criteria) this;
        }

        public Criteria andVillagecodeEqualTo(String value) {
            addCriterion("villagecode =", value, "villagecode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeNotEqualTo(String value) {
            addCriterion("villagecode <>", value, "villagecode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeGreaterThan(String value) {
            addCriterion("villagecode >", value, "villagecode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeGreaterThanOrEqualTo(String value) {
            addCriterion("villagecode >=", value, "villagecode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeLessThan(String value) {
            addCriterion("villagecode <", value, "villagecode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeLessThanOrEqualTo(String value) {
            addCriterion("villagecode <=", value, "villagecode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeLike(String value) {
            addCriterion("villagecode like", value, "villagecode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeNotLike(String value) {
            addCriterion("villagecode not like", value, "villagecode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeIn(List<String> values) {
            addCriterion("villagecode in", values, "villagecode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeNotIn(List<String> values) {
            addCriterion("villagecode not in", values, "villagecode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeBetween(String value1, String value2) {
            addCriterion("villagecode between", value1, value2, "villagecode");
            return (Criteria) this;
        }

        public Criteria andVillagecodeNotBetween(String value1, String value2) {
            addCriterion("villagecode not between", value1, value2, "villagecode");
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

        public Criteria andElevationIsNull() {
            addCriterion("elevation is null");
            return (Criteria) this;
        }

        public Criteria andElevationIsNotNull() {
            addCriterion("elevation is not null");
            return (Criteria) this;
        }

        public Criteria andElevationEqualTo(Double value) {
            addCriterion("elevation =", value, "elevation");
            return (Criteria) this;
        }

        public Criteria andElevationNotEqualTo(Double value) {
            addCriterion("elevation <>", value, "elevation");
            return (Criteria) this;
        }

        public Criteria andElevationGreaterThan(Double value) {
            addCriterion("elevation >", value, "elevation");
            return (Criteria) this;
        }

        public Criteria andElevationGreaterThanOrEqualTo(Double value) {
            addCriterion("elevation >=", value, "elevation");
            return (Criteria) this;
        }

        public Criteria andElevationLessThan(Double value) {
            addCriterion("elevation <", value, "elevation");
            return (Criteria) this;
        }

        public Criteria andElevationLessThanOrEqualTo(Double value) {
            addCriterion("elevation <=", value, "elevation");
            return (Criteria) this;
        }

        public Criteria andElevationIn(List<Double> values) {
            addCriterion("elevation in", values, "elevation");
            return (Criteria) this;
        }

        public Criteria andElevationNotIn(List<Double> values) {
            addCriterion("elevation not in", values, "elevation");
            return (Criteria) this;
        }

        public Criteria andElevationBetween(Double value1, Double value2) {
            addCriterion("elevation between", value1, value2, "elevation");
            return (Criteria) this;
        }

        public Criteria andElevationNotBetween(Double value1, Double value2) {
            addCriterion("elevation not between", value1, value2, "elevation");
            return (Criteria) this;
        }

        public Criteria andDependenceidIsNull() {
            addCriterion("dependenceid is null");
            return (Criteria) this;
        }

        public Criteria andDependenceidIsNotNull() {
            addCriterion("dependenceid is not null");
            return (Criteria) this;
        }

        public Criteria andDependenceidEqualTo(String value) {
            addCriterion("dependenceid =", value, "dependenceid");
            return (Criteria) this;
        }

        public Criteria andDependenceidNotEqualTo(String value) {
            addCriterion("dependenceid <>", value, "dependenceid");
            return (Criteria) this;
        }

        public Criteria andDependenceidGreaterThan(String value) {
            addCriterion("dependenceid >", value, "dependenceid");
            return (Criteria) this;
        }

        public Criteria andDependenceidGreaterThanOrEqualTo(String value) {
            addCriterion("dependenceid >=", value, "dependenceid");
            return (Criteria) this;
        }

        public Criteria andDependenceidLessThan(String value) {
            addCriterion("dependenceid <", value, "dependenceid");
            return (Criteria) this;
        }

        public Criteria andDependenceidLessThanOrEqualTo(String value) {
            addCriterion("dependenceid <=", value, "dependenceid");
            return (Criteria) this;
        }

        public Criteria andDependenceidLike(String value) {
            addCriterion("dependenceid like", value, "dependenceid");
            return (Criteria) this;
        }

        public Criteria andDependenceidNotLike(String value) {
            addCriterion("dependenceid not like", value, "dependenceid");
            return (Criteria) this;
        }

        public Criteria andDependenceidIn(List<String> values) {
            addCriterion("dependenceid in", values, "dependenceid");
            return (Criteria) this;
        }

        public Criteria andDependenceidNotIn(List<String> values) {
            addCriterion("dependenceid not in", values, "dependenceid");
            return (Criteria) this;
        }

        public Criteria andDependenceidBetween(String value1, String value2) {
            addCriterion("dependenceid between", value1, value2, "dependenceid");
            return (Criteria) this;
        }

        public Criteria andDependenceidNotBetween(String value1, String value2) {
            addCriterion("dependenceid not between", value1, value2, "dependenceid");
            return (Criteria) this;
        }

        public Criteria andOrgidIsNull() {
            addCriterion("orgid is null");
            return (Criteria) this;
        }

        public Criteria andOrgidIsNotNull() {
            addCriterion("orgid is not null");
            return (Criteria) this;
        }

        public Criteria andOrgidEqualTo(String value) {
            addCriterion("orgid =", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidNotEqualTo(String value) {
            addCriterion("orgid <>", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidGreaterThan(String value) {
            addCriterion("orgid >", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidGreaterThanOrEqualTo(String value) {
            addCriterion("orgid >=", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidLessThan(String value) {
            addCriterion("orgid <", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidLessThanOrEqualTo(String value) {
            addCriterion("orgid <=", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidLike(String value) {
            addCriterion("orgid like", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidNotLike(String value) {
            addCriterion("orgid not like", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidIn(List<String> values) {
            addCriterion("orgid in", values, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidNotIn(List<String> values) {
            addCriterion("orgid not in", values, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidBetween(String value1, String value2) {
            addCriterion("orgid between", value1, value2, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidNotBetween(String value1, String value2) {
            addCriterion("orgid not between", value1, value2, "orgid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidIsNull() {
            addCriterion("departmentid is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentidIsNotNull() {
            addCriterion("departmentid is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentidEqualTo(String value) {
            addCriterion("departmentid =", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidNotEqualTo(String value) {
            addCriterion("departmentid <>", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidGreaterThan(String value) {
            addCriterion("departmentid >", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidGreaterThanOrEqualTo(String value) {
            addCriterion("departmentid >=", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidLessThan(String value) {
            addCriterion("departmentid <", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidLessThanOrEqualTo(String value) {
            addCriterion("departmentid <=", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidLike(String value) {
            addCriterion("departmentid like", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidNotLike(String value) {
            addCriterion("departmentid not like", value, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidIn(List<String> values) {
            addCriterion("departmentid in", values, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidNotIn(List<String> values) {
            addCriterion("departmentid not in", values, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidBetween(String value1, String value2) {
            addCriterion("departmentid between", value1, value2, "departmentid");
            return (Criteria) this;
        }

        public Criteria andDepartmentidNotBetween(String value1, String value2) {
            addCriterion("departmentid not between", value1, value2, "departmentid");
            return (Criteria) this;
        }

        public Criteria andBelongpeasantidIsNull() {
            addCriterion("belongpeasantid is null");
            return (Criteria) this;
        }

        public Criteria andBelongpeasantidIsNotNull() {
            addCriterion("belongpeasantid is not null");
            return (Criteria) this;
        }

        public Criteria andBelongpeasantidEqualTo(Integer value) {
            addCriterion("belongpeasantid =", value, "belongpeasantid");
            return (Criteria) this;
        }

        public Criteria andBelongpeasantidNotEqualTo(Integer value) {
            addCriterion("belongpeasantid <>", value, "belongpeasantid");
            return (Criteria) this;
        }

        public Criteria andBelongpeasantidGreaterThan(Integer value) {
            addCriterion("belongpeasantid >", value, "belongpeasantid");
            return (Criteria) this;
        }

        public Criteria andBelongpeasantidGreaterThanOrEqualTo(Integer value) {
            addCriterion("belongpeasantid >=", value, "belongpeasantid");
            return (Criteria) this;
        }

        public Criteria andBelongpeasantidLessThan(Integer value) {
            addCriterion("belongpeasantid <", value, "belongpeasantid");
            return (Criteria) this;
        }

        public Criteria andBelongpeasantidLessThanOrEqualTo(Integer value) {
            addCriterion("belongpeasantid <=", value, "belongpeasantid");
            return (Criteria) this;
        }

        public Criteria andBelongpeasantidIn(List<Integer> values) {
            addCriterion("belongpeasantid in", values, "belongpeasantid");
            return (Criteria) this;
        }

        public Criteria andBelongpeasantidNotIn(List<Integer> values) {
            addCriterion("belongpeasantid not in", values, "belongpeasantid");
            return (Criteria) this;
        }

        public Criteria andBelongpeasantidBetween(Integer value1, Integer value2) {
            addCriterion("belongpeasantid between", value1, value2, "belongpeasantid");
            return (Criteria) this;
        }

        public Criteria andBelongpeasantidNotBetween(Integer value1, Integer value2) {
            addCriterion("belongpeasantid not between", value1, value2, "belongpeasantid");
            return (Criteria) this;
        }

        public Criteria andIntroduceIsNull() {
            addCriterion("introduce is null");
            return (Criteria) this;
        }

        public Criteria andIntroduceIsNotNull() {
            addCriterion("introduce is not null");
            return (Criteria) this;
        }

        public Criteria andIntroduceEqualTo(String value) {
            addCriterion("introduce =", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceNotEqualTo(String value) {
            addCriterion("introduce <>", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceGreaterThan(String value) {
            addCriterion("introduce >", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceGreaterThanOrEqualTo(String value) {
            addCriterion("introduce >=", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceLessThan(String value) {
            addCriterion("introduce <", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceLessThanOrEqualTo(String value) {
            addCriterion("introduce <=", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceLike(String value) {
            addCriterion("introduce like", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceNotLike(String value) {
            addCriterion("introduce not like", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceIn(List<String> values) {
            addCriterion("introduce in", values, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceNotIn(List<String> values) {
            addCriterion("introduce not in", values, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceBetween(String value1, String value2) {
            addCriterion("introduce between", value1, value2, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceNotBetween(String value1, String value2) {
            addCriterion("introduce not between", value1, value2, "introduce");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("area is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("area is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(Double value) {
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(Double value) {
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(Double value) {
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(Double value) {
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(Double value) {
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(Double value) {
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<Double> values) {
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<Double> values) {
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(Double value1, Double value2) {
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(Double value1, Double value2) {
            addCriterion("area not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNull() {
            addCriterion("longitude is null");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNotNull() {
            addCriterion("longitude is not null");
            return (Criteria) this;
        }

        public Criteria andLongitudeEqualTo(Double value) {
            addCriterion("longitude =", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotEqualTo(Double value) {
            addCriterion("longitude <>", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThan(Double value) {
            addCriterion("longitude >", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThanOrEqualTo(Double value) {
            addCriterion("longitude >=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThan(Double value) {
            addCriterion("longitude <", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThanOrEqualTo(Double value) {
            addCriterion("longitude <=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeIn(List<Double> values) {
            addCriterion("longitude in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotIn(List<Double> values) {
            addCriterion("longitude not in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeBetween(Double value1, Double value2) {
            addCriterion("longitude between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotBetween(Double value1, Double value2) {
            addCriterion("longitude not between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNull() {
            addCriterion("latitude is null");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNotNull() {
            addCriterion("latitude is not null");
            return (Criteria) this;
        }

        public Criteria andLatitudeEqualTo(Double value) {
            addCriterion("latitude =", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotEqualTo(Double value) {
            addCriterion("latitude <>", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThan(Double value) {
            addCriterion("latitude >", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThanOrEqualTo(Double value) {
            addCriterion("latitude >=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThan(Double value) {
            addCriterion("latitude <", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThanOrEqualTo(Double value) {
            addCriterion("latitude <=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIn(List<Double> values) {
            addCriterion("latitude in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotIn(List<Double> values) {
            addCriterion("latitude not in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeBetween(Double value1, Double value2) {
            addCriterion("latitude between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotBetween(Double value1, Double value2) {
            addCriterion("latitude not between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andInsuranceIsNull() {
            addCriterion("insurance is null");
            return (Criteria) this;
        }

        public Criteria andInsuranceIsNotNull() {
            addCriterion("insurance is not null");
            return (Criteria) this;
        }

        public Criteria andInsuranceEqualTo(String value) {
            addCriterion("insurance =", value, "insurance");
            return (Criteria) this;
        }

        public Criteria andInsuranceNotEqualTo(String value) {
            addCriterion("insurance <>", value, "insurance");
            return (Criteria) this;
        }

        public Criteria andInsuranceGreaterThan(String value) {
            addCriterion("insurance >", value, "insurance");
            return (Criteria) this;
        }

        public Criteria andInsuranceGreaterThanOrEqualTo(String value) {
            addCriterion("insurance >=", value, "insurance");
            return (Criteria) this;
        }

        public Criteria andInsuranceLessThan(String value) {
            addCriterion("insurance <", value, "insurance");
            return (Criteria) this;
        }

        public Criteria andInsuranceLessThanOrEqualTo(String value) {
            addCriterion("insurance <=", value, "insurance");
            return (Criteria) this;
        }

        public Criteria andInsuranceLike(String value) {
            addCriterion("insurance like", value, "insurance");
            return (Criteria) this;
        }

        public Criteria andInsuranceNotLike(String value) {
            addCriterion("insurance not like", value, "insurance");
            return (Criteria) this;
        }

        public Criteria andInsuranceIn(List<String> values) {
            addCriterion("insurance in", values, "insurance");
            return (Criteria) this;
        }

        public Criteria andInsuranceNotIn(List<String> values) {
            addCriterion("insurance not in", values, "insurance");
            return (Criteria) this;
        }

        public Criteria andInsuranceBetween(String value1, String value2) {
            addCriterion("insurance between", value1, value2, "insurance");
            return (Criteria) this;
        }

        public Criteria andInsuranceNotBetween(String value1, String value2) {
            addCriterion("insurance not between", value1, value2, "insurance");
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