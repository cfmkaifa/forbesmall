/*
 *
 *
 *
 *
 */
package net.mall.dao;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.DistributionCommission;
import net.mall.entity.Distributor;

/**
 * Dao - 分销佣金
 *
 * @author huanghy
 * @version 6.1
 */
public interface DistributionCommissionDao extends BaseDao<DistributionCommission, Long> {

    /**
     * 查找分销佣金记录分页
     *
     * @param distributor 分销员
     * @param pageable    分页信息
     * @return 分销佣金记录分页
     */
    Page<DistributionCommission> findPage(Distributor distributor, Pageable pageable);

}