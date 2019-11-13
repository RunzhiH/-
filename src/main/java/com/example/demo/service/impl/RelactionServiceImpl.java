package com.example.demo.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserRelationMapper;
import com.example.demo.service.RelactionService;

@Service("RelactionServiceImpl")
public class RelactionServiceImpl implements RelactionService {
	@Autowired
	private UserRelationMapper userRelationMapper;

	@Override
	public int addUserRelaction(String phone, String share_user_id) {
		// TODO Auto-generated method stub
		int num;
		if (StringUtils.isNotBlank(share_user_id)) {
			num = userRelationMapper.addUserRelactionWithShareUserId(phone, share_user_id);
		} else {
			num = userRelationMapper.addUserRelactionWithoutShareUserId(phone);
		}
		return num;
	}

}
