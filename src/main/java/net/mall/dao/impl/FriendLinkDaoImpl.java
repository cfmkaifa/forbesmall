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
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import net.mall.dao.FriendLinkDao;
import net.mall.entity.FriendLink;

/**
 * Dao - 友情链接
 * 
 * @author huanghy
 * @version 6.1
 */
@Repository
public class FriendLinkDaoImpl extends BaseDaoImpl<FriendLink, Long> implements FriendLinkDao {

	@Override
	public List<FriendLink> findList(FriendLink.Type type) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<FriendLink> criteriaQuery = criteriaBuilder.createQuery(FriendLink.class);
		Root<FriendLink> root = criteriaQuery.from(FriendLink.class);
		criteriaQuery.select(root);
		if (type != null) {
			criteriaQuery.where(criteriaBuilder.equal(root.get("type"), type));
		}
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("order")));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

}