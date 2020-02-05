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

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Job - 购物车
 * 
 * @author huanghy
 * @version 6.1
 */
@Component
@Lazy(false)
public class CartJob {

	private ReentrantReadWriteLock  CART_LOCK = new ReentrantReadWriteLock();

	@Inject
	private CartService cartService;

	/**
	 * 删除过期购物车
	 */
	@Scheduled(cron = "${job.cart_delete_expired.cron}")
	public void deleteExpired() {
		if(CART_LOCK.writeLock().tryLock()){
			cartService.deleteExpired();
			CART_LOCK.writeLock().unlock();
		}
	}

}