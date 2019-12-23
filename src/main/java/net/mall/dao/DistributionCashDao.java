/*
 *
 * 
 *
 * 
 */
package net.mall.dao;

import java.math.BigDecimal;
import java.util.Date;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.DistributionCash;
import net.mall.entity.Distributor;

/**
 * Dao - 分销提现
 * 
 * @author huanghy
 * @version 6.1
 */
public interface DistributionCashDao extends BaseDao<DistributionCash, Long> {

	/**
	 * 查找分销提现记录分页
	 * 
	 * @param status
	 *            状态
	 * @param bank
	 *            收款银行
	 * @param account
	 *            收款账号
	 * @param accountHolder
	 *            开户人
	 * @param distributor
	 *            分销员
	 * @param pageable
	 *            分页信息
	 * @return 分销提现记录分页
	 */
	Page<DistributionCash> findPage(DistributionCash.Status status, String bank, String account, String accountHolder, Distributor distributor, Pageable pageable);

	/**
	 * 查询提现总额
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 提现总额
	 */
	BigDecimal cashTotalAmount(Date beginDate, Date endDate);

	/**
	 * 查找分销提现数量
	 * 
	 * @param status
	 *            状态
	 * @param bank
	 *            收款银行
	 * @param account
	 *            收款账号
	 * @param accountHolder
	 *            开户人
	 * @param distributor
	 *            分销员
	 * @return 分销提现数量
	 */
	Long count(DistributionCash.Status status, String bank, String account, String accountHolder, Distributor distributor);

}