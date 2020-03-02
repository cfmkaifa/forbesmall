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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import net.mall.Filter;
import net.mall.Order;
import net.mall.Page;
import net.mall.Pageable;
import net.mall.dao.ReviewDao;
import net.mall.entity.Member;
import net.mall.entity.Product;
import net.mall.entity.Review;
import net.mall.entity.Store;

/**
 * Dao - 评论
 *
 * @author huanghy
 * @version 6.1
 */
@Repository
public class ReviewDaoImpl extends BaseDaoImpl<Review, Long> implements ReviewDao {

    @Override
    public List<Review> findList(Member member, Product product, Review.Type type, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
        Root<Review> root = criteriaQuery.from(Review.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNull(root.get("forReview")));
        if (member != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
        }
        if (product != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("product"), product));
        }
        if (type != null) {
            switch (type) {
                case POSITIVE:
                    restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number>get("score"), 4));
                    break;
                case MODERATE:
                    restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Number>get("score"), 3));
                    break;
                case NEGATIVE:
                    restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number>get("score"), 2));
                    break;
            }
        }
        if (isShow != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isShow"), isShow));
        }
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery, null, count, filters, orders);
    }

    @Override
    public Page<Review> findPage(Member member, Product product, Store store, Review.Type type, Boolean isShow, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
        Root<Review> root = criteriaQuery.from(Review.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNull(root.get("forReview")));
        if (member != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
        }
        if (product != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("product"), product));
        }
        if (type != null) {
            switch (type) {
                case POSITIVE:
                    restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number>get("score"), 4));
                    break;
                case MODERATE:
                    restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Number>get("score"), 3));
                    break;
                case NEGATIVE:
                    restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number>get("score"), 2));
                    break;
            }
        }
        if (isShow != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isShow"), isShow));
        }
        if (store != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageable);
    }

    @Override
    public Long count(Member member, Product product, Review.Type type, Boolean isShow) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
        Root<Review> root = criteriaQuery.from(Review.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNull(root.get("forReview")));
        if (member != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
        }
        if (product != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("product"), product));
        }
        if (type != null) {
            switch (type) {
                case POSITIVE:
                    restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number>get("score"), 4));
                    break;
                case MODERATE:
                    restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Number>get("score"), 3));
                    break;
                case NEGATIVE:
                    restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number>get("score"), 2));
                    break;
            }
        }
        if (isShow != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isShow"), isShow));
        }
        criteriaQuery.where(restrictions);
        return super.count(criteriaQuery, null);
    }

    @Override
    public long calculateTotalScore(Product product) {
        Assert.notNull(product, "[Assertion failed] - product is required; it must not be null");

        String jpql = "select sum(review.score) from Review review where review.product = :product and review.isShow = :isShow and review.forReview is null";
        Long totalScore = entityManager.createQuery(jpql, Long.class).setParameter("product", product).setParameter("isShow", true).getSingleResult();
        return totalScore != null ? totalScore : 0L;
    }

    @Override
    public long calculateScoreCount(Product product) {
        Assert.notNull(product, "[Assertion failed] - product is required; it must not be null");

        String jpql = "select count(*) from Review review where review.product = :product and review.isShow = :isShow and review.forReview is null";
        return entityManager.createQuery(jpql, Long.class).setParameter("product", product).setParameter("isShow", true).getSingleResult();
    }

}