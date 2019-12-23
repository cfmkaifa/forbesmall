/*
 *
 * 
 *
 * 
 */
package net.mall.service;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.Area;
import net.mall.entity.DefaultFreightConfig;
import net.mall.entity.ShippingMethod;
import net.mall.entity.Store;

/**
 * Service - 默认运费配置
 * 
 * @author huanghy
 * @version 6.1
 */
public interface DefaultFreightConfigService extends BaseService<DefaultFreightConfig, Long> {

	/**
	 * 判断运费配置是否存在
	 * 
	 * @param shippingMethod
	 *            配送方式
	 * @param area
	 *            地区
	 * @return 运费配置是否存在
	 */
	boolean exists(ShippingMethod shippingMethod, Area area);

	/**
	 * 判断运费配置是否唯一
	 * 
	 * @param shippingMethod
	 *            配送方式
	 * @param previousArea
	 *            修改前地区
	 * @param currentArea
	 *            当前地区
	 * @return 运费配置是否唯一
	 */
	boolean unique(ShippingMethod shippingMethod, Area previousArea, Area currentArea);

	/**
	 * 查找运费配置分页
	 * 
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页信息
	 * @return 运费配置分页
	 */
	Page<DefaultFreightConfig> findPage(Store store, Pageable pageable);

	/**
	 * 查找默认运费配置
	 * 
	 * @param shippingMethod
	 *            配送方式
	 * @param store
	 *            店铺
	 * @return 默认运费配置
	 */
	DefaultFreightConfig find(ShippingMethod shippingMethod, Store store);

	/**
	 * 更新
	 * 
	 * @param defaultFreightConfig
	 *            默认运费配置
	 * @param store
	 *            店铺
	 * @param shippingMethod
	 *            配送方式
	 */
	void update(DefaultFreightConfig defaultFreightConfig, Store store, ShippingMethod shippingMethod);

}