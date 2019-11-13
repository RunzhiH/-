package com.example.demo.service;

import com.example.demo.entity.RechargeRecord;

public interface RechargeService {

	RechargeRecord getRechargeRecordInfo(String record_id);

	void afterRechargeDo(String record_id);

	void directRechargeReward(String record_id);

}
