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

import net.mall.service.OrderService;

/**
 * Job - 订单
 * 
 * @author huanghy
 * @version 6.1
 */
//@Lazy(false)
//@Component
public class OrderJob {

	@Inject
	private OrderService orderService;

	/**
	 * 过期订单处理
	 */
	@Scheduled(cron = "${job.order_expired_processing.cron}")
	public void expiredProcessing() {
		orderService.expiredRefundHandle();
		orderService.undoExpiredUseCouponCode();
		orderService.undoExpiredExchangePoint();
		orderService.releaseExpiredAllocatedStock();
	}

	/**
	 * 自动收货
	 */
	@Scheduled(cron = "${job.order_automatic_receive.cron}")
	public void automaticReceive() {
		orderService.automaticReceive();
	}

}