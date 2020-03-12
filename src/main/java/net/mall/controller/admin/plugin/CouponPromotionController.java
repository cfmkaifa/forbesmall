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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.mall.Results;
import net.mall.controller.admin.BaseController;
import net.mall.entity.PluginConfig;
import net.mall.plugin.CouponPromotionPlugin;
import net.mall.plugin.PaymentPlugin;
import net.mall.plugin.PromotionPlugin;
import net.mall.service.PluginConfigService;
import net.mall.service.PromotionService;

/**
 * Controller - 优惠券
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("adminPluginCouponPromotionController")
@RequestMapping("/admin/plugin/coupon_promotion")
public class CouponPromotionController extends BaseController {

    @Inject
    private CouponPromotionPlugin couponPromotionPlugin;
    @Inject
    private PluginConfigService pluginConfigService;
    @Inject
    private PromotionService promotionService;

    /**
     * 安装
     */
    @PostMapping("/install")
    public ResponseEntity<?> install() {
        if (!couponPromotionPlugin.getIsInstalled()) {
            PluginConfig pluginConfig = new PluginConfig();
            pluginConfig.setPluginId(couponPromotionPlugin.getId());
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
        if (couponPromotionPlugin.getIsInstalled()) {
            pluginConfigService.deleteByPluginId(couponPromotionPlugin.getId());
            promotionService.shutDownPromotion(couponPromotionPlugin);
        }
        return Results.OK;
    }

    /**
     * 设置
     */
    @GetMapping("/setting")
    public String setting(ModelMap model) {
        PluginConfig pluginConfig = couponPromotionPlugin.getPluginConfig();
        model.addAttribute("pluginConfig", pluginConfig);
        return "/admin/plugin/coupon_promotion/setting";
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public ResponseEntity<?> update(String displayName, BigDecimal serviceCharge, String description, @RequestParam(defaultValue = "false") Boolean isEnabled, Integer order) {
        if (serviceCharge == null || serviceCharge.compareTo(BigDecimal.ZERO) < 0) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        PluginConfig pluginConfig = couponPromotionPlugin.getPluginConfig();
        Map<String, String> attributes = new HashMap<>();
        attributes.put(PaymentPlugin.DISPLAY_NAME_ATTRIBUTE_NAME, displayName);
        attributes.put(PromotionPlugin.SERVICE_CHARGE, String.valueOf(serviceCharge));
        attributes.put(PaymentPlugin.DESCRIPTION_ATTRIBUTE_NAME, description);
        pluginConfig.setAttributes(attributes);
        pluginConfig.setIsEnabled(isEnabled);
        pluginConfig.setOrder(order);
        pluginConfigService.update(pluginConfig);
        if (!pluginConfig.getIsEnabled()) {
            promotionService.shutDownPromotion(couponPromotionPlugin);
        }
        return Results.OK;
    }

}