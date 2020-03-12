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
 * Controller - 登录插件
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("adminLoginPluginController")
@RequestMapping("/admin/login_plugin")
public class LoginPluginController extends BaseController {

    @Inject
    private PluginService pluginService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(ModelMap model) {
        model.addAttribute("loginPlugins", pluginService.getLoginPlugins());
        return "admin/login_plugin/list";
    }

}