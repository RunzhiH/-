package com.example.demo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.WalletChangeRecordMapper;
import com.example.demo.dao.WalletMapper;
import com.example.demo.dao.WithdrawalRecordMapper;
import com.example.demo.entity.WithdrawalRecord;
import com.example.demo.service.WalletService;
import com.example.demo.util.DynamicCodeUtil;

@Service("WalletServiceImpl")
public class WalletServiceImpl implements WalletService {
	@Autowired
	private WalletMapper walletMapper;
	@Autowired
	private WalletChangeRecordMapper walletChangeRecordMapper;
	@Autowired
	private WithdrawalRecordMapper withdrawalRecordMapper;
	@Autowired
	private PayServiceImpl payServiceImpl;

	@Override
	public List<Map<String, String>> getWalletChangeRcord(String user_id) {
		// TODO Auto-generated method stub
		return walletMapper.getWalletChangeRcord(user_id);
	}

	@Override
	public void aotuUnfreeze() {
		// TODO Auto-generated method stub
		// 获取解冻金额大于零的用户
		List<String> user_list = walletMapper.getUserForRmbIsNotZero();
		for (String user_id : user_list) {
			String record_id = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
			int num = walletChangeRecordMapper.insertRecordForUnfreeze(user_id, record_id);
			if (num > 0) {
				walletMapper.updateWalletByUnfreeze(record_id);
			}
		}
	}

	@Override
	public Map<String, String> getWalletInfo(String user_id) {
		// TODO Auto-generated method stub
		return walletMapper.getWalletInfo(user_id);
	}

	@Override
	public int insertWithdrawalRecord(String record_id, String user_id, String rmb, String record_type, String account) {
		// TODO Auto-generated method stub
		return withdrawalRecordMapper.insertWithdrawalRecord(record_id, user_id, rmb, record_type,account);
	}

	@Override
	public int updateWalletByWithdrawal(String record_id) {

		return walletMapper.updateWalletByWithdrawal(record_id);
	}

	@Override
	public int withdrawalToAccount(String record_id) {
		// TODO Auto-generated method stub
		// 修改状态
		WithdrawalRecord withdrawalRecord = new WithdrawalRecord();
		withdrawalRecord.setRecordId(record_id);
		withdrawalRecord.setRecordStatus(2);
		withdrawalRecordMapper.updateByPrimaryKeySelective(withdrawalRecord);

		//
		WithdrawalRecord withdrawal_record = withdrawalRecordMapper.selectByPrimaryKey(record_id);
		String record_type = String.valueOf(withdrawal_record.getRecordType());
		Map<String, String> resultMap = null;
		if ("1".equals(record_type)) {
			// 提现到支付宝
			// resultMap= payServiceImpl.alipay2User(bizNo, amount, account, userName);
		} else if ("2".equals(record_type)) {
			// 提现到微信
			// resultMap = payServiceImpl.weixinWithdraw(openId, ip, partner_trade_no,
			// money, desc);
		}
		if (true) {
			// 修改状态
			withdrawalRecord.setRecordStatus(1);
			withdrawalRecordMapper.updateByPrimaryKeySelective(withdrawalRecord);

			String change_record_id = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
			int num = walletChangeRecordMapper.insertRecordForWithdrawal(change_record_id, record_id);
			// 更新钱包余额
			if (num > 0) {
				walletMapper.updateWalletByWithdrawal(change_record_id);
			}

		}
		return 0;
	}

	@Override
	public List<Map<String, String>> getDrawlRcord(Map<String, String> param) {
		// TODO Auto-generated method stub
		return walletChangeRecordMapper.getDrawlRcord(param);
	}

	@Override
	public Map<String, String> getDrawlRrcordTotal(Map<String, String> param) {
		// TODO Auto-generated method stub
		return walletChangeRecordMapper.getDrawlRrcordTotal(param);
	}
	@Override
	public Map<String, String> getUserIncome(String user_id) {
		// TODO Auto-generated method stub
		return walletChangeRecordMapper.getUserIncome(user_id);
	}

}
