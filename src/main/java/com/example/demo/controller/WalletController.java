package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.service.impl.WalletServiceImpl;
import com.example.demo.util.DynamicCodeUtil;

@RestController
@RequestMapping("/wallet")
public class WalletController {
	@Autowired
	private WalletServiceImpl walletServiceImpl;
	@Autowired
	private UserServiceImpl userServiceImpl;

	@RequestMapping("/getWalletChangeRcord")
	public Map<String, Object> getWalletChangeRcord() {
		String phone = userServiceImpl.getCurrentUser();
		Map<String, String> user_info = userServiceImpl.getUserByPhone(phone);
		String user_id = user_info.get("user_id");

		List<Map<String, String>> record_list = walletServiceImpl.getWalletChangeRcord(user_id);
		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("context", record_list);
		msg.put("status", 200);
		return msg;
	}

	@RequestMapping("/getWalletInfo")
	public Map<String, Object> getWalletInfo() {
		String phone = userServiceImpl.getCurrentUser();
		Map<String, String> user_info = userServiceImpl.getUserByPhone(phone);
		String user_id = user_info.get("user_id");
		Map<String, String> wallet_info = walletServiceImpl.getWalletInfo(user_id);

		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("context", wallet_info);
		msg.put("status", 200);
		return msg;
	}

	@RequestMapping("doWithdrawal")
	public Map<String, Object> doWithdrawal(String rmb, String record_type) {
		String phone = userServiceImpl.getCurrentUser();
		Map<String, String> user_info = userServiceImpl.getUserByPhone(phone);
		String user_id = user_info.get("user_id");
		Map<String, Object> msg = new HashMap<String, Object>();
		// 插入提现记录
		String record_id = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
		int num = walletServiceImpl.insertWithdrawalRecord(record_id, user_id, rmb, record_type);
		if (num > 0) {
			// 提现到账户
			walletServiceImpl.withdrawalToAccount(record_id);

		} else {
			msg.put("status", 500);
			msg.put("message", "余额不足");
		}
		return msg;
	}

	@RequestMapping("getDrawlRcord")
	public Map<String, Object> getDrawlRcord(HttpServletRequest request) {
		String phone = userServiceImpl.getCurrentUser();
		Map<String, String> user_info = userServiceImpl.getUserByPhone(phone);
		String user_id = user_info.get("user_id");
		Map<String, String> param = new HashMap<String, String>();
		param.put("month", request.getParameter("month"));
		param.put("user_id", user_id);
		List<Map<String, String>> list = walletServiceImpl.getDrawlRcord(param);
		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("context", list);
		msg.put("status", 200);
		return msg;
	}

	@RequestMapping("getDrawlRrcordTotal")
	public Map<String, Object> getDrawlRrcordTotal(HttpServletRequest request) {
		String phone = userServiceImpl.getCurrentUser();
		Map<String, String> user_info = userServiceImpl.getUserByPhone(phone);
		String user_id = user_info.get("user_id");
		Map<String, String> param = new HashMap<String, String>();
		param.put("month", request.getParameter("month"));
		param.put("user_id", user_id);
		Map<String, String> map = walletServiceImpl.getDrawlRrcordTotal(param);
		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("context", map);
		msg.put("status", 200);
		return msg;
	}
}
