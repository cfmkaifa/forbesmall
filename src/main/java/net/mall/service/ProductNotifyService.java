/*
 *
 * 
 *
 * 
 */
package net.mall.service;

import java.util.List;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.Member;
import net.mall.entity.ProductNotify;
import net.mall.entity.Sku;
import net.mall.entity.Store;

/**
 * Service - 到货通知
 * 
 * @author huanghy
 * @version 6.1
 */
public interface ProductNotifyService extends BaseService<ProductNotify, Long> {

	/**
	 * 判断到货通知是否存在
	 * 
	 * @param sku
	 *            SKU
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return 到货通知是否存在
	 */
	boolean exists(Sku sku, String email);

	/**
	 * 查找到货通知分页
	 * 
	 * @param store
	 *            店铺
	 * @param member
	 *            会员
	 * @param isMarketable
	 *            是否上架
	 * @param isOutOfStock
	 *            SKU是否缺货
	 * @param hasSent
	 *            是否已发送.
	 * @param pageable
	 *            分页信息
	 * @return 到货通知分页
	 */
	Page<ProductNotify> findPage(Store store, Member member, Boolean isMarketable, Boolean isOutOfStock, Boolean hasSent, Pageable pageable);

	/**
	 * 查找到货通知数量
	 * 
	 * @param member
	 *            会员
	 * @param isMarketable
	 *            是否上架
	 * @param isOutOfStock
	 *            SKU是否缺货
	 * @param hasSent
	 *            是否已发送.
	 * @return 到货通知数量
	 */
	Long count(Member member, Boolean isMarketable, Boolean isOutOfStock, Boolean hasSent);

	/**
	 * 发送到货通知
	 * 
	 * @param productNotifies
	 *            到货通知
	 * @return 发送到货通知数
	 */
	int send(List<ProductNotify> productNotifies);

}