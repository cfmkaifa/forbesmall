/*
 *
 *
 *
 *
 */
package net.mall.service;

import java.util.List;

import net.mall.entity.ArticleCategory;

/**
 * Service - 文章分类
 *
 * @author huanghy
 * @version 6.1
 */
public interface ArticleCategoryService extends BaseService<ArticleCategory, Long> {

    /**
     * 查找顶级文章分类
     *
     * @return 顶级文章分类
     */
    List<ArticleCategory> findRoots(ArticleCategory.Type type);

    /**
     * 查找顶级文章分类
     *
     * @param count 数量
     * @return 顶级文章分类
     */
    List<ArticleCategory> findRoots(Integer count, ArticleCategory.Type type);

    /**
     * 查找顶级文章分类
     *
     * @param count    数量
     * @param useCache 是否使用缓存
     * @return 顶级文章分类
     */
    List<ArticleCategory> findRoots(Integer count, boolean useCache, ArticleCategory.Type type);


    /**
     * @return net.mall.entity.ArticleCategory
     * @Author xfx
     * @Date 18:30 2020/1/15
     * @Param [articleCategoryId, type]
     **/
    ArticleCategory findArticleCategory(Long articleCategoryId, ArticleCategory.Type type);

    /**
     * 查找上级文章分类
     *
     * @param articleCategory 文章分类
     * @param recursive       是否递归
     * @param count           数量
     * @return 上级文章分类
     */
    List<ArticleCategory> findParents(ArticleCategory articleCategory, boolean recursive, Integer count);

    /**
     * 查找上级文章分类
     *
     * @param articleCategoryId 文章分类ID
     * @param recursive         是否递归
     * @param count             数量
     * @param useCache          是否使用缓存
     * @return 上级文章分类
     */
    List<ArticleCategory> findParents(Long articleCategoryId, boolean recursive, Integer count, boolean useCache);

    /**
     * 查找文章分类树
     *
     * @return 文章分类树
     */
    List<ArticleCategory> findTree();

    /**
     * 查找下级文章分类
     *
     * @param articleCategory 文章分类
     * @param recursive       是否递归
     * @param count           数量
     * @return 下级文章分类
     */
    List<ArticleCategory> findChildren(ArticleCategory articleCategory, boolean recursive, Integer count);

    /**
     * 查找下级文章分类
     *
     * @param articleCategoryId 文章分类ID
     * @param recursive         是否递归
     * @param count             数量
     * @param useCache          是否使用缓存
     * @return 下级文章分类
     */
    List<ArticleCategory> findChildren(Long articleCategoryId, boolean recursive, Integer count, boolean useCache);

}