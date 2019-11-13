package com.example.demo.dao;

import com.example.demo.entity.SysParam;
import com.example.demo.entity.SysParamExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SysParamMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_param
	 * @mbg.generated
	 */
	long countByExample(SysParamExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_param
	 * @mbg.generated
	 */
	int deleteByExample(SysParamExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_param
	 * @mbg.generated
	 */
	int deleteByPrimaryKey(String sysParamId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_param
	 * @mbg.generated
	 */
	int insert(SysParam record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_param
	 * @mbg.generated
	 */
	int insertSelective(SysParam record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_param
	 * @mbg.generated
	 */
	List<SysParam> selectByExample(SysParamExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_param
	 * @mbg.generated
	 */
	SysParam selectByPrimaryKey(String sysParamId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_param
	 * @mbg.generated
	 */
	int updateByExampleSelective(@Param("record") SysParam record, @Param("example") SysParamExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_param
	 * @mbg.generated
	 */
	int updateByExample(@Param("record") SysParam record, @Param("example") SysParamExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_param
	 * @mbg.generated
	 */
	int updateByPrimaryKeySelective(SysParam record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_param
	 * @mbg.generated
	 */
	int updateByPrimaryKey(SysParam record);

	Map<String, String> getSysParam();
}