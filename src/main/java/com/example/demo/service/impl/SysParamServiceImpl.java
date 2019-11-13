package com.example.demo.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.SysParamMapper;
import com.example.demo.entity.SysParam;
import com.example.demo.service.SysParamService;
import com.example.demo.util.PropertyUtil;

@Service("SysParamServiceImpl")
public class SysParamServiceImpl implements SysParamService {

	@Autowired
	SysParamMapper sysParamMapper;
	@Override
	public int editSysParam(BigDecimal pay_price, int direct_reward, int indirect_reward) {
		// TODO Auto-generated method stub
		String sysParam_id = PropertyUtil.getProperty("sysParam_id");
		SysParam sysParam = sysParamMapper.selectByPrimaryKey(sysParam_id);
		int num = 0;
		if (ObjectUtils.isNotEmpty(sysParam)) {
			sysParam.setPayPrice(pay_price);
			sysParam.setSysParamId(sysParam_id);
			if (direct_reward > 0) {
				sysParam.setDirectReward(direct_reward);
			}
			if (indirect_reward > 0) {
				sysParam.setIndirectReward(indirect_reward);
			}
			num = sysParamMapper.updateByPrimaryKeySelective(sysParam);
		} else {
			sysParam = new SysParam();
			sysParam.setPayPrice(pay_price);
			sysParam.setSysParamId(sysParam_id);
			if (direct_reward > 0) {
				sysParam.setDirectReward(direct_reward);
			}
			if (indirect_reward > 0) {
				sysParam.setIndirectReward(indirect_reward);
			}
			num = sysParamMapper.insertSelective(sysParam);
		}

		return num;
	}
	@Override
	public Map<String, String> getSysParam() {
		// TODO Auto-generated method stub
		return sysParamMapper.getSysParam();
	}

}
