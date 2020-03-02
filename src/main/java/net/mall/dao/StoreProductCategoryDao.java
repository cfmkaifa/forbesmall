/*
 *
 *
 *
 *
 */
package net.mall.dao;

import java.util.List;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.Store;
import net.mall.entity.StoreProductCategory;

/**
 * Dao - 店铺商品分类
 *
 * @author huanghy
 * @version 6.1
 */
public interface StoreProductCategoryDao extends BaseDao<StoreProductCategory, Long> {

    /**
     * 查找顶级店铺商品分类
     *
     * @param store 店铺
     * @param count 数量
     * @return 顶级店铺商品分类
     */
    List<StoreProductCategory> findRoots(Store store, Integer count);

    /**
     * 查找上级店铺商品分类
     *
     * @param storeProductCategory 店铺商品分类
     * @param recursive            是否递归
     * @param count                数量
     * @return 上级店铺商品分类
     */
    List<StoreProductCategory> findParents(StoreProductCategory storeProductCategory, boolean recursive, Integer count);

    /**
     * 查找下级店铺商品分类
     *
     * @param storeProductCategory 店铺商品分类
     * @param store                店铺
     * @param recursive            是否递归
     * @param count                数量
     * @return 下级店铺商品分类
     */
    List<StoreProductCategory> findChildren(StoreProductCategory storeProductCategory, Store store, boolean recursive, Integer count);

    /**
     * 查找店铺商品分类
     *
     * @param store    店铺
     * @param pageable 分页
     * @return 店铺商品分类
     */
    Page<StoreProductCategory> findPage(Store store, Pageable pageable);
}