package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.impl.RechargeServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	private RechargeServiceImpl rechargeServiceImpl;

	@RequestMapping("/getAllUser")
	public Map<String, Object> getAllUser() {
		Map<String, Object> msg = new HashMap<String, Object>();

		List<Map<String, String>> user_list = userServiceImpl.getAllUser();
		msg.put("context", user_list);
		msg.put("status", "200");
		return msg;
	}

	@RequestMapping("/getUserInfo")
	public Map<String, Object> getUserInfo() {
		Map<String, Object> msg = new HashMap<String, Object>();
		String phone = userServiceImpl.getCurrentUser();
		Map<String, String> user_info = userServiceImpl.getUserByPhone(phone);
		msg.put("context", user_info);
		msg.put("status", 200);
		return msg;
	}

	@RequestMapping("/updateUserInfo")
	public Map<String, Object> updateUserInfo(HttpServletRequest request) {
		// 获取当前用户
		String phone = userServiceImpl.getCurrentUser();
		Map<String, String> user_info = userServiceImpl.getUserByPhone(phone);
		String user_id = user_info.get("user_id");
		Map<String, Object> msg = new HashMap<String, Object>();
		String user_name = request.getParameter("user_name");
		String alipay_account = request.getParameter("alipay_account");
		String photo = request.getParameter("photo");

		int num = userServiceImpl.updateUserInfo(user_id, user_name, alipay_account, photo);
		if (num > 0) {
			msg.put("message", "成功");
			msg.put("status", 200);
		} else {
			msg.put("message", "失败");
			msg.put("status", 500);
		}
		return msg;
	}

	@RequestMapping("getRechargeRrcord")
	public Map<String, Object> getRechargeRrcord(HttpServletRequest request) {
		String phone = userServiceImpl.getCurrentUser();
		Map<String, String> user_info = userServiceImpl.getUserByPhone(phone);
		String user_id = user_info.get("user_id");
		Map<String, String> param = new HashMap<String, String>();
		param.put("month", request.getParameter("month"));
		param.put("user_id", user_id);
		List<Map<String, String>> list = rechargeServiceImpl.getRechargeRecordList(param);
		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("context", list);
		msg.put("status", 200);
		return msg;
	}

	@RequestMapping("getRechargeRrcordTotal")
	public Map<String, Object> getRechargeRrcordTotal(HttpServletRequest request) {
		String phone = userServiceImpl.getCurrentUser();
		Map<String, String> user_info = userServiceImpl.getUserByPhone(phone);
		String user_id = user_info.get("user_id");
		Map<String, String> param = new HashMap<String, String>();
		param.put("month", request.getParameter("month"));
		param.put("user_id", user_id);
		Map<String, String> map= rechargeServiceImpl.getRechargeRrcordTotal(param);
		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("context", map);
		msg.put("status", 200);
		return msg;
	}
}
