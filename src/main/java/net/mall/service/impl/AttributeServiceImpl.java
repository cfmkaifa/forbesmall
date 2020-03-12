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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.mall.Filter;
import net.mall.Order;
import net.mall.dao.AttributeDao;
import net.mall.dao.ProductCategoryDao;
import net.mall.dao.ProductDao;
import net.mall.entity.Attribute;
import net.mall.entity.ProductCategory;
import net.mall.service.AttributeService;

/**
 * Service - 属性
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class AttributeServiceImpl extends BaseServiceImpl<Attribute, Long> implements AttributeService {

    @Inject
    private AttributeDao attributeDao;
    @Inject
    private ProductCategoryDao productCategoryDao;
    @Inject
    private ProductDao productDao;

    @Override
    @Transactional(readOnly = true)
    public Integer findUnusedPropertyIndex(ProductCategory productCategory) {
        return attributeDao.findUnusedPropertyIndex(productCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Attribute> findList(ProductCategory productCategory, Integer count, List<Filter> filters, List<Order> orders) {
        return attributeDao.findList(productCategory, count, filters, orders);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "attribute", condition = "#useCache")
    public List<Attribute> findList(Long productCategoryId, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
        ProductCategory productCategory = productCategoryDao.find(productCategoryId);
        if (productCategoryId != null && productCategory == null) {
            return Collections.emptyList();
        }
        return attributeDao.findList(productCategory, count, filters, orders);
    }

    @Override
    @Transactional(readOnly = true)
    public String toAttributeValue(Attribute attribute, String value) {
        Assert.notNull(attribute, "[Assertion failed] - attribute is required; it must not be null");

        if (StringUtils.isEmpty(value) || CollectionUtils.isEmpty(attribute.getOptions()) || !attribute.getOptions().contains(value)) {
            return null;
        }
        return value;
    }

    @Override
    @Transactional
    @CacheEvict(value = "attribute", allEntries = true)
    public Attribute save(Attribute attribute) {
        Assert.notNull(attribute, "[Assertion failed] - attribute is required; it must not be null");

        Integer unusedPropertyIndex = attributeDao.findUnusedPropertyIndex(attribute.getProductCategory());
        Assert.notNull(unusedPropertyIndex, "[Assertion failed] - unusedPropertyIndex is required; it must not be null");

        attribute.setPropertyIndex(unusedPropertyIndex);
        return super.save(attribute);
    }

    @Override
    @Transactional
    @CacheEvict(value = "attribute", allEntries = true)
    public Attribute update(Attribute attribute) {
        return super.update(attribute);
    }

    @Override
    @Transactional
    @CacheEvict(value = "attribute", allEntries = true)
    public Attribute update(Attribute attribute, String... ignoreProperties) {
        return super.update(attribute, ignoreProperties);
    }

    @Override
    @Transactional
    @CacheEvict(value = "attribute", allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "attribute", allEntries = true)
    public void delete(Long... ids) {
        super.delete(ids);
    }

    @Override
    @Transactional
    @CacheEvict(value = "attribute", allEntries = true)
    public void delete(Attribute attribute) {
        if (attribute != null) {
            productDao.clearAttributeValue(attribute);
        }

        super.delete(attribute);
    }

}