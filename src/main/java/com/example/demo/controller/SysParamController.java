package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.impl.SysParamServiceImpl;

@RestController
@RequestMapping("/sysParam")
public class SysParamController {
	@Autowired
	private SysParamServiceImpl sysParamServiceImpl;

	@RequestMapping("/updateSysParam")
	public Map<String, Object> updateSysParam(@RequestParam("pay_price") String pay_price,
			@RequestParam("direct_reward") int direct_reward, @RequestParam("indirect_reward") int indirect_reward) {

		int num = sysParamServiceImpl.editSysParam(new BigDecimal(pay_price), direct_reward, indirect_reward);
		Map<String, Object> msg = new HashMap<String, Object>();
		if (num > 0) {
			msg.put("message", "成功");
			msg.put("status", 200);
		} else {
			msg.put("message", "失败");
			msg.put("status", 500);
		}
		return msg;
	}

	@RequestMapping("getSysParam")
	public Map<String, Object> getSysParam() {
		Map<String, String> sysParam = sysParamServiceImpl.getSysParam();
		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("context", sysParam);
		msg.put("status", 200);
		return msg;
	}
}
