package com.example.demo.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SysParamExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table sys_param
	 * @mbg.generated
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table sys_param
	 * @mbg.generated
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table sys_param
	 * @mbg.generated
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_param
	 * @mbg.generated
	 */
	public SysParamExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_param
	 * @mbg.generated
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_param
	 * @mbg.generated
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_param
	 * @mbg.generated
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_param
	 * @mbg.generated
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_param
	 * @mbg.generated
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_param
	 * @mbg.generated
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_param
	 * @mbg.generated
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_param
	 * @mbg.generated
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_param
	 * @mbg.generated
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_param
	 * @mbg.generated
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table sys_param
	 * @mbg.generated
	 */
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

		public Criteria andSysParamIdIsNull() {
			addCriterion("sys_param_id is null");
			return (Criteria) this;
		}

		public Criteria andSysParamIdIsNotNull() {
			addCriterion("sys_param_id is not null");
			return (Criteria) this;
		}

		public Criteria andSysParamIdEqualTo(String value) {
			addCriterion("sys_param_id =", value, "sysParamId");
			return (Criteria) this;
		}

		public Criteria andSysParamIdNotEqualTo(String value) {
			addCriterion("sys_param_id <>", value, "sysParamId");
			return (Criteria) this;
		}

		public Criteria andSysParamIdGreaterThan(String value) {
			addCriterion("sys_param_id >", value, "sysParamId");
			return (Criteria) this;
		}

		public Criteria andSysParamIdGreaterThanOrEqualTo(String value) {
			addCriterion("sys_param_id >=", value, "sysParamId");
			return (Criteria) this;
		}

		public Criteria andSysParamIdLessThan(String value) {
			addCriterion("sys_param_id <", value, "sysParamId");
			return (Criteria) this;
		}

		public Criteria andSysParamIdLessThanOrEqualTo(String value) {
			addCriterion("sys_param_id <=", value, "sysParamId");
			return (Criteria) this;
		}

		public Criteria andSysParamIdLike(String value) {
			addCriterion("sys_param_id like", value, "sysParamId");
			return (Criteria) this;
		}

		public Criteria andSysParamIdNotLike(String value) {
			addCriterion("sys_param_id not like", value, "sysParamId");
			return (Criteria) this;
		}

		public Criteria andSysParamIdIn(List<String> values) {
			addCriterion("sys_param_id in", values, "sysParamId");
			return (Criteria) this;
		}

		public Criteria andSysParamIdNotIn(List<String> values) {
			addCriterion("sys_param_id not in", values, "sysParamId");
			return (Criteria) this;
		}

		public Criteria andSysParamIdBetween(String value1, String value2) {
			addCriterion("sys_param_id between", value1, value2, "sysParamId");
			return (Criteria) this;
		}

		public Criteria andSysParamIdNotBetween(String value1, String value2) {
			addCriterion("sys_param_id not between", value1, value2, "sysParamId");
			return (Criteria) this;
		}

		public Criteria andPayPriceIsNull() {
			addCriterion("pay_price is null");
			return (Criteria) this;
		}

		public Criteria andPayPriceIsNotNull() {
			addCriterion("pay_price is not null");
			return (Criteria) this;
		}

		public Criteria andPayPriceEqualTo(BigDecimal value) {
			addCriterion("pay_price =", value, "payPrice");
			return (Criteria) this;
		}

		public Criteria andPayPriceNotEqualTo(BigDecimal value) {
			addCriterion("pay_price <>", value, "payPrice");
			return (Criteria) this;
		}

		public Criteria andPayPriceGreaterThan(BigDecimal value) {
			addCriterion("pay_price >", value, "payPrice");
			return (Criteria) this;
		}

		public Criteria andPayPriceGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("pay_price >=", value, "payPrice");
			return (Criteria) this;
		}

		public Criteria andPayPriceLessThan(BigDecimal value) {
			addCriterion("pay_price <", value, "payPrice");
			return (Criteria) this;
		}

		public Criteria andPayPriceLessThanOrEqualTo(BigDecimal value) {
			addCriterion("pay_price <=", value, "payPrice");
			return (Criteria) this;
		}

		public Criteria andPayPriceIn(List<BigDecimal> values) {
			addCriterion("pay_price in", values, "payPrice");
			return (Criteria) this;
		}

		public Criteria andPayPriceNotIn(List<BigDecimal> values) {
			addCriterion("pay_price not in", values, "payPrice");
			return (Criteria) this;
		}

		public Criteria andPayPriceBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("pay_price between", value1, value2, "payPrice");
			return (Criteria) this;
		}

		public Criteria andPayPriceNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("pay_price not between", value1, value2, "payPrice");
			return (Criteria) this;
		}

		public Criteria andDirectRewardIsNull() {
			addCriterion("direct_reward is null");
			return (Criteria) this;
		}

		public Criteria andDirectRewardIsNotNull() {
			addCriterion("direct_reward is not null");
			return (Criteria) this;
		}

		public Criteria andDirectRewardEqualTo(Integer value) {
			addCriterion("direct_reward =", value, "directReward");
			return (Criteria) this;
		}

		public Criteria andDirectRewardNotEqualTo(Integer value) {
			addCriterion("direct_reward <>", value, "directReward");
			return (Criteria) this;
		}

		public Criteria andDirectRewardGreaterThan(Integer value) {
			addCriterion("direct_reward >", value, "directReward");
			return (Criteria) this;
		}

		public Criteria andDirectRewardGreaterThanOrEqualTo(Integer value) {
			addCriterion("direct_reward >=", value, "directReward");
			return (Criteria) this;
		}

		public Criteria andDirectRewardLessThan(Integer value) {
			addCriterion("direct_reward <", value, "directReward");
			return (Criteria) this;
		}

		public Criteria andDirectRewardLessThanOrEqualTo(Integer value) {
			addCriterion("direct_reward <=", value, "directReward");
			return (Criteria) this;
		}

		public Criteria andDirectRewardIn(List<Integer> values) {
			addCriterion("direct_reward in", values, "directReward");
			return (Criteria) this;
		}

		public Criteria andDirectRewardNotIn(List<Integer> values) {
			addCriterion("direct_reward not in", values, "directReward");
			return (Criteria) this;
		}

		public Criteria andDirectRewardBetween(Integer value1, Integer value2) {
			addCriterion("direct_reward between", value1, value2, "directReward");
			return (Criteria) this;
		}

		public Criteria andDirectRewardNotBetween(Integer value1, Integer value2) {
			addCriterion("direct_reward not between", value1, value2, "directReward");
			return (Criteria) this;
		}

		public Criteria andIndirectRewardIsNull() {
			addCriterion("indirect_reward is null");
			return (Criteria) this;
		}

		public Criteria andIndirectRewardIsNotNull() {
			addCriterion("indirect_reward is not null");
			return (Criteria) this;
		}

		public Criteria andIndirectRewardEqualTo(Integer value) {
			addCriterion("indirect_reward =", value, "indirectReward");
			return (Criteria) this;
		}

		public Criteria andIndirectRewardNotEqualTo(Integer value) {
			addCriterion("indirect_reward <>", value, "indirectReward");
			return (Criteria) this;
		}

		public Criteria andIndirectRewardGreaterThan(Integer value) {
			addCriterion("indirect_reward >", value, "indirectReward");
			return (Criteria) this;
		}

		public Criteria andIndirectRewardGreaterThanOrEqualTo(Integer value) {
			addCriterion("indirect_reward >=", value, "indirectReward");
			return (Criteria) this;
		}

		public Criteria andIndirectRewardLessThan(Integer value) {
			addCriterion("indirect_reward <", value, "indirectReward");
			return (Criteria) this;
		}

		public Criteria andIndirectRewardLessThanOrEqualTo(Integer value) {
			addCriterion("indirect_reward <=", value, "indirectReward");
			return (Criteria) this;
		}

		public Criteria andIndirectRewardIn(List<Integer> values) {
			addCriterion("indirect_reward in", values, "indirectReward");
			return (Criteria) this;
		}

		public Criteria andIndirectRewardNotIn(List<Integer> values) {
			addCriterion("indirect_reward not in", values, "indirectReward");
			return (Criteria) this;
		}

		public Criteria andIndirectRewardBetween(Integer value1, Integer value2) {
			addCriterion("indirect_reward between", value1, value2, "indirectReward");
			return (Criteria) this;
		}

		public Criteria andIndirectRewardNotBetween(Integer value1, Integer value2) {
			addCriterion("indirect_reward not between", value1, value2, "indirectReward");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table sys_param
	 * @mbg.generated
	 */
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

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table sys_param
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}