/*
 *
 *
 *
 *
 */
package net.mall.dao.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Repository;

import net.mall.Filter;
import net.mall.Page;
import net.mall.Pageable;
import net.mall.dao.OrderDao;
import net.mall.entity.Member;
import net.mall.entity.Order;
import net.mall.entity.Order.CommissionType;
import net.mall.entity.Order.Status;
import net.mall.entity.OrderItem;
import net.mall.entity.PaymentMethod;
import net.mall.entity.Product;
import net.mall.entity.Sku;
import net.mall.entity.Store;

/**
 * Dao - 订单
 *
 * @author huanghy
 * @version 6.1
 */
@Repository
public class OrderDaoImpl extends BaseDaoImpl<Order, Long> implements OrderDao {

    @Override
    public List<Order> findList(Order.Type type, Order.Status status, Store store, Member member, Product product, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isUseCouponCode, Boolean isExchangePoint, Boolean isAllocatedStock, Boolean hasExpired, Integer count, List<Filter> filters,
                                List<net.mall.Order> orders) {
        return findList(type, status, store, member, product, isPendingReceive, isPendingRefunds, isUseCouponCode, isExchangePoint, isAllocatedStock, hasExpired, null, count, filters, orders);
    }

    @Override
    public List<Order> findList(Order.Type type, Order.Status status, Store store, Member member, Product product, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isUseCouponCode, Boolean isExchangePoint, Boolean isAllocatedStock, Boolean hasExpired, Integer first, Integer count,
                                List<Filter> filters, List<net.mall.Order> orders) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (type != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
        }
        if (status != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
        }
        if (store != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
        }
        if (member != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
        }
        if (product != null) {
            Subquery<Sku> skuSubquery = criteriaQuery.subquery(Sku.class);
            Root<Sku> skuSubqueryRoot = skuSubquery.from(Sku.class);
            skuSubquery.select(skuSubqueryRoot);
            skuSubquery.where(criteriaBuilder.equal(skuSubqueryRoot.get("product"), product));

            Subquery<OrderItem> orderItemSubquery = criteriaQuery.subquery(OrderItem.class);
            Root<OrderItem> orderItemSubqueryRoot = orderItemSubquery.from(OrderItem.class);
            orderItemSubquery.select(orderItemSubqueryRoot);
            orderItemSubquery.where(criteriaBuilder.equal(orderItemSubqueryRoot.get("order"), root), criteriaBuilder.in(orderItemSubqueryRoot.get("sku")).value(skuSubquery));
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(orderItemSubquery));
        }
        if (isPendingReceive != null) {
            Predicate predicate = criteriaBuilder.and(criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date>get("expire"), new Date())), criteriaBuilder.equal(root.get("paymentMethodType"), PaymentMethod.Type.CASH_ON_DELIVERY),
                    criteriaBuilder.notEqual(root.get("status"), Order.Status.COMPLETED), criteriaBuilder.notEqual(root.get("status"), Order.Status.FAILED), criteriaBuilder.notEqual(root.get("status"), Order.Status.CANCELED), criteriaBuilder.notEqual(root.get("status"), Order.Status.DENIED),
                    criteriaBuilder.lessThan(root.<BigDecimal>get("amountPaid"), root.<BigDecimal>get("amount")));
            if (isPendingReceive) {
                restrictions = criteriaBuilder.and(restrictions, predicate);
            } else {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
            }
        }
        if (isPendingRefunds != null) {
            Predicate predicate = criteriaBuilder.or(
                    criteriaBuilder.and(criteriaBuilder.or(criteriaBuilder.and(root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date>get("expire"), new Date())), criteriaBuilder.equal(root.get("status"), Order.Status.FAILED),
                            criteriaBuilder.equal(root.get("status"), Order.Status.CANCELED), criteriaBuilder.equal(root.get("status"), Order.Status.DENIED)), criteriaBuilder.greaterThan(root.<BigDecimal>get("amountPaid"), BigDecimal.ZERO)),
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), Order.Status.COMPLETED), criteriaBuilder.greaterThan(root.<BigDecimal>get("amountPaid"), root.<BigDecimal>get("amount"))));
            if (isPendingRefunds) {
                restrictions = criteriaBuilder.and(restrictions, predicate);
            } else {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
            }
        }
        if (isUseCouponCode != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isUseCouponCode"), isUseCouponCode));
        }
        if (isExchangePoint != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isExchangePoint"), isExchangePoint));
        }
        if (isAllocatedStock != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isAllocatedStock"), isAllocatedStock));
        }
        if (hasExpired != null) {
            if (hasExpired) {
                restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date>get("expire"), new Date()));
            } else {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date>get("expire"), new Date())));
            }
        }
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery, first, count, filters, orders);
    }

    @Override
    public Page<Order> findPage(Order.Type type, Order.Status status, Store store, Member member, Product product, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isUseCouponCode, Boolean isExchangePoint, Boolean isAllocatedStock, Boolean hasExpired, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (type != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
        }
        if (status != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
        }
        if (store != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
        }
        if (member != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
        }
        if (product != null) {
            Subquery<Sku> skuSubquery = criteriaQuery.subquery(Sku.class);
            Root<Sku> skuSubqueryRoot = skuSubquery.from(Sku.class);
            skuSubquery.select(skuSubqueryRoot);
            skuSubquery.where(criteriaBuilder.equal(skuSubqueryRoot.get("product"), product));

            Subquery<OrderItem> orderItemSubquery = criteriaQuery.subquery(OrderItem.class);
            Root<OrderItem> orderItemSubqueryRoot = orderItemSubquery.from(OrderItem.class);
            orderItemSubquery.select(orderItemSubqueryRoot);
            orderItemSubquery.where(criteriaBuilder.equal(orderItemSubqueryRoot.get("order"), root), criteriaBuilder.in(orderItemSubqueryRoot.get("sku")).value(skuSubquery));
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(orderItemSubquery));
        }
        if (isPendingReceive != null) {
            Predicate predicate = criteriaBuilder.and(criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date>get("expire"), new Date())), criteriaBuilder.equal(root.get("paymentMethodType"), PaymentMethod.Type.CASH_ON_DELIVERY),
                    criteriaBuilder.notEqual(root.get("status"), Order.Status.COMPLETED), criteriaBuilder.notEqual(root.get("status"), Order.Status.FAILED), criteriaBuilder.notEqual(root.get("status"), Order.Status.CANCELED), criteriaBuilder.notEqual(root.get("status"), Order.Status.DENIED),
                    criteriaBuilder.lessThan(root.<BigDecimal>get("amountPaid"), root.<BigDecimal>get("amount")));
            if (isPendingReceive) {
                restrictions = criteriaBuilder.and(restrictions, predicate);
            } else {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
            }
        }
        if (isPendingRefunds != null) {
            Predicate predicate = criteriaBuilder.or(
                    criteriaBuilder.and(criteriaBuilder.or(criteriaBuilder.and(root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date>get("expire"), new Date())), criteriaBuilder.equal(root.get("status"), Order.Status.FAILED),
                            criteriaBuilder.equal(root.get("status"), Order.Status.CANCELED), criteriaBuilder.equal(root.get("status"), Order.Status.DENIED)), criteriaBuilder.greaterThan(root.<BigDecimal>get("amountPaid"), BigDecimal.ZERO)),
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), Order.Status.COMPLETED), criteriaBuilder.greaterThan(root.<BigDecimal>get("amountPaid"), root.<BigDecimal>get("amount"))));
            if (isPendingRefunds) {
                restrictions = criteriaBuilder.and(restrictions, predicate);
            } else {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
            }
        }
        if (isUseCouponCode != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isUseCouponCode"), isUseCouponCode));
        }
        if (isExchangePoint != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isExchangePoint"), isExchangePoint));
        }
        if (isAllocatedStock != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isAllocatedStock"), isAllocatedStock));
        }
        if (hasExpired != null) {
            if (hasExpired) {
                restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date>get("expire"), new Date()));
            } else {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date>get("expire"), new Date())));
            }
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageable);
    }

    @Override
    public Long count(Order.Type type, Store store, Member member, Product product, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isUseCouponCode, Boolean isExchangePoint, Boolean isAllocatedStock, Boolean hasExpired, Order.Status... status) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (type != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
        }
        if (status != null) {
            if (status.length > 1) {
                restrictions = criteriaBuilder.and(restrictions, root.get("status").in(status));
            } else {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status[0]));
            }
        }
        if (store != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
        }
        if (member != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
        }
        if (product != null) {
            Subquery<Sku> skuSubquery = criteriaQuery.subquery(Sku.class);
            Root<Sku> skuSubqueryRoot = skuSubquery.from(Sku.class);
            skuSubquery.select(skuSubqueryRoot);
            skuSubquery.where(criteriaBuilder.equal(skuSubqueryRoot.get("product"), product));

            Subquery<OrderItem> orderItemSubquery = criteriaQuery.subquery(OrderItem.class);
            Root<OrderItem> orderItemSubqueryRoot = orderItemSubquery.from(OrderItem.class);
            orderItemSubquery.select(orderItemSubqueryRoot);
            orderItemSubquery.where(criteriaBuilder.equal(orderItemSubqueryRoot.get("order"), root), criteriaBuilder.in(orderItemSubqueryRoot.get("sku")).value(skuSubquery));
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(orderItemSubquery));
        }
        if (isPendingReceive != null) {
            Predicate predicate = criteriaBuilder.and(criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date>get("expire"), new Date())), criteriaBuilder.equal(root.get("paymentMethodType"), PaymentMethod.Type.CASH_ON_DELIVERY),
                    criteriaBuilder.notEqual(root.get("status"), Order.Status.COMPLETED), criteriaBuilder.notEqual(root.get("status"), Order.Status.FAILED), criteriaBuilder.notEqual(root.get("status"), Order.Status.CANCELED), criteriaBuilder.notEqual(root.get("status"), Order.Status.DENIED),
                    criteriaBuilder.lessThan(root.<BigDecimal>get("amountPaid"), root.<BigDecimal>get("amount")));
            if (isPendingReceive) {
                restrictions = criteriaBuilder.and(restrictions, predicate);
            } else {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
            }
        }
        if (isPendingRefunds != null) {
            Predicate predicate = criteriaBuilder.or(
                    criteriaBuilder.and(criteriaBuilder.or(criteriaBuilder.and(root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date>get("expire"), new Date())), criteriaBuilder.equal(root.get("status"), Order.Status.FAILED),
                            criteriaBuilder.equal(root.get("status"), Order.Status.CANCELED), criteriaBuilder.equal(root.get("status"), Order.Status.DENIED)), criteriaBuilder.greaterThan(root.<BigDecimal>get("amountPaid"), BigDecimal.ZERO)),
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), Order.Status.COMPLETED), criteriaBuilder.greaterThan(root.<BigDecimal>get("amountPaid"), root.<BigDecimal>get("amount"))));
            if (isPendingRefunds) {
                restrictions = criteriaBuilder.and(restrictions, predicate);
            } else {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
            }
        }
        if (isUseCouponCode != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isUseCouponCode"), isUseCouponCode));
        }
        if (isExchangePoint != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isExchangePoint"), isExchangePoint));
        }
        if (isAllocatedStock != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isAllocatedStock"), isAllocatedStock));
        }
        if (hasExpired != null) {
            if (hasExpired) {
                restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date>get("expire"), new Date()));
            } else {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date>get("expire"), new Date())));
            }
        }
        criteriaQuery.where(restrictions);
        return super.count(criteriaQuery, null);
    }

    @Override
    public Long createOrderCount(Store store, Date beginDate, Date endDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (store != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
        }
        if (beginDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createdDate"), beginDate));
        }
        if (endDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get("createdDate"), endDate));
        }
        criteriaQuery.where(restrictions);
        return super.count(criteriaQuery, null);
    }

    @Override
    public Long completeOrderCount(Store store, Date beginDate, Date endDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (store != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
        }
        if (beginDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("completeDate"), beginDate));
        }
        if (endDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get("completeDate"), endDate));
        }
        criteriaQuery.where(restrictions);
        return super.count(criteriaQuery, null);
    }

    @Override
    public BigDecimal createOrderAmount(Store store, Date beginDate, Date endDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(criteriaBuilder.sum(root.<BigDecimal>get("amount")));
        Predicate restrictions = criteriaBuilder.conjunction();
        if (store != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
        }
        if (beginDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createdDate"), beginDate));
        }
        if (endDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get("createdDate"), endDate));
        }
        criteriaQuery.where(restrictions);
        BigDecimal result = entityManager.createQuery(criteriaQuery).getSingleResult();
        return result != null ? result : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal completeOrderAmount(Store store, Date beginDate, Date endDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(criteriaBuilder.sum(root.<BigDecimal>get("amount")));
        Predicate restrictions = criteriaBuilder.conjunction();
        if (store != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
        }
        if (beginDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("completeDate"), beginDate));
        }
        if (endDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get("completeDate"), endDate));
        }
        criteriaQuery.where(restrictions);
        BigDecimal result = entityManager.createQuery(criteriaQuery).getSingleResult();
        return result != null ? result : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal grantedCommissionTotalAmount(Store store, CommissionType commissionType, Date beginDate, Date endDate, Status... statuses) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
        Root<OrderItem> root = criteriaQuery.from(OrderItem.class);
        switch (commissionType) {
            case PLATFORM:
                criteriaQuery.select(criteriaBuilder.sum(root.<BigDecimal>get("platformCommissionTotals")));
                break;
            case DISTRIBUTION:
                criteriaQuery.select(criteriaBuilder.sum(root.<BigDecimal>get("distributionCommissionTotals")));
                break;
        }
        Predicate restrictions = criteriaBuilder.conjunction();

        Subquery<Order> orderSubquery = criteriaQuery.subquery(Order.class);
        Root<Order> orderSubqueryRoot = orderSubquery.from(Order.class);
        orderSubquery.select(orderSubqueryRoot);
        Predicate orderSubqueryRestrictions = criteriaBuilder.conjunction();
        if (store != null) {
            orderSubqueryRestrictions = criteriaBuilder.and(orderSubqueryRestrictions, criteriaBuilder.equal(orderSubqueryRoot.get("store"), store));
        }
        if (beginDate != null) {
            orderSubqueryRestrictions = criteriaBuilder.and(orderSubqueryRestrictions, criteriaBuilder.greaterThanOrEqualTo(orderSubqueryRoot.<Date>get("completeDate"), beginDate));
        }
        if (endDate != null) {
            orderSubqueryRestrictions = criteriaBuilder.and(orderSubqueryRestrictions, criteriaBuilder.lessThanOrEqualTo(orderSubqueryRoot.<Date>get("completeDate"), endDate));
        }
        if (statuses != null) {
            orderSubqueryRestrictions = criteriaBuilder.and(orderSubqueryRestrictions, criteriaBuilder.in(orderSubqueryRoot.get("status")).value(Arrays.asList(statuses)));
        }
        orderSubquery.where(orderSubqueryRestrictions);

        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("order")).value(orderSubquery));
        criteriaQuery.where(restrictions);
        BigDecimal result = entityManager.createQuery(criteriaQuery).getSingleResult();
        return result != null ? result : BigDecimal.ZERO;
    }


    @Override
    public void modifyStatPath(String statPath, Long orderId){
        String jpql = "UPDATE Order SET statPath = :statPath WHERE id = :id";
        entityManager.createQuery(jpql).setParameter("statPath", statPath)
                .setParameter("id", orderId).executeUpdate();
    }

}