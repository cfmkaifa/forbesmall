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
import net.mall.dao.AreaFreightConfigDao;
import net.mall.entity.Area;
import net.mall.entity.AreaFreightConfig;
import net.mall.entity.ShippingMethod;
import net.mall.entity.Store;
import net.mall.service.AreaFreightConfigService;

/**
 * Service - 地区运费配置
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class AreaFreightConfigServiceImpl extends BaseServiceImpl<AreaFreightConfig, Long> implements AreaFreightConfigService {

    @Inject
    private AreaFreightConfigDao areaFreightConfigDao;

    @Override
    @Transactional(readOnly = true)
    public boolean exists(ShippingMethod shippingMethod, Store store, Area area) {
        return areaFreightConfigDao.exists(shippingMethod, store, area);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean unique(Long id, ShippingMethod shippingMethod, Store store, Area area) {
        return areaFreightConfigDao.unique(id, shippingMethod, store, area);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AreaFreightConfig> findPage(ShippingMethod shippingMethod, Store store, Pageable pageable) {
        return areaFreightConfigDao.findPage(shippingMethod, store, pageable);
    }

}