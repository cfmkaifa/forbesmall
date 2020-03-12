/*
 *
 *
 *
 *
 */
package net.mall.service.impl;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.mall.dao.OrderPaymentDao;
import net.mall.dao.SnDao;
import net.mall.entity.OrderPayment;
import net.mall.entity.Sn;
import net.mall.service.OrderPaymentService;

/**
 * Service - 订单支付
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class OrderPaymentServiceImpl extends BaseServiceImpl<OrderPayment, Long> implements OrderPaymentService {

    @Inject
    private OrderPaymentDao orderPaymentDao;
    @Inject
    private SnDao snDao;

    @Override
    @Transactional(readOnly = true)
    public OrderPayment findBySn(String sn) {
        return orderPaymentDao.find("sn", StringUtils.lowerCase(sn));
    }

    @Override
    @Transactional
    public OrderPayment save(OrderPayment orderPayment) {
        Assert.notNull(orderPayment, "[Assertion failed] - orderPayment is required; it must not be null");

        orderPayment.setSn(snDao.generate(Sn.Type.ORDER_PAYMENT));

        return super.save(orderPayment);
    }

}