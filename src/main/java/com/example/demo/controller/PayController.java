package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.RechargeRecordMapper;
import com.example.demo.dao.SysParamMapper;
import com.example.demo.entity.RechargeRecord;
import com.example.demo.entity.SysParam;
import com.example.demo.service.impl.PayServiceImpl;
import com.example.demo.service.impl.RechargeServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.util.DynamicCodeUtil;
import com.example.demo.util.PropertyUtil;

@RestController
@RequestMapping("/pay")
public class PayController {
	@Autowired
	private SysParamMapper sysParamMapper;
	@Autowired
	private RechargeRecordMapper rechargeRecordMapper;
	@Autowired
	private RechargeServiceImpl rechargeServiceImpl;
	@Autowired
	private PayServiceImpl payServiceImpl;
	@Autowired
	private UserServiceImpl userServiceImpl;
//
//	@RequestMapping("addRechargeRecord")
//	public Map<String, Object> addRechargeRecord() {
//		// 获取当前用户
//		String phone = userServiceImpl.getCurrentUser();
//		Map<String, String> user_info = userServiceImpl.getUserByPhone(phone);
//		String user_id = user_info.get("user_id");
//		// 获取系统参数
//		String sysParam_id = PropertyUtil.getProperty("sysParam_id");
//		SysParam sys_param = sysParamMapper.selectByPrimaryKey(sysParam_id);
//
//		RechargeRecord record = new RechargeRecord();
//		String record_id = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
//		record.setRecordId(record_id);
//		record.setCreateTime(new Date());
//		record.setRmb(sys_param.getPayPrice());
//		record.setStatus(0);
//		record.setUserId(user_id);
//		int num = rechargeRecordMapper.insertSelective(record);
//		Map<String, Object> msg = new HashMap<String, Object>();
//		if (num > 0) {
//			msg.put("message", "成功");
//			msg.put("status", 200);
//		} else {
//			msg.put("message", "失败");
//			msg.put("status", 500);
//		}
//		return msg;
//	}

	@RequestMapping("payRecharge")
	public ModelAndView payRecharge(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> msg = new HashMap<String, Object>();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("");// callback view
		// String record_id = request.getParameter("record_id");
		// 获取当前用户
		String phone = userServiceImpl.getCurrentUser();
		Map<String, String> user_info = userServiceImpl.getUserByPhone(phone);
		String user_id = user_info.get("user_id");
		// 获取系统参数
		String sysParam_id = PropertyUtil.getProperty("sysParam_id");
		SysParam sys_param = sysParamMapper.selectByPrimaryKey(sysParam_id);

		RechargeRecord record = new RechargeRecord();
		String record_id = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
		record.setRecordId(record_id);
		record.setCreateTime(new Date());
		record.setRmb(sys_param.getPayPrice());
		record.setStatus(0);
		record.setUserId(user_id);
		int num = rechargeRecordMapper.insertSelective(record);
		if (num > 0) {

			String pay_way = request.getParameter("pay_way");
			RechargeRecord rechargeRecord = rechargeServiceImpl.getRechargeRecordInfo(record_id);
			if (rechargeRecord == null || StringUtils.isBlank(rechargeRecord.getRecordId())) {
				modelAndView.addObject("message", "充值错误");
				modelAndView.addObject("status", "fail");
				modelAndView.setViewName("");// 重新发起支付页
				return modelAndView;
			}
			String order_no = rechargeRecord.getRecordId();
			BigDecimal amount = rechargeRecord.getRmb();
			String subject = "充值" + rechargeRecord.getRmb() + "会员";
			String wap_name = "充值";
			String notify_url = "";
			
			// 修改状态为支付中
			rechargeRecord.setStatus(2);
			rechargeRecordMapper.updateByPrimaryKeySelective(rechargeRecord);
			// 修改状态为支付中
			JSONObject result = new JSONObject();
			if ("1".equals(pay_way)) {
				// 支付宝支付
				notify_url = "/payNotify/aliRechargeNotify";
				result = payServiceImpl.createAlipay(order_no, amount, notify_url, subject, wap_name, response,
						request);
			} else if ("2".equals(pay_way)) {
				// 微信支付
				notify_url = "/payNotify/wxRechargeNotify";
				result = payServiceImpl.createWxPay(order_no, amount, notify_url, subject, wap_name, response, request);
			}
			modelAndView.addAllObjects(result);
			return modelAndView;
		} else {

		}
		return null;
	}

}
