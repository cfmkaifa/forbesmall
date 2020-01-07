/*
 *
 * 
 *
 * 
 */
package net.mall.dao.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import net.mall.dao.PaymentTransactionDao;
import net.mall.entity.Order;
import net.mall.entity.PaymentTransaction;
import net.mall.entity.PaymentTransaction.LineItem;
import net.mall.plugin.PaymentPlugin;

/**
 * Dao - 支付事务
 * 
 * @author huanghy
 * @version 6.1
 */
@Repository
public class PaymentTransactionDaoImpl extends BaseDaoImpl<PaymentTransaction, Long> implements PaymentTransactionDao {

	@Override
	public PaymentTransaction findAvailable(PaymentTransaction.LineItem lineItem, PaymentPlugin paymentPlugin) {
		Assert.notNull(lineItem, "[Assertion failed] - lineItem is required; it must not be null");
		Assert.notNull(paymentPlugin, "[Assertion failed] - paymentPlugin is required; it must not be null");
		Assert.notNull(lineItem.getAmount(), "[Assertion failed] - lineItem amount is required; it must not be null");
		Assert.notNull(lineItem.getType(), "[Assertion failed] - lineItem type is required; it must not be null");
		Assert.notNull(lineItem.getTarget(), "[Assertion failed] - lineItem target is required; it must not be null");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PaymentTransaction> criteriaQuery = criteriaBuilder.createQuery(PaymentTransaction.class);
		Root<PaymentTransaction> root = criteriaQuery.from(PaymentTransaction.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		BigDecimal amount = paymentPlugin.calculateAmount(lineItem.getAmount());
		BigDecimal fee = paymentPlugin.calculateFee(lineItem.getAmount());
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), lineItem.getType()), criteriaBuilder.equal(root.get("amount"), amount), criteriaBuilder.equal(root.get("fee"), fee), criteriaBuilder.equal(root.get("isSuccess"), false),
				criteriaBuilder.equal(root.get("paymentPluginId"), paymentPlugin.getId()), root.get("parent").isNull(), criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date>get("expire"), new Date())));
		switch (lineItem.getType()) {
		case ORDER_PAYMENT:
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("order"), lineItem.getTarget()));
			break;
		case SVC_PAYMENT:
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("svc"), lineItem.getTarget()));
			break;
		case DEPOSIT_RECHARGE:
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("user"), lineItem.getTarget()));
			break;
		case BAIL_PAYMENT:
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), lineItem.getTarget()));
			break;
		}
		try {
			return entityManager.createQuery(criteriaQuery.where(restrictions)).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public PaymentTransaction findAvailableParent(Collection<PaymentTransaction.LineItem> lineItems, PaymentPlugin paymentPlugin) {
		Assert.notEmpty(lineItems, "[Assertion failed] - lineItems must not be empty: it must contain at least 1 element");
		Assert.state(lineItems.size() > 1, "[Assertion failed] - lineItems's size must be greater than 1");
		Assert.notNull(paymentPlugin, "[Assertion failed] - paymentPlugin is required; it must not be null");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PaymentTransaction> criteriaQuery = criteriaBuilder.createQuery(PaymentTransaction.class);
		Root<PaymentTransaction> root = criteriaQuery.from(PaymentTransaction.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date>get("expire"), new Date())), criteriaBuilder.equal(root.get("isSuccess"), false),
				criteriaBuilder.equal(root.get("paymentPluginId"), paymentPlugin.getId()));
		for (LineItem lineItem : lineItems) {
			Assert.notNull(lineItem, "[Assertion failed] - lineItem is required; it must not be null");
			Assert.notNull(lineItem.getAmount(), "[Assertion failed] - lineItem amount is required; it must not be null");
			Assert.notNull(lineItem.getType(), "[Assertion failed] - lineItem type is required; it must not be null");
			Assert.notNull(lineItem.getTarget(), "[Assertion failed] - lineItem target is required; it must not be null");

			Subquery<PaymentTransaction> subquery = criteriaQuery.subquery(PaymentTransaction.class);
			Root<PaymentTransaction> subqueryRoot = subquery.from(PaymentTransaction.class);
			subquery.select(subqueryRoot);
			Predicate subqueryRestrictions = criteriaBuilder.conjunction();
			BigDecimal amount = paymentPlugin.calculateAmount(lineItem.getAmount());
			BigDecimal fee = paymentPlugin.calculateFee(lineItem.getAmount());
			subqueryRestrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(subqueryRoot.get("type"), lineItem.getType()), criteriaBuilder.equal(subqueryRoot.get("amount"), amount), criteriaBuilder.equal(subqueryRoot.get("fee"), fee),
					criteriaBuilder.equal(subqueryRoot.get("parent"), root));
			switch (lineItem.getType()) {
			case ORDER_PAYMENT:
				subqueryRestrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(subqueryRoot.get("order"), lineItem.getTarget()));
				break;
			case SVC_PAYMENT:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("svc"), lineItem.getTarget()));
				break;
			case DEPOSIT_RECHARGE:
				subqueryRestrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(subqueryRoot.get("user"), lineItem.getTarget()));
				break;
			case BAIL_PAYMENT:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), lineItem.getTarget()));
				break;
			}
			subquery.where(subqueryRestrictions);
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		try {
			return entityManager.createQuery(criteriaQuery.where(restrictions)).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}