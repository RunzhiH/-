package com.example.demo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserInfoMapper;
import com.example.demo.dao.WalletMapper;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserService;
import com.example.demo.util.DynamicCodeUtil;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private WalletMapper walletMapper;
	@Autowired
	private RelactionServiceImpl relactionServiceImpl;

	@Override
	public List<Map<String, String>> getAllUser() {
		// TODO Auto-generated method stub
		return userInfoMapper.getAllUser();
	}

	@Override
	public Map<String, String> getUserInfo(String userId) {
		// TODO Auto-generated method stub
		// 用户信息
		Map<String, String> userInfo = userInfoMapper.getUserInfo(userId);
		// 钱包信息
		userInfo.putAll(walletMapper.getWalletInfo(userId));
		return userInfo;
	}

	@Override
	public Map<String, String> getUserByPhone(String phone) {
		// TODO Auto-generated method stub
		return userInfoMapper.getUserByPhone(phone);
	}

	public int addUser(String phone, String password, String share_user_id) {
		// TODO Auto-generated method stub
		UserInfo user = new UserInfo();
		user.setLevel(0);
		user.setPassword(new BCryptPasswordEncoder().encode(password));
		user.setPhone(phone);
		user.setUserId(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null));
		user.setCreateTime(new Date());

		int num = userInfoMapper.insertSelective(user);
		if (num > 0) {
			// 建立推荐关系
			relactionServiceImpl.addUserRelaction(phone, share_user_id);
			// 创建钱包
			walletMapper.insertNewWallerByUser(phone);
		}
		return num;
	}

	@Override
	public int updatePassword(String phone, String pwd) {
		// TODO Auto-generated method stub
		return userInfoMapper.updatePassword(phone, new BCryptPasswordEncoder().encode(pwd));
	}

	@Override
	public String getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			return currentUserName;
		}
		return null;
	}

	public int updateUserInfo(String user_id, String user_name, String alipay_account, String photo) {
		// TODO Auto-generated method stub
		UserInfo user = new UserInfo();
		user.setAlipayAccount(alipay_account);
		user.setPhoto(photo);
		user.setUserId(user_id);
		user.setUserName(user_name);
		return userInfoMapper.updateByPrimaryKeySelective(user);
	}

}
