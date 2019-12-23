/*
 *
 * 
 *
 * 
 */
package net.mall.service;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.StockLog;
import net.mall.entity.Store;

/**
 * Service - 库存记录
 * 
 * @author huanghy
 * @version 6.1
 */
public interface StockLogService extends BaseService<StockLog, Long> {

	/**
	 * 查找库存记录分页
	 * 
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页
	 * @return 库存记录分页
	 */
	Page<StockLog> findPage(Store store, Pageable pageable);

}