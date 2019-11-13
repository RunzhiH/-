package com.example.demo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.util.QrCodeUtil;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

@RequestMapping("/qrCode")
@RestController
public class QRCodeController {
	@RequestMapping("/getQRCode")
	public void getQRCode(HttpServletRequest request, HttpServletResponse response) {
		String url = request.getParameter("url");

		// String url = "http://www.baidu.com";
		// 配置生成路径
		// String path = "d:测试二维码/photo/";
		// 生成文件名称
		BitMatrix qRcodeImg = QrCodeUtil.createQrCode(url, response);
		try {
			MatrixToImageWriter.writeToStream(qRcodeImg, "png", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
