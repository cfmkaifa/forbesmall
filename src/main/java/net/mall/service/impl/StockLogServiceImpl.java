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

import net.mall.Page;
import net.mall.Pageable;
import net.mall.dao.StockLogDao;
import net.mall.entity.StockLog;
import net.mall.entity.Store;
import net.mall.service.StockLogService;

/**
 * Service - 库存记录
 * 
 * @author huanghy
 * @version 6.1
 */
@Service
public class StockLogServiceImpl extends BaseServiceImpl<StockLog, Long> implements StockLogService {

	@Inject
	private StockLogDao stockLogDao;

	@Override
	@Transactional(readOnly = true)
	public Page<StockLog> findPage(Store store, Pageable pageable) {
		return stockLogDao.findPage(store, pageable);
	}

}