/*
 *
 *
 *
 *
 */
package net.mall.service.impl;

import java.util.Date;

import javax.inject.Inject;
import javax.persistence.LockModeType;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.mall.dao.StorePluginStatusDao;
import net.mall.entity.Store;
import net.mall.entity.StorePluginStatus;
import net.mall.service.StorePluginStatusService;

/**
 * Service - 店铺插件状态
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class StorePluginStatusServiceImpl extends BaseServiceImpl<StorePluginStatus, Long> implements StorePluginStatusService {

    @Inject
    private StorePluginStatusDao storePluginStatusDao;

    @Override
    @Transactional(readOnly = true)
    public StorePluginStatus find(Store store, String pluginId) {
        return storePluginStatusDao.find(store, pluginId);
    }

    @Override
    public void addPluginEndDays(StorePluginStatus storePluginStatus, int amount) {
        Assert.notNull(storePluginStatus, "[Assertion failed] - storePluginStatus is required; it must not be null");

        if (amount == 0) {
            return;
        }

        if (!LockModeType.PESSIMISTIC_WRITE.equals(storePluginStatusDao.getLockMode(storePluginStatus))) {
            storePluginStatusDao.flush();
            storePluginStatusDao.refresh(storePluginStatus, LockModeType.PESSIMISTIC_WRITE);
        }

        Date now = new Date();
        Date currentEndDate = storePluginStatus.getEndDate() != null ? storePluginStatus.getEndDate() : now;
        if (amount > 0) {
            storePluginStatus.setEndDate(DateUtils.addDays(currentEndDate.after(now) ? currentEndDate : now, amount));
        } else {
            storePluginStatus.setEndDate(DateUtils.addDays(currentEndDate, amount));
        }
        storePluginStatusDao.flush();
    }

    public StorePluginStatus create(Store store, String pluginId, Integer amount) {
        Assert.notNull(store, "[Assertion failed] - store is required; it must not be null");
        Assert.hasText(pluginId, "[Assertion failed] - pluginId must have text; it must not be null, empty, or blank");

        StorePluginStatus storePluginStatus = new StorePluginStatus();
        storePluginStatus.setEndDate(amount != null ? DateUtils.addDays(new Date(), amount) : null);
        storePluginStatus.setStore(store);
        storePluginStatus.setPluginId(pluginId);
        storePluginStatusDao.persist(storePluginStatus);
        return storePluginStatus;
    }

}