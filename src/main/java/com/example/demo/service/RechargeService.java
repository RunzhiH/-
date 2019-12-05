package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.entity.RechargeRecord;

public interface RechargeService {

	RechargeRecord getRechargeRecordInfo(String record_id);

	void afterRechargeDo(String record_id);

	void directRechargeReward(String record_id);

	List<Map<String, String>> getRechargeRecordList(Map<String, String> param);

	Map<String, String> getRechargeRrcordTotal(Map<String, String> param);

}
