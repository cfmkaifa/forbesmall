/*
 *
 * 
 *
 * 
 */
package net.mall.dao;

import net.mall.entity.Store;
import net.mall.entity.StorePluginStatus;

/**
 * Dao - 店铺插件状态
 * 
 * @author huanghy
 * @version 6.1
 */
public interface StorePluginStatusDao extends BaseDao<StorePluginStatus, Long> {

	/**
	 * 查找店铺插件状态
	 * 
	 * @param store
	 *            店铺
	 * @param pluginId
	 *            插件ID
	 * @return 店铺插件状态，若不存在则返回null
	 */
	StorePluginStatus find(Store store, String pluginId);
}