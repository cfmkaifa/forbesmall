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
import net.mall.dao.DistributionCommissionDao;
import net.mall.entity.DistributionCommission;
import net.mall.entity.Distributor;
import net.mall.service.DistributionCommissionService;

/**
 * Service - 分销佣金
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class DistributionCommissionServiceImpl extends BaseServiceImpl<DistributionCommission, Long> implements DistributionCommissionService {

    @Inject
    private DistributionCommissionDao distributionCommissionDao;

    @Override
    @Transactional(readOnly = true)
    public Page<DistributionCommission> findPage(Distributor distributor, Pageable pageable) {
        return distributionCommissionDao.findPage(distributor, pageable);
    }

}