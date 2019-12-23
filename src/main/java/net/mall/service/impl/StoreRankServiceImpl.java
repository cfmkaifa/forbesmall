/*
 *
 * 
 *
 * 
 */
package net.mall.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.mall.Filter;
import net.mall.Order;
import net.mall.dao.StoreRankDao;
import net.mall.entity.StoreRank;
import net.mall.service.StoreRankService;

/**
 * Service - 店铺等级
 * 
 * @author huanghy
 * @version 6.1
 */
@Service
public class StoreRankServiceImpl extends BaseServiceImpl<StoreRank, Long> implements StoreRankService {

	@Inject
	private StoreRankDao storeRankDao;

	@Override
	@Transactional(readOnly = true)
	public boolean nameExists(String name) {
		return storeRankDao.exists("name", name);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean nameUnique(Long id, String name) {
		return storeRankDao.unique(id, "name", name);
	}

	@Override
	@Transactional(readOnly = true)
	public List<StoreRank> findList(Boolean isAllowRegister, List<Filter> filters, List<Order> orders) {
		return storeRankDao.findList(isAllowRegister, filters, orders);
	}

}