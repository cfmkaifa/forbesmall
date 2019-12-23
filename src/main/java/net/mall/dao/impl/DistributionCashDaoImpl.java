/*
 *
 * 
 *
 * 
 */
package net.mall.dao.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.dao.DistributionCashDao;
import net.mall.entity.DistributionCash;
import net.mall.entity.DistributionCash.Status;
import net.mall.entity.Distributor;

/**
 * Dao - 分销提现
 * 
 * @author huanghy
 * @version 6.1
 */
@Repository
public class DistributionCashDaoImpl extends BaseDaoImpl<DistributionCash, Long> implements DistributionCashDao {

	@Override
	public Page<DistributionCash> findPage(Status status, String bank, String account, String accountHolder, Distributor distributor, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DistributionCash> criteriaQuery = criteriaBuilder.createQuery(DistributionCash.class);
		Root<DistributionCash> root = criteriaQuery.from(DistributionCash.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (bank != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("bank"), bank));
		}
		if (account != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("account"), account));
		}
		if (accountHolder != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("accountHolder"), accountHolder));
		}
		if (distributor != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("distributor"), distributor));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public BigDecimal cashTotalAmount(Date beginDate, Date endDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
		Root<DistributionCash> root = criteriaQuery.from(DistributionCash.class);
		criteriaQuery.select(criteriaBuilder.sum(root.<BigDecimal>get("amount")));
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), DistributionCash.Status.APPROVED));
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
	public Long count(Status status, String bank, String account, String accountHolder, Distributor distributor) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DistributionCash> criteriaQuery = criteriaBuilder.createQuery(DistributionCash.class);
		Root<DistributionCash> root = criteriaQuery.from(DistributionCash.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (bank != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("bank"), bank));
		}
		if (account != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("account"), account));
		}
		if (accountHolder != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("accountHolder"), accountHolder));
		}
		if (distributor != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("distributor"), distributor));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

}