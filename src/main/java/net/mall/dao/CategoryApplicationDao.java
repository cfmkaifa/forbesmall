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
import net.mall.entity.CategoryApplication;
import net.mall.entity.ProductCategory;
import net.mall.entity.Store;

/**
 * Dao - 经营分类申请
 *
 * @author huanghy
 * @version 6.1
 */
public interface CategoryApplicationDao extends BaseDao<CategoryApplication, Long> {

    /**
     * 查找经营分类申请
     *
     * @param store           店铺
     * @param productCategory 经营分类
     * @param status          状态
     * @return 经营分类申请
     */
    List<CategoryApplication> findList(Store store, ProductCategory productCategory, CategoryApplication.Status status);

    /**
     * 查找经营分类申请分页
     *
     * @param status          状态
     * @param store           店铺
     * @param productCategory 经营分类
     * @param pageable        分页
     * @return 经营分类申请分页
     */
    Page<CategoryApplication> findPage(CategoryApplication.Status status, Store store, ProductCategory productCategory, Pageable pageable);

    /**
     * 查找经营分类申请数量
     *
     * @param status          状态
     * @param store           店铺
     * @param productCategory 经营分类
     * @return 经营分类申请数量
     */
    Long count(CategoryApplication.Status status, Store store, ProductCategory productCategory);

}