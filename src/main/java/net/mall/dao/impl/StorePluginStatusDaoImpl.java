/*
 *
 * 
 *
 * 
 */
package net.mall.dao.impl;

import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import net.mall.dao.StorePluginStatusDao;
import net.mall.entity.Store;
import net.mall.entity.StorePluginStatus;

/**
 * Dao - 店铺插件状态
 * 
 * @author huanghy
 * @version 6.1
 */
@Repository
public class StorePluginStatusDaoImpl extends BaseDaoImpl<StorePluginStatus, Long> implements StorePluginStatusDao {

	public StorePluginStatus find(Store store, String pluginId) {
		if (store == null || StringUtils.isEmpty(pluginId)) {
			return null;
		}
		try {
			String jpql = "select storePluginStatus from StorePluginStatus storePluginStatus where storePluginStatus.store = :store and storePluginStatus.pluginId = :pluginId";
			return entityManager.createQuery(jpql, StorePluginStatus.class).setParameter("store", store).setParameter("pluginId", pluginId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}