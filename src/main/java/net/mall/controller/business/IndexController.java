/*
 *
 *
 *
 *
 */
package net.mall.controller.business;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.mall.entity.Store;
import net.mall.security.CurrentStore;

/**
 * Controller - 商家中心
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("businessIndexController")
@RequestMapping("/business/index")
public class IndexController extends BaseController {

    /**
     * 首页
     */
    @GetMapping
    public String index(@CurrentStore Store currentStore, ModelMap model) {
        model.addAttribute("currentStore", currentStore);
        return "business/index";
    }

}