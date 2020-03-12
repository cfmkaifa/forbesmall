/*
 *
 *
 *
 *
 */
package net.mall.controller.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.mall.entity.Admin;
import net.mall.security.CurrentUser;

/**
 * Controller - 管理员登录
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("adminLoginController")
@RequestMapping("/admin")
public class LoginController extends BaseController {

    @Value("${security.admin_login_success_url}")
    private String adminLoginSuccessUrl;

    /**
     * 登录跳转
     */
    @GetMapping({"", "/"})
    public String index() {
        return "redirect:/admin/login";
    }

    /**
     * 登录页面
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(@CurrentUser Admin currentUser, ModelMap model) {
        if (currentUser != null) {
            return "redirect:" + adminLoginSuccessUrl;
        }
        model.addAttribute("adminLoginSuccessUrl", adminLoginSuccessUrl);
        return "admin/login/index";
    }

}