/*
 *
 *
 *
 *
 */
package net.mall.controller.admin.plugin;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.mall.Results;
import net.mall.controller.admin.BaseController;
import net.mall.entity.PluginConfig;
import net.mall.plugin.LocalStoragePlugin;
import net.mall.service.PluginConfigService;

/**
 * Controller - 本地文件存储
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("adminPluginLocalStorageController")
@RequestMapping("/admin/plugin/local_storage")
public class LocalStorageController extends BaseController {

    @Inject
    private LocalStoragePlugin localStoragePlugin;
    @Inject
    private PluginConfigService pluginConfigService;

    /**
     * 设置
     */
    @GetMapping("/setting")
    public String setting(ModelMap model) {
        PluginConfig pluginConfig = localStoragePlugin.getPluginConfig();
        model.addAttribute("pluginConfig", pluginConfig);
        return "/admin/plugin/local_storage/setting";
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public ResponseEntity<?> update(Integer order) {
        PluginConfig pluginConfig = localStoragePlugin.getPluginConfig();
        pluginConfig.setIsEnabled(true);
        pluginConfig.setOrder(order);
        pluginConfigService.update(pluginConfig);
        return Results.OK;
    }

}