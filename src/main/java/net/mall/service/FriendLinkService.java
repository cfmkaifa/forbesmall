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
import net.mall.entity.FriendLink;

/**
 * Service - 友情链接
 *
 * @author huanghy
 * @version 6.1
 */
public interface FriendLinkService extends BaseService<FriendLink, Long> {

    /**
     * 查找友情链接
     *
     * @param type 类型
     * @return 友情链接
     */
    List<FriendLink> findList(FriendLink.Type type);

    /**
     * 查找友情链接
     *
     * @param count    数量
     * @param filters  筛选
     * @param orders   排序
     * @param useCache 是否使用缓存
     * @return 友情链接
     */
    List<FriendLink> findList(Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

}