/*
 *
 *
 *
 *
 */
package net.mall.dao.impl;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.dao.CouponCodeDao;
import net.mall.entity.Coupon;
import net.mall.entity.CouponCode;
import net.mall.entity.Member;

/**
 * Dao - 优惠码
 *
 * @author huanghy
 * @version 6.1
 */
@Repository
public class CouponCodeDaoImpl extends BaseDaoImpl<CouponCode, Long> implements CouponCodeDao {

    @Override
    public Page<CouponCode> findPage(Member member, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CouponCode> criteriaQuery = criteriaBuilder.createQuery(CouponCode.class);
        Root<CouponCode> root = criteriaQuery.from(CouponCode.class);
        criteriaQuery.select(root);
        if (member != null) {
            criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
        }
        return super.findPage(criteriaQuery, pageable);
    }

    @Override
    public Long count(Coupon coupon, Member member, Boolean hasBegun, Boolean hasExpired, Boolean isUsed) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CouponCode> criteriaQuery = criteriaBuilder.createQuery(CouponCode.class);
        Root<CouponCode> root = criteriaQuery.from(CouponCode.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        Path<Coupon> couponPath = root.get("coupon");
        if (coupon != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(couponPath, coupon));
        }
        if (member != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
        }
        if (hasBegun != null) {
            if (hasBegun) {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(couponPath.get("beginDate").isNull(), criteriaBuilder.lessThanOrEqualTo(couponPath.<Date>get("beginDate"), new Date())));
            } else {
                restrictions = criteriaBuilder.and(restrictions, couponPath.get("beginDate").isNotNull(), criteriaBuilder.greaterThan(couponPath.<Date>get("beginDate"), new Date()));
            }
        }
        if (hasExpired != null) {
            if (hasExpired) {
                restrictions = criteriaBuilder.and(restrictions, couponPath.get("endDate").isNotNull(), criteriaBuilder.lessThanOrEqualTo(couponPath.<Date>get("endDate"), new Date()));
            } else {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(couponPath.get("endDate").isNull(), criteriaBuilder.greaterThan(couponPath.<Date>get("endDate"), new Date())));
            }
        }
        if (isUsed != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isUsed"), isUsed));
        }
        criteriaQuery.where(restrictions);
        return super.count(criteriaQuery, null);
    }

}