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

import net.mall.Filter;
import net.mall.Order;
import net.mall.dao.BrandDao;
import net.mall.entity.Brand;
import net.mall.entity.ProductCategory;

/**
 * Dao - 品牌
 *
 * @author huanghy
 * @version 6.1
 */
@Repository
public class BrandDaoImpl extends BaseDaoImpl<Brand, Long> implements BrandDao {

    @Override
    public List<Brand> findList(ProductCategory productCategory, Integer count, List<Filter> filters, List<Order> orders) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
        Root<Brand> root = criteriaQuery.from(Brand.class);
        criteriaQuery.select(root);
        if (productCategory != null) {
            criteriaQuery.where(criteriaBuilder.equal(root.join("productCategories"), productCategory));
        }
        return super.findList(criteriaQuery, null, count, filters, orders);
    }

}