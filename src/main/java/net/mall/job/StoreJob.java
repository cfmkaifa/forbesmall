/*
 *
 * 
 *
 * 
 */
package net.mall.job;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.mall.service.StoreService;

/**
 * Job - 店铺
 * 
 * @author huanghy
 * @version 6.1
 */
//@Lazy(false)
//@Component
public class StoreJob {

	@Inject
	private StoreService storeService;

	/**
	 * 过期店铺处理
	 */
	@Scheduled(cron = "${job.store_expired_processing.cron}")
	public void evictExpired() {
		storeService.expiredStoreProcessing();
	}

}