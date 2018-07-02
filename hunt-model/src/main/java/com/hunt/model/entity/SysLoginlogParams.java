package com.hunt.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysLoginlogParams {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SysLoginlogParams() {
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

        public Criteria andAccountIsNull() {
            addCriterion("account is null");
            return (Criteria) this;
        }

        public Criteria andAccountIsNotNull() {
            addCriterion("account is not null");
            return (Criteria) this;
        }

        public Criteria andAccountEqualTo(String value) {
            addCriterion("account =", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotEqualTo(String value) {
            addCriterion("account <>", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThan(String value) {
            addCriterion("account >", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThanOrEqualTo(String value) {
            addCriterion("account >=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThan(String value) {
            addCriterion("account <", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThanOrEqualTo(String value) {
            addCriterion("account <=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLike(String value) {
            addCriterion("account like", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotLike(String value) {
            addCriterion("account not like", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountIn(List<String> values) {
            addCriterion("account in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotIn(List<String> values) {
            addCriterion("account not in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountBetween(String value1, String value2) {
            addCriterion("account between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotBetween(String value1, String value2) {
            addCriterion("account not between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Short value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Short value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Short value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Short value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Short value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Short value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Short> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Short> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Short value1, Short value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Short value1, Short value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andAddtimeIsNull() {
            addCriterion("addTime is null");
            return (Criteria) this;
        }

        public Criteria andAddtimeIsNotNull() {
            addCriterion("addTime is not null");
            return (Criteria) this;
        }

        public Criteria andAddtimeEqualTo(Date value) {
            addCriterion("addTime =", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeNotEqualTo(Date value) {
            addCriterion("addTime <>", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeGreaterThan(Date value) {
            addCriterion("addTime >", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("addTime >=", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeLessThan(Date value) {
            addCriterion("addTime <", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeLessThanOrEqualTo(Date value) {
            addCriterion("addTime <=", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeIn(List<Date> values) {
            addCriterion("addTime in", values, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeNotIn(List<Date> values) {
            addCriterion("addTime not in", values, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeBetween(Date value1, Date value2) {
            addCriterion("addTime between", value1, value2, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeNotBetween(Date value1, Date value2) {
            addCriterion("addTime not between", value1, value2, "addtime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("updateTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("updateTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("updateTime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("updateTime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("updateTime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updateTime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("updateTime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("updateTime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("updateTime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("updateTime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("updateTime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("updateTime not between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andLoginipIsNull() {
            addCriterion("loginIp is null");
            return (Criteria) this;
        }

        public Criteria andLoginipIsNotNull() {
            addCriterion("loginIp is not null");
            return (Criteria) this;
        }

        public Criteria andLoginipEqualTo(String value) {
            addCriterion("loginIp =", value, "loginip");
            return (Criteria) this;
        }

        public Criteria andLoginipNotEqualTo(String value) {
            addCriterion("loginIp <>", value, "loginip");
            return (Criteria) this;
        }

        public Criteria andLoginipGreaterThan(String value) {
            addCriterion("loginIp >", value, "loginip");
            return (Criteria) this;
        }

        public Criteria andLoginipGreaterThanOrEqualTo(String value) {
            addCriterion("loginIp >=", value, "loginip");
            return (Criteria) this;
        }

        public Criteria andLoginipLessThan(String value) {
            addCriterion("loginIp <", value, "loginip");
            return (Criteria) this;
        }

        public Criteria andLoginipLessThanOrEqualTo(String value) {
            addCriterion("loginIp <=", value, "loginip");
            return (Criteria) this;
        }

        public Criteria andLoginipLike(String value) {
            addCriterion("loginIp like", value, "loginip");
            return (Criteria) this;
        }

        public Criteria andLoginipNotLike(String value) {
            addCriterion("loginIp not like", value, "loginip");
            return (Criteria) this;
        }

        public Criteria andLoginipIn(List<String> values) {
            addCriterion("loginIp in", values, "loginip");
            return (Criteria) this;
        }

        public Criteria andLoginipNotIn(List<String> values) {
            addCriterion("loginIp not in", values, "loginip");
            return (Criteria) this;
        }

        public Criteria andLoginipBetween(String value1, String value2) {
            addCriterion("loginIp between", value1, value2, "loginip");
            return (Criteria) this;
        }

        public Criteria andLoginipNotBetween(String value1, String value2) {
            addCriterion("loginIp not between", value1, value2, "loginip");
            return (Criteria) this;
        }

        public Criteria andLogintimeIsNull() {
            addCriterion("loginTime is null");
            return (Criteria) this;
        }

        public Criteria andLogintimeIsNotNull() {
            addCriterion("loginTime is not null");
            return (Criteria) this;
        }

        public Criteria andLogintimeEqualTo(Date value) {
            addCriterion("loginTime =", value, "logintime");
            return (Criteria) this;
        }

        public Criteria andLogintimeNotEqualTo(Date value) {
            addCriterion("loginTime <>", value, "logintime");
            return (Criteria) this;
        }

        public Criteria andLogintimeGreaterThan(Date value) {
            addCriterion("loginTime >", value, "logintime");
            return (Criteria) this;
        }

        public Criteria andLogintimeGreaterThanOrEqualTo(Date value) {
            addCriterion("loginTime >=", value, "logintime");
            return (Criteria) this;
        }

        public Criteria andLogintimeLessThan(Date value) {
            addCriterion("loginTime <", value, "logintime");
            return (Criteria) this;
        }

        public Criteria andLogintimeLessThanOrEqualTo(Date value) {
            addCriterion("loginTime <=", value, "logintime");
            return (Criteria) this;
        }

        public Criteria andLogintimeIn(List<Date> values) {
            addCriterion("loginTime in", values, "logintime");
            return (Criteria) this;
        }

        public Criteria andLogintimeNotIn(List<Date> values) {
            addCriterion("loginTime not in", values, "logintime");
            return (Criteria) this;
        }

        public Criteria andLogintimeBetween(Date value1, Date value2) {
            addCriterion("loginTime between", value1, value2, "logintime");
            return (Criteria) this;
        }

        public Criteria andLogintimeNotBetween(Date value1, Date value2) {
            addCriterion("loginTime not between", value1, value2, "logintime");
            return (Criteria) this;
        }

        public Criteria andIssuccessIsNull() {
            addCriterion("isSuccess is null");
            return (Criteria) this;
        }

        public Criteria andIssuccessIsNotNull() {
            addCriterion("isSuccess is not null");
            return (Criteria) this;
        }

        public Criteria andIssuccessEqualTo(String value) {
            addCriterion("isSuccess =", value, "issuccess");
            return (Criteria) this;
        }

        public Criteria andIssuccessNotEqualTo(String value) {
            addCriterion("isSuccess <>", value, "issuccess");
            return (Criteria) this;
        }

        public Criteria andIssuccessGreaterThan(String value) {
            addCriterion("isSuccess >", value, "issuccess");
            return (Criteria) this;
        }

        public Criteria andIssuccessGreaterThanOrEqualTo(String value) {
            addCriterion("isSuccess >=", value, "issuccess");
            return (Criteria) this;
        }

        public Criteria andIssuccessLessThan(String value) {
            addCriterion("isSuccess <", value, "issuccess");
            return (Criteria) this;
        }

        public Criteria andIssuccessLessThanOrEqualTo(String value) {
            addCriterion("isSuccess <=", value, "issuccess");
            return (Criteria) this;
        }

        public Criteria andIssuccessLike(String value) {
            addCriterion("isSuccess like", value, "issuccess");
            return (Criteria) this;
        }

        public Criteria andIssuccessNotLike(String value) {
            addCriterion("isSuccess not like", value, "issuccess");
            return (Criteria) this;
        }

        public Criteria andIssuccessIn(List<String> values) {
            addCriterion("isSuccess in", values, "issuccess");
            return (Criteria) this;
        }

        public Criteria andIssuccessNotIn(List<String> values) {
            addCriterion("isSuccess not in", values, "issuccess");
            return (Criteria) this;
        }

        public Criteria andIssuccessBetween(String value1, String value2) {
            addCriterion("isSuccess between", value1, value2, "issuccess");
            return (Criteria) this;
        }

        public Criteria andIssuccessNotBetween(String value1, String value2) {
            addCriterion("isSuccess not between", value1, value2, "issuccess");
            return (Criteria) this;
        }

        public Criteria andErrormsgIsNull() {
            addCriterion("errorMsg is null");
            return (Criteria) this;
        }

        public Criteria andErrormsgIsNotNull() {
            addCriterion("errorMsg is not null");
            return (Criteria) this;
        }

        public Criteria andErrormsgEqualTo(String value) {
            addCriterion("errorMsg =", value, "errormsg");
            return (Criteria) this;
        }

        public Criteria andErrormsgNotEqualTo(String value) {
            addCriterion("errorMsg <>", value, "errormsg");
            return (Criteria) this;
        }

        public Criteria andErrormsgGreaterThan(String value) {
            addCriterion("errorMsg >", value, "errormsg");
            return (Criteria) this;
        }

        public Criteria andErrormsgGreaterThanOrEqualTo(String value) {
            addCriterion("errorMsg >=", value, "errormsg");
            return (Criteria) this;
        }

        public Criteria andErrormsgLessThan(String value) {
            addCriterion("errorMsg <", value, "errormsg");
            return (Criteria) this;
        }

        public Criteria andErrormsgLessThanOrEqualTo(String value) {
            addCriterion("errorMsg <=", value, "errormsg");
            return (Criteria) this;
        }

        public Criteria andErrormsgLike(String value) {
            addCriterion("errorMsg like", value, "errormsg");
            return (Criteria) this;
        }

        public Criteria andErrormsgNotLike(String value) {
            addCriterion("errorMsg not like", value, "errormsg");
            return (Criteria) this;
        }

        public Criteria andErrormsgIn(List<String> values) {
            addCriterion("errorMsg in", values, "errormsg");
            return (Criteria) this;
        }

        public Criteria andErrormsgNotIn(List<String> values) {
            addCriterion("errorMsg not in", values, "errormsg");
            return (Criteria) this;
        }

        public Criteria andErrormsgBetween(String value1, String value2) {
            addCriterion("errorMsg between", value1, value2, "errormsg");
            return (Criteria) this;
        }

        public Criteria andErrormsgNotBetween(String value1, String value2) {
            addCriterion("errorMsg not between", value1, value2, "errormsg");
            return (Criteria) this;
        }

        public Criteria andLogtypeIsNull() {
            addCriterion("logType is null");
            return (Criteria) this;
        }

        public Criteria andLogtypeIsNotNull() {
            addCriterion("logType is not null");
            return (Criteria) this;
        }

        public Criteria andLogtypeEqualTo(String value) {
            addCriterion("logType =", value, "logtype");
            return (Criteria) this;
        }

        public Criteria andLogtypeNotEqualTo(String value) {
            addCriterion("logType <>", value, "logtype");
            return (Criteria) this;
        }

        public Criteria andLogtypeGreaterThan(String value) {
            addCriterion("logType >", value, "logtype");
            return (Criteria) this;
        }

        public Criteria andLogtypeGreaterThanOrEqualTo(String value) {
            addCriterion("logType >=", value, "logtype");
            return (Criteria) this;
        }

        public Criteria andLogtypeLessThan(String value) {
            addCriterion("logType <", value, "logtype");
            return (Criteria) this;
        }

        public Criteria andLogtypeLessThanOrEqualTo(String value) {
            addCriterion("logType <=", value, "logtype");
            return (Criteria) this;
        }

        public Criteria andLogtypeLike(String value) {
            addCriterion("logType like", value, "logtype");
            return (Criteria) this;
        }

        public Criteria andLogtypeNotLike(String value) {
            addCriterion("logType not like", value, "logtype");
            return (Criteria) this;
        }

        public Criteria andLogtypeIn(List<String> values) {
            addCriterion("logType in", values, "logtype");
            return (Criteria) this;
        }

        public Criteria andLogtypeNotIn(List<String> values) {
            addCriterion("logType not in", values, "logtype");
            return (Criteria) this;
        }

        public Criteria andLogtypeBetween(String value1, String value2) {
            addCriterion("logType between", value1, value2, "logtype");
            return (Criteria) this;
        }

        public Criteria andLogtypeNotBetween(String value1, String value2) {
            addCriterion("logType not between", value1, value2, "logtype");
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