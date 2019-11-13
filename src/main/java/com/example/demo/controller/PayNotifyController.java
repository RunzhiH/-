package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.example.demo.config.AliPayConfig;
import com.example.demo.dao.RechargeRecordMapper;
import com.example.demo.dao.UserInfoMapper;
import com.example.demo.entity.RechargeRecord;
import com.example.demo.service.impl.RechargeServiceImpl;
import com.example.demo.util.PayUtil;

@RestController
@RequestMapping("/payNotify")
public class PayNotifyController {
	@Autowired
	private RechargeServiceImpl rechargeServiceImpl;
	@Autowired
	private RechargeRecordMapper rechargeRecordMapper;
	@Autowired
	private UserInfoMapper userInfoMapper;

	@RequestMapping(value = "wxRechargeNotify", method = RequestMethod.POST)
	public void wechatPayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {

		BufferedReader reader = request.getReader();
		String line = "";
		Map map = new HashMap();
		String xml = "<xml><return_code><![CDATA[FAIL]]></xml>";
		;
		JSONObject dataInfo = new JSONObject();
		StringBuffer inputString = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			inputString.append(line);
		}
		request.getReader().close();
		System.out.println("----接收到的报文---" + inputString.toString());
		if (inputString.toString().length() > 0) {
			map = PayUtil.parseXmlToMap(inputString.toString());
		} else {
			System.out.println("接受微信报文为空");
		}
		System.out.println("map=" + map);
		if (map != null && "SUCCESS".equals(map.get("result_code"))) {

			xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
			// 成功的业务。。。
			String record_id = String.valueOf(map.get("out_trade_no"));
			rechargeServiceImpl.afterRechargeDo(record_id);
			
			//保存openid
			String open_id= String.valueOf(map.get("openid"));
			userInfoMapper.UpdateUserOpenId(record_id,open_id);
		} else {
			// 失败的业务。。。
			String record_id = String.valueOf(map.get("out_trade_no"));
			RechargeRecord rechargeRecord = new RechargeRecord();
			rechargeRecord.setRecordId(record_id);
			rechargeRecord.setStatus(0);
			rechargeRecordMapper.updateByPrimaryKeySelective(rechargeRecord);
		}
		// 告诉微信端已经确认支付成功
		response.getWriter().write(xml);
	}

	@RequestMapping(value = "aliRechargeNotify", method = RequestMethod.POST)
	public void aliPayNotify(HttpServletRequest request, HttpServletResponse response) {

		Map<String, String> map = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			map.put(name, valueStr);
		}
		System.out.println("支付结果---：" + map.toString());
		// 调用SDK验证签名
		boolean signVerified = false;
		try {
			signVerified = AlipaySignature.rsaCheckV1(map, AliPayConfig.alipay_app_public_key,
					AliPayConfig.alipay_charset, AliPayConfig.alipay_sign_type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (signVerified) {
			System.out.println("支付结果---：" + map.toString());
			String record_id = map.get("out_trade_no");
			// 根据交易流水号查询交易信息
			if ("TRADE_SUCCESS".equals(map.get("trade_status"))) {// 交易成功
				// 处理业务
				// 修改支付状态为已支付
				rechargeServiceImpl.afterRechargeDo(record_id);
				// 处理业务结束
			} else {
				// 失败业务
				RechargeRecord rechargeRecord = new RechargeRecord();
				rechargeRecord.setRecordId(record_id);
				rechargeRecord.setStatus(0);
				rechargeRecordMapper.updateByPrimaryKeySelective(rechargeRecord);
			}
			try {
				PrintWriter out = response.getWriter();
				out.print("success");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// TODO
			// 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
		} else {
			// TODO 验签失败则记录异常日志，并在response中返回failure.
			try {
				PrintWriter out = response.getWriter();
				out.print("failure");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
