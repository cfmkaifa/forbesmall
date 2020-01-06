/*
 *
 * 
 *
 * 
 */
package net.mall.controller.admin;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import net.mall.plugin.PaymentPlugin;
import net.mall.service.PluginService;

/**
 * Controller - 支付插件
 * 
 * @author huanghy
 * @version 6.1
 */
@Controller("supplierPaymentPluginController")
@RequestMapping("/admin/business_payment_plugin")
public class SupplierPaymentPluginController extends BaseController {

	@Inject
	private PluginService pluginService;

	/**
	 * 列表
	 */
	@GetMapping("/list/{supplierId}")
	public String list(@PathVariable String supplierId,
			ModelMap model) {
		List<PaymentPlugin> paymentPlugins = pluginService.getPaymentPlugins();
		List<PaymentPlugin> installPaymentPlugins = paymentPlugins.stream().filter(paymentPlugin->paymentPlugin.getIsInstalled()).collect(Collectors.toList());
		model.addAttribute("paymentPlugins", installPaymentPlugins);
		model.addAttribute("supplierId", supplierId);
		return "admin/business_payment_plugin/list";
	}

}