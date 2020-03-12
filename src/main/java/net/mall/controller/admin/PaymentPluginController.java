/*
 *
 *
 *
 *
 */
package net.mall.controller.admin;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.mall.service.PluginService;

/**
 * Controller - 支付插件
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("adminPaymentPluginController")
@RequestMapping("/admin/payment_plugin")
public class PaymentPluginController extends BaseController {

    @Inject
    private PluginService pluginService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(ModelMap model) {
        model.addAttribute("paymentPlugins", pluginService.getPaymentPlugins());
        return "admin/payment_plugin/list";
    }

}