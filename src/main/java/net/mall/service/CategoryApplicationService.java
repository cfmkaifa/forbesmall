/*
 *
 * 
 *
 * 
 */
package net.mall.service;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.CategoryApplication;
import net.mall.entity.ProductCategory;
import net.mall.entity.Store;

/**
 * Service - 经营分类申请
 * 
 * @author huanghy
 * @version 6.1
 */
public interface CategoryApplicationService extends BaseService<CategoryApplication, Long> {

	/**
	 * 判断经营分类申请是否存在
	 * 
	 * @param store
	 *            店铺
	 * @param productCategory
	 *            经营分类
	 * @param status
	 *            状态
	 * @return 经营分类申请是否存在
	 */
	boolean exist(Store store, ProductCategory productCategory, CategoryApplication.Status status);

	/**
	 * 查找经营分类申请分页
	 * 
	 * @param status
	 *            状态
	 * @param store
	 *            店铺
	 * @param productCategory
	 *            经营分类
	 * @param pageable
	 *            分页
	 * @return 经营分类申请分页
	 */
	Page<CategoryApplication> findPage(CategoryApplication.Status status, Store store, ProductCategory productCategory, Pageable pageable);

	/**
	 * 审核经营分类申请
	 * 
	 * @param categoryApplication
	 *            经营分类申请
	 * @param isPassed
	 *            是否审核通过
	 */
	void review(CategoryApplication categoryApplication, boolean isPassed);

	/**
	 * 查找经营分类申请数量
	 * 
	 * @param status
	 *            状态
	 * @param store
	 *            店铺
	 * @param productCategory
	 *            经营分类
	 * @return 经营分类申请数量
	 */
	Long count(CategoryApplication.Status status, Store store, ProductCategory productCategory);

	/**
	 * 查找经营分类申请数量
	 * 
	 * @param status
	 *            状态
	 * @param storeId
	 *            店铺ID
	 * @param productCategoryId
	 *            经营分类ID
	 * @return 经营分类申请数量
	 */
	Long count(CategoryApplication.Status status, Long storeId, Long productCategoryId);

}