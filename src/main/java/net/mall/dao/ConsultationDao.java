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
import net.mall.entity.Consultation;
import net.mall.entity.Member;
import net.mall.entity.Product;
import net.mall.entity.Store;

/**
 * Dao - 咨询
 *
 * @author huanghy
 * @version 6.1
 */
public interface ConsultationDao extends BaseDao<Consultation, Long> {

    /**
     * 查找咨询
     *
     * @param member  会员
     * @param product 商品
     * @param isShow  是否显示
     * @param count   数量
     * @param filters 筛选
     * @param orders  排序
     * @return 咨询，不包含咨询回复
     */
    List<Consultation> findList(Member member, Product product, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders);

    /**
     * 查找咨询分页
     *
     * @param member   会员
     * @param product  商品
     * @param store    店铺
     * @param isShow   是否显示
     * @param pageable 分页信息
     * @return 咨询分页，不包含咨询回复
     */
    Page<Consultation> findPage(Member member, Product product, Store store, Boolean isShow, Pageable pageable);

    /**
     * 查找咨询数量
     *
     * @param member  会员
     * @param product 商品
     * @param isShow  是否显示
     * @return 咨询数量，不包含咨询回复
     */
    Long count(Member member, Product product, Boolean isShow);

}