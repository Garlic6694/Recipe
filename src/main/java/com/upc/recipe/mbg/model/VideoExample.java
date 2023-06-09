package com.upc.recipe.mbg.model;

import java.util.ArrayList;
import java.util.List;

public class VideoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public VideoExample() {
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

        public Criteria andLikeCountIsNull() {
            addCriterion("like_count is null");
            return (Criteria) this;
        }

        public Criteria andLikeCountIsNotNull() {
            addCriterion("like_count is not null");
            return (Criteria) this;
        }

        public Criteria andLikeCountEqualTo(Integer value) {
            addCriterion("like_count =", value, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountNotEqualTo(Integer value) {
            addCriterion("like_count <>", value, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountGreaterThan(Integer value) {
            addCriterion("like_count >", value, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("like_count >=", value, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountLessThan(Integer value) {
            addCriterion("like_count <", value, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountLessThanOrEqualTo(Integer value) {
            addCriterion("like_count <=", value, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountIn(List<Integer> values) {
            addCriterion("like_count in", values, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountNotIn(List<Integer> values) {
            addCriterion("like_count not in", values, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountBetween(Integer value1, Integer value2) {
            addCriterion("like_count between", value1, value2, "likeCount");
            return (Criteria) this;
        }

        public Criteria andLikeCountNotBetween(Integer value1, Integer value2) {
            addCriterion("like_count not between", value1, value2, "likeCount");
            return (Criteria) this;
        }

        public Criteria andScanCountIsNull() {
            addCriterion("scan_count is null");
            return (Criteria) this;
        }

        public Criteria andScanCountIsNotNull() {
            addCriterion("scan_count is not null");
            return (Criteria) this;
        }

        public Criteria andScanCountEqualTo(Integer value) {
            addCriterion("scan_count =", value, "scanCount");
            return (Criteria) this;
        }

        public Criteria andScanCountNotEqualTo(Integer value) {
            addCriterion("scan_count <>", value, "scanCount");
            return (Criteria) this;
        }

        public Criteria andScanCountGreaterThan(Integer value) {
            addCriterion("scan_count >", value, "scanCount");
            return (Criteria) this;
        }

        public Criteria andScanCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("scan_count >=", value, "scanCount");
            return (Criteria) this;
        }

        public Criteria andScanCountLessThan(Integer value) {
            addCriterion("scan_count <", value, "scanCount");
            return (Criteria) this;
        }

        public Criteria andScanCountLessThanOrEqualTo(Integer value) {
            addCriterion("scan_count <=", value, "scanCount");
            return (Criteria) this;
        }

        public Criteria andScanCountIn(List<Integer> values) {
            addCriterion("scan_count in", values, "scanCount");
            return (Criteria) this;
        }

        public Criteria andScanCountNotIn(List<Integer> values) {
            addCriterion("scan_count not in", values, "scanCount");
            return (Criteria) this;
        }

        public Criteria andScanCountBetween(Integer value1, Integer value2) {
            addCriterion("scan_count between", value1, value2, "scanCount");
            return (Criteria) this;
        }

        public Criteria andScanCountNotBetween(Integer value1, Integer value2) {
            addCriterion("scan_count not between", value1, value2, "scanCount");
            return (Criteria) this;
        }

        public Criteria andComCountIsNull() {
            addCriterion("com_count is null");
            return (Criteria) this;
        }

        public Criteria andComCountIsNotNull() {
            addCriterion("com_count is not null");
            return (Criteria) this;
        }

        public Criteria andComCountEqualTo(Integer value) {
            addCriterion("com_count =", value, "comCount");
            return (Criteria) this;
        }

        public Criteria andComCountNotEqualTo(Integer value) {
            addCriterion("com_count <>", value, "comCount");
            return (Criteria) this;
        }

        public Criteria andComCountGreaterThan(Integer value) {
            addCriterion("com_count >", value, "comCount");
            return (Criteria) this;
        }

        public Criteria andComCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("com_count >=", value, "comCount");
            return (Criteria) this;
        }

        public Criteria andComCountLessThan(Integer value) {
            addCriterion("com_count <", value, "comCount");
            return (Criteria) this;
        }

        public Criteria andComCountLessThanOrEqualTo(Integer value) {
            addCriterion("com_count <=", value, "comCount");
            return (Criteria) this;
        }

        public Criteria andComCountIn(List<Integer> values) {
            addCriterion("com_count in", values, "comCount");
            return (Criteria) this;
        }

        public Criteria andComCountNotIn(List<Integer> values) {
            addCriterion("com_count not in", values, "comCount");
            return (Criteria) this;
        }

        public Criteria andComCountBetween(Integer value1, Integer value2) {
            addCriterion("com_count between", value1, value2, "comCount");
            return (Criteria) this;
        }

        public Criteria andComCountNotBetween(Integer value1, Integer value2) {
            addCriterion("com_count not between", value1, value2, "comCount");
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