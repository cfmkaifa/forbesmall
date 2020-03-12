/*
 *
 *
 *
 *
 */
package net.mall.dao.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import net.mall.Filter;
import net.mall.Order;
import net.mall.Page;
import net.mall.Pageable;
import net.mall.dao.PromotionDao;
import net.mall.entity.MemberRank;
import net.mall.entity.ProductCategory;
import net.mall.entity.Promotion;
import net.mall.entity.Store;
import net.mall.plugin.PromotionPlugin;

/**
 * Dao - 促销
 *
 * @author huanghy
 * @version 6.1
 */
@Repository
public class PromotionDaoImpl extends BaseDaoImpl<Promotion, Long> implements PromotionDao {

    @Override
    public List<Promotion> search(String keyword, Set<Promotion> excludes, Integer count) {
        if (StringUtils.isEmpty(keyword)) {
            return Collections.emptyList();
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Promotion> criteriaQuery = criteriaBuilder.createQuery(Promotion.class);
        Root<Promotion> root = criteriaQuery.from(Promotion.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String>get("name"), "%" + keyword + "%"));
        if (CollectionUtils.isNotEmpty(excludes)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(root.in(excludes)));
        }
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery, null, count, null, null);
    }

    @Override
    public List<Promotion> findList(PromotionPlugin promotionPlugin, Store store, Boolean isEnabled) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Promotion> criteriaQuery = criteriaBuilder.createQuery(Promotion.class);
        Root<Promotion> root = criteriaQuery.from(Promotion.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (store != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
        }
        if (promotionPlugin != null && promotionPlugin.getId() != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("promotionPluginId"), promotionPlugin.getId()));
        }
        if (isEnabled != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isEnabled"), isEnabled));
        }
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery, null, null, null, null);
    }

    @Override
    public List<Promotion> findList(PromotionPlugin promotionPlugin, Store store, MemberRank memberRank, ProductCategory productCategory, Boolean hasBegun, Boolean hasEnded, Integer count, List<Filter> filters, List<Order> orders) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Promotion> criteriaQuery = criteriaBuilder.createQuery(Promotion.class);
        Root<Promotion> root = criteriaQuery.from(Promotion.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (store != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
        }
        if (promotionPlugin != null && promotionPlugin.getId() != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("promotionPluginId"), promotionPlugin.getId()));
        }
        if (memberRank != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.join("memberRanks"), memberRank));
        }
        if (productCategory != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.join("productCategories"), productCategory));
        }
        if (hasBegun != null) {
            if (hasBegun) {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("beginDate").isNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date>get("beginDate"), new Date())));
            } else {
                restrictions = criteriaBuilder.and(restrictions, root.get("beginDate").isNotNull(), criteriaBuilder.greaterThan(root.<Date>get("beginDate"), new Date()));
            }
        }
        if (hasEnded != null) {
            if (hasEnded) {
                restrictions = criteriaBuilder.and(restrictions, root.get("endDate").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date>get("endDate"), new Date()));
            } else {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("endDate").isNull(), criteriaBuilder.greaterThan(root.<Date>get("endDate"), new Date())));
            }
        }
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery, null, count, filters, orders);
    }

    @Override
    public Page<Promotion> findPage(PromotionPlugin promotionPlugin, Store store, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Promotion> criteriaQuery = criteriaBuilder.createQuery(Promotion.class);
        Root<Promotion> root = criteriaQuery.from(Promotion.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (store != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
        }
        if (promotionPlugin != null && promotionPlugin.getId() != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("promotionPluginId"), promotionPlugin.getId()));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageable);
    }
}