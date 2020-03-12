/*
 *
 *
 *
 *
 */
package net.mall.service.impl;

import javax.inject.Inject;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.mall.dao.AdPositionDao;
import net.mall.entity.AdPosition;
import net.mall.service.AdPositionService;

/**
 * Service - 广告位
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class AdPositionServiceImpl extends BaseServiceImpl<AdPosition, Long> implements AdPositionService {

    @Inject
    private AdPositionDao adPositionDao;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "adPosition", condition = "#useCache")
    public AdPosition find(Long id, boolean useCache) {
        return adPositionDao.find(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "adPosition", allEntries = true)
    public AdPosition save(AdPosition adPosition) {
        return super.save(adPosition);
    }

    @Override
    @Transactional
    @CacheEvict(value = "adPosition", allEntries = true)
    public AdPosition update(AdPosition adPosition) {
        return super.update(adPosition);
    }

    @Override
    @Transactional
    @CacheEvict(value = "adPosition", allEntries = true)
    public AdPosition update(AdPosition adPosition, String... ignoreProperties) {
        return super.update(adPosition, ignoreProperties);
    }

    @Override
    @Transactional
    @CacheEvict(value = "adPosition", allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "adPosition", allEntries = true)
    public void delete(Long... ids) {
        super.delete(ids);
    }

    @Override
    @Transactional
    @CacheEvict(value = "adPosition", allEntries = true)
    public void delete(AdPosition adPosition) {
        super.delete(adPosition);
    }

}