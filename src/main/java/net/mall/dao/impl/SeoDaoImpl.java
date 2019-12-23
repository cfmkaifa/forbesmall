/*
 *
 * 
 *
 * 
 */
package net.mall.dao.impl;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import net.mall.dao.SeoDao;
import net.mall.entity.Seo;

/**
 * Dao - SEO设置
 * 
 * @author huanghy
 * @version 6.1
 */
@Repository
public class SeoDaoImpl extends BaseDaoImpl<Seo, Long> implements SeoDao {

	@Override
	public Seo find(Seo.Type type) {
		if (type == null) {
			return null;
		}
		try {
			String jpql = "select seo from Seo seo where seo.type = :type";
			return entityManager.createQuery(jpql, Seo.class).setParameter("type", type).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}