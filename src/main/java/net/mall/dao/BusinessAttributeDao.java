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
import net.mall.entity.BusinessAttribute;

/**
 * Dao - 商家注册项
 *
 * @author huanghy
 * @version 6.1
 */
public interface BusinessAttributeDao extends BaseDao<BusinessAttribute, Long> {

    /**
     * 查找未使用的对象属性序号
     *
     * @return 未使用的对象属性序号，若无可用序号则返回null
     */
    Integer findUnusedPropertyIndex();

    /**
     * 查找商家注册项
     *
     * @param isEnabled 是否启用
     * @param count     数量
     * @param filters   筛选
     * @param orders    排序
     * @return 会员注册项
     */
    List<BusinessAttribute> findList(Boolean isEnabled, Integer count, List<Filter> filters, List<Order> orders);

    /**
     * 清空商家注册项值
     *
     * @param businessAttribute 商家注册项
     */
    void clearAttributeValue(BusinessAttribute businessAttribute);

}