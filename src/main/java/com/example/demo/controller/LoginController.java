package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.ValidateCode;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.util.AliSMSUtil;
import com.example.demo.util.DynamicCodeUtil;
import com.example.demo.util.generator.SmsCodeGenerator;

@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	UserServiceImpl userServiceImpl;
	@Autowired
	private SmsCodeGenerator smsCodeGenerator;

	public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	@RequestMapping(value = "/registerByPhone")
	public Map<String, Object> register(HttpServletRequest request) {
		Map<String, Object> msg = new HashMap<String, Object>();
		String phone =request.getParameter("phone");
		String code =request.getParameter("code");
		String share_user_id =request.getParameter("share_user_id");
		String password =request.getParameter("password");
		HttpSession httpSession = request.getSession();
		String Vcode = String.valueOf(httpSession.getAttribute(phone));
		if (!code.equals(Vcode)) {
			msg.put("message", "验证码错误");
			msg.put("status", 300);
			return msg;
		} else {
			httpSession.removeAttribute(phone);
		}

		Map<String, String> user = userServiceImpl.getUserByPhone(phone);
		if (user != null) {
			msg.put("message", "改手机号已注册");
			msg.put("status", 501);
			return msg;
		}
		int num = userServiceImpl.addUser(phone, password, share_user_id);
		if (num > 0) {
			 msg.put("message", "注册成功");
			 msg.put("status", 200);

		} else {
			msg.put("message", "注册失败");
			msg.put("status", 500);
		}
		return msg;
	}

	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public Map<String, Object> login(@RequestParam("phone") String phone, @RequestParam("password") String password) {
		Map<String, Object> msg = new HashMap<String, Object>();
		Map<String, String> user = userServiceImpl.getUserByPhone(phone);
		if (MapUtils.isEmpty(user)) {
			msg.put("message", "未注册");
			msg.put("status", 500);
			return msg;
		}
		String db_pwd = user.get("password");
		if (db_pwd.equals(password)) {

			msg.put("message", "登录成功");
			msg.put("status", 200);
		} else {
			msg.put("message", "密码错误");
			msg.put("status", 501);
			return msg;
		}
		return msg;
	}

	@RequestMapping("loginByPhoneCode")
	public Map<String, Object> loginByPhoneCode(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> msg = new HashMap<String, Object>();
		String phone = request.getParameter("phone");
		String code = request.getParameter("code");
		HttpSession httpSession = request.getSession();
		String Vcode = String.valueOf(httpSession.getAttribute(phone));
		if (!code.equals(Vcode)) {
			msg.put("message", "验证码错误");
			msg.put("status", 300);
			return msg;
		} else {
			httpSession.removeAttribute(phone);
		}
		// 登录

		return msg;
	}

	@RequestMapping("/sendSMSByLogin")
	public Map<String, Object> sendSMSByLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		String phone = request.getParameter("phone");
		ValidateCode smsCode = smsCodeGenerator.generate(new ServletWebRequest(request));
		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, smsCode);
		String sendSmsResponse = AliSMSUtil.sendSmsByPost(phone, smsCode.getCode());
		Map<String, Object> msg = new HashMap<String, Object>();
		JSONObject result = JSONObject.parseObject(sendSmsResponse);
		if ("0".equals(result.getString("code"))) {
			msg.put("status", "200");
			msg.put("message", "发送短信成功！");
		} else {
			msg.put("status", result.getString("code"));
			msg.put("message", result.getString("errorMsg"));
		}
		return msg;
	}

	@RequestMapping("/sendSMS")
	public Map<String, Object> sendSMS(String phone, HttpServletRequest request) {
		Map<String, Object> msg = new HashMap<String, Object>();

		if (phone == null || "".equals(phone)) {
			msg.put("status", "500");
			msg.put("message", "手机号为空！");
			return msg;
		}
		String sendSmsResponse = "";
		String code = "";
		HttpSession httpSession = request.getSession();

		if (httpSession.getAttribute(phone) == null) {

			code = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_NUM_ONLY, 6, null);
			// 加入缓存
			httpSession.setAttribute(phone, code);
			System.out.println(httpSession);
		} else {
			code = String.valueOf(httpSession.getAttribute(phone));// 根据key获取缓存中的val

			if (StringUtils.isBlank(code)) {
				code = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_NUM_ONLY, 6, null);
				httpSession.setAttribute(phone, code);
			}
		}
		System.out.println(httpSession);
		sendSmsResponse = AliSMSUtil.sendSmsByPost(phone, code);

		JSONObject result = JSONObject.parseObject(sendSmsResponse);
		if ("0".equals(result.getString("code"))) {
			msg.put("status", "200");
			msg.put("message", "发送短信成功！");
		} else {
			msg.put("status", result.getString("code"));
			msg.put("message", result.getString("errorMsg"));
		}
		return msg;
	}

	@RequestMapping("updatePassword")
	public Map<String, Object> updatePassword(HttpServletRequest request) {
		String code = request.getParameter("code");
		String phone = request.getParameter("phone");
		String pwd = request.getParameter("password");
		Map<String, Object> msg = new HashMap<String, Object>();
		HttpSession httpSession = request.getSession();
		String Vcode = String.valueOf(httpSession.getAttribute(phone));
		if (!code.equals(Vcode)) {
			msg.put("message", "验证码错误");
			msg.put("status", 300);
			return msg;
		} else {
			httpSession.removeAttribute(phone);
		}
		int num = userServiceImpl.updatePassword(phone, pwd);
		if (num > 0) {
			msg.put("status", 200);
		} else {
			msg.put("status", 500);
		}
		return msg;
	}

	@RequestMapping("redisterUpdatePassword")
	public Map<String, Object> redisterUpdatePassword(HttpServletRequest request) {
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		int num = userServiceImpl.updatePassword(phone, password);
		Map<String, Object> msg = new HashMap<String, Object>();
		if (num > 0) {
			msg.put("status", 200);
		} else {
			msg.put("status", 500);
		}
		return msg;
	}
}
