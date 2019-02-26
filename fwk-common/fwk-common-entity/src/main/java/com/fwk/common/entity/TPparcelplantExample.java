package com.fwk.common.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TPparcelplantExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TPparcelplantExample() {
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

        public Criteria andBreedIsNull() {
            addCriterion("breed is null");
            return (Criteria) this;
        }

        public Criteria andBreedIsNotNull() {
            addCriterion("breed is not null");
            return (Criteria) this;
        }

        public Criteria andBreedEqualTo(String value) {
            addCriterion("breed =", value, "breed");
            return (Criteria) this;
        }

        public Criteria andBreedNotEqualTo(String value) {
            addCriterion("breed <>", value, "breed");
            return (Criteria) this;
        }

        public Criteria andBreedGreaterThan(String value) {
            addCriterion("breed >", value, "breed");
            return (Criteria) this;
        }

        public Criteria andBreedGreaterThanOrEqualTo(String value) {
            addCriterion("breed >=", value, "breed");
            return (Criteria) this;
        }

        public Criteria andBreedLessThan(String value) {
            addCriterion("breed <", value, "breed");
            return (Criteria) this;
        }

        public Criteria andBreedLessThanOrEqualTo(String value) {
            addCriterion("breed <=", value, "breed");
            return (Criteria) this;
        }

        public Criteria andBreedLike(String value) {
            addCriterion("breed like", value, "breed");
            return (Criteria) this;
        }

        public Criteria andBreedNotLike(String value) {
            addCriterion("breed not like", value, "breed");
            return (Criteria) this;
        }

        public Criteria andBreedIn(List<String> values) {
            addCriterion("breed in", values, "breed");
            return (Criteria) this;
        }

        public Criteria andBreedNotIn(List<String> values) {
            addCriterion("breed not in", values, "breed");
            return (Criteria) this;
        }

        public Criteria andBreedBetween(String value1, String value2) {
            addCriterion("breed between", value1, value2, "breed");
            return (Criteria) this;
        }

        public Criteria andBreedNotBetween(String value1, String value2) {
            addCriterion("breed not between", value1, value2, "breed");
            return (Criteria) this;
        }

        public Criteria andVarietiesIsNull() {
            addCriterion("varieties is null");
            return (Criteria) this;
        }

        public Criteria andVarietiesIsNotNull() {
            addCriterion("varieties is not null");
            return (Criteria) this;
        }

        public Criteria andVarietiesEqualTo(Integer value) {
            addCriterion("varieties =", value, "varieties");
            return (Criteria) this;
        }

        public Criteria andVarietiesNotEqualTo(Integer value) {
            addCriterion("varieties <>", value, "varieties");
            return (Criteria) this;
        }

        public Criteria andVarietiesGreaterThan(Integer value) {
            addCriterion("varieties >", value, "varieties");
            return (Criteria) this;
        }

        public Criteria andVarietiesGreaterThanOrEqualTo(Integer value) {
            addCriterion("varieties >=", value, "varieties");
            return (Criteria) this;
        }

        public Criteria andVarietiesLessThan(Integer value) {
            addCriterion("varieties <", value, "varieties");
            return (Criteria) this;
        }

        public Criteria andVarietiesLessThanOrEqualTo(Integer value) {
            addCriterion("varieties <=", value, "varieties");
            return (Criteria) this;
        }

        public Criteria andVarietiesIn(List<Integer> values) {
            addCriterion("varieties in", values, "varieties");
            return (Criteria) this;
        }

        public Criteria andVarietiesNotIn(List<Integer> values) {
            addCriterion("varieties not in", values, "varieties");
            return (Criteria) this;
        }

        public Criteria andVarietiesBetween(Integer value1, Integer value2) {
            addCriterion("varieties between", value1, value2, "varieties");
            return (Criteria) this;
        }

        public Criteria andVarietiesNotBetween(Integer value1, Integer value2) {
            addCriterion("varieties not between", value1, value2, "varieties");
            return (Criteria) this;
        }

        public Criteria andGrowingbatchIsNull() {
            addCriterion("growingbatch is null");
            return (Criteria) this;
        }

        public Criteria andGrowingbatchIsNotNull() {
            addCriterion("growingbatch is not null");
            return (Criteria) this;
        }

        public Criteria andGrowingbatchEqualTo(String value) {
            addCriterion("growingbatch =", value, "growingbatch");
            return (Criteria) this;
        }

        public Criteria andGrowingbatchNotEqualTo(String value) {
            addCriterion("growingbatch <>", value, "growingbatch");
            return (Criteria) this;
        }

        public Criteria andGrowingbatchGreaterThan(String value) {
            addCriterion("growingbatch >", value, "growingbatch");
            return (Criteria) this;
        }

        public Criteria andGrowingbatchGreaterThanOrEqualTo(String value) {
            addCriterion("growingbatch >=", value, "growingbatch");
            return (Criteria) this;
        }

        public Criteria andGrowingbatchLessThan(String value) {
            addCriterion("growingbatch <", value, "growingbatch");
            return (Criteria) this;
        }

        public Criteria andGrowingbatchLessThanOrEqualTo(String value) {
            addCriterion("growingbatch <=", value, "growingbatch");
            return (Criteria) this;
        }

        public Criteria andGrowingbatchLike(String value) {
            addCriterion("growingbatch like", value, "growingbatch");
            return (Criteria) this;
        }

        public Criteria andGrowingbatchNotLike(String value) {
            addCriterion("growingbatch not like", value, "growingbatch");
            return (Criteria) this;
        }

        public Criteria andGrowingbatchIn(List<String> values) {
            addCriterion("growingbatch in", values, "growingbatch");
            return (Criteria) this;
        }

        public Criteria andGrowingbatchNotIn(List<String> values) {
            addCriterion("growingbatch not in", values, "growingbatch");
            return (Criteria) this;
        }

        public Criteria andGrowingbatchBetween(String value1, String value2) {
            addCriterion("growingbatch between", value1, value2, "growingbatch");
            return (Criteria) this;
        }

        public Criteria andGrowingbatchNotBetween(String value1, String value2) {
            addCriterion("growingbatch not between", value1, value2, "growingbatch");
            return (Criteria) this;
        }

        public Criteria andPlantbegintimeIsNull() {
            addCriterion("plantbegintime is null");
            return (Criteria) this;
        }

        public Criteria andPlantbegintimeIsNotNull() {
            addCriterion("plantbegintime is not null");
            return (Criteria) this;
        }

        public Criteria andPlantbegintimeEqualTo(Date value) {
            addCriterion("plantbegintime =", value, "plantbegintime");
            return (Criteria) this;
        }

        public Criteria andPlantbegintimeNotEqualTo(Date value) {
            addCriterion("plantbegintime <>", value, "plantbegintime");
            return (Criteria) this;
        }

        public Criteria andPlantbegintimeGreaterThan(Date value) {
            addCriterion("plantbegintime >", value, "plantbegintime");
            return (Criteria) this;
        }

        public Criteria andPlantbegintimeGreaterThanOrEqualTo(Date value) {
            addCriterion("plantbegintime >=", value, "plantbegintime");
            return (Criteria) this;
        }

        public Criteria andPlantbegintimeLessThan(Date value) {
            addCriterion("plantbegintime <", value, "plantbegintime");
            return (Criteria) this;
        }

        public Criteria andPlantbegintimeLessThanOrEqualTo(Date value) {
            addCriterion("plantbegintime <=", value, "plantbegintime");
            return (Criteria) this;
        }

        public Criteria andPlantbegintimeIn(List<Date> values) {
            addCriterion("plantbegintime in", values, "plantbegintime");
            return (Criteria) this;
        }

        public Criteria andPlantbegintimeNotIn(List<Date> values) {
            addCriterion("plantbegintime not in", values, "plantbegintime");
            return (Criteria) this;
        }

        public Criteria andPlantbegintimeBetween(Date value1, Date value2) {
            addCriterion("plantbegintime between", value1, value2, "plantbegintime");
            return (Criteria) this;
        }

        public Criteria andPlantbegintimeNotBetween(Date value1, Date value2) {
            addCriterion("plantbegintime not between", value1, value2, "plantbegintime");
            return (Criteria) this;
        }

        public Criteria andPlantendtimeIsNull() {
            addCriterion("plantendtime is null");
            return (Criteria) this;
        }

        public Criteria andPlantendtimeIsNotNull() {
            addCriterion("plantendtime is not null");
            return (Criteria) this;
        }

        public Criteria andPlantendtimeEqualTo(Date value) {
            addCriterion("plantendtime =", value, "plantendtime");
            return (Criteria) this;
        }

        public Criteria andPlantendtimeNotEqualTo(Date value) {
            addCriterion("plantendtime <>", value, "plantendtime");
            return (Criteria) this;
        }

        public Criteria andPlantendtimeGreaterThan(Date value) {
            addCriterion("plantendtime >", value, "plantendtime");
            return (Criteria) this;
        }

        public Criteria andPlantendtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("plantendtime >=", value, "plantendtime");
            return (Criteria) this;
        }

        public Criteria andPlantendtimeLessThan(Date value) {
            addCriterion("plantendtime <", value, "plantendtime");
            return (Criteria) this;
        }

        public Criteria andPlantendtimeLessThanOrEqualTo(Date value) {
            addCriterion("plantendtime <=", value, "plantendtime");
            return (Criteria) this;
        }

        public Criteria andPlantendtimeIn(List<Date> values) {
            addCriterion("plantendtime in", values, "plantendtime");
            return (Criteria) this;
        }

        public Criteria andPlantendtimeNotIn(List<Date> values) {
            addCriterion("plantendtime not in", values, "plantendtime");
            return (Criteria) this;
        }

        public Criteria andPlantendtimeBetween(Date value1, Date value2) {
            addCriterion("plantendtime between", value1, value2, "plantendtime");
            return (Criteria) this;
        }

        public Criteria andPlantendtimeNotBetween(Date value1, Date value2) {
            addCriterion("plantendtime not between", value1, value2, "plantendtime");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleIsNull() {
            addCriterion("preestimategrowthcycle is null");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleIsNotNull() {
            addCriterion("preestimategrowthcycle is not null");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleEqualTo(Integer value) {
            addCriterion("preestimategrowthcycle =", value, "preestimategrowthcycle");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleNotEqualTo(Integer value) {
            addCriterion("preestimategrowthcycle <>", value, "preestimategrowthcycle");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleGreaterThan(Integer value) {
            addCriterion("preestimategrowthcycle >", value, "preestimategrowthcycle");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleGreaterThanOrEqualTo(Integer value) {
            addCriterion("preestimategrowthcycle >=", value, "preestimategrowthcycle");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleLessThan(Integer value) {
            addCriterion("preestimategrowthcycle <", value, "preestimategrowthcycle");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleLessThanOrEqualTo(Integer value) {
            addCriterion("preestimategrowthcycle <=", value, "preestimategrowthcycle");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleIn(List<Integer> values) {
            addCriterion("preestimategrowthcycle in", values, "preestimategrowthcycle");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleNotIn(List<Integer> values) {
            addCriterion("preestimategrowthcycle not in", values, "preestimategrowthcycle");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleBetween(Integer value1, Integer value2) {
            addCriterion("preestimategrowthcycle between", value1, value2, "preestimategrowthcycle");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleNotBetween(Integer value1, Integer value2) {
            addCriterion("preestimategrowthcycle not between", value1, value2, "preestimategrowthcycle");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleunitIsNull() {
            addCriterion("preestimategrowthcycleunit is null");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleunitIsNotNull() {
            addCriterion("preestimategrowthcycleunit is not null");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleunitEqualTo(String value) {
            addCriterion("preestimategrowthcycleunit =", value, "preestimategrowthcycleunit");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleunitNotEqualTo(String value) {
            addCriterion("preestimategrowthcycleunit <>", value, "preestimategrowthcycleunit");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleunitGreaterThan(String value) {
            addCriterion("preestimategrowthcycleunit >", value, "preestimategrowthcycleunit");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleunitGreaterThanOrEqualTo(String value) {
            addCriterion("preestimategrowthcycleunit >=", value, "preestimategrowthcycleunit");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleunitLessThan(String value) {
            addCriterion("preestimategrowthcycleunit <", value, "preestimategrowthcycleunit");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleunitLessThanOrEqualTo(String value) {
            addCriterion("preestimategrowthcycleunit <=", value, "preestimategrowthcycleunit");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleunitLike(String value) {
            addCriterion("preestimategrowthcycleunit like", value, "preestimategrowthcycleunit");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleunitNotLike(String value) {
            addCriterion("preestimategrowthcycleunit not like", value, "preestimategrowthcycleunit");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleunitIn(List<String> values) {
            addCriterion("preestimategrowthcycleunit in", values, "preestimategrowthcycleunit");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleunitNotIn(List<String> values) {
            addCriterion("preestimategrowthcycleunit not in", values, "preestimategrowthcycleunit");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleunitBetween(String value1, String value2) {
            addCriterion("preestimategrowthcycleunit between", value1, value2, "preestimategrowthcycleunit");
            return (Criteria) this;
        }

        public Criteria andPreestimategrowthcycleunitNotBetween(String value1, String value2) {
            addCriterion("preestimategrowthcycleunit not between", value1, value2, "preestimategrowthcycleunit");
            return (Criteria) this;
        }

        public Criteria andPlantageIsNull() {
            addCriterion("plantage is null");
            return (Criteria) this;
        }

        public Criteria andPlantageIsNotNull() {
            addCriterion("plantage is not null");
            return (Criteria) this;
        }

        public Criteria andPlantageEqualTo(Integer value) {
            addCriterion("plantage =", value, "plantage");
            return (Criteria) this;
        }

        public Criteria andPlantageNotEqualTo(Integer value) {
            addCriterion("plantage <>", value, "plantage");
            return (Criteria) this;
        }

        public Criteria andPlantageGreaterThan(Integer value) {
            addCriterion("plantage >", value, "plantage");
            return (Criteria) this;
        }

        public Criteria andPlantageGreaterThanOrEqualTo(Integer value) {
            addCriterion("plantage >=", value, "plantage");
            return (Criteria) this;
        }

        public Criteria andPlantageLessThan(Integer value) {
            addCriterion("plantage <", value, "plantage");
            return (Criteria) this;
        }

        public Criteria andPlantageLessThanOrEqualTo(Integer value) {
            addCriterion("plantage <=", value, "plantage");
            return (Criteria) this;
        }

        public Criteria andPlantageIn(List<Integer> values) {
            addCriterion("plantage in", values, "plantage");
            return (Criteria) this;
        }

        public Criteria andPlantageNotIn(List<Integer> values) {
            addCriterion("plantage not in", values, "plantage");
            return (Criteria) this;
        }

        public Criteria andPlantageBetween(Integer value1, Integer value2) {
            addCriterion("plantage between", value1, value2, "plantage");
            return (Criteria) this;
        }

        public Criteria andPlantageNotBetween(Integer value1, Integer value2) {
            addCriterion("plantage not between", value1, value2, "plantage");
            return (Criteria) this;
        }

        public Criteria andPlantageunitIsNull() {
            addCriterion("plantageunit is null");
            return (Criteria) this;
        }

        public Criteria andPlantageunitIsNotNull() {
            addCriterion("plantageunit is not null");
            return (Criteria) this;
        }

        public Criteria andPlantageunitEqualTo(String value) {
            addCriterion("plantageunit =", value, "plantageunit");
            return (Criteria) this;
        }

        public Criteria andPlantageunitNotEqualTo(String value) {
            addCriterion("plantageunit <>", value, "plantageunit");
            return (Criteria) this;
        }

        public Criteria andPlantageunitGreaterThan(String value) {
            addCriterion("plantageunit >", value, "plantageunit");
            return (Criteria) this;
        }

        public Criteria andPlantageunitGreaterThanOrEqualTo(String value) {
            addCriterion("plantageunit >=", value, "plantageunit");
            return (Criteria) this;
        }

        public Criteria andPlantageunitLessThan(String value) {
            addCriterion("plantageunit <", value, "plantageunit");
            return (Criteria) this;
        }

        public Criteria andPlantageunitLessThanOrEqualTo(String value) {
            addCriterion("plantageunit <=", value, "plantageunit");
            return (Criteria) this;
        }

        public Criteria andPlantageunitLike(String value) {
            addCriterion("plantageunit like", value, "plantageunit");
            return (Criteria) this;
        }

        public Criteria andPlantageunitNotLike(String value) {
            addCriterion("plantageunit not like", value, "plantageunit");
            return (Criteria) this;
        }

        public Criteria andPlantageunitIn(List<String> values) {
            addCriterion("plantageunit in", values, "plantageunit");
            return (Criteria) this;
        }

        public Criteria andPlantageunitNotIn(List<String> values) {
            addCriterion("plantageunit not in", values, "plantageunit");
            return (Criteria) this;
        }

        public Criteria andPlantageunitBetween(String value1, String value2) {
            addCriterion("plantageunit between", value1, value2, "plantageunit");
            return (Criteria) this;
        }

        public Criteria andPlantageunitNotBetween(String value1, String value2) {
            addCriterion("plantageunit not between", value1, value2, "plantageunit");
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

        public Criteria andAreaEqualTo(BigDecimal value) {
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(BigDecimal value) {
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(BigDecimal value) {
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(BigDecimal value) {
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(BigDecimal value) {
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<BigDecimal> values) {
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<BigDecimal> values) {
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("area not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andNumberIsNull() {
            addCriterion("number is null");
            return (Criteria) this;
        }

        public Criteria andNumberIsNotNull() {
            addCriterion("number is not null");
            return (Criteria) this;
        }

        public Criteria andNumberEqualTo(Integer value) {
            addCriterion("number =", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotEqualTo(Integer value) {
            addCriterion("number <>", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThan(Integer value) {
            addCriterion("number >", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("number >=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThan(Integer value) {
            addCriterion("number <", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThanOrEqualTo(Integer value) {
            addCriterion("number <=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberIn(List<Integer> values) {
            addCriterion("number in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotIn(List<Integer> values) {
            addCriterion("number not in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberBetween(Integer value1, Integer value2) {
            addCriterion("number between", value1, value2, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("number not between", value1, value2, "number");
            return (Criteria) this;
        }

        public Criteria andUnitIsNull() {
            addCriterion("unit is null");
            return (Criteria) this;
        }

        public Criteria andUnitIsNotNull() {
            addCriterion("unit is not null");
            return (Criteria) this;
        }

        public Criteria andUnitEqualTo(String value) {
            addCriterion("unit =", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualTo(String value) {
            addCriterion("unit <>", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThan(String value) {
            addCriterion("unit >", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualTo(String value) {
            addCriterion("unit >=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThan(String value) {
            addCriterion("unit <", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualTo(String value) {
            addCriterion("unit <=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLike(String value) {
            addCriterion("unit like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotLike(String value) {
            addCriterion("unit not like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitIn(List<String> values) {
            addCriterion("unit in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotIn(List<String> values) {
            addCriterion("unit not in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitBetween(String value1, String value2) {
            addCriterion("unit between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotBetween(String value1, String value2) {
            addCriterion("unit not between", value1, value2, "unit");
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