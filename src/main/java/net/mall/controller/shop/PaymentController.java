/*
 *
 *
 *
 *
 */
package net.mall.controller.shop;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mall.entity.Area;
import net.mall.entity.Order;
import net.mall.util.ConvertUtils;
import net.mall.util.SensorsAnalyticsUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.mall.entity.PaymentItem;
import net.mall.entity.PaymentTransaction;
import net.mall.entity.PaymentTransaction.LineItem;
import net.mall.plugin.PaymentPlugin;
import net.mall.service.PaymentTransactionService;
import net.mall.service.PluginService;
import net.mall.util.SpringUtils;

/**
 * Controller - 支付
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("shopPaymentController")
@RequestMapping("/payment")
public class PaymentController extends BaseController {

    @Inject
    private PluginService pluginService;
    @Inject
    private PaymentTransactionService paymentTransactionService;
    @Inject
    SensorsAnalyticsUtils sensorsAnalyticsUtils;

    /**
     * 是否支付成功
     */
    @GetMapping("/is_pay_success")
    public ResponseEntity<?> isPaySuccess(String paymentTransactionSn) {
        Map<String, Object> data = new HashMap<>();
        PaymentTransaction paymentTransaction = paymentTransactionService.findBySn(paymentTransactionSn);
        data.put("isPaySuccess", paymentTransaction != null && BooleanUtils.isTrue(paymentTransaction.getIsSuccess()));
        return ResponseEntity.ok(data);
    }

    /**
     * 首页
     */
    @RequestMapping
    public String index(String paymentPluginId, String rePayUrl, PaymentItemListForm paymentItemListForm) {
        PaymentPlugin paymentPlugin = pluginService.getPaymentPlugin(paymentPluginId);
        if (paymentPlugin == null || BooleanUtils.isNotTrue(paymentPlugin.getIsEnabled()) || StringUtils.isEmpty(rePayUrl)) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }
        List<PaymentItem> paymentItems = paymentItemListForm != null ? paymentItemListForm.getPaymentItemList() : null;
        if (CollectionUtils.isEmpty(paymentItems)) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }

        PaymentTransaction paymentTransaction = null;
        if (paymentItems.size() > 1) {
            Set<PaymentTransaction.LineItem> lineItems = new HashSet<>();
            for (PaymentItem paymentItem : paymentItems) {
                LineItem lineItem = paymentTransactionService.generate(paymentItem);
                if (lineItem != null) {
                    lineItems.add(lineItem);
                }
            }
            paymentTransaction = paymentTransactionService.generateParent(lineItems, paymentPlugin, rePayUrl);
        } else {
            PaymentItem paymentItem = paymentItems.get(0);
            LineItem lineItem = paymentTransactionService.generate(paymentItem);
            paymentTransaction = paymentTransactionService.generate(lineItem, paymentPlugin, rePayUrl);
        }
        return "redirect:" + paymentPlugin.getPrePayUrl(paymentPlugin, paymentTransaction);
    }

    /**
     * 支付前处理
     */
    @RequestMapping({"/pre_pay_{paymentTransactionSn:[^_]+}", "/pre_pay_{paymentTransactionSn[^_]+}_{extra}"})
    public ModelAndView prePay(@PathVariable String paymentTransactionSn, @PathVariable(required = false) String extra, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PaymentTransaction paymentTransaction = paymentTransactionService.findBySn(paymentTransactionSn);
        if (paymentTransaction == null || paymentTransaction.hasExpired()) {
            return new ModelAndView(UNPROCESSABLE_ENTITY_VIEW);
        }
        if (paymentTransaction.getIsSuccess()) {
            return new ModelAndView(UNPROCESSABLE_ENTITY_VIEW, "errorMessage", SpringUtils.getMessage("shop.payment.payCompleted"));
        }
        String paymentPluginId = paymentTransaction.getPaymentPluginId();
        PaymentPlugin paymentPlugin = StringUtils.isNotEmpty(paymentPluginId) ? pluginService.getPaymentPlugin(paymentPluginId) : null;
        if (paymentPlugin == null || BooleanUtils.isNotTrue(paymentPlugin.getIsEnabled())) {
            return new ModelAndView(UNPROCESSABLE_ENTITY_VIEW);
        }

        ModelAndView modelAndView = new ModelAndView();
        paymentPlugin.prePayHandle(paymentPlugin, paymentTransaction, getPaymentDescription(paymentTransaction), extra, request, response, modelAndView);
        return modelAndView;
    }

    /**
     * 支付处理
     */
    @RequestMapping({"/pay_{paymentTransactionSn:[^_]+}", "/pay_{paymentTransactionSn[^_]+}_{extra}"})
    public ModelAndView pay(@PathVariable String paymentTransactionSn, @PathVariable(required = false) String extra, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PaymentTransaction paymentTransaction = paymentTransactionService.findBySn(paymentTransactionSn);
        if (paymentTransaction == null || paymentTransaction.hasExpired()) {
            return new ModelAndView(UNPROCESSABLE_ENTITY_VIEW);
        }
        if (paymentTransaction.getIsSuccess()) {
            return new ModelAndView(UNPROCESSABLE_ENTITY_VIEW, "errorMessage", SpringUtils.getMessage("shop.payment.payCompleted"));
        }
        String paymentPluginId = paymentTransaction.getPaymentPluginId();
        PaymentPlugin paymentPlugin = StringUtils.isNotEmpty(paymentPluginId) ? pluginService.getPaymentPlugin(paymentPluginId) : null;
        if (paymentPlugin == null || BooleanUtils.isNotTrue(paymentPlugin.getIsEnabled())) {
            return new ModelAndView(UNPROCESSABLE_ENTITY_VIEW);
        }

        ModelAndView modelAndView = new ModelAndView();
        paymentPlugin.payHandle(paymentPlugin, paymentTransaction, getPaymentDescription(paymentTransaction), extra, request, response, modelAndView);
        return modelAndView;
    }

    /**
     * 支付后处理
     */
    @RequestMapping({"/post_pay_{paymentTransactionSn:[^_]+}", "/post_pay_{paymentTransactionSn:[^_]+}_{extra}"})
    public ModelAndView postPay(@PathVariable String paymentTransactionSn, @PathVariable(required = false) String extra, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PaymentTransaction paymentTransaction = paymentTransactionService.findBySn(paymentTransactionSn);
        if (paymentTransaction == null) {
            return new ModelAndView(UNPROCESSABLE_ENTITY_VIEW);
        }
        String paymentPluginId = paymentTransaction.getPaymentPluginId();
        PaymentPlugin paymentPlugin = StringUtils.isNotEmpty(paymentPluginId) ? pluginService.getPaymentPlugin(paymentPluginId) : null;
        if (paymentPlugin == null || BooleanUtils.isNotTrue(paymentPlugin.getIsEnabled())) {
            return new ModelAndView(UNPROCESSABLE_ENTITY_VIEW);
        }
        boolean isPaySuccess = paymentPlugin.isPaySuccess(paymentPlugin, paymentTransaction, getPaymentDescription(paymentTransaction), extra, request, response);
        if (isPaySuccess) {
            paymentTransactionService.handle(paymentTransaction);
            Order order = paymentTransaction.getOrder();
            /****上报神策数据****/
            if(ConvertUtils.isNotEmpty(order)){
                Map<String,Object> properties = new HashMap<String,Object>();
                properties.put("order_id",order.getSn());
                properties.put("order_amount",order.getAmount().setScale(2, RoundingMode.UP));
                properties.put("payment_method",paymentPlugin.getDisplayName());
                properties.put("pay_type",order.getPaymentMethodName());
                Long memberId = order.getMember().getId();
                properties.put("supplier_id",String.valueOf(order.getStore().getBusiness().getId()));
                properties.put("store_id",String.valueOf(memberId));
                Area area = order.getArea();
                if(ConvertUtils.isNotEmpty(area)){
                    if(ConvertUtils.isNotEmpty(area.getParent())){
                        properties.put("receiver_province",area.getParent().getName());
                    } else {
                        properties.put("receiver_province",area.getName());
                    }
                    properties.put("receiver_city",order.getArea().getName());
                }
                /****设置省市区**/
                else {
                    properties.put("receiver_province",order.getAreaName());
                    properties.put("receiver_city",order.getAreaName());
                }
                properties.put("delivery_method",order.getShippingMethodName());
                sensorsAnalyticsUtils.reportData(String.valueOf(memberId),"PayOrder",properties);
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        paymentPlugin.postPayHandle(paymentPlugin, paymentTransaction, getPaymentDescription(paymentTransaction), extra, isPaySuccess, request, response, modelAndView);
        return modelAndView.hasView() ? modelAndView : null;
    }

    /**
     * 获取支付描述
     *
     * @param paymentTransaction 支付事务
     * @return 支付描述
     */
    private String getPaymentDescription(PaymentTransaction paymentTransaction) {
        Assert.notNull(paymentTransaction, "[Assertion failed] - paymentTransaction is required; it must not be null");
        if (CollectionUtils.isEmpty(paymentTransaction.getChildren())) {
            Assert.notNull(paymentTransaction.getType(), "[Assertion failed] - paymentTransaction type is required; it must not be null");
        } else {
            return SpringUtils.getMessage("shop.payment.paymentDescription", paymentTransaction.getSn());
        }

        switch (paymentTransaction.getType()) {
            case ORDER_PAYMENT:
                return SpringUtils.getMessage("shop.payment.orderPaymentDescription", paymentTransaction.getOrder().getSn());
            case SVC_PAYMENT:
                return SpringUtils.getMessage("shop.payment.svcPaymentDescription", paymentTransaction.getSvc().getSn());
            case DEPOSIT_RECHARGE:
                return SpringUtils.getMessage("shop.payment.depositRechargeDescription", paymentTransaction.getSn());
            case BAIL_PAYMENT:
                return SpringUtils.getMessage("shop.payment.bailPaymentDescription", paymentTransaction.getSn());
            case NEWS_SUBSCRIBE_PAYMENT:
                return SpringUtils.getMessage("shop.payment.newsSubscribePaymentDescription", paymentTransaction.getSn());
            default:
                return SpringUtils.getMessage("shop.payment.paymentDescription", paymentTransaction.getSn());
        }
    }

    /**
     * FormBean - 支付项
     *
     * @author huanghy
     * @version 6.1
     */
    public static class PaymentItemListForm {

        /**
         * 支付项
         */
        private List<PaymentItem> paymentItemList;

        /**
         * 获取支付项
         *
         * @return 支付项
         */
        public List<PaymentItem> getPaymentItemList() {
            return paymentItemList;
        }

        /**
         * 设置支付项
         *
         * @param paymentItemList 支付项
         */
        public void setPaymentItemList(List<PaymentItem> paymentItemList) {
            this.paymentItemList = paymentItemList;
        }
    }

}