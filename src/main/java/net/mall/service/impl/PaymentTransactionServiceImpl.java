/*
 *
 *
 *
 *
 */
package net.mall.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import javax.inject.Inject;
import javax.persistence.LockModeType;

import net.mall.entity.*;
import net.mall.util.SensorsAnalyticsUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.mall.Filter;
import net.mall.Setting;
import net.mall.dao.PaymentTransactionDao;
import net.mall.dao.SnDao;
import net.mall.entity.PaymentTransaction.LineItem;
import net.mall.entity.PaymentTransaction.NewsSubscribePaymentLineItem;
import net.mall.plugin.PaymentPlugin;
import net.mall.service.BusinessService;
import net.mall.service.MemberService;
import net.mall.service.OrderService;
import net.mall.service.PaymentTransactionService;
import net.mall.service.ProductService;
import net.mall.service.StorePluginStatusService;
import net.mall.service.StoreService;
import net.mall.service.SubsNewsHumanService;
import net.mall.service.SvcService;
import net.mall.service.UserService;
import net.mall.util.ConvertUtils;
import net.mall.util.SystemUtils;

/**
 * Service - 支付事务
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class PaymentTransactionServiceImpl extends BaseServiceImpl<PaymentTransaction, Long> implements PaymentTransactionService {

    @Inject
    private PaymentTransactionDao paymentTransactionDao;
    @Inject
    private SnDao snDao;
    @Inject
    private ProductService productService;
    @Inject
    private OrderService orderService;
    @Inject
    private UserService userService;
    @Inject
    private MemberService memberService;
    @Inject
    private BusinessService businessService;
    @Inject
    private StoreService storeService;
    @Inject
    private SvcService svcService;
    @Inject
    private StorePluginStatusService storePluginStatusService;
    @Inject
    SubsNewsHumanService subsNewsHumanService;
    @Inject
    SensorsAnalyticsUtils sensorsAnalyticsUtils;

    @Override
    @Transactional(readOnly = true)
    public PaymentTransaction findBySn(String sn) {
        return paymentTransactionDao.find("sn", StringUtils.lowerCase(sn));
    }

    @Override
    public PaymentTransaction generate(PaymentTransaction.LineItem lineItem, PaymentPlugin paymentPlugin, String rePayUrl) {
        Assert.notNull(lineItem, "[Assertion failed] - lineItem is required; it must not be null");
        Assert.notNull(paymentPlugin, "[Assertion failed] - paymentPlugin is required; it must not be null");
        Assert.notNull(rePayUrl, "[Assertion failed] - rePayUrl is required; it must not be null");
        Assert.notNull(lineItem.getAmount(), "[Assertion failed] - lineItem amount is required; it must not be null");
        Assert.notNull(lineItem.getType(), "[Assertion failed] - lineItem type is required; it must not be null");
        Assert.notNull(lineItem.getTarget(), "[Assertion failed] - lineItem target is required; it must not be null");

        PaymentTransaction paymentTransaction = paymentTransactionDao.findAvailable(lineItem, paymentPlugin);
        if (paymentTransaction == null) {
            paymentTransaction = new PaymentTransaction();
            paymentTransaction.setSn(snDao.generate(Sn.Type.PAYMENT_TRANSACTION));
            paymentTransaction.setType(lineItem.getType());
            paymentTransaction.setAmount(paymentPlugin.calculateAmount(lineItem.getAmount()));
            paymentTransaction.setFee(paymentPlugin.calculateFee(lineItem.getAmount()));
            paymentTransaction.setIsSuccess(false);
            paymentTransaction.setExpire(DateUtils.addSeconds(new Date(), paymentPlugin.getTimeout()));
            paymentTransaction.setParent(null);
            paymentTransaction.setChildren(null);
            paymentTransaction.setTarget(lineItem.getTarget());
            paymentTransaction.setPaymentPlugin(paymentPlugin);
            paymentTransaction.setRePayUrl(rePayUrl);
            if (lineItem instanceof NewsSubscribePaymentLineItem) {
                NewsSubscribePaymentLineItem newsSubscribePaymentLineItem = (net.mall.entity.PaymentTransaction.NewsSubscribePaymentLineItem) lineItem;
                paymentTransaction.setSubSn(newsSubscribePaymentLineItem.getOrderSn());
            }
            paymentTransactionDao.persist(paymentTransaction);
        }
        return paymentTransaction;
    }

    @Override
    public PaymentTransaction generateParent(Collection<PaymentTransaction.LineItem> lineItems, PaymentPlugin paymentPlugin, String rePayUrl) {
        Assert.notEmpty(lineItems, "[Assertion failed] - lineItems must not be empty: it must contain at least 1 element");
        Assert.notNull(paymentPlugin, "[Assertion failed] - paymentPlugin is required; it must not be null");
        Assert.notNull(rePayUrl, "[Assertion failed] - rePayUrl is required; it must not be null");
        Assert.isTrue(lineItems.size() > 1, "[Assertion failed] - lineItems size must be greater than 1");

        PaymentTransaction parentPaymentTransaction = paymentTransactionDao.findAvailableParent(lineItems, paymentPlugin);
        if (parentPaymentTransaction == null) {
            BigDecimal amount = BigDecimal.ZERO;
            for (PaymentTransaction.LineItem lineItem : lineItems) {
                Assert.notNull(lineItem, "[Assertion failed] - lineItem is required; it must not be null");
                Assert.notNull(lineItem.getAmount(), "[Assertion failed] - lineItem amount is required; it must not be null");
                Assert.notNull(lineItem.getType(), "[Assertion failed] - lineItem type is required; it must not be null");
                Assert.notNull(lineItem.getTarget(), "[Assertion failed] - lineItem target is required; it must not be null");

                amount = amount.add(lineItem.getAmount());
            }

            parentPaymentTransaction = new PaymentTransaction();
            parentPaymentTransaction.setSn(snDao.generate(Sn.Type.PAYMENT_TRANSACTION));
            parentPaymentTransaction.setType(null);
            parentPaymentTransaction.setAmount(paymentPlugin.calculateAmount(amount));
            parentPaymentTransaction.setFee(paymentPlugin.calculateFee(amount));
            parentPaymentTransaction.setIsSuccess(false);
            parentPaymentTransaction.setExpire(DateUtils.addSeconds(new Date(), paymentPlugin.getTimeout()));
            parentPaymentTransaction.setParent(null);
            parentPaymentTransaction.setChildren(null);
            parentPaymentTransaction.setTarget(null);
            parentPaymentTransaction.setPaymentPlugin(paymentPlugin);
            parentPaymentTransaction.setRePayUrl(rePayUrl);
            paymentTransactionDao.persist(parentPaymentTransaction);
            for (PaymentTransaction.LineItem lineItem : lineItems) {
                Assert.notNull(lineItem, "[Assertion failed] - lineItem is required; it must not be null");
                Assert.notNull(lineItem.getAmount(), "[Assertion failed] - lineItem amount is required; it must not be null");
                Assert.notNull(lineItem.getType(), "[Assertion failed] - lineItem type is required; it must not be null");
                Assert.notNull(lineItem.getTarget(), "[Assertion failed] - lineItem target is required; it must not be null");

                PaymentTransaction paymentTransaction = new PaymentTransaction();
                paymentTransaction.setSn(snDao.generate(Sn.Type.PAYMENT_TRANSACTION));
                paymentTransaction.setType(lineItem.getType());
                paymentTransaction.setAmount(paymentPlugin.calculateAmount(lineItem.getAmount()));
                paymentTransaction.setFee(paymentPlugin.calculateFee(lineItem.getAmount()));
                paymentTransaction.setIsSuccess(null);
                paymentTransaction.setExpire(null);
                paymentTransaction.setChildren(null);
                paymentTransaction.setTarget(lineItem.getTarget());
                paymentTransaction.setPaymentPlugin(null);
                paymentTransaction.setParent(parentPaymentTransaction);
                paymentTransactionDao.persist(paymentTransaction);
            }
        }
        return parentPaymentTransaction;
    }

    @Override
    public void handle(PaymentTransaction paymentTransaction) {
        Assert.notNull(paymentTransaction, "[Assertion failed] - paymentTransaction is required; it must not be null");
        if (!LockModeType.PESSIMISTIC_WRITE.equals(paymentTransactionDao.getLockMode(paymentTransaction))) {
            paymentTransactionDao.flush();
            paymentTransactionDao.refresh(paymentTransaction, LockModeType.PESSIMISTIC_WRITE);
        }

        if (BooleanUtils.isNotFalse(paymentTransaction.getIsSuccess())) {
            return;
        }
        Set<PaymentTransaction> paymentTransactions = new HashSet<>();
        Set<PaymentTransaction> childrenList = paymentTransaction.getChildren();
        if (CollectionUtils.isNotEmpty(childrenList)) {
            paymentTransaction.setIsSuccess(true);
            paymentTransactions = childrenList;
        } else {
            paymentTransactions.add(paymentTransaction);
        }
        for (PaymentTransaction transaction : paymentTransactions) {
            Svc svc = transaction.getSvc();
            Store store = transaction.getStore();
            User user = transaction.getUser();
            BigDecimal effectiveAmount = transaction.getEffectiveAmount();
            Assert.notNull(transaction.getType(), "[Assertion failed] - transaction type is required; it must not be null");
            switch (transaction.getType()) {
                case ORDER_PAYMENT:
                    Order order = transaction.getOrder();
                    if (order != null) {
                        OrderPayment orderPayment = new OrderPayment();
                        orderPayment.setMethod(OrderPayment.Method.ONLINE);
                        orderPayment.setPaymentMethod(transaction.getPaymentPluginName());
                        orderPayment.setAmount(transaction.getAmount());
                        orderPayment.setFee(transaction.getFee());
                        orderPayment.setOrder(order);
                        orderService.payment(order, orderPayment);
                    }
                    break;
                case SVC_PAYMENT:
                    if (svc == null || svc.getStore() == null) {
                        break;
                    }
                    store = svc.getStore();
                    Integer durationDays = svc.getDurationDays();
                    if (svc instanceof PlatformSvc) {
                        storeService.addEndDays(store, durationDays);
                        if (Store.Status.APPROVED.equals(store.getStatus()) && !store.hasExpired() && store.getBailPayable().compareTo(BigDecimal.ZERO) == 0) {
                            store.setStatus(Store.Status.SUCCESS);
                        } else {
                            productService.refreshActive(store);
                        }
                        /****平台服务费***/
                        /****上报神策数据****/
                        Map<String,Object> properties = new HashMap<String,Object>();
                        properties.put("present_page","");
                        properties.put("vip_type",store.getStoreRank().getName());
                        BigDecimal duration=transaction.getAmount().setScale(0,RoundingMode.UP).divide(store.getStoreRank().getServiceFee());
                        properties.put("vip_price",transaction.getAmount().setScale(0,RoundingMode.UP));
                        properties.put("years",duration);
                        properties.put("store_id",String.valueOf(store.getId()));
                        properties.put("store_name",store.getName());
                        properties.put("is_success",true);
                        properties.put("service_type","店铺升级");
                        properties.put("fail_reason","");
                        properties.put("pay_type",transaction.getPaymentPluginName());
                        sensorsAnalyticsUtils.reportData(String.valueOf(store.getBusiness().getId()),"vipPay",properties);
                    } else if (svc instanceof PromotionPluginSvc) {
                        String promotionPluginId = ((PromotionPluginSvc) svc).getPromotionPluginId();
                        StorePluginStatus storePluginStatus = storePluginStatusService.find(store, promotionPluginId);
                        if (storePluginStatus != null) {
                            storePluginStatusService.addPluginEndDays(storePluginStatus, durationDays);
                        } else {
                            storePluginStatusService.create(store, promotionPluginId, durationDays);
                        }
                    }
                    break;
                case DEPOSIT_RECHARGE:
                    if (user instanceof Member) {
                        memberService.addBalance((Member) user, effectiveAmount, MemberDepositLog.Type.RECHARGE, null);
                    } else if (user instanceof Business) {
                        businessService.addBalance((Business) user, effectiveAmount, BusinessDepositLog.Type.RECHARGE, null);
                    }
                    break;
                case NEWS_SUBSCRIBE_PAYMENT:
                    List<Filter> filters = new ArrayList<Filter>();
                    Filter filter = new Filter("sn", Filter.Operator.EQ, transaction.getSubSn());
                    filters.add(filter);
                    List<SubsNewsHuman> subsNewsHumans = subsNewsHumanService.findList(1, filters, null);
                    if (ConvertUtils.isNotEmpty(subsNewsHumans)) {
                        subsNewsHumans.forEach(subsNewsHuman -> {
                            subsNewsHuman.setPaySn(transaction.getSn());
                            String subType = subsNewsHuman.getDataType();
                            String  newsBuyType = "周报";
                            if (subType.equals("weekSubFee")) {
                                subsNewsHuman.setExpd(DateUtils.addDays(new Date(), 7));
                                newsBuyType = "周报";
                            }
                            if (subType.equals("monthSubFee")) {
                                subsNewsHuman.setExpd(DateUtils.addMonths(new Date(), 1));
                                newsBuyType = "月报";
                            }
                            if (subType.equals("quarterSubFee")) {
                                subsNewsHuman.setExpd(DateUtils.addMonths(new Date(), 3));
                                newsBuyType = "季报";
                            }
                            if (subType.equals("yearSubFee")) {
                                subsNewsHuman.setExpd(DateUtils.addYears(new Date(), 1));
                                newsBuyType = "年报";
                            }
                            subsNewsHuman.setStatus((long)1);
                            subsNewsHumanService.update(subsNewsHuman);
                            /**订阅**/
                            /****上报神策数据****/
                            Long humanId = subsNewsHuman.getHumanId();
                            Member member = memberService.find(humanId);
                            Map<String,Object> properties = new HashMap<String,Object>();
                            properties.put("pay_no",transaction.getSn());
                            properties.put("news_buy_type",newsBuyType);
                            properties.put("news_buy_price",transaction.getAmount().setScale(0,RoundingMode.UP));
                            properties.put("is_vip",false);
                            if(ConvertUtils.isNotEmpty(member)){
                                properties.put("news_buy_user","采购商");
                                MemberRank memberRank =  member.getMemberRank();
                                if(ConvertUtils.isNotEmpty(memberRank)){
                                    properties.put("is_vip",true);
                                    properties.put("vip_type",memberRank.getName());
                                }
                            } else {
                                properties.put("news_buy_user","供应商");
                                Business business =  businessService.find(humanId);
                                if(ConvertUtils.isNotEmpty(business)
                                        && ConvertUtils.isNotEmpty(business.getStore())
                                        && ConvertUtils.isNotEmpty(business.getStore().getStoreRank())){
                                    StoreRank storeRank = business.getStore().getStoreRank();
                                    properties.put("is_vip",true);
                                    properties.put("vip_type",storeRank.getName());
                                }
                            }
                            properties.put("pay_type",transaction.getPaymentPluginName());
                            sensorsAnalyticsUtils.reportData(String.valueOf(humanId),"payNews",properties);
                        });
                    }
                    break;
                case BAIL_PAYMENT:
                    if (store == null) {
                        break;
                    }

                    storeService.addBailPaid(store, effectiveAmount);
                    if (Store.Status.APPROVED.equals(store.getStatus()) && !store.hasExpired() && store.getBailPayable().compareTo(BigDecimal.ZERO) == 0) {
                        store.setStatus(Store.Status.SUCCESS);
                    } else {
                        productService.refreshActive(store);
                    }
                    break;
            }
            transaction.setIsSuccess(true);
        }
    }

    @Override
    public LineItem generate(PaymentItem paymentItem) {
        if (paymentItem == null || paymentItem.getType() == null) {
            return null;
        }
        Setting setting = SystemUtils.getSetting();
        User user = userService.getCurrent();
        switch (paymentItem.getType()) {
            case ORDER_PAYMENT:
                Member member = (Member) user;
                if (member == null) {
                    return null;
                }
                Order order = orderService.findBySn(paymentItem.getOrderSn());
                if (order == null || !member.equals(order.getMember()) || !orderService.acquireLock(order, member)) {
                    return null;
                }
                if (order.getPaymentMethod() == null || !PaymentMethod.Method.ONLINE.equals(order.getPaymentMethod().getMethod())) {
                    return null;
                }
                if (order.getAmountPayable().compareTo(BigDecimal.ZERO) <= 0) {
                    return null;
                }
                return new PaymentTransaction.OrderLineItem(order);
            case SVC_PAYMENT:
                Svc svc = svcService.findBySn(paymentItem.getSvcSn());
                if (svc == null) {
                    return null;
                }
                if (svc.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                    return null;
                }
                return new PaymentTransaction.SvcLineItem(svc);
            case DEPOSIT_RECHARGE:
                if (user == null) {
                    return null;
                }
                if (paymentItem.getAmount() == null || paymentItem.getAmount().compareTo(BigDecimal.ZERO) <= 0 || paymentItem.getAmount().precision() > 15 || paymentItem.getAmount().scale() > setting.getPriceScale()) {
                    return null;
                }
                if (user instanceof Member || user instanceof Business) {
                    return new PaymentTransaction.DepositRechargerLineItem(user, paymentItem.getAmount());
                } else {
                    return null;
                }
            case NEWS_SUBSCRIBE_PAYMENT:
                if (user == null) {
                    return null;
                }
                if (paymentItem.getAmount() == null || paymentItem.getAmount().compareTo(BigDecimal.ZERO) <= 0 || paymentItem.getAmount().precision() > 15 || paymentItem.getAmount().scale() > setting.getPriceScale()) {
                    return null;
                }
                if (user instanceof Member || user instanceof Business) {
                    return new PaymentTransaction.NewsSubscribePaymentLineItem(user, paymentItem.getAmount(), paymentItem.getOrderSn());
                } else {
                    return null;
                }
            case BAIL_PAYMENT:
                Store store = storeService.getCurrent();
                if (store == null) {
                    return null;
                }
                if (paymentItem.getAmount() == null || paymentItem.getAmount().compareTo(BigDecimal.ZERO) <= 0 || paymentItem.getAmount().precision() > 15 || paymentItem.getAmount().scale() > setting.getPriceScale()) {
                    return null;
                }
                return new PaymentTransaction.BailPaymentLineItem(store, paymentItem.getAmount());
        }
        return null;
    }

    @Override
    @Transactional
    public PaymentTransaction save(PaymentTransaction paymentTransaction) {
        Assert.notNull(paymentTransaction, "[Assertion failed] - paymentTransaction is required; it must not be null");
        paymentTransaction.setSn(snDao.generate(Sn.Type.PAYMENT_TRANSACTION));

        return super.save(paymentTransaction);
    }

}