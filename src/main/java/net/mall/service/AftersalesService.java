/*
 *
 *
 *
 *
 */
package net.mall.service;

import java.util.List;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.Aftersales;
import net.mall.entity.AftersalesItem;
import net.mall.entity.AftersalesReturns;
import net.mall.entity.Member;
import net.mall.entity.OrderItem;
import net.mall.entity.Store;

/**
 * Service - 售后
 *
 * @author huanghy
 * @version 6.1
 */
public interface AftersalesService extends BaseService<Aftersales, Long> {

    /**
     * 查找售后列表
     *
     * @param orderItems 订单项
     * @return 售后列表
     */
    List<Aftersales> findList(List<OrderItem> orderItems);

    /**
     * 查找售后分页
     *
     * @param type     类型
     * @param status   状态
     * @param member   会员
     * @param store    店铺
     * @param pageable 分页信息
     * @return 售后分页
     */
    Page<Aftersales> findPage(Aftersales.Type type, Aftersales.Status status, Member member, Store store, Pageable pageable);

    /**
     * 审核
     *
     * @param aftersales 售后
     * @param passed     是否审核成功
     */
    void review(Aftersales aftersales, boolean passed);

    /**
     * 完成
     *
     * @param aftersales 售后
     */
    void complete(Aftersales aftersales);

    /**
     * 完成退货
     *
     * @param aftersalesReturns 退货
     */
    void completeReturns(AftersalesReturns aftersalesReturns);

    /**
     * 取消
     *
     * @param aftersales 售后
     */
    void cancle(Aftersales aftersales);

    /**
     * 筛选失效售后项
     *
     * @param aftersales 售后
     */
    void filterNotActiveAftersalesItem(Aftersales aftersales);

    /**
     * 判断是否存在异常售后项
     *
     * @param aftersalesItems 售后项
     * @return 是否存在异常售后项
     */
    boolean existsIllegalAftersalesItems(List<AftersalesItem> aftersalesItems);

}