/*
 *
 * 
 *
 * 
 */
package net.mall.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.type.TypeReference;

import net.mall.Setting;
import net.mall.dao.OrderShippingDao;
import net.mall.dao.SnDao;
import net.mall.entity.OrderShipping;
import net.mall.entity.Sn;
import net.mall.service.OrderShippingService;
import net.mall.util.JsonUtils;
import net.mall.util.SystemUtils;
import net.mall.util.WebUtils;

/**
 * Service - 订单发货
 * 
 * @author huanghy
 * @version 6.1
 */
@Service
public class OrderShippingServiceImpl extends BaseServiceImpl<OrderShipping, Long> implements OrderShippingService {

	@Inject
	private OrderShippingDao orderShippingDao;
	@Inject
	private SnDao snDao;

	@Override
	@Transactional(readOnly = true)
	public OrderShipping findBySn(String sn) {
		return orderShippingDao.find("sn", StringUtils.lowerCase(sn));
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable("transitSteps")
	public List<Map<String, String>> getTransitSteps(OrderShipping orderShipping) {
		Assert.notNull(orderShipping, "[Assertion failed] - orderShipping is required; it must not be null");

		return getTransitSteps(orderShipping.getDeliveryCorpCode(), orderShipping.getTrackingNo());
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	@Cacheable("transitSteps")
	public List<Map<String, String>> getTransitSteps(String deliveryCorpCode, String trackingNo) {
		Setting setting = SystemUtils.getSetting();
		String kuaidi100Customer = setting.getKuaidi100Customer();
		String kuaidi100Key = setting.getKuaidi100Key();
		if (StringUtils.isEmpty(kuaidi100Customer) || StringUtils.isEmpty(kuaidi100Key) || StringUtils.isEmpty(deliveryCorpCode) || StringUtils.isEmpty(trackingNo)) {
			return Collections.emptyList();
		}

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("com", deliveryCorpCode);
		paramMap.put("num", trackingNo);
		String param = JsonUtils.toJson(paramMap);
		String sign = DigestUtils.md5Hex(param + kuaidi100Key + kuaidi100Customer).toUpperCase();

		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("customer", kuaidi100Customer);
		parameterMap.put("sign", sign);
		parameterMap.put("param", param);
		String content = WebUtils.post("http://poll.kuaidi100.com/poll/query.do", parameterMap);
		Map<String, Object> data = JsonUtils.toObject(content, new TypeReference<Map<String, Object>>() {
		});

		if (!StringUtils.equals(String.valueOf(data.get("message")), "ok")) {
			return Collections.emptyList();
		}
		return (List<Map<String, String>>) data.get("data");
	}

	@Override
	@Transactional
	public OrderShipping save(OrderShipping orderShipping) {
		Assert.notNull(orderShipping, "[Assertion failed] - orderShipping is required; it must not be null");

		orderShipping.setSn(snDao.generate(Sn.Type.ORDER_SHIPPING));

		return super.save(orderShipping);
	}

}