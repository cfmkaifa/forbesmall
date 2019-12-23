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
import net.mall.entity.OrderReturns;
import net.mall.entity.Sn;
import net.mall.service.OrderReturnsService;

/**
 * Service - 订单退货
 * 
 * @author huanghy
 * @version 6.1
 */
@Service
public class OrderReturnsServiceImpl extends BaseServiceImpl<OrderReturns, Long> implements OrderReturnsService {

	@Inject
	private SnDao snDao;

	@Override
	@Transactional
	public OrderReturns save(OrderReturns orderReturns) {
		Assert.notNull(orderReturns, "[Assertion failed] - orderReturns is required; it must not be null");

		orderReturns.setSn(snDao.generate(Sn.Type.ORDER_RETURNS));

		return super.save(orderReturns);
	}

}