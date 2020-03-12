/*
 *
 *
 *
 *
 */
package net.mall.dao;

import java.util.List;

import net.mall.Filter;
import net.mall.Order;
import net.mall.entity.Brand;
import net.mall.entity.ProductCategory;

/**
 * Dao - 品牌
 *
 * @author huanghy
 * @version 6.1
 */
public interface BrandDao extends BaseDao<Brand, Long> {

    /**
     * 查找品牌
     *
     * @param productCategory 商品分类
     * @param count           数量
     * @param filters         筛选
     * @param orders          排序
     * @return 品牌
     */
    List<Brand> findList(ProductCategory productCategory, Integer count, List<Filter> filters, List<Order> orders);

}