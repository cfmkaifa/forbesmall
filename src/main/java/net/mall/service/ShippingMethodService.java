/*
 *
 *
 *
 *
 */
package net.mall.service;

import java.math.BigDecimal;

import net.mall.entity.Area;
import net.mall.entity.Receiver;
import net.mall.entity.ShippingMethod;
import net.mall.entity.Store;

/**
 * Service - 配送方式
 *
 * @author huanghy
 * @version 6.1
 */
public interface ShippingMethodService extends BaseService<ShippingMethod, Long> {

    /**
     * 计算运费
     *
     * @param shippingMethod 配送方式
     * @param store          店铺
     * @param area           地区
     * @param weight         重量
     * @return 运费
     */
    BigDecimal calculateFreight(ShippingMethod shippingMethod, Store store, Area area, Integer weight);

    /**
     * 计算运费
     *
     * @param shippingMethod 配送方式
     * @param store          店铺
     * @param receiver       收货地址
     * @param weight         重量
     * @return 运费
     */
    BigDecimal calculateFreight(ShippingMethod shippingMethod, Store store, Receiver receiver, Integer weight);
}