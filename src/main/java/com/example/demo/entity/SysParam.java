package com.example.demo.entity;

import java.math.BigDecimal;

public class SysParam {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column sys_param.sys_param_id
	 * @mbg.generated
	 */
	private String sysParamId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column sys_param.pay_price
	 * @mbg.generated
	 */
	private BigDecimal payPrice;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column sys_param.direct_reward
	 * @mbg.generated
	 */
	private Integer directReward;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column sys_param.indirect_reward
	 * @mbg.generated
	 */
	private Integer indirectReward;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column sys_param.sys_param_id
	 * @return  the value of sys_param.sys_param_id
	 * @mbg.generated
	 */
	public String getSysParamId() {
		return sysParamId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column sys_param.sys_param_id
	 * @param sysParamId  the value for sys_param.sys_param_id
	 * @mbg.generated
	 */
	public void setSysParamId(String sysParamId) {
		this.sysParamId = sysParamId == null ? null : sysParamId.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column sys_param.pay_price
	 * @return  the value of sys_param.pay_price
	 * @mbg.generated
	 */
	public BigDecimal getPayPrice() {
		return payPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column sys_param.pay_price
	 * @param payPrice  the value for sys_param.pay_price
	 * @mbg.generated
	 */
	public void setPayPrice(BigDecimal payPrice) {
		this.payPrice = payPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column sys_param.direct_reward
	 * @return  the value of sys_param.direct_reward
	 * @mbg.generated
	 */
	public Integer getDirectReward() {
		return directReward;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column sys_param.direct_reward
	 * @param directReward  the value for sys_param.direct_reward
	 * @mbg.generated
	 */
	public void setDirectReward(Integer directReward) {
		this.directReward = directReward;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column sys_param.indirect_reward
	 * @return  the value of sys_param.indirect_reward
	 * @mbg.generated
	 */
	public Integer getIndirectReward() {
		return indirectReward;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column sys_param.indirect_reward
	 * @param indirectReward  the value for sys_param.indirect_reward
	 * @mbg.generated
	 */
	public void setIndirectReward(Integer indirectReward) {
		this.indirectReward = indirectReward;
	}
}