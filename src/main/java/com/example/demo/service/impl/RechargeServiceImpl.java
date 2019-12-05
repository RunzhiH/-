package com.example.demo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.RechargeRecordMapper;
import com.example.demo.dao.WalletChangeRecordMapper;
import com.example.demo.dao.WalletMapper;
import com.example.demo.entity.RechargeRecord;
import com.example.demo.service.RechargeService;
import com.example.demo.util.DynamicCodeUtil;

@Service("RechargeServiceImpl")
public class RechargeServiceImpl implements RechargeService {
	@Autowired
	private RechargeRecordMapper RechargeRecordMapper;
	@Autowired
	private WalletChangeRecordMapper walletChangeRecordMapper;
	@Autowired
	private WalletMapper walletMapper;

	@Override
	public RechargeRecord getRechargeRecordInfo(String record_id) {
		// TODO Auto-generated method stub
		return RechargeRecordMapper.selectByPrimaryKey(record_id);
	}

	@Override
	public void afterRechargeDo(String record_id) {
		RechargeRecord record = RechargeRecordMapper.selectByPrimaryKey(record_id);
		String pay_way = String.valueOf(record.getPayWay());
		if ("2".equals(String.valueOf(record.getStatus()))) {
			if ("1".equals(pay_way) || "2".equals(pay_way)) {
				// 修改支付状态
				record.setStatus(1);
				RechargeRecordMapper.updateByPrimaryKeySelective(record);
				// 插入余额变动记录
				String change_record_id = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
				int num = walletChangeRecordMapper.insertRecordForRecharge(record_id, change_record_id);
				// 更新钱包金额
				if (num > 0) {
					walletMapper.updateWalletByRecharge(change_record_id);
				}
				// 为推荐人增加冻结金额
				directRechargeReward(record_id);

				indirectRechargeReward(record_id);
			}
		}

	}

	public void indirectRechargeReward(String record_id) {
		// 间推奖励
		String change_record_id = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
		int num = walletChangeRecordMapper.insertRecordForIndirectRechargeReward(record_id, change_record_id);
		// 更新钱包金额
		if (num > 0) {
			walletMapper.updateWalletByRecharge(change_record_id);
		}
	}

	@Override
	public void directRechargeReward(String record_id) {
		// 直推奖励
		String change_record_id = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
		int num = walletChangeRecordMapper.insertRecordForDirectRechargeReward(record_id, change_record_id);
		// 更新钱包金额
		if (num > 0) {
			walletMapper.updateWalletByRecharge(change_record_id);
		}
	}
	@Override
	public List<Map<String, String>> getRechargeRecordList(Map<String, String> param) {
		
		return RechargeRecordMapper.getRechargeRecordList(param);
	}
	@Override
	public Map<String, String> getRechargeRrcordTotal(Map<String, String> param) {
		// TODO Auto-generated method stub
		return RechargeRecordMapper.getRechargeRrcordTotal(param);
	}

}
