/*
 *
 * 
 *
 * 
 */
package net.mall.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.Results;
import net.mall.Setting;
import net.mall.entity.Member;
import net.mall.entity.Order;
import net.mall.entity.OrderPayment;
import net.mall.entity.OrderRefunds;
import net.mall.entity.OrderShipping;
import net.mall.service.DeliveryCorpService;
import net.mall.service.MemberService;
import net.mall.service.OrderService;
import net.mall.service.OrderShippingService;
import net.mall.service.PaymentMethodService;
import net.mall.service.ShippingMethodService;
import net.mall.util.SystemUtils;

/**
 * Controller - 订单
 * 
 * @author huanghy
 * @version 6.1
 */
@Controller("adminOrderController")
@RequestMapping("/admin/order")
public class OrderController extends BaseController {

	@Inject
	private OrderService orderService;
	@Inject
	private ShippingMethodService shippingMethodService;
	@Inject
	private PaymentMethodService paymentMethodService;
	@Inject
	private DeliveryCorpService deliveryCorpService;
	@Inject
	private OrderShippingService orderShippingService;
	@Inject
	private MemberService memberService;

	/**
	 * 物流动态
	 */
	@GetMapping("/transit_step")
	public ResponseEntity<?> transitStep(Long shippingId) {
		Map<String, Object> data = new HashMap<>();
		OrderShipping orderShipping = orderShippingService.find(shippingId);
		if (orderShipping == null) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		Setting setting = SystemUtils.getSetting();
		if (StringUtils.isEmpty(setting.getKuaidi100Customer()) || StringUtils.isEmpty(setting.getKuaidi100Key()) || StringUtils.isEmpty(orderShipping.getDeliveryCorpCode()) || StringUtils.isEmpty(orderShipping.getTrackingNo())) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		data.put("transitSteps", orderShippingService.getTransitSteps(orderShipping));
		return ResponseEntity.ok(data);
	}

	/**
	 * 查看
	 */
	@GetMapping("/view")
	public String view(Long id, ModelMap model) {
		Setting setting = SystemUtils.getSetting();
		model.addAttribute("methods", OrderPayment.Method.values());
		model.addAttribute("refundsMethods", OrderRefunds.Method.values());
		model.addAttribute("paymentMethods", paymentMethodService.findAll());
		model.addAttribute("shippingMethods", shippingMethodService.findAll());
		model.addAttribute("deliveryCorps", deliveryCorpService.findAll());
		model.addAttribute("isKuaidi100Enabled", StringUtils.isNotEmpty(setting.getKuaidi100Customer()) && StringUtils.isNotEmpty(setting.getKuaidi100Key()));
		model.addAttribute("order", orderService.find(id));
		return "admin/order/view";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Order.Type type, Order.Status status, String memberUsername, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired, Pageable pageable, ModelMap model) {
		model.addAttribute("types", Order.Type.values());
		model.addAttribute("statuses", Order.Status.values());
		model.addAttribute("type", type);
		model.addAttribute("status", status);
		model.addAttribute("memberUsername", memberUsername);
		model.addAttribute("isPendingReceive", isPendingReceive);
		model.addAttribute("isPendingRefunds", isPendingRefunds);
		model.addAttribute("isAllocatedStock", isAllocatedStock);
		model.addAttribute("hasExpired", hasExpired);

		Member member = memberService.findByUsername(memberUsername);
		if (StringUtils.isNotEmpty(memberUsername) && member == null) {
			model.addAttribute("page", Page.emptyPage(pageable));
		} else {
			model.addAttribute("page", orderService.findPage(type, status, null, member, null, isPendingReceive, isPendingRefunds, null, null, isAllocatedStock, hasExpired, pageable));
		}
		return "admin/order/list";
	}

}