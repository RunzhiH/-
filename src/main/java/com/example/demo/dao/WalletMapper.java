package com.example.demo.dao;

import java.util.Map;
import com.example.demo.entity.Wallet;
import com.example.demo.entity.WalletExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WalletMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table wallet
	 * @mbg.generated
	 */
	long countByExample(WalletExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table wallet
	 * @mbg.generated
	 */
	int deleteByExample(WalletExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table wallet
	 * @mbg.generated
	 */
	int deleteByPrimaryKey(String wallerId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table wallet
	 * @mbg.generated
	 */
	int insert(Wallet record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table wallet
	 * @mbg.generated
	 */
	int insertSelective(Wallet record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table wallet
	 * @mbg.generated
	 */
	List<Wallet> selectByExample(WalletExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table wallet
	 * @mbg.generated
	 */
	Wallet selectByPrimaryKey(String wallerId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table wallet
	 * @mbg.generated
	 */
	int updateByExampleSelective(@Param("record") Wallet record, @Param("example") WalletExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table wallet
	 * @mbg.generated
	 */
	int updateByExample(@Param("record") Wallet record, @Param("example") WalletExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table wallet
	 * @mbg.generated
	 */
	int updateByPrimaryKeySelective(Wallet record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table wallet
	 * @mbg.generated
	 */
	int updateByPrimaryKey(Wallet record);

	Map<String, String> getWalletInfo(String userId);

	int insertNewWallerByUser(String phone);

	List<Map<String, String>> getWalletChangeRcord(String user_id);

	int updateWalletByRecharge(String change_record_id);

	List<String> getUserForRmbIsNotZero();

	int updateWalletByUnfreeze(String record_id);

	int updateWalletByWithdrawal(String record_id);
	
	
}