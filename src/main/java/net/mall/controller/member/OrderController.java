/*
 *
 *
 *
 *
 */
package net.mall.controller.member;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonView;

import net.mall.Pageable;
import net.mall.Results;
import net.mall.Setting;
import net.mall.entity.BaseEntity;
import net.mall.entity.Member;
import net.mall.entity.Order;
import net.mall.entity.OrderShipping;
import net.mall.exception.UnauthorizedException;
import net.mall.security.CurrentUser;
import net.mall.service.OrderService;
import net.mall.service.OrderShippingService;
import net.mall.util.SystemUtils;

/**
 * Controller - 订单
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("memberOrderController")
@RequestMapping("/member/order")
public class OrderController extends BaseController {

    /**
     * 每页记录数
     */
    private static final int PAGE_SIZE = 10;

    @Inject
    private OrderService orderService;
    @Inject
    private OrderShippingService orderShippingService;

    /**
     * 添加属性
     */
    @ModelAttribute
    public void populateModel(String orderSn, String orderShippingSn, @CurrentUser Member currentUser, ModelMap model) {
        Order order = orderService.findBySn(orderSn);
        if (order != null && !currentUser.equals(order.getMember())) {
            throw new UnauthorizedException();
        }
        model.addAttribute("order", order);

        OrderShipping orderShipping = orderShippingService.findBySn(orderShippingSn);
        if (orderShipping != null && orderShipping.getOrder() != null && !currentUser.equals(orderShipping.getOrder().getMember())) {
            throw new UnauthorizedException();
        }
        model.addAttribute("orderShipping", orderShipping);
    }

    /**
     * 检查锁定
     */
    @PostMapping("/check_lock")
    public ResponseEntity<?> checkLock(@ModelAttribute(binding = false) Order order) {
        if (order == null) {
            return Results.NOT_FOUND;
        }

        if (!orderService.acquireLock(order)) {
            return Results.unprocessableEntity("member.order.locked");
        }
        return Results.OK;
    }

    /**
     * 物流动态
     */
    @GetMapping("/transit_step")
    public ResponseEntity<?> transitStep(@ModelAttribute(binding = false) OrderShipping orderShipping, @CurrentUser Member currentUser) {
        Map<String, Object> data = new HashMap<>();
        if (orderShipping == null) {
            return Results.NOT_FOUND;
        }

        Setting setting = SystemUtils.getSetting();
        if (StringUtils.isEmpty(setting.getKuaidi100Customer()) || StringUtils.isEmpty(setting.getKuaidi100Key()) || StringUtils.isEmpty(orderShipping.getDeliveryCorpCode()) || StringUtils.isEmpty(orderShipping.getTrackingNo())) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        data.put("transitSteps", orderShippingService.getTransitSteps(orderShipping));
        return ResponseEntity.ok(data);
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(Order.Status status, Boolean hasExpired, Integer pageNumber, @CurrentUser Member currentUser, ModelMap model) {
        Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
        Setting setting = SystemUtils.getSetting();
        model.addAttribute("status", status);
        model.addAttribute("hasExpired", hasExpired);
        model.addAttribute("isKuaidi100Enabled", StringUtils.isNotEmpty(setting.getKuaidi100Customer()) && StringUtils.isNotEmpty(setting.getKuaidi100Key()));
        model.addAttribute("page", orderService.findPage(null, status, null, currentUser, null, null, null, null, null, null, hasExpired, pageable));
        return "member/order/list";
    }

    /**
     * 列表
     */
    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(BaseEntity.BaseView.class)
    public ResponseEntity<?> list(Order.Status status, Boolean hasExpired, Integer pageNumber, @CurrentUser Member currentUser) {
        Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
        return ResponseEntity.ok(orderService.findPage(null, status, null, currentUser, null, null, null, null, null, null, hasExpired, pageable).getContent());
    }

    /**
     * 查看
     */
    @GetMapping("/view")
    public String view(@ModelAttribute(binding = false) Order order, @CurrentUser Member currentUser, ModelMap model) {
        if (order == null) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }
        Setting setting = SystemUtils.getSetting();
        model.addAttribute("isKuaidi100Enabled", StringUtils.isNotEmpty(setting.getKuaidi100Customer()) && StringUtils.isNotEmpty(setting.getKuaidi100Key()));
        model.addAttribute("order", order);
        return "member/order/view";
    }

    /**
     * 取消
     */
    @PostMapping("/cancel")
    public ResponseEntity<?> cancel(@ModelAttribute(binding = false) Order order, @CurrentUser Member currentUser) {
        if (order == null) {
            return Results.NOT_FOUND;
        }

        if (order.hasExpired() || (!Order.Status.PENDING_PAYMENT.equals(order.getStatus()) && !Order.Status.PENDING_REVIEW.equals(order.getStatus()))) {
            return Results.NOT_FOUND;
        }
        if (!orderService.acquireLock(order, currentUser)) {
            return Results.unprocessableEntity("member.order.locked");
        }
        orderService.cancel(order);
        /**释放订单锁***/
        orderService.releaseLock(order);
        return Results.OK;
    }

    /**
     * 收货
     */
    @PostMapping("/receive")
    public ResponseEntity<?> receive(@ModelAttribute(binding = false) Order order, @CurrentUser Member currentUser) {
        if (order == null) {
            return Results.NOT_FOUND;
        }

        if (order.hasExpired() || !Order.Status.SHIPPED.equals(order.getStatus())) {
            return Results.NOT_FOUND;
        }
        if (!orderService.acquireLock(order, currentUser)) {
            return Results.unprocessableEntity("member.order.locked.receive");
        }
        orderService.receive(order);
        /**释放订单锁***/
        orderService.releaseLock(order);
        return Results.OK;
    }

}