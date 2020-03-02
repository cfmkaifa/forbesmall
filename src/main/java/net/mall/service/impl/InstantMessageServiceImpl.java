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
import net.mall.dao.InstantMessageDao;
import net.mall.dao.StoreDao;
import net.mall.entity.InstantMessage;
import net.mall.entity.Store;
import net.mall.service.InstantMessageService;

/**
 * Service - 即时通讯
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class InstantMessageServiceImpl extends BaseServiceImpl<InstantMessage, Long> implements InstantMessageService {

    @Inject
    private InstantMessageDao instantMessageDao;
    @Inject
    private StoreDao storeDao;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "instantMessage", condition = "#useCache")
    public List<InstantMessage> findList(InstantMessage.Type type, Long storeId, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
        Store store = storeDao.find(storeId);
        if (storeId != null && store == null) {
            return Collections.emptyList();
        }

        return instantMessageDao.findList(type, store, count, filters, orders);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InstantMessage> findPage(Store store, Pageable pageable) {
        return instantMessageDao.findPage(store, pageable);
    }

    @Override
    @CacheEvict(value = "instantMessage", allEntries = true)
    public InstantMessage save(InstantMessage entity) {
        return super.save(entity);
    }

    @Override
    @CacheEvict(value = "instantMessage", allEntries = true)
    public InstantMessage update(InstantMessage entity) {
        return super.update(entity);
    }

    @Override
    @CacheEvict(value = "instantMessage", allEntries = true)
    public InstantMessage update(InstantMessage entity, String... ignoreProperties) {
        return super.update(entity, ignoreProperties);
    }

    @Override
    @CacheEvict(value = "instantMessage", allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @CacheEvict(value = "instantMessage", allEntries = true)
    public void delete(Long... ids) {
        super.delete(ids);
    }

    @Override
    @CacheEvict(value = "instantMessage", allEntries = true)
    public void delete(InstantMessage entity) {
        super.delete(entity);
    }

}