package com.example.demo.util.generator;

import org.springframework.web.context.request.ServletWebRequest;

import com.example.demo.entity.ValidateCode;

public interface ValidateCodeGenerator {

    ValidateCode generate(ServletWebRequest request);

}