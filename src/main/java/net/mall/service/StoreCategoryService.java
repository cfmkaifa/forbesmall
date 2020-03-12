/*
 *
 *
 *
 *
 */
package net.mall.service;

import net.mall.entity.StoreCategory;

/**
 * Service - 店铺分类
 *
 * @author huanghy
 * @version 6.1
 */
public interface StoreCategoryService extends BaseService<StoreCategory, Long> {

    /**
     * 判断名称是否存在
     *
     * @param name 名称(忽略大小写)
     * @return 名称是否存在
     */
    boolean nameExists(String name);

}