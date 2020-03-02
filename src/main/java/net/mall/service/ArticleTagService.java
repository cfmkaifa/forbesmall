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
import net.mall.entity.ArticleTag;

/**
 * Service - 文章标签
 *
 * @author huanghy
 * @version 6.1
 */
public interface ArticleTagService extends BaseService<ArticleTag, Long> {

    /**
     * 查找文章标签
     *
     * @param count    数量
     * @param filters  筛选
     * @param orders   排序
     * @param useCache 是否使用缓存
     * @return 文章标签
     */
    List<ArticleTag> findList(Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

}