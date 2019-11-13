package com.example.demo.dao;

import com.example.demo.entity.UserRelation;
import com.example.demo.entity.UserRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRelationMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_relation
	 * @mbg.generated
	 */
	long countByExample(UserRelationExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_relation
	 * @mbg.generated
	 */
	int deleteByExample(UserRelationExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_relation
	 * @mbg.generated
	 */
	int deleteByPrimaryKey(String userRelationId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_relation
	 * @mbg.generated
	 */
	int insert(UserRelation record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_relation
	 * @mbg.generated
	 */
	int insertSelective(UserRelation record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_relation
	 * @mbg.generated
	 */
	List<UserRelation> selectByExample(UserRelationExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_relation
	 * @mbg.generated
	 */
	UserRelation selectByPrimaryKey(String userRelationId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_relation
	 * @mbg.generated
	 */
	int updateByExampleSelective(@Param("record") UserRelation record, @Param("example") UserRelationExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_relation
	 * @mbg.generated
	 */
	int updateByExample(@Param("record") UserRelation record, @Param("example") UserRelationExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_relation
	 * @mbg.generated
	 */
	int updateByPrimaryKeySelective(UserRelation record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_relation
	 * @mbg.generated
	 */
	int updateByPrimaryKey(UserRelation record);

	int addUserRelactionWithShareUserId(String phone, String share_user_id);
	
	int addUserRelactionWithoutShareUserId(String phone);
}