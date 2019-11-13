package com.example.demo.service;

import java.util.List;
import java.util.Map;

public interface UserService {

	List<Map<String, String>> getAllUser();

	Map<String, String> getUserInfo(String userId);

	Map<String, String> getUserByPhone(String phone);

	int updatePassword(String phone, String pwd);
	
	String getCurrentUser();
}
