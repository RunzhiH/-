package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.example.demo.handler.MyAuthenctiationFailureHandler;
import com.example.demo.handler.MyAuthenctiationSuccessHandler;
import com.example.demo.util.filter.ValidateCodeFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	MyAuthenctiationSuccessHandler successHandler;
	@Autowired
	MyAuthenctiationFailureHandler failureHandler;
	@Autowired
	SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
	@Autowired
	private AuthenticationFailureHandler coreqiAuthenticationFailureHandler;

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
		validateCodeFilter.setAuthenticationFailureHandler(coreqiAuthenticationFailureHandler);

		ValidateCodeFilter smsCodeFilter = new ValidateCodeFilter();

		http.authorizeRequests()// 定义哪些URL需要被保护、哪些不需要被保护
				.antMatchers("/login.html", "/login/*", "/loginByphone.html", "/login", "/code/*", "/test/**",
						"/wx/css/**", "/wx/js/**", "/wx/image/**", "/wx/html/login.html", "/wx/html/loginBypwd.html","/wx/html/resiter.html","/wx/html/forgotPwd.html","/wx/html/suerpwd.html",
						"/Plugin/**")
				.permitAll().anyRequest()// 任何请求,登录后可以访问
				
				.authenticated().and().addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class) // 加载用户名密码过滤器的前面
				.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class) // 加载用户名密码过滤器的前面
				// 定义哪些URL需要被保护、哪些不需要被保护
				.formLogin().loginPage("/wx/html/login.html") // 定义当需要用户登录时候，转到的登录页面。
				.loginProcessingUrl("/login/doLogin").successHandler(successHandler) // 自定义登录成功处理
				.failureHandler(failureHandler).permitAll()
				// 以下短信登录认证的配置
				.and().apply(smsCodeAuthenticationSecurityConfig).and().logout().logoutSuccessUrl("/wx/html/login.html").logoutUrl("/login/loginOut")
				.and().csrf().disable();

	}

	// BrowerSecurityConfig
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

	/**
	 * 配置TokenRepository
	 * 
	 * @return
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(dataSource);
		// 初始化记住我的数据库表，建议通过看源码直接创建出来
		// jdbcTokenRepository.setCreateTableOnStartup(true);
		return jdbcTokenRepository;
	}

}