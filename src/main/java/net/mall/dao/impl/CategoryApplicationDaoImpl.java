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

import net.mall.Page;
import net.mall.Pageable;
import net.mall.dao.CategoryApplicationDao;
import net.mall.entity.CategoryApplication;
import net.mall.entity.CategoryApplication.Status;
import net.mall.entity.ProductCategory;
import net.mall.entity.Store;

/**
 * Dao - 经营分类申请
 * 
 * @author huanghy
 * @version 6.1
 */
@Repository
public class CategoryApplicationDaoImpl extends BaseDaoImpl<CategoryApplication, Long> implements CategoryApplicationDao {

	@Override
	public List<CategoryApplication> findList(Store store, ProductCategory productCategory, CategoryApplication.Status status) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CategoryApplication> criteriaQuery = criteriaBuilder.createQuery(CategoryApplication.class);
		Root<CategoryApplication> root = criteriaQuery.from(CategoryApplication.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("productCategory"), productCategory));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery);
	}

	@Override
	public Page<CategoryApplication> findPage(Status status, Store store, ProductCategory productCategory, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CategoryApplication> criteriaQuery = criteriaBuilder.createQuery(CategoryApplication.class);
		Root<CategoryApplication> root = criteriaQuery.from(CategoryApplication.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("productCategory"), productCategory));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public Long count(Status status, Store store, ProductCategory productCategory) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CategoryApplication> criteriaQuery = criteriaBuilder.createQuery(CategoryApplication.class);
		Root<CategoryApplication> root = criteriaQuery.from(CategoryApplication.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("productCategory"), productCategory));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

}