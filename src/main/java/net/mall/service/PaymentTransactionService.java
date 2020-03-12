/*
 *
 *
 *
 *
 */
package net.mall.service;

import java.util.Collection;

import net.mall.entity.PaymentItem;
import net.mall.entity.PaymentTransaction;
import net.mall.plugin.PaymentPlugin;

/**
 * Service - 支付事务
 *
 * @author huanghy
 * @version 6.1
 */
public interface PaymentTransactionService extends BaseService<PaymentTransaction, Long> {

    /**
     * 根据编号查找支付事务
     *
     * @param sn 编号(忽略大小写)
     * @return 支付事务，若不存在则返回null
     */
    PaymentTransaction findBySn(String sn);

    /**
     * 生成支付事务
     *
     * @param lineItem      支付明细
     * @param paymentPlugin 支付插件
     * @param rePayUrl      重新支付URL
     * @return 支付事务
     */
    PaymentTransaction generate(PaymentTransaction.LineItem lineItem, PaymentPlugin paymentPlugin, String rePayUrl);

    /**
     * 生成父支付事务
     *
     * @param lineItems     支付明细
     * @param paymentPlugin 支付插件
     * @param rePayUrl      重新支付URL
     * @return 父支付事务
     */
    PaymentTransaction generateParent(Collection<PaymentTransaction.LineItem> lineItems, PaymentPlugin paymentPlugin, String rePayUrl);

    /**
     * 支付处理
     *
     * @param paymentTransaction 支付事务
     */
    void handle(PaymentTransaction paymentTransaction);

    /**
     * 生成支付明细
     *
     * @param paymentItem 支付项
     * @return 支付明细
     */
    PaymentTransaction.LineItem generate(PaymentItem paymentItem);

}