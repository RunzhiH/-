package com.example.demo.service;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public interface PayService {
	/**
	 * 支付宝支付
	 * 
	 * @param order_no
	 * @param amount
	 * @param alipayNotify
	 * @param subject
	 * @param wap_name
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	ModelAndView createAlipay(String order_no, BigDecimal amount, String alipayNotify, String subject, String wap_name,
			HttpServletResponse response, HttpServletRequest request) throws Exception;

	/**
	 * 微信支付
	 * 
	 * @param order_no
	 * @param amount
	 * @param wxPayNotify
	 * @param subject
	 * @param wap_name
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	ModelAndView createWxPay(String order_no, BigDecimal amount, String wxPayNotify, String subject, String wap_name,
			HttpServletResponse response, HttpServletRequest request) throws Exception;

	/**
	 * 微信企业打款
	 * 
	 * @param openId
	 * @param ip
	 * @param partner_trade_no
	 * @param money
	 * @param desc
	 * @return
	 */
	Map<String, String> weixinWithdraw(String openId, String ip, String partner_trade_no, String money, String desc);

	/**
	 * 微信退款
	 * 
	 * @param refundid
	 * @param orderId
	 * @param total_fee
	 * @param refund_fee
	 * @param refund_desc
	 * @return
	 */
	Map<String, String> wxRefund(String refundid, String orderId, String total_fee, String refund_fee,
			String refund_desc);

}
