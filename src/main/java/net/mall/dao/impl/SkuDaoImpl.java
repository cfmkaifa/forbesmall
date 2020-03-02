/*
 *
 *
 *
 *
 */
package net.mall.dao.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import net.mall.dao.SkuDao;
import net.mall.entity.Product;
import net.mall.entity.Product.Type;
import net.mall.entity.Sku;
import net.mall.entity.Store;

/**
 * Dao - SKU
 *
 * @author huanghy
 * @version 6.1
 */
@Repository
public class SkuDaoImpl extends BaseDaoImpl<Sku, Long> implements SkuDao {

    @Override
    public List<Sku> search(Store store, Product.Type type, String keyword, Set<Sku> excludes, Integer count) {
        if (StringUtils.isEmpty(keyword)) {
            return Collections.emptyList();
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Sku> criteriaQuery = criteriaBuilder.createQuery(Sku.class);
        Root<Sku> root = criteriaQuery.from(Sku.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (store != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("product").get("store"), store));
        }
        if (type != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("product").get("type"), type));
        }
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.like(root.<String>get("sn"), "%" + keyword + "%"), criteriaBuilder.like(root.get("product").<String>get("name"), "%" + keyword + "%")));
        if (CollectionUtils.isNotEmpty(excludes)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(root.in(excludes)));
        }
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery, null, count, null, null);
    }

    @Override
    public List<Sku> findList(Store store, Type type, Set<Sku> matchs, Integer count) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Sku> criteriaQuery = criteriaBuilder.createQuery(Sku.class);
        Root<Sku> root = criteriaQuery.from(Sku.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (store != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("product").get("store"), store));
        }
        if (type != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("product").get("type"), type));
        }
        if (CollectionUtils.isNotEmpty(matchs)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.in(matchs)));
        }
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery, null, count, null, null);
    }


    public void modifySkuGroupPrice(BigDecimal groupPrice, Boolean group, String skuSn) {
        String jpql = "UPDATE Sku SET groupPrice = :groupPrice,isGroup = :group WHERE sn = :sn";
        entityManager.createQuery(jpql).setParameter("groupPrice", groupPrice)
                .setParameter("group", group)
                .setParameter("sn", skuSn).executeUpdate();
    }
}