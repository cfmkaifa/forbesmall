/*
 *
 *
 *
 *
 */
package net.mall.controller.shop;

import com.fasterxml.jackson.annotation.JsonView;
import net.mall.Results;
import net.mall.entity.*;
import net.mall.entity.Order.Status;
import net.mall.entity.PaymentMethod.Method;
import net.mall.entity.Specification.Sample;
import net.mall.plugin.PaymentPlugin;
import net.mall.rools.ExtDataProviderCompiler;
import net.mall.rools.GroupPurchRools;
import net.mall.security.CurrentCart;
import net.mall.security.CurrentUser;
import net.mall.service.*;
import net.mall.util.ConvertUtils;
import net.mall.util.WebUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Controller - 订单
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("shopOrderController")
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Inject
    private SkuService skuService;
    @Inject
    private AreaService areaService;
    @Inject
    private ReceiverService receiverService;
    @Inject
    private PaymentMethodService paymentMethodService;
    @Inject
    private ShippingMethodService shippingMethodService;
    @Inject
    private CouponCodeService couponCodeService;
    @Inject
    private OrderService orderService;
    @Inject
    GroupPurchApplyService groupPurchApplyService;
    @Inject
    private PluginService pluginService;
    @Inject
    ExtDataProviderCompiler extDataProviderCompiler;

    /**
     * 检查SKU
     * methodCode  sample -样品购买
     */
    @GetMapping("/check_sku")
    public ResponseEntity<?> checkSku(Long skuId, Integer quantity,
                                      String methodCode) {
        if (quantity == null || quantity < 1) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        Sku sku = skuService.find(skuId);
        if (sku == null) {
            return Results.unprocessableEntity("shop.order.skuNotExist");
        }
        if ("sample".equals(methodCode)
                && Sample.NO.equals(sku.getSample())) {
            return Results.unprocessableEntity("shop.order.skuSampleNotExist");
        }
        if (Product.Type.GIFT.equals(sku.getType())) {
            return Results.unprocessableEntity("shop.order.skuNotForSale");
        }
        if (!sku.getIsActive()) {
            return Results.unprocessableEntity("shop.order.skuNotActive");
        }
        if (!sku.getIsMarketable()) {
            return Results.unprocessableEntity("shop.order.skuNotMarketable");
        }
        if (quantity > sku.getAvailableStock()) {
            return Results.unprocessableEntity("shop.order.skuLowStock");
        }
        if (sku.getProduct().getStore().hasExpired()) {
            return Results.unprocessableEntity("shop.order.skuNotBuyExpired");
        }
        return Results.OK;
    }

    /**
     * 检查购物车
     */
    @GetMapping("/check_cart")
    public ResponseEntity<?> checkCart(@CurrentCart Cart currentCart) {
        if (currentCart == null || currentCart.isEmpty()) {
            return Results.unprocessableEntity("shop.order.cartEmpty");
        }
        if (currentCart.hasNotActive()) {
            return Results.unprocessableEntity("shop.order.cartHasNotActive");
        }
        if (currentCart.hasNotMarketable()) {
            return Results.unprocessableEntity("shop.order.cartHasNotMarketable");
        }
        if (currentCart.hasLowStock()) {
            return Results.unprocessableEntity("shop.order.cartHasLowStock");
        }
        if (currentCart.hasExpiredProduct()) {
            return Results.unprocessableEntity("shop.order.cartHasExpiredProduct");
        }
        return Results.OK;
    }

    /**
     * 收货地址列表
     */
    @GetMapping("/receiver_list")
    @JsonView(BaseEntity.BaseView.class)
    public ResponseEntity<?> receiverList(@CurrentUser Member currentUser) {
        return ResponseEntity.ok(receiverService.findList(currentUser));
    }

    /**
     * 添加收货地址
     */
    @PostMapping("/add_receiver")
    @JsonView(BaseEntity.BaseView.class)
    public ResponseEntity<?> addReceiver(Receiver receiver, Long areaId, @CurrentUser Member currentUser) {
        receiver.setArea(areaService.find(areaId));
        if (!isValid(receiver)) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (Receiver.MAX_RECEIVER_COUNT != null && currentUser.getReceivers().size() >= Receiver.MAX_RECEIVER_COUNT) {
            return Results.unprocessableEntity("shop.order.addReceiverCountNotAllowed", Receiver.MAX_RECEIVER_COUNT);
        }
        receiver.setAreaName(null);
        receiver.setMember(currentUser);
        return ResponseEntity.ok(receiverService.save(receiver));
    }

    /**
     * 订单锁定
     */
    @PostMapping("/lock")
    public @ResponseBody
    void lock(String[] orderSns, @CurrentUser Member currentUser) {
        for (String orderSn : orderSns) {
            Order order = orderService.findBySn(orderSn);
            if (order != null && currentUser.equals(order.getMember()) && order.getPaymentMethod() != null && PaymentMethod.Method.ONLINE.equals(order.getPaymentMethod().getMethod()) && order.getAmountPayable().compareTo(BigDecimal.ZERO) > 0) {
                orderService.acquireLock(order, currentUser);
            }
        }
    }

    /**
     * 检查等待付款
     */
    @GetMapping("/check_pending_payment")
    public @ResponseBody
    boolean checkPendingPayment(String[] orderSns, @CurrentUser final Member currentUser) {
        return ArrayUtils.isNotEmpty(orderSns) && CollectionUtils.exists(Arrays.asList(orderSns), new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                String orderSn = (String) object;
                Order order = orderService.findBySn(orderSn);

                return order != null && currentUser.equals(order.getMember()) && order.getPaymentMethod() != null && PaymentMethod.Method.ONLINE.equals(order.getPaymentMethod().getMethod()) && order.getAmountPayable().compareTo(BigDecimal.ZERO) > 0;
            }
        });
    }

    /**
     * 检查优惠券
     */
    @GetMapping("/check_coupon")
    public ResponseEntity<?> checkCoupon(Long skuId, Integer quantity, String code, @CurrentUser Member currentUser, @CurrentCart Cart currentCart) {
        Map<String, Object> data = new HashMap<>();
        Cart cart;
        if (skuId != null) {
            Sku sku = skuService.find(skuId);
            if (sku == null) {
                return Results.UNPROCESSABLE_ENTITY;
            }
            if (!Product.Type.GENERAL.equals(sku.getType())) {
                return Results.UNPROCESSABLE_ENTITY;
            }
            if (quantity == null || quantity < 1) {
                return Results.UNPROCESSABLE_ENTITY;
            }

            cart = generateCart(currentUser, sku, quantity);
        } else {
            cart = currentCart;
        }

        if (cart == null || cart.isEmpty()) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        CouponCode couponCode = couponCodeService.findByCode(code);
        if (couponCode == null) {
            return Results.unprocessableEntity("shop.order.couponCodeNotExist");
        }
        Coupon coupon = couponCode.getCoupon();
        if (coupon == null) {
            return Results.unprocessableEntity("shop.order.couponCodeNotExist");
        }
        if (couponCode.getIsUsed()) {
            return Results.unprocessableEntity("shop.order.couponCodeUsed");
        }
        if (!coupon.getIsEnabled()) {
            return Results.unprocessableEntity("shop.order.couponDisabled");
        }
        if (!coupon.hasBegun()) {
            return Results.unprocessableEntity("shop.order.couponNotBegin");
        }
        if (coupon.hasExpired()) {
            return Results.unprocessableEntity("shop.order.couponHasExpired");
        }
        Store store = coupon.getStore();
        if (!cart.isValid(store, coupon)) {
            return Results.unprocessableEntity("shop.order.couponInvalid");
        }
        if (!cart.isCouponAllowed(store)) {
            return Results.unprocessableEntity("shop.order.couponNotAllowed");
        }
        data.put("couponName", coupon.getName());
        return ResponseEntity.ok(data);
    }

    /**
     * 结算
     */
    @GetMapping("/checkout")
    public String checkout(Long skuId, Integer quantity,
                           String methodCode,
                           @CurrentUser Member currentUser, @CurrentCart Cart currentCart,
                           HttpServletRequest request,
                           ModelMap model) throws UnsupportedEncodingException {
        Cart cart;
        Order.Type orderType;
        if (skuId != null) {
            Sku sku = skuService.find(skuId);
            model.addAttribute("sku",sku);
            Set<Sku> skuSet=sku.getProduct().getSkus();
            for(Sku temp:skuSet){
                List<SpecificationValue> specificationValues=temp.getSpecificationValues();
                for(SpecificationValue spec:specificationValues){
                    if(spec.getValue().contains("mm")){
                        model.addAttribute("temp_commodity_length",spec.getValue());
                    }else if(spec.getValue().contains("dtex")){
                        model.addAttribute("temp_commodity_dtex",spec.getValue());
                    } else if(spec.getValue().contains("kg")){
                        model.addAttribute("temp_commodity_weight",spec.getValue());
                    }else{
                        model.addAttribute("temp_commodity_color",spec.getValue());
                    }
                }
            }
            model.addAttribute("temp_is_group",sku.getProduct().getGroup());
            model.addAttribute("temp_is_purch",sku.getProduct().getPurch());
            model.addAttribute("temp_is_sample",sku.getProduct().getSample());
            if (sku == null) {
                return UNPROCESSABLE_ENTITY_VIEW;
            }
            if (Product.Type.GIFT.equals(sku.getType())) {
                return UNPROCESSABLE_ENTITY_VIEW;
            }
            if (quantity == null || quantity < 1) {
                return UNPROCESSABLE_ENTITY_VIEW;
            }
            cart = generateCart(currentUser, sku, quantity);
            switch (sku.getType()) {
                case GENERAL:
                    orderType = Order.Type.GENERAL;
                    break;
                case EXCHANGE:
                    orderType = Order.Type.EXCHANGE;
                    break;
                default:
                    orderType = null;
                    break;
            }
        } else {
            cart = currentCart;
            orderType = Order.Type.GENERAL;
        }
        /***团购申请
         * **/
        if ("group_purch".equalsIgnoreCase(methodCode)) {
            if (!this.checkGroupPurch(skuId, quantity, currentUser, model)) {
                return UNPROCESSABLE_ENTITY_VIEW;
            }
        }
        if (cart == null || cart.isEmpty()) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }
        if (cart.hasNotActive()) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }
        if (cart.hasNotMarketable()) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }
        if (cart.hasLowStock()) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }
        if (cart.hasExpiredProduct()) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }
        if (orderType == null) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }
        Receiver defaultReceiver = receiverService.findDefault(currentUser);
        List<Order> orders = orderService.generate(orderType, cart, defaultReceiver, null, null, null, null, null, null);
        BigDecimal price = BigDecimal.ZERO;
        BigDecimal fee = BigDecimal.ZERO;
        BigDecimal freight = BigDecimal.ZERO;
        BigDecimal tax = BigDecimal.ZERO;
        BigDecimal promotionDiscount = BigDecimal.ZERO;
        BigDecimal couponDiscount = BigDecimal.ZERO;
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal amountPayable = BigDecimal.ZERO;
        Long rewardPoint = 0L;
        Long exchangePoint = 0L;
        boolean isDelivery = false;
        for (Order order : orders) {
            price = price.add(order.getPrice());
            fee = fee.add(order.getFee());
            freight = freight.add(order.getFreight());
            tax = tax.add(order.getTax());
            promotionDiscount = promotionDiscount.add(order.getPromotionDiscount());
            couponDiscount = couponDiscount.add(order.getCouponDiscount());
            amount = amount.add(order.getAmount());
            amountPayable = amountPayable.add(order.getAmountPayable());
            rewardPoint = rewardPoint + order.getRewardPoint();
            exchangePoint = exchangePoint + order.getExchangePoint();
            if (order.getIsDelivery()) {
                isDelivery = true;
            }
        }
        model.addAttribute("skuId", skuId);
        model.addAttribute("quantity", quantity);
        model.addAttribute("cart", cart);
        model.addAttribute("orderType", orderType);
        model.addAttribute("defaultReceiver", defaultReceiver);
        model.addAttribute("orders", orders);
        model.addAttribute("price", price);
        model.addAttribute("fee", fee);
        model.addAttribute("freight", freight);
        model.addAttribute("tax", tax);
        model.addAttribute("methodCode", methodCode);
        model.addAttribute("promotionDiscount", promotionDiscount);
        model.addAttribute("couponDiscount", couponDiscount);
        model.addAttribute("quantity",quantity);
        model.addAttribute("amount", amount);
        model.addAttribute("amountPayable", amountPayable);
        model.addAttribute("rewardPoint", rewardPoint);
        model.addAttribute("exchangePoint", exchangePoint);
        model.addAttribute("isDelivery", isDelivery);
        List<PaymentMethod> paymentMethods = paymentMethodService.findAll();

        /***判断是否手机端
         * **/
        if (request.getAttribute(DeviceUtils.CURRENT_DEVICE_ATTRIBUTE) instanceof Device) {
            Device device = (Device) request.getAttribute(DeviceUtils.CURRENT_DEVICE_ATTRIBUTE);
            if (device.isMobile()
                    || device.isTablet()) {
                if (cart.contains(Store.Type.GENERAL)) {
                    CollectionUtils.select(paymentMethodService.findAll(), new Predicate() {
                        @Override
                        public boolean evaluate(Object object) {
                            PaymentMethod paymentMethod = (PaymentMethod) object;
                            return paymentMethod != null && PaymentMethod.Method.ONLINE.equals(paymentMethod.getMethod());
                        }
                    }, paymentMethods);
                } else {
                    paymentMethods = paymentMethodService.findAll();
                }
            }
        }
        model.addAttribute("defaultPaymentMethod", paymentMethods.get(0));
        model.addAttribute("paymentMethods", paymentMethods);
        model.addAttribute("shippingMethods", shippingMethodService.findAll());
        model.addAttribute("member",currentUser);
        String refererUrl = request.getHeader("Referer");
        if(ConvertUtils.isNotEmpty(refererUrl)
                && refererUrl.contains("/cart/list")){
            model.addAttribute("entrance", "cart");
        } else if (ConvertUtils.isNotEmpty(refererUrl)
                && refererUrl.contains("/product/sample-detail")) {
            model.addAttribute("entrance", "sample");
        } else {
            model.addAttribute("entrance", "buyNow");
        }
        return "shop/order/checkout";
    }

    /***监测团购
     * @param skuId
     * @return
     */
    private boolean checkGroupPurch(Long skuId, Integer quantity, Member currentUser, ModelMap model) throws UnsupportedEncodingException {
        Sku sku = skuService.find(skuId);
        Product product = sku.getProduct();
        GroupPurchRools groupPurchRools = new GroupPurchRools();
        groupPurchRools.setTotalWeight(sku.getTotalUnit().multiply(new BigDecimal(quantity)).longValue());
        groupPurchRools.setTotalCount(1);
        groupPurchRools.setMember(currentUser);
        groupPurchRools.setProduct(product);
        groupPurchRools.setSku(sku);
        groupPurchRools.setStore(product.getStore());
        Date currentDate = new Date();
        GroupPurchApply groupPurchApply = groupPurchApplyService.putawayGroupPurchApply(GroupPurchApply.ApplyStatus.APPROVED, currentDate, product.getSn(), sku.getSn());
        if (ConvertUtils.isNotEmpty(groupPurchApply)) {
            String[] params = {String.valueOf(groupPurchApply.getMqqWeight()), String.valueOf(groupPurchApply.getLimitWeight()), String.valueOf(groupPurchApply.getLimitPeople()), product.getUnit()};
            extDataProviderCompiler.executeRules(params, groupPurchRools);
            model.addAttribute(ERROR_MSG_CODE, groupPurchRools.getMessage());
            return groupPurchRools.isCreate();
        } else {
            return false;
        }
    }

    /**
     * 计算
     */
    @GetMapping("/calculate")
    public ResponseEntity<?> calculate(Long skuId, Integer quantity,
                                       Long receiverId, Long paymentMethodId,
                                       Long shippingMethodId, String code, String invoiceTitle,
                                       String invoiceTaxNumber, BigDecimal balance, String memo,
                                       @CurrentUser Member currentUser, @CurrentCart Cart currentCart,
                                       HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();
        Cart cart;
        Order.Type orderType;
        if (skuId != null) {
            Sku sku = skuService.find(skuId);
            if (sku == null) {
                return Results.UNPROCESSABLE_ENTITY;
            }
            if (Product.Type.GIFT.equals(sku.getType())) {
                return Results.UNPROCESSABLE_ENTITY;
            }
            if (quantity == null || quantity < 1) {
                return Results.UNPROCESSABLE_ENTITY;
            }

            cart = generateCart(currentUser, sku, quantity);

            switch (sku.getType()) {
                case GENERAL:
                    orderType = Order.Type.GENERAL;
                    break;
                case EXCHANGE:
                    orderType = Order.Type.EXCHANGE;
                    break;
                default:
                    orderType = null;
                    break;
            }
        } else {
            cart = currentCart;
            orderType = Order.Type.GENERAL;
        }
        if (cart == null || cart.isEmpty()) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (cart.hasNotActive()) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (cart.hasNotMarketable()) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (cart.hasLowStock()) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (cart.hasExpiredProduct()) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (orderType == null) {
            return Results.UNPROCESSABLE_ENTITY;
        }

        Receiver receiver = receiverService.find(receiverId);
        if (receiver != null && !currentUser.equals(receiver.getMember())) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (balance != null && balance.compareTo(BigDecimal.ZERO) < 0) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (balance != null && balance.compareTo(currentUser.getAvailableBalance()) > 0) {
            return Results.unprocessableEntity("shop.order.insufficientBalance");
        }
        PaymentMethod paymentMethod = paymentMethodService.find(paymentMethodId);
        /***判断是否手机端
         * **/
        if (request.getAttribute(DeviceUtils.CURRENT_DEVICE_ATTRIBUTE) instanceof Device) {
            Device device = (Device) request.getAttribute(DeviceUtils.CURRENT_DEVICE_ATTRIBUTE);
            if (device.isMobile()
                    || device.isTablet()) {
                if (cart.contains(Store.Type.GENERAL) && paymentMethod != null && PaymentMethod.Method.OFFLINE.equals(paymentMethod.getMethod())) {
                    return Results.UNPROCESSABLE_ENTITY;
                }
            }
        }
        //cart.contains(Store.Type.GENERAL) && paymentMethod != null && PaymentMethod.Method.OFFLINE.equals(paymentMethod.getMethod())
        if (null == paymentMethod) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        ShippingMethod shippingMethod = shippingMethodService.find(shippingMethodId);
        CouponCode couponCode = couponCodeService.findByCode(code);
        if (couponCode != null && couponCode.getCoupon() != null && !cart.isValid(couponCode.getCoupon().getStore(), couponCode)) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        Invoice invoice = StringUtils.isNotEmpty(invoiceTitle) ? new Invoice(invoiceTitle, invoiceTaxNumber, null) : null;
        List<Order> orders = orderService.generate(orderType, cart, receiver, paymentMethod, shippingMethod, couponCode, invoice, balance, memo);

        BigDecimal price = BigDecimal.ZERO;
        BigDecimal fee = BigDecimal.ZERO;
        BigDecimal freight = BigDecimal.ZERO;
        BigDecimal tax = BigDecimal.ZERO;
        BigDecimal promotionDiscount = BigDecimal.ZERO;
        BigDecimal couponDiscount = BigDecimal.ZERO;
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal amountPayable = BigDecimal.ZERO;
        Long rewardPoint = 0L;
        Long exchangePoint = 0L;
        boolean isDelivery = false;

        for (Order order : orders) {
            price = price.add(order.getPrice());
            fee = fee.add(order.getFee());
            freight = freight.add(order.getFreight());
            tax = tax.add(order.getTax());
            promotionDiscount = promotionDiscount.add(order.getPromotionDiscount());
            couponDiscount = couponDiscount.add(order.getCouponDiscount());
            amount = amount.add(order.getAmount());
            amountPayable = amountPayable.add(order.getAmountPayable());
            rewardPoint = rewardPoint + order.getRewardPoint();
            exchangePoint = exchangePoint + order.getExchangePoint();
            if (order.getIsDelivery()) {
                isDelivery = true;
            }
        }

        data.put("price", price);
        data.put("fee", fee);
        data.put("freight", freight);
        data.put("tax", tax);
        data.put("promotionDiscount", promotionDiscount);
        data.put("couponDiscount", couponDiscount);
        data.put("amount", amount);
        data.put("amountPayable", amountPayable);
        data.put("rewardPoint", rewardPoint);
        data.put("exchangePoint", exchangePoint);
        data.put("isDelivery", isDelivery);
        return ResponseEntity.ok(data);
    }

    /**
     * 创建
     */
    @PostMapping("/create")
    public ResponseEntity<?> create(Long skuId, Integer quantity, String methodCode,String entrance,
                                    String cartTag, Long receiverId, Long paymentMethodId, Long shippingMethodId, String code, String invoiceTitle, String invoiceTaxNumber, BigDecimal balance, String memo, @CurrentUser Member currentUser,
                                    @CurrentCart Cart currentCart) {
        Map<String, Object> data = new HashMap<>();
        Cart cart;
        Order.Type orderType;
        if (skuId != null) {
            Sku sku = skuService.find(skuId);
            if (sku == null) {
                return Results.UNPROCESSABLE_ENTITY;
            }
            if (Product.Type.GIFT.equals(sku.getType())) {
                return Results.UNPROCESSABLE_ENTITY;
            }
            if (quantity == null || quantity < 1) {
                return Results.UNPROCESSABLE_ENTITY;
            }

            cart = generateCart(currentUser, sku, quantity);

            switch (sku.getType()) {
                case GENERAL:
                    orderType = Order.Type.GENERAL;
                    break;
                case EXCHANGE:
                    orderType = Order.Type.EXCHANGE;
                    break;
                default:
                    orderType = null;
                    break;
            }
        } else {
            cart = currentCart;
            orderType = Order.Type.GENERAL;
        }
        if (cart == null || cart.isEmpty()) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (cartTag != null && !StringUtils.equals(cart.getTag(), cartTag)) {
            return Results.unprocessableEntity("shop.order.cartHasChanged");
        }
        if (cart.hasNotActive()) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (cart.hasNotMarketable()) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (cart.hasLowStock()) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (cart.hasExpiredProduct()) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (orderType == null) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        Receiver receiver = cart.getIsDelivery() ? receiverService.find(receiverId) : null;
        if (cart.getIsDelivery() && (receiver == null || !currentUser.equals(receiver.getMember()))) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (balance != null && balance.compareTo(BigDecimal.ZERO) < 0) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (balance != null && balance.compareTo(currentUser.getAvailableBalance()) > 0) {
            return Results.unprocessableEntity("shop.order.insufficientBalance");
        }
        if (currentUser.getPoint() < cart.getExchangePoint()) {
            return Results.unprocessableEntity("shop.order.lowPoint");
        }
        PaymentMethod paymentMethod = paymentMethodService.find(paymentMethodId);
        //cart.contains(Store.Type.GENERAL) && paymentMethod != null && PaymentMethod.Method.OFFLINE.equals(paymentMethod.getMethod())
        if (null == paymentMethod) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        ShippingMethod shippingMethod = shippingMethodService.find(shippingMethodId);
        if (cart.getIsDelivery() && shippingMethod == null) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        CouponCode couponCode = couponCodeService.findByCode(code);
        if (couponCode != null && couponCode.getCoupon() != null && !cart.isValid(couponCode.getCoupon().getStore(), couponCode)) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        Invoice invoice = StringUtils.isNotEmpty(invoiceTitle) ? new Invoice(invoiceTitle, invoiceTaxNumber, null) : null;
        if (ConvertUtils.isNotEmpty(methodCode)) {
            cart.setMethodCode(methodCode);
        }
        /***订单入口
         * */
        if(ConvertUtils.isNotEmpty(entrance)){
            cart.setEntrance(entrance);
        }
        List<Order> orders = orderService.create(orderType, cart, receiver, paymentMethod, shippingMethod, couponCode, invoice, balance, memo);
        List<String> orderSns = new ArrayList<>();
        for (Order order : orders) {
            if (order != null && order.getAmount().compareTo(order.getAmountPaid()) > 0 && order.getAmountPayable().compareTo(BigDecimal.ZERO) > 0) {
                orderSns.add(order.getSn());
            }
        }
        data.put("orderSns", orderSns);
        return ResponseEntity.ok(data);
    }

    /**
     * 支付
     */
    @GetMapping("/payment")
    public String payment(String[] orderSns, @CurrentUser Member currentUser, ModelMap model) {
        if (ArrayUtils.isEmpty(orderSns)) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }
        List<PaymentPlugin> paymentPlugins = pluginService.getActivePaymentPlugins(WebUtils.getRequest());
        PaymentPlugin defaultPaymentPlugin = null;
        PaymentMethod orderPaymentMethod = null;
        BigDecimal fee = BigDecimal.ZERO;
        BigDecimal amount = BigDecimal.ZERO;
        boolean online = false;
        List<Order> orders = new ArrayList<>();
        for (String orderSn : orderSns) {
            Order order = orderService.findBySn(orderSn);
            if (order == null) {
                return UNPROCESSABLE_ENTITY_VIEW;
            }
            BigDecimal amountPayable = order.getAmountPayable();
            if (order.getAmount().compareTo(order.getAmountPaid()) <= 0 || amountPayable.compareTo(BigDecimal.ZERO) <= 0) {
                return "redirect:/member/order/list";
            }
            orderPaymentMethod = order.getPaymentMethod();
            if (!currentUser.equals(order.getMember()) || orderPaymentMethod == null) {
                return UNPROCESSABLE_ENTITY_VIEW;
            }
            if (PaymentMethod.Method.ONLINE.equals(orderPaymentMethod.getMethod())) {
                if (!orderService.acquireLock(order, currentUser)) {
                    return "redirect:/member/order/list";
                }
                if (CollectionUtils.isNotEmpty(paymentPlugins)) {
                    defaultPaymentPlugin = paymentPlugins.get(0);
                }
                online = true;
            } else {
                fee = fee.add(order.getFee());
                online = false;
            }
            amount = amount.add(amountPayable);
            orders.add(order);
        }
        if (online && defaultPaymentPlugin != null) {
            fee = defaultPaymentPlugin.calculateFee(amount).add(fee);
            amount = fee.add(amount);
            model.addAttribute("online", online);
            model.addAttribute("defaultPaymentPlugin", defaultPaymentPlugin);
            model.addAttribute("paymentPlugins", paymentPlugins);
        }
        if (CollectionUtils.isNotEmpty(orders)) {
            Order order = orders.get(0);
            model.addAttribute("shippingMethodName", order.getShippingMethodName());
            model.addAttribute("paymentMethodName", order.getPaymentMethodName());
            model.addAttribute("paymentMethod", orderPaymentMethod);
            model.addAttribute("expireDate", order.getExpire());
        }
        model.addAttribute("fee", fee);
        model.addAttribute("amount", amount);
        model.addAttribute("orders", orders);
        model.addAttribute("orderSns", Arrays.asList(orderSns));
       for(Order orderTemp:orders){
            model.addAttribute("orderTemp",orderTemp);
            List<OrderItem> orderItems=orderTemp.getOrderItems();
            for(OrderItem itemTemp:orderItems){
                model.addAttribute("product",itemTemp);
                model.addAttribute("temp_is_group",itemTemp.getProduct().getGroup());
                model.addAttribute("temp_is_purch",itemTemp.getProduct().getPurch());
                model.addAttribute("temp_is_sample",itemTemp.getProduct().getSample());
                model.addAttribute("commodity_name",itemTemp.getProduct().getName());
                model.addAttribute("quantity",itemTemp.getQuantity());
                model.addAttribute("present_price",itemTemp.getProduct().getPrice());
                model.addAttribute("commodity_id",itemTemp.getProduct().getId());
                if(ConvertUtils.isEmpty(itemTemp.getSku().getProduct().getProductCategory().getParent())){
                    model.addAttribute("first_commodity","无");
                }else {
                    model.addAttribute("first_commodity",itemTemp.getSku().getProduct().getProductCategory().getParent().getName());
                }
                model.addAttribute("second_commodity",itemTemp.getProduct().getProductCategory().getName());
                model.addAttribute("store_id",itemTemp.getOrder().getStore().getId());
                model.addAttribute("store_name",itemTemp.getOrder().getStore().getName());
                List<String> specifications=itemTemp.getSpecifications();
                for(String spec:specifications){
                    if(spec.contains("mm")){
                        model.addAttribute("temp_commodity_length",spec);
                    }else if(spec.contains("dtex")){
                        model.addAttribute("temp_commodity_dtex",spec);
                    } else if(spec.contains("kg")){
                        model.addAttribute("temp_commodity_weight",spec);
                    }else{
                        model.addAttribute("temp_commodity_color",spec);
                    }
                }
                break;
            }
        }
        return "shop/order/payment";
    }

    /**
     * 计算支付金额
     */
    @GetMapping("/calculate_amount")
    public ResponseEntity<?> calculateAmount(String paymentPluginId, String[] orderSns, @CurrentUser Member currentUser) {
        Map<String, Object> data = new HashMap<>();
        if (ArrayUtils.isEmpty(orderSns)) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        PaymentPlugin paymentPlugin = pluginService.getPaymentPlugin(paymentPluginId);
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal fee = BigDecimal.ZERO;
        for (String orderSn : orderSns) {
            Order order = orderService.findBySn(orderSn);
            if (order == null || !currentUser.equals(order.getMember()) || paymentPlugin == null || !paymentPlugin.getIsEnabled()) {
                return Results.UNPROCESSABLE_ENTITY;
            }
            amount = amount.add(order.getAmountPayable());
        }
        paymentPlugin.calculateFee(amount);
        data.put("fee", fee);
        data.put("amount", amount.add(fee));
        return ResponseEntity.ok(data);
    }

    /**
     * 生成购物车
     *
     * @param member   会员
     * @param sku      SKU
     * @param quantity 数量
     * @return 购物车
     */
    public Cart generateCart(Member member, Sku sku, Integer quantity) {
        Assert.notNull(member, "[Assertion failed] - member is required; it must not be null");
        Assert.notNull(sku, "[Assertion failed] - sku is required; it must not be null");
        Assert.state(!Product.Type.GIFT.equals(sku.getType()), "[Assertion failed] - sku type can't be GIFT");
        Assert.notNull(quantity, "[Assertion failed] - quantity is required; it must not be null");
        Assert.state(quantity > 0, "[Assertion failed] - quantity must be greater than 0");
        Cart cart = new Cart();
        Set<CartItem> cartItems = new HashSet<>();
        CartItem cartItem = new CartItem();
        cartItem.setSku(sku);
        cartItem.setQuantity(quantity);
        cartItems.add(cartItem);
        cartItem.setCart(cart);
        cart.setMember(member);
        cart.setCartItems(cartItems);
        return cart;
    }


    /****
     * certificatePayment方法慨述:支付订单
     * @param
     * @param
     * @param
     * @return ResponseEntity<?>
     * @创建人 huanghy
     * @创建时间 2019年12月26日 下午3:18:01
     * @修改人 (修改了该文件 ， 请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @PostMapping("/certificatePayment")
    public ResponseEntity<?> certificatePayment(String orderSn, String certificatePath) {
        if (null == orderSn
                || orderSn.trim().length() == 0
                || null == certificatePath
                || certificatePath.trim().length() == 0) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        Order order = orderService.findBySn(orderSn);
        if (order.getPaymentMethod().getMethod().equals(Method.OFFLINE)) {
            if (order.getStatus().equals(Status.PENDING_PAYMENT)) {
                order.setCertificatePath(certificatePath);
                order.setStatus(Status.MERCHANT_CONFIRM);
                orderService.update(order);
            } else {
                return Results.UNPROCESSABLE_ENTITY;
            }
        } else {
            return Results.UNPROCESSABLE_ENTITY;
        }
        return Results.OK;
    }

    /***
     * sealContract方法慨述:
     * @param
     * @param sealContractPath
     * @return ResponseEntity<?>
     * @创建人 huanghy
     * @创建时间 2019年12月28日 下午4:36:27
     * @修改人 (修改了该文件 ， 请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @PostMapping("/sealContract")
    public ResponseEntity<?> sealContract(Long orderId, String sealContractPath) {
        if (null == orderId
                || null == sealContractPath
                || sealContractPath.trim().length() == 0) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        Order order = orderService.find(orderId);
        if (order.getStatus().equals(Status.PENDING_PAYMENT)) {
            order.setSealContract(sealContractPath);
            orderService.update(order);
        } else {
            return Results.UNPROCESSABLE_ENTITY;
        }
        return Results.OK;
    }

}