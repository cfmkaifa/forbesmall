/*
 *
 *
 *
 *
 */
package net.mall.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.CategoryApplication;
import net.mall.entity.ProductCategory;
import net.mall.entity.Store;

/**
 * Dao - 店铺
 *
 * @author huanghy
 * @version 6.1
 */
public interface StoreDao extends BaseDao<Store, Long> {

    /**
     * 查找店铺
     *
     * @param type       类型
     * @param status     状态
     * @param isEnabled  是否启用
     * @param hasExpired 是否过期
     * @param first      起始记录
     * @param count      数量
     * @return 店铺
     */
    List<Store> findList(Store.Type type, Store.Status status, Boolean isEnabled, Boolean hasExpired, Integer first, Integer count);

    /**
     * 查找经营分类
     *
     * @param store  店铺
     * @param status 状态
     * @return 经营分类
     */
    List<ProductCategory> findProductCategoryList(Store store, CategoryApplication.Status status);

    /**
     * 查找店铺分页
     *
     * @param type       类型
     * @param status     状态
     * @param isEnabled  是否启用
     * @param hasExpired 是否过期
     * @param pageable   分页信息
     * @return 店铺分页
     */
    Page<Store> findPage(Store.Type type, Store.Status status, Boolean isEnabled, Boolean hasExpired, Pageable pageable);

    /**
     * 查询已付保证金总额
     *
     * @param beginDate 起始日期
     * @param endDate   结束日期
     * @return 已付保证金总额
     */
    BigDecimal bailPaidTotalAmount(Date beginDate, Date endDate);

    /**
     * 查询已付保证金总额
     *
     * @return 已付保证金总额
     */
    BigDecimal bailPaidTotalAmount();

    /**
     * 查找店铺数量
     *
     * @param type       类型
     * @param status     状态
     * @param isEnabled  是否启用
     * @param hasExpired 是否过期
     * @return 店铺数量
     */
    Long count(Store.Type type, Store.Status status, Boolean isEnabled, Boolean hasExpired);

}