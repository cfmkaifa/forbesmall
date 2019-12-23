/*
 *
 * 
 *
 * 
 */
package net.mall.controller.admin.plugin;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.mall.Results;
import net.mall.controller.admin.BaseController;
import net.mall.entity.PluginConfig;
import net.mall.plugin.AlipayLoginPlugin;
import net.mall.plugin.LoginPlugin;
import net.mall.service.PluginConfigService;

/**
 * Controller - 支付宝快捷登录
 * 
 * @author huanghy
 * @version 6.1
 */
@Controller("adminPluginAlipayLoginController")
@RequestMapping("/admin/plugin/alipay_login")
public class AlipayLoginController extends BaseController {

	@Inject
	private AlipayLoginPlugin alipayLoginPlugin;
	@Inject
	private PluginConfigService pluginConfigService;

	/**
	 * 安装
	 */
	@PostMapping("/install")
	public ResponseEntity<?> install() {
		if (!alipayLoginPlugin.getIsInstalled()) {
			PluginConfig pluginConfig = new PluginConfig();
			pluginConfig.setPluginId(alipayLoginPlugin.getId());
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
		if (alipayLoginPlugin.getIsInstalled()) {
			pluginConfigService.deleteByPluginId(alipayLoginPlugin.getId());
		}
		return Results.OK;
	}

	/**
	 * 设置
	 */
	@GetMapping("/setting")
	public String setting(ModelMap model) {
		PluginConfig pluginConfig = alipayLoginPlugin.getPluginConfig();
		model.addAttribute("pluginConfig", pluginConfig);
		return "/admin/plugin/alipay_login/setting";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public ResponseEntity<?> update(String displayName, String appId, String appPrivateKey, String alipayPublicKey, String logo, String description, @RequestParam(defaultValue = "false") Boolean isEnabled, Integer order) {
		PluginConfig pluginConfig = alipayLoginPlugin.getPluginConfig();
		Map<String, String> attributes = new HashMap<>();
		attributes.put(LoginPlugin.DISPLAY_NAME_ATTRIBUTE_NAME, displayName);
		attributes.put("appId", appId);
		attributes.put("appPrivateKey", appPrivateKey);
		attributes.put("alipayPublicKey", alipayPublicKey);
		attributes.put(LoginPlugin.LOGO_ATTRIBUTE_NAME, logo);
		attributes.put(LoginPlugin.DESCRIPTION_ATTRIBUTE_NAME, description);
		pluginConfig.setAttributes(attributes);
		pluginConfig.setIsEnabled(isEnabled);
		pluginConfig.setOrder(order);
		pluginConfigService.update(pluginConfig);
		return Results.OK;
	}

}