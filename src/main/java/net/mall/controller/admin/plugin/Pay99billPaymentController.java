/*
 *
 * 
 *
 * 
 */
package net.mall.controller.admin.plugin;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.mall.Results;
import net.mall.controller.admin.BaseController;
import net.mall.entity.PluginConfig;
import net.mall.entity.SupplierPluginConfig;
import net.mall.plugin.Pay99billPaymentPlugin;
import net.mall.plugin.PaymentPlugin;
import net.mall.service.PluginConfigService;
import net.mall.util.ConvertUtils;

/**
 * Controller - 快钱支付
 * 
 * @author huanghy
 * @version 6.1
 */
@Controller("adminPluginPay99billPaymentController")
@RequestMapping("/admin/plugin/pay_99bill_payment")
public class Pay99billPaymentController extends BaseController {

	@Inject
	private Pay99billPaymentPlugin pay99billPaymentPlugin;
	@Inject
	private PluginConfigService pluginConfigService;

	/**
	 * 安装
	 */
	@PostMapping("/install")
	public ResponseEntity<?> install() {
		if (!pay99billPaymentPlugin.getIsInstalled()) {
			PluginConfig pluginConfig = new PluginConfig();
			pluginConfig.setPluginId(pay99billPaymentPlugin.getId());
			pluginConfig.setIsEnabled(false);
			pluginConfig.setAttributes(null);
			pluginConfigService.save(pluginConfig);
		}
		return Results.OK;
	}

	/**
	 * 卸载
	 */
	@PostMapping("/uninstall")
	public ResponseEntity<?> uninstall() {
		if (pay99billPaymentPlugin.getIsInstalled()) {
			pluginConfigService.deleteByPluginId(pay99billPaymentPlugin.getId());
		}
		return Results.OK;
	}

	/**
	 * 设置
	 */
	@GetMapping("/setting/{supplierId}")
	public String setting(@PathVariable String supplierId,ModelMap model) {
		PluginConfig pluginConfig = pay99billPaymentPlugin.getNoCachePluginConfig();
		model.addAttribute("feeTypes", PaymentPlugin.FeeType.values());
		SupplierPluginConfig  supplierPluginConfig = receSupplierPluginConfig(pluginConfig.getPluginId(), supplierId);
		if(ConvertUtils.isNotEmpty(supplierPluginConfig)){
			model.addAttribute("pluginConfig", supplierPluginConfig);
		} else {
			model.addAttribute("pluginConfig", pluginConfig);
		}
		model.addAttribute("supplierId", supplierId);
		return "/admin/plugin/pay_99bill_payment/setting";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update/{supplierId}")
	public ResponseEntity<?> update(@PathVariable String supplierId,String displayName, String partner, String key, PaymentPlugin.FeeType feeType, BigDecimal fee, String logo, String description, @RequestParam(defaultValue = "false") Boolean isEnabled, Integer order) {
		PluginConfig pluginConfig = pay99billPaymentPlugin.getNoCachePluginConfig();
		Map<String, String> attributes = new HashMap<>();
		attributes.put(PaymentPlugin.DISPLAY_NAME_ATTRIBUTE_NAME, displayName);
		attributes.put("partner", partner);
		attributes.put("key", key);
		attributes.put(PaymentPlugin.FEE_TYPE_ATTRIBUTE_NAME, String.valueOf(feeType));
		attributes.put(PaymentPlugin.FEE_ATTRIBUTE_NAME, String.valueOf(fee));
		attributes.put(PaymentPlugin.LOGO_ATTRIBUTE_NAME, logo);
		attributes.put(PaymentPlugin.DESCRIPTION_ATTRIBUTE_NAME, description);
		pluginConfig.setAttributes(attributes);
		pluginConfig.setIsEnabled(isEnabled);
		pluginConfig.setOrder(order);
		/***保存商家支付插件**/
		if(!"-1".equalsIgnoreCase(supplierId)){
			oprSupplierPluginConfig(pluginConfig,supplierId);
		} else {
			pluginConfigService.update(pluginConfig);
		}
		return Results.OK;
	}

}