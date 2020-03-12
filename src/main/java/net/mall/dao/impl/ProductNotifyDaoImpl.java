/*
 *
 *
 *
 *
 */
package net.mall.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.dao.ProductNotifyDao;
import net.mall.entity.Member;
import net.mall.entity.ProductNotify;
import net.mall.entity.Sku;
import net.mall.entity.Store;

/**
 * Dao - 到货通知
 *
 * @author huanghy
 * @version 6.1
 */
@Repository
public class ProductNotifyDaoImpl extends BaseDaoImpl<ProductNotify, Long> implements ProductNotifyDao {

    @Override
    public boolean exists(Sku sku, String email) {
        if (sku == null || StringUtils.isEmpty(email)) {
            return false;
        }
        String jpql = "select count(*) from ProductNotify productNotify where productNotify.sku = :sku and lower(productNotify.email) = lower(:email) and productNotify.hasSent = false";
        Long count = entityManager.createQuery(jpql, Long.class).setParameter("sku", sku).setParameter("email", email).getSingleResult();
        return count > 0;
    }

    @Override
    public Page<ProductNotify> findPage(Store store, Member member, Boolean isMarketable, Boolean isOutOfStock, Boolean hasSent, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductNotify> criteriaQuery = criteriaBuilder.createQuery(ProductNotify.class);
        Root<ProductNotify> root = criteriaQuery.from(ProductNotify.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (store != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("sku").get("product").get("store"), store));
        }
        if (member != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
        }
        if (isMarketable != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("sku").get("product").get("isMarketable"), isMarketable));
        }
        if (isOutOfStock != null) {
            Path<Integer> stock = root.get("sku").get("stock");
            Path<Integer> allocatedStock = root.get("sku").get("allocatedStock");
            if (isOutOfStock) {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(stock), criteriaBuilder.lessThanOrEqualTo(stock, allocatedStock));
            } else {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.isNull(stock), criteriaBuilder.greaterThan(stock, allocatedStock)));
            }
        }
        if (hasSent != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("hasSent"), hasSent));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageable);
    }

    @Override
    public Long count(Member member, Boolean isMarketable, Boolean isOutOfStock, Boolean hasSent) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductNotify> criteriaQuery = criteriaBuilder.createQuery(ProductNotify.class);
        Root<ProductNotify> root = criteriaQuery.from(ProductNotify.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (member != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
        }
        if (isMarketable != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("sku").get("product").get("isMarketable"), isMarketable));
        }
        if (isOutOfStock != null) {
            Path<Integer> stock = root.get("sku").get("stock");
            Path<Integer> allocatedStock = root.get("sku").get("allocatedStock");
            if (isOutOfStock) {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(stock), criteriaBuilder.lessThanOrEqualTo(stock, allocatedStock));
            } else {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.isNull(stock), criteriaBuilder.greaterThan(stock, allocatedStock)));
            }
        }
        if (hasSent != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("hasSent"), hasSent));
        }
        criteriaQuery.where(restrictions);
        return super.count(criteriaQuery, null);
    }

}