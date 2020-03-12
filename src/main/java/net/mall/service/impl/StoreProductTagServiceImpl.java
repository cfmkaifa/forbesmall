/*
 *
 *
 *
 *
 */
package net.mall.service.impl;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.mall.Filter;
import net.mall.Order;
import net.mall.Page;
import net.mall.Pageable;
import net.mall.dao.StoreDao;
import net.mall.dao.StoreProductTagDao;
import net.mall.entity.Store;
import net.mall.entity.StoreProductTag;
import net.mall.service.StoreProductTagService;

/**
 * Service - 店铺商品标签
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class StoreProductTagServiceImpl extends BaseServiceImpl<StoreProductTag, Long> implements StoreProductTagService {

    @Inject
    private StoreProductTagDao storeProductTagDao;
    @Inject
    private StoreDao storeDao;

    @Override
    @Transactional(readOnly = true)
    public List<StoreProductTag> findList(Store store, Boolean isEnabled) {
        return storeProductTagDao.findList(store, isEnabled);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "storeProductTag", condition = "#useCache")
    public List<StoreProductTag> findList(Long storeId, Boolean isEnabled, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
        Store store = storeDao.find(storeId);
        if (storeId != null && store == null) {
            return Collections.emptyList();
        }

        return storeProductTagDao.findList(store, isEnabled, count, filters, orders);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StoreProductTag> findPage(Store store, Pageable pageable) {
        return storeProductTagDao.findPage(store, pageable);
    }

    @Override
    @CacheEvict(value = "storeProductTag", allEntries = true)
    public StoreProductTag save(StoreProductTag entity) {
        return super.save(entity);
    }

    @Override
    @CacheEvict(value = "storeProductTag", allEntries = true)
    public StoreProductTag update(StoreProductTag entity) {
        return super.update(entity);
    }

    @Override
    @CacheEvict(value = "storeProductTag", allEntries = true)
    public StoreProductTag update(StoreProductTag entity, String... ignoreProperties) {
        return super.update(entity, ignoreProperties);
    }

    @Override
    @CacheEvict(value = "storeProductTag", allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @CacheEvict(value = "storeProductTag", allEntries = true)
    public void delete(Long... ids) {
        super.delete(ids);
    }

    @Override
    @CacheEvict(value = "storeProductTag", allEntries = true)
    public void delete(StoreProductTag entity) {
        super.delete(entity);
    }

}