/*
 *
 *
 *
 *
 */
package net.mall.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.mall.dao.SnDao;
import net.mall.entity.OrderRefunds;
import net.mall.entity.Sn;
import net.mall.service.OrderRefundsService;

/**
 * Service - 订单退款
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class OrderRefundsServiceImpl extends BaseServiceImpl<OrderRefunds, Long> implements OrderRefundsService {

    @Inject
    private SnDao snDao;

    @Override
    @Transactional
    public OrderRefunds save(OrderRefunds orderRefunds) {
        Assert.notNull(orderRefunds, "[Assertion failed] - orderRefunds is required; it must not be null");

        orderRefunds.setSn(snDao.generate(Sn.Type.ORDER_REFUNDS));

        return super.save(orderRefunds);
    }

}