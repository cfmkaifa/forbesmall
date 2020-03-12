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
import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.Member;
import net.mall.entity.Product;
import net.mall.entity.Review;
import net.mall.entity.Store;

/**
 * Dao - 评论
 *
 * @author huanghy
 * @version 6.1
 */
public interface ReviewDao extends BaseDao<Review, Long> {

    /**
     * 查找评论
     *
     * @param member  会员
     * @param product 商品
     * @param type    类型
     * @param isShow  是否显示
     * @param count   数量
     * @param filters 筛选
     * @param orders  排序
     * @return 评论
     */
    List<Review> findList(Member member, Product product, Review.Type type, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders);

    /**
     * 查找评论分页
     *
     * @param member   会员
     * @param product  商品
     * @param store    店铺
     * @param type     类型
     * @param isShow   是否显示
     * @param pageable 分页信息
     * @return 评论分页
     */
    Page<Review> findPage(Member member, Product product, Store store, Review.Type type, Boolean isShow, Pageable pageable);

    /**
     * 查找评论数量
     *
     * @param member  会员
     * @param product 商品
     * @param type    类型
     * @param isShow  是否显示
     * @return 评论数量
     */
    Long count(Member member, Product product, Review.Type type, Boolean isShow);

    /**
     * 计算商品总评分
     *
     * @param product 商品
     * @return 商品总评分，仅计算显示评论
     */
    long calculateTotalScore(Product product);

    /**
     * 计算商品评分次数
     *
     * @param product 商品
     * @return 商品评分次数，仅计算显示评论
     */
    long calculateScoreCount(Product product);

}