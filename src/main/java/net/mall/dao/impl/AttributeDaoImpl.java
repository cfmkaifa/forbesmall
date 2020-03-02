/*
 *
 *
 *
 *
 */
package net.mall.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import net.mall.Filter;
import net.mall.Order;
import net.mall.dao.AttributeDao;
import net.mall.entity.Attribute;
import net.mall.entity.Product;
import net.mall.entity.ProductCategory;

/**
 * Dao - 属性
 *
 * @author huanghy
 * @version 6.1
 */
@Repository
public class AttributeDaoImpl extends BaseDaoImpl<Attribute, Long> implements AttributeDao {

    @Override
    public Integer findUnusedPropertyIndex(ProductCategory productCategory) {
        Assert.notNull(productCategory, "[Assertion failed] - productCategory is required; it must not be null");

        for (int i = 0; i < Product.ATTRIBUTE_VALUE_PROPERTY_COUNT; i++) {
            String jpql = "select count(*) from Attribute attribute where attribute.productCategory = :productCategory and attribute.propertyIndex = :propertyIndex";
            Long count = entityManager.createQuery(jpql, Long.class).setParameter("productCategory", productCategory).setParameter("propertyIndex", i).getSingleResult();
            if (count == 0) {
                return i;
            }
        }
        return null;
    }

    @Override
    public List<Attribute> findList(ProductCategory productCategory, Integer count, List<Filter> filters, List<Order> orders) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Attribute> criteriaQuery = criteriaBuilder.createQuery(Attribute.class);
        Root<Attribute> root = criteriaQuery.from(Attribute.class);
        criteriaQuery.select(root);
        if (productCategory != null) {
            criteriaQuery.where(criteriaBuilder.equal(root.get("productCategory"), productCategory));
        }
        return super.findList(criteriaQuery, null, count, filters, orders);
    }

}