package com.example.demo.timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.service.impl.WalletServiceImpl;

@Component
@EnableScheduling
public class AotuUnfreezeTimerTask {
	@Autowired
	private WalletServiceImpl walletServiceImpl;
	
	// 自动解冻金额
	@Scheduled(cron = "0 0 1 * * ?") // 每天凌晨一点执行
	private void UnfreezeTimerTask() {
		walletServiceImpl.aotuUnfreeze();
	}
}
