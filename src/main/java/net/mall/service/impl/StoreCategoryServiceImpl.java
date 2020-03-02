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

import net.mall.dao.StoreCategoryDao;
import net.mall.entity.StoreCategory;
import net.mall.service.StoreCategoryService;

/**
 * Service - 店铺分类
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class StoreCategoryServiceImpl extends BaseServiceImpl<StoreCategory, Long> implements StoreCategoryService {

    @Inject
    private StoreCategoryDao storeCategoryDao;

    @Override
    @Transactional(readOnly = true)
    public boolean nameExists(String name) {
        return storeCategoryDao.exists("name", name, true);
    }

}