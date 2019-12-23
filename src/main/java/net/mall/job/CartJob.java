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

import net.mall.service.CartService;

/**
 * Job - 购物车
 * 
 * @author huanghy
 * @version 6.1
 */
//@Lazy(false)
//@Component
public class CartJob {

	@Inject
	private CartService cartService;

	/**
	 * 删除过期购物车
	 */
	@Scheduled(cron = "${job.cart_delete_expired.cron}")
	public void deleteExpired() {
		cartService.deleteExpired();
	}

}