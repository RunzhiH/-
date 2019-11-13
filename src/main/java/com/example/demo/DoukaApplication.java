package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
@MapperScan("com.example.demo.dao")
@ComponentScan(basePackages = {"com.example.*"})
public class DoukaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoukaApplication.class, args);
	}

}