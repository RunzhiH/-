package com.example.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrowserSecurityController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	// 原请求信息的缓存及恢复
	private RequestCache requestCache = new HttpSessionRequestCache();

	// 用于重定向
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	/**
	 * 当需要身份认证的时候，跳转过来
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/authentication/require")
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public Map<String, Object> requireAuthenication(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		Map<String, Object> msg= new HashMap<String, Object>();
		if (savedRequest != null) {
			String targetUrl = savedRequest.getRedirectUrl();
			logger.info("引发跳转的请求是:" + targetUrl);
			if(targetUrl.indexOf("wx")>0) {
				redirectStrategy.sendRedirect(request, response, "/wx/login.html");
			}else {
				if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
					redirectStrategy.sendRedirect(request, response, "/login.html");
				}
			}
		}
		msg.put("message", "访问的服务需要身份认证，请引导用户到登录页");
		return msg;
	}
}