package com.example.demo.service;

import java.math.BigDecimal;
import java.util.Map;

public interface SysParamService {

	int editSysParam(BigDecimal pay_price, int direct_reward, int indirect_reward);

	Map<String, String> getSysParam();
	
}
