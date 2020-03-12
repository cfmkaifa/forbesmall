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
import net.mall.entity.Business;
import net.mall.entity.BusinessDepositLog;

/**
 * Dao - 商家预存款记录
 *
 * @author huanghy
 * @version 6.1
 */
public interface BusinessDepositLogDao extends BaseDao<BusinessDepositLog, Long> {

    /**
     * 查找商家预存款记录分页
     *
     * @param business 商家
     * @param pageable 分页信息
     * @return 商家预存款记录分页
     */
    Page<BusinessDepositLog> findPage(Business business, Pageable pageable);

    /**
     * 查询提现总额
     *
     * @param beginDate 起始日期
     * @param endDate   结束日期
     * @return 提现总额
     */
    BigDecimal cashTotalAmount(Date beginDate, Date endDate);

}