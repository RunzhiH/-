package com.example.demo.util.generator;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.example.demo.entity.ValidateCode;
import com.example.demo.util.DynamicCodeUtil;

//短信验证码生成器
@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

 @Override
 public ValidateCode generate(ServletWebRequest request) {
     String code = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_NUM_ONLY, 6, null);
     return new ValidateCode(code, 15*60*1000);
 }

}