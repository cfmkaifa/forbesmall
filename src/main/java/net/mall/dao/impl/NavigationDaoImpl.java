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

import net.mall.Filter;
import net.mall.Order;
import net.mall.dao.NavigationDao;
import net.mall.entity.Navigation;
import net.mall.entity.NavigationGroup;

/**
 * Dao - 导航
 * 
 * @author huanghy
 * @version 6.1
 */
@Repository
public class NavigationDaoImpl extends BaseDaoImpl<Navigation, Long> implements NavigationDao {

	public List<Navigation> findList(NavigationGroup navigationGroup, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Navigation> criteriaQuery = criteriaBuilder.createQuery(Navigation.class);
		Root<Navigation> root = criteriaQuery.from(Navigation.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (navigationGroup != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("navigationGroup"), navigationGroup));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

}