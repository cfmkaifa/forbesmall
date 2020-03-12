/*
 *
 *
 *
 *
 */
package net.mall.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.mall.entity.NavigationGroup;
import net.mall.service.NavigationGroupService;

/**
 * Service - 导航组
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class NavigationGroupServiceImpl extends BaseServiceImpl<NavigationGroup, Long> implements NavigationGroupService {

    @Override
    @Transactional
    @CacheEvict(value = "navigation", allEntries = true)
    public NavigationGroup save(NavigationGroup navigationGroup) {
        return super.save(navigationGroup);
    }

    @Override
    @Transactional
    @CacheEvict(value = "navigation", allEntries = true)
    public NavigationGroup update(NavigationGroup navigationGroup) {
        return super.update(navigationGroup);
    }

    @Override
    @Transactional
    @CacheEvict(value = "navigation", allEntries = true)
    public NavigationGroup update(NavigationGroup navigationGroup, String... ignoreProperties) {
        return super.update(navigationGroup, ignoreProperties);
    }

    @Override
    @Transactional
    @CacheEvict(value = "navigation", allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "navigation", allEntries = true)
    public void delete(Long... ids) {
        super.delete(ids);
    }

    @Override
    @Transactional
    @CacheEvict(value = "navigation", allEntries = true)
    public void delete(NavigationGroup navigationGroup) {
        super.delete(navigationGroup);
    }

}