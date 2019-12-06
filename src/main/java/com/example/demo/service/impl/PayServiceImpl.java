package com.example.demo.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.example.demo.config.AliPayConfig;
import com.example.demo.config.WxPayConfig;
import com.example.demo.entity.Transfers;
import com.example.demo.service.PayService;
import com.example.demo.util.DynamicCodeUtil;
import com.example.demo.util.HttpsUtil;
import com.example.demo.util.PayUtil;
import com.example.demo.util.PropertyUtil;

@Service("PayServiceImpl")
public class PayServiceImpl implements PayService {
	@Override
	public JSONObject createAlipay(String order_no, BigDecimal amount, String alipayNotify, String subject,
			String wap_name, HttpServletResponse response, HttpServletRequest request) throws Exception {
		String form = "";
		//ModelAndView modelAndView = new ModelAndView();
		JSONObject result = new JSONObject();
		// 生成一笔预付订单流水
		String trad_no = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_NUM_UPPER, 16, null);// 订单流水号

		try {
			// 初始化客户端
			AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.alipay_url, AliPayConfig.alipay_appid,
					AliPayConfig.alipay_app_private_key, AliPayConfig.alipay_format, AliPayConfig.alipay_charset,
					AliPayConfig.alipay_app_public_key, AliPayConfig.alipay_sign_type);
			// 创建API对应的request
			AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
			alipayRequest.setReturnUrl("");// 回退到订单列表页面
			alipayRequest.setNotifyUrl(PropertyUtil.getProperty("host") + alipayNotify);// 在公共参数中设置回跳和通知地址
			alipayRequest.setBizContent("{" + "    \"out_trade_no\":\"" + trad_no + "\","
					+ "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," + "    \"total_amount\":" + amount.toString()
					+ ","
//		            + "    \"total_amount\":\"0.01\"," 
					+ "    \"subject\":\"" + wap_name + "\"," + "    \"body\":\"" + subject + "\","
					+ "    \"passback_params\":\"" + order_no + "\"" + "  }");// 填充业务参数
			form = alipayClient.pageExecute(alipayRequest).getBody(); // 调用SDK生成表单
		} catch (Exception e) {
			e.printStackTrace();
			String sOut = "";
			StackTraceElement[] trace = e.getStackTrace();
			for (StackTraceElement s : trace) {
				sOut += "\tat " + s + "\r\n";
			}
			result.put("msg", sOut + "alipay_url:" + AliPayConfig.alipay_url);
			result.put("status", "fail");
			return result;
		}
		response.setContentType("text/html;charset=" + AliPayConfig.alipay_charset);
		response.getWriter().write(form);// 直接将完整的表单html输出到页面
		response.getWriter().flush();
		response.getWriter().close();
		result.put("status", "success");
		return result;
	}

	@Override
	public  JSONObject createWxPay(String order_no, BigDecimal amount, String wxPayNotify, String subject,
			String wap_name, HttpServletResponse response, HttpServletRequest request) throws Exception {
		String APPID = WxPayConfig.wechat_app_id;
		String MERID = WxPayConfig.wechat_mch_id;
		String SIGNKEY = WxPayConfig.wechat_key;
		String shop_host = PropertyUtil.getProperty("shop_host");
		String spbill_create_ip = PayUtil.getIp(request);// 生产
		System.out.println("spbill_create_ip=" + spbill_create_ip);
		// String spbill_create_ip = "";//测试地址，也就是本地真是ip，用于本地测试用
		String scene_info = "{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"" + shop_host + "\",\"wap_name\": \""
				+ wap_name + "\"}}";// 我这里是网页入口，app入口参考文档的安卓和ios写法
		String tradeType = "MWEB";// H5支付标记
		JSONObject result = new JSONObject();
		String total_amount = String.valueOf(amount.multiply(new BigDecimal(100)));// 支付金额
		// 金额转化为分为单位 微信支付以分为单位
		String out_trade_no = order_no;
		// 随机数
		String nonce_str = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_NUM_UPPER, 32, null);
		// 签名数据
		StringBuilder sb = new StringBuilder();
		sb.append("appid=" + APPID);
		sb.append("&body=" + subject);// 支付主题
		sb.append("&mch_id=" + MERID);
		sb.append("&nonce_str=" + nonce_str);
		sb.append("&notify_url=" + wxPayNotify);
		sb.append("&out_trade_no=" + out_trade_no);
		sb.append("&scene_info=" + scene_info);
		sb.append("&sign_type=" + "MD5");// 虽然官方文档不是必须参数，但是不送有时候会验签失败
		sb.append("&spbill_create_ip=" + spbill_create_ip);
		sb.append("&total_fee=" + total_amount);
		sb.append("&trade_type=" + tradeType);
		sb.append("&key=" + SIGNKEY);
		System.out.println("sb=" + sb);
		// 签名MD5加密
		String sign = PayUtil.MD5(sb.toString());
		System.out.println("sign=" + sign);
		// log.info("签名数据:" + sign);
		// 封装xml报文
		String xml = "<xml>" + "<appid>" + APPID + "</appid>" + "<mch_id>" + MERID + "</mch_id>" + "<nonce_str>"
				+ nonce_str + "</nonce_str>" + "<sign>" + sign + "</sign>" + "<body>" + subject + "</body>" + //
				"<out_trade_no>" + out_trade_no + "</out_trade_no>" + "<total_fee>" + total_amount + "</total_fee>" + //
				"<trade_type>" + tradeType + "</trade_type>" + "<notify_url>" + wxPayNotify + "</notify_url>"
				+ "<sign_type>MD5</sign_type>" + "<scene_info>" + scene_info + "</scene_info>" + "<spbill_create_ip>"
				+ spbill_create_ip + "</spbill_create_ip>" + "</xml>";

		String createOrderURL = WxPayConfig.wechat_pay_url;// 微信统一下单接口
		String mweb_url = "";
		Map<String, String> map = new HashMap<String, String>();
		try {
			// 预下单 获取接口地址
			String pay_result = PayUtil.httpRequest(createOrderURL, "POST", xml);
			map = PayUtil.xmlToMap(pay_result);
			String return_code = (String) map.get("return_code");
			String return_msg = (String) map.get("return_msg");
			if ("SUCCESS".equals(return_code) && "OK".equals(return_msg)) {
				mweb_url = (String) map.get("mweb_url");// 调微信支付接口地址
			} else {
				result.put("msg", "统一支付接口获取预支付订单出错");
				result.put("status", "fail");
				//modelAndView.addAllObjects(result);
				//modelAndView.setViewName("");// 失败页面
				return result;
			}
		} catch (Exception e) {
			result.put("msg", "统一支付接口获取预支付订单出错");
			result.put("status", "fail");
			//modelAndView.addAllObjects(result);
			//modelAndView.setViewName("");// 失败页面
			return result;
		}
		result.put("mwebUrl", mweb_url);

		// 添加微信支付记录日志等操作

		result.put("msg", "success");
		result.put("status", "success");
		//modelAndView.addAllObjects(result);
		//modelAndView.setViewName("");// 成功页面
		return result;
	}

	public Map<String, String> alipay2User(String bizNo, String amount, String account, String userName) {
		Map<String, String> resultMap = new HashMap<String, String>();
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.alipay_to_user_url, AliPayConfig.alipay_appid,
				AliPayConfig.alipay_app_private_key, AliPayConfig.alipay_format, AliPayConfig.alipay_charset,
				AliPayConfig.alipay_app_public_key, AliPayConfig.alipay_sign_type);
		AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
		request.setBizContent("{" + "\"out_biz_no\":\"" + bizNo + "\"," + "\"trans_amount\":" + amount + ","
				+ "\"product_code\":\"STD_RED_PACKET\"," + "\"biz_scene\":\"DIRECT_TRANSFER\","
				+ "\"order_title\":\"余额提现\","
				// + "\"original_order_id\":\"20190620110075000006640000063056\","
				+ "\"payee_info\":{" + "\"identity\":\"" + account + "\"," + "\"identity_type\":\"ALIPAY_LOGON_ID\","
				+ "\"name\":\"抖咖联盟\"" + "    }," + "\"remark\":\"余额提现\","
				+ "\"business_params\":\"{\\\"sub_biz_scene\\\":\\\"REDPACKET\\\"}\"" + "  }");
		AlipayFundTransUniTransferResponse response;
		try {
			response = alipayClient.execute(request);
			if (response.isSuccess()) {
				System.out.println("调用成功");
				resultMap.put("result_code", "SUCCESS");

			} else {
				System.out.println("调用失败");
				resultMap.put("result_code", "ERROR");
			}
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultMap;
	}

	@Override
	public Map<String, String> weixinWithdraw(String openId, String ip, String partner_trade_no, String money,
			String desc) {
		if (StringUtils.isNotBlank(money) && StringUtils.isNotBlank(ip) && StringUtils.isNotBlank(openId)) {
			// 参数组
			String url = "";
			String appid = WxPayConfig.wechat_app_id;
			String mch_id = WxPayConfig.wechat_mch_id;
			String nonce_str = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
			// 是否校验用户姓名 NO_CHECK：不校验真实姓名 FORCE_CHECK：强校验真实姓名
			String checkName = "NO_CHECK";
			// 等待确认转账金额,ip,openid的来源
			Integer amount = Integer.valueOf(money);
			String spbill_create_ip = ip;
			// String partner_trade_no =
			// DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
			// 描述
			// String desc = "健康由我医师助手提现" + amount / 100 + "元";
			// 参数：开始生成第一次签名

			// 构造签名的map
			SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
			parameters.put("appid", appid);
			parameters.put("mch_id", mch_id);
			parameters.put("partner_trade_no", partner_trade_no);
			parameters.put("nonce_str", nonce_str);
			parameters.put("openId", openId);
			parameters.put("checkName", checkName);
			parameters.put("amount", amount);
			parameters.put("spbill_create_ip", spbill_create_ip);
			parameters.put("desc", desc);
			String sign = PayUtil.createSign("UTF-8", parameters);
			Transfers transfers = new Transfers();
			transfers.setAmount(amount);
			transfers.setCheck_name(checkName);
			transfers.setDesc(desc);
			transfers.setMch_appid(appid);
			transfers.setMchid(mch_id);
			transfers.setNonce_str(nonce_str);
			transfers.setOpenid(openId);
			transfers.setPartner_trade_no(partner_trade_no);
			transfers.setSign(sign);
			transfers.setSpbill_create_ip(spbill_create_ip);
			String xmlInfo = PayUtil.transferXml(transfers);
			try {
				CloseableHttpResponse response = HttpsUtil.Post(url, xmlInfo, true);
				String transfersXml = EntityUtils.toString(response.getEntity(), "utf-8");
				Map<String, String> transferMap = PayUtil.parseRefundXml(transfersXml);
				return transferMap;
//				if (transferMap.size() > 0) {
//					if (transferMap.get("result_code").equals("SUCCESS") && transferMap.get("return_code").equals("SUCCESS")) {
//						// 成功需要进行的逻辑操作，
//						
//						return "SUCCESS";
//					}else {
//						return transferMap.get("return_msg");
//					}
//				}
//				System.out.println("成功");
			} catch (Exception e) {
				// log.error(e.getMessage());
				// throw new Exception(this, "企业付款异常" + e.getMessage());
//				Map<String, String> map = new HashMap<String, String>();
//				map.put("result_code", "ERROR");
//				map.put(key, value)
//				return "企业付款异常:" + e.getMessage();
				e.printStackTrace();
			}
		} else {
			System.out.println("失败");
		}
		return null;
	}

	@Override
	public Map<String, String> wxRefund(String refundid, String orderId, String total_fee, String refund_fee,
			String refund_desc) {
		String appid = PropertyUtil.getProperty("wx.appid");
		String mch_key = PropertyUtil.getProperty("wx.mch_key");
		String mch_id = PropertyUtil.getProperty("wx.mch_id");
		String ssl_path = PropertyUtil.getProperty("weixin.ssl.path");
		Map<String, String> result = new HashMap<String, String>();
		if (StringUtils.isBlank(refundid)) {
			refundid = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
		}
		String nonce_str = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
		/*-----  1.生成预支付订单需要的的package数据-----*/
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("op_user_id", mch_id);
		packageParams.put("out_trade_no", orderId);
		packageParams.put("out_refund_no", refundid);
		packageParams.put("total_fee", total_fee);
		packageParams.put("refund_fee", refund_fee);
		packageParams.put("refund_desc", refund_desc);
		/*----2.根据package生成签名sign---- */
		String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		// MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
		String sign = PayUtil.sign(prestr, mch_key, "utf-8").toUpperCase();
		/*----3.拼装需要提交到微信的数据xml---- */
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>" + mch_id + "</mch_id>" + "<nonce_str>"
				+ nonce_str + "</nonce_str>" + "<op_user_id>" + mch_id + "</op_user_id>" + "<out_trade_no>" + orderId
				+ "</out_trade_no>" + "<out_refund_no>" + refundid + "</out_refund_no>" + "<refund_fee>" + refund_fee
				+ "</refund_fee>" + "<total_fee>" + total_fee + "</total_fee>" + "<refund_desc>" + refund_desc
				+ "</refund_desc>" + "<sign>" + sign + "</sign>" + "</xml>";
		try {
			/*----4.读取证书文件,这一段是直接从微信支付平台提供的demo中copy的，所以一般不需要修改---- */
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			FileInputStream instream = new FileInputStream(new File(ssl_path));
			try {
				keyStore.load(instream, mch_id.toCharArray());
			} finally {
				instream.close();
			}
			// Trust own CA and all self-signed certs
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mch_id.toCharArray()).build();
			// Allow TLSv1 protocol only
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
					null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			/*----5.发送数据到微信的退款接口---- */
			String refund_url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
			String jsonStr = PayUtil.httpRequest(refund_url, "POST", xml);
			// logger.info(jsonStr);

			Map map = PayUtil.parseXmlToMap(jsonStr);
			if ("success".equalsIgnoreCase((String) map.get("return_code"))) {
				// logger.info("退款成功");
				result.put("returncode", "ok");
				result.put("returninfo", "退款成功");
			} else {
				// logger.info("退款失败");
				result.put("returncode", "error");
				result.put("returninfo", "退款失败");
			}
		} catch (Exception e) {
			// logger.info("退款失败");
			result.put("returncode", "error");
			result.put("returninfo", "退款失败");
		}
		return result;

	}
}
