/*
 *
 * 
 *
 * 
 */
package net.mall.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.dao.StockLogDao;
import net.mall.entity.StockLog;
import net.mall.entity.Store;

/**
 * Dao - 库存记录
 * 
 * @author huanghy
 * @version 6.1
 */
@Repository
public class StockLogDaoImpl extends BaseDaoImpl<StockLog, Long> implements StockLogDao {

	@Override
	public Page<StockLog> findPage(Store store, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<StockLog> criteriaQuery = criteriaBuilder.createQuery(StockLog.class);
		Root<StockLog> root = criteriaQuery.from(StockLog.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("sku").get("product").get("store"), store));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

}