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

import net.mall.Page;
import net.mall.Pageable;
import net.mall.dao.DefaultFreightConfigDao;
import net.mall.entity.Area;
import net.mall.entity.DefaultFreightConfig;
import net.mall.entity.ShippingMethod;
import net.mall.entity.Store;
import net.mall.service.DefaultFreightConfigService;

/**
 * Service - 默认运费配置
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class DefaultFreightConfigServiceImpl extends BaseServiceImpl<DefaultFreightConfig, Long> implements DefaultFreightConfigService {

    @Inject
    private DefaultFreightConfigDao defaultFreightConfigDao;

    @Override
    @Transactional(readOnly = true)
    public boolean exists(ShippingMethod shippingMethod, Area area) {
        return defaultFreightConfigDao.exists(shippingMethod, area);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean unique(ShippingMethod shippingMethod, Area previousArea, Area currentArea) {
        return (previousArea != null && previousArea.equals(currentArea)) || !defaultFreightConfigDao.exists(shippingMethod, currentArea);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DefaultFreightConfig> findPage(Store store, Pageable pageable) {
        return defaultFreightConfigDao.findPage(store, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public DefaultFreightConfig find(ShippingMethod shippingMethod, Store store) {
        return defaultFreightConfigDao.find(shippingMethod, store);
    }

    @Override
    public void update(DefaultFreightConfig defaultFreightConfig, Store store, ShippingMethod shippingMethod) {
        Assert.notNull(store, "[Assertion failed] - store is required; it must not be null");
        Assert.notNull(shippingMethod, "[Assertion failed] - shippingMethod is required; it must not be null");
        if (!defaultFreightConfig.isNew()) {
            super.update(defaultFreightConfig);
        } else {
            defaultFreightConfig.setStore(store);
            defaultFreightConfig.setShippingMethod(shippingMethod);
            super.save(defaultFreightConfig);
        }
    }

}