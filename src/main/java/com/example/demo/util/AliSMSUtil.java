package com.example.demo.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class AliSMSUtil {

	/**
	 * Created on 17/6/7. 短信API产品的DEMO程序,工程中包含了一个SmsDemo类，直接通过
	 * 执行main函数即可体验短信产品API功能(只需要将AK替换成开通了云通信-短信产品功能的AK即可) 工程依赖了2个jar包(存放在工程的libs目录下)
	 * 1:aliyun-java-sdk-core.jar 2:aliyun-java-sdk-dysmsapi.jar
	 *
	 * 备注:Demo工程编码采用UTF-8 国际短信发送请勿参照此DEMO
	 */

	private final static Logger logger = LoggerFactory.getLogger(AliSMSUtil.class);
	// 产品名称:云通信短信API产品,开发者无需替换
	static final String product = "Dysmsapi";
	// 产品域名,开发者无需替换
	static final String domain = "dysmsapi.aliyuncs.com";

	// TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
	static final String accessKeyId = PropertyUtil.getProperty("aliyun.sms.accessKeyId");
	static final String accessKeySecret = PropertyUtil.getProperty("aliyun.sms.accessKeySecret");
	static final String signName = PropertyUtil.getProperty("aliyun.sms.signName");
	static final String verificationCode = PropertyUtil.getProperty("aliyun.sms.verificationCode");

	/**
	 * Created on 17/6/7.信API产品的DEMO程序,工程中包含了一个SmsDemo类，直接通过 短
	 * 执行main函数即可体验短信产品API功能(只需要将AK替换成开通了云通信-短信产品功能的AK即可) 工程依赖了2个jar包(存放在工程的libs目录下)
	 * 1:aliyun-java-sdk-core.jar 2:aliyun-java-sdk-dysmsapi.jar
	 * <p>
	 * 备注:Demo工程编码采用UTF-8 国际短信发送请勿参照此DEMO
	 */

	public static SendSmsResponse sendSmsCode(String mobile, String code) throws ClientException {

		// 可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		// 初始化acsClient,暂不支持region化
		DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
		IAcsClient acsClient = new DefaultAcsClient(profile);

		SendSmsRequest request = new SendSmsRequest();
		// request.setProtocol(ProtocolType.HTTPS);
		request.setMethod(MethodType.POST);
		request.putQueryParameter("PhoneNumbers", mobile);
		request.putQueryParameter("SignName", signName);
		request.putQueryParameter("TemplateCode", verificationCode);
		request.putQueryParameter("TemplateParam", "{\"code\":'" + code + "'}");
		SendSmsResponse sendSmsResponse = null;
		try {
			// 请求失败这里会抛ClientException异常
			sendSmsResponse = acsClient.getAcsResponse(request);
			if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
				// 请求成功
			}
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return sendSmsResponse;
	}

	public static String sendSmsByPost(String phone, String code) {
		// 短信下发
		String sendUrl = "http://smssh1.253.com/msg/send/json";
		Map<String, String> map = new HashMap<String, String>();
		map.put("account", "N5018613");// API账号
		map.put("password", "b547XdcgZ");// API密码
		map.put("msg", "你登录验证码是{" + code + "}，请勿泄露。【睿赢】");// 短信内容
		map.put("phone", phone);// 手机号
		map.put("report", "true");// 是否需要状态报告
		map.put("extend", "186211");// 自定义扩展码
		JSONObject js = (JSONObject) JSONObject.toJSON(map);
		

		URL url = null;
		try {
			url = new URL(sendUrl);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setConnectTimeout(10000);
			httpURLConnection.setReadTimeout(10000);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type", "application/json");
			httpURLConnection.connect();
			OutputStream os = httpURLConnection.getOutputStream();
			os.write(js.toString().getBytes("UTF-8"));
			os.flush();
			StringBuilder sb = new StringBuilder();
			int httpRspCode = httpURLConnection.getResponseCode();
			if (httpRspCode == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();
				return sb.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
