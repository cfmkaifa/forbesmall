/*
 *
 * 
 *
 * 
 */
package net.mall.dao;

import java.util.List;

import net.mall.entity.ArticleCategory;

/**
 * Dao - 文章分类
 * 
 * @author huanghy
 * @version 6.1
 */
public interface ArticleCategoryDao extends BaseDao<ArticleCategory, Long> {

	/**
	 * 查找顶级文章分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级文章分类
	 */
	List<ArticleCategory> findRoots(Integer count);

	/**
	 * 查找上级文章分类
	 * 
	 * @param articleCategory
	 *            文章分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 上级文章分类
	 */
	List<ArticleCategory> findParents(ArticleCategory articleCategory, boolean recursive, Integer count);

	/**
	 * 查找下级文章分类
	 * 
	 * @param articleCategory
	 *            文章分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 下级文章分类
	 */
	List<ArticleCategory> findChildren(ArticleCategory articleCategory, boolean recursive, Integer count);

}