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

import net.mall.dao.AftersalesSettingDao;
import net.mall.entity.AftersalesSetting;
import net.mall.entity.Store;
import net.mall.service.AftersalesSettingService;

/**
 * Service - 售后设置
 * 
 * @author huanghy
 * @version 6.1
 */
@Service
public class AftersalesSettingServiceImpl extends BaseServiceImpl<AftersalesSetting, Long> implements AftersalesSettingService {

	@Inject
	private AftersalesSettingDao aftersalesSettingDao;

	@Override
	@Transactional(readOnly = true)
	public AftersalesSetting findByStore(Store store) {
		Assert.notNull(store, "[Assertion failed] - store is required; it must not be null");

		return aftersalesSettingDao.find("store", store);
	}

}