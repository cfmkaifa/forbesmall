/*
 *
 *
 *
 *
 */
package net.mall.controller.member;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.mall.controller.shop.BaseController;
import net.mall.entity.Member;
import net.mall.entity.Order;
import net.mall.security.CurrentUser;
import net.mall.service.ConsultationService;
import net.mall.service.CouponCodeService;
import net.mall.service.MessageService;
import net.mall.service.OrderService;
import net.mall.service.ProductFavoriteService;
import net.mall.service.ProductNotifyService;
import net.mall.service.ReviewService;

/**
 * Controller - 首页
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("memberIndexController")
@RequestMapping("/member/index")
public class IndexController extends BaseController {

    /**
     * 最新订单数量
     */
    private static final int NEW_ORDER_SIZE = 3;

    @Inject
    private OrderService orderService;
    @Inject
    private CouponCodeService couponCodeService;
    @Inject
    private MessageService messageService;
    @Inject
    private ProductFavoriteService productFavoriteService;
    @Inject
    private ProductNotifyService productNotifyService;
    @Inject
    private ReviewService reviewService;
    @Inject
    private ConsultationService consultationService;

    /**
     * 首页
     */
    @GetMapping
    public String index(@CurrentUser Member currentUser, ModelMap model) {
        model.addAttribute("pendingPaymentOrderCount", orderService.count(null, Order.Status.PENDING_PAYMENT, null, currentUser, null, null, null, null, null, null, false));
        model.addAttribute("pendingShipmentOrderCount", orderService.count(null, Order.Status.PENDING_SHIPMENT, null, currentUser, null, null, null, null, null, null, false));
        model.addAttribute("shippedOrderCount", orderService.count(null, Order.Status.SHIPPED, null, currentUser, null, null, null, null, null, null, null));
        model.addAttribute("unreadMessageCount", messageService.unreadMessageCount(null, currentUser));
        model.addAttribute("couponCodeCount", couponCodeService.count(null, currentUser, null, false, false));
        model.addAttribute("productFavoriteCount", productFavoriteService.count(currentUser));
        model.addAttribute("productNotifyCount", productNotifyService.count(currentUser, null, null, null));
        model.addAttribute("reviewCount", reviewService.count(currentUser, null, null, null));
        model.addAttribute("consultationCount", consultationService.count(currentUser, null, null));
        model.addAttribute("newOrders", orderService.findList(null, null, null, currentUser, null, null, null, null, null, null, null, NEW_ORDER_SIZE, null, null));
        return "member/index";
    }
}