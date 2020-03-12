/*
 *
 *
 *
 *
 */
package net.mall.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.dao.CategoryApplicationDao;
import net.mall.dao.ProductCategoryDao;
import net.mall.dao.ProductDao;
import net.mall.dao.StoreDao;
import net.mall.entity.CategoryApplication;
import net.mall.entity.CategoryApplication.Status;
import net.mall.entity.ProductCategory;
import net.mall.entity.Store;
import net.mall.service.CategoryApplicationService;

/**
 * Service - 经营分类申请
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class CategoryApplicationServiceImpl extends BaseServiceImpl<CategoryApplication, Long> implements CategoryApplicationService {

    @Inject
    private CategoryApplicationDao categoryApplicationDao;
    @Inject
    private ProductDao productDao;
    @Inject
    private StoreDao storeDao;
    @Inject
    private ProductCategoryDao productCategoryDao;

    @Override
    @Transactional(readOnly = true)
    public boolean exist(Store store, ProductCategory productCategory, CategoryApplication.Status status) {
        Assert.notNull(status, "[Assertion failed] - status is required; it must not be null");
        Assert.notNull(store, "[Assertion failed] - store is required; it must not be null");
        Assert.notNull(productCategory, "[Assertion failed] - productCategory is required; it must not be null");

        return categoryApplicationDao.findList(store, productCategory, status).size() > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryApplication> findPage(Status status, Store store, ProductCategory productCategory, Pageable pageable) {
        return categoryApplicationDao.findPage(status, store, productCategory, pageable);
    }

    @Override
    @CacheEvict(value = {"product", "productCategory"}, allEntries = true)
    public void review(CategoryApplication categoryApplication, boolean isPassed) {
        Assert.notNull(categoryApplication, "[Assertion failed] - categoryApplication is required; it must not be null");

        if (isPassed) {
            Store store = categoryApplication.getStore();
            ProductCategory productCategory = categoryApplication.getProductCategory();

            categoryApplication.setStatus(CategoryApplication.Status.APPROVED);
            store.getProductCategories().add(productCategory);
            Set<ProductCategory> productCategories = new HashSet<>();
            productCategories.add(productCategory);
            productDao.refreshActive(store);
        } else {
            categoryApplication.setStatus(CategoryApplication.Status.FAILED);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Long count(CategoryApplication.Status status, Store store, ProductCategory productCategory) {
        return categoryApplicationDao.count(status, store, productCategory);
    }

    @Override
    public Long count(Status status, Long storeId, Long productCategoryId) {
        Store store = storeDao.find(storeId);
        if (storeId != null && store == null) {
            return 0L;
        }
        ProductCategory productCategory = productCategoryDao.find(productCategoryId);
        if (productCategoryId != null && productCategory == null) {
            return 0L;
        }
        return categoryApplicationDao.count(status, store, productCategory);
    }

}