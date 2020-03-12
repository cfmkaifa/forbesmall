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
import net.mall.dao.MemberDao;
import net.mall.dao.StoreFavoriteDao;
import net.mall.entity.Member;
import net.mall.entity.Store;
import net.mall.entity.StoreFavorite;
import net.mall.service.StoreFavoriteService;

/**
 * Service - 店铺收藏
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class StoreFavoriteServiceImpl extends BaseServiceImpl<StoreFavorite, Long> implements StoreFavoriteService {

    @Inject
    private StoreFavoriteDao storeFavoriteDao;
    @Inject
    private MemberDao memberDao;

    @Override
    @Transactional(readOnly = true)
    public boolean exists(Member member, Store store) {
        return storeFavoriteDao.exists(member, store);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreFavorite> findList(Member member, Integer count, List<Filter> filters, List<Order> orders) {
        return storeFavoriteDao.findList(member, count, filters, orders);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StoreFavorite> findPage(Member member, Pageable pageable) {
        return storeFavoriteDao.findPage(member, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Long count(Member member) {
        return storeFavoriteDao.count(member);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "storeFavorite", condition = "#useCache")
    public List<StoreFavorite> findList(Long memberId, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
        Member member = memberDao.find(memberId);
        if (memberId != null && member == null) {
            return Collections.emptyList();
        }
        return storeFavoriteDao.findList(member, count, filters, orders);
    }

    @Override
    @CacheEvict(value = "storeFavorite", allEntries = true)
    public StoreFavorite save(StoreFavorite storeFavorite) {
        return super.save(storeFavorite);
    }

    @Override
    @CacheEvict(value = "storeFavorite", allEntries = true)
    public StoreFavorite update(StoreFavorite storeFavorite) {
        return super.update(storeFavorite);
    }

    @Override
    @CacheEvict(value = "storeFavorite", allEntries = true)
    public StoreFavorite update(StoreFavorite storeFavorite, String... ignoreProperties) {
        return super.update(storeFavorite, ignoreProperties);
    }

    @Override
    @CacheEvict(value = "storeFavorite", allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @CacheEvict(value = "storeFavorite", allEntries = true)
    public void delete(Long... ids) {
        super.delete(ids);
    }

    @Override
    @CacheEvict(value = "storeFavorite", allEntries = true)
    public void delete(StoreFavorite storeFavorite) {
        super.delete(storeFavorite);
    }

}