package com.example.demo.service;

import java.util.List;
import java.util.Map;

public interface WalletService {

	List<Map<String, String>> getWalletChangeRcord(String user_id);

	void aotuUnfreeze();

	Map<String, String> getWalletInfo(String user_id);

	int insertWithdrawalRecord(String record_id, String user_id, String rmb, String record_type, String account);

	int updateWalletByWithdrawal(String record_id);

	int withdrawalToAccount(String record_id);

	List<Map<String, String>> getDrawlRcord(Map<String, String> param);

	Map<String, String> getDrawlRrcordTotal(Map<String, String> param);

	Map<String, String> getUserIncome(String user_id);

}
