/*
 *
 * 
 *
 * 
 */
package net.mall.service;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.Business;
import net.mall.entity.BusinessDepositLog;

/**
 * Service - 商家预存款记录
 * 
 * @author huanghy
 * @version 6.1
 */
public interface BusinessDepositLogService extends BaseService<BusinessDepositLog, Long> {

	/**
	 * 查找商家预存款记录分页
	 * 
	 * @param business
	 *            商家
	 * @param pageable
	 *            分页信息
	 * @return 商家预存款记录分页
	 */
	Page<BusinessDepositLog> findPage(Business business, Pageable pageable);

}