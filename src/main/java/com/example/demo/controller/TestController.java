package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.impl.WalletServiceImpl;

@RequestMapping("/test")
@RestController
public class TestController {
	@Autowired
	private WalletServiceImpl walletServiceImpl;

	@RequestMapping("/aotuUnfreeze")
	public String aotuUnfreeze() {
		walletServiceImpl.aotuUnfreeze();
		return null;
	}
}
