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

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Job - 订单
 *
 * @author huanghy
 * @version 6.1
 */
/*@Lazy(false)
@Component*/
public class OrderJob {

    private ReentrantReadWriteLock ORDER_LOCK = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock RECEIVE_LOCK = new ReentrantReadWriteLock();
    @Inject
    private OrderService orderService;

    /**
     * 过期订单处理
     */
    @Scheduled(cron = "${job.order_expired_processing.cron}")
    public void expiredProcessing() {
        if (ORDER_LOCK.writeLock().tryLock()) {
            orderService.expiredRefundHandle();
            orderService.undoExpiredUseCouponCode();
            orderService.undoExpiredExchangePoint();
            orderService.releaseExpiredAllocatedStock();
            ORDER_LOCK.writeLock().unlock();
        }
    }

    /**
     * 自动收货
     */
    @Scheduled(cron = "${job.order_automatic_receive.cron}")
    public void automaticReceive() {
        if (RECEIVE_LOCK.writeLock().tryLock()) {
            orderService.automaticReceive();
            RECEIVE_LOCK.writeLock().unlock();
        }

    }

}