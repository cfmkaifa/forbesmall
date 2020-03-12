/*
 *
 *
 *
 *
 */
package net.mall.service;

import java.util.List;

import net.mall.Filter;
import net.mall.Order;
import net.mall.entity.ProductTag;

/**
 * Service - 商品标签
 *
 * @author huanghy
 * @version 6.1
 */
public interface ProductTagService extends BaseService<ProductTag, Long> {

    /**
     * 查找商品标签
     *
     * @param count    数量
     * @param filters  筛选
     * @param orders   排序
     * @param useCache 是否使用缓存
     * @return 商品标签
     */
    List<ProductTag> findList(Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

}