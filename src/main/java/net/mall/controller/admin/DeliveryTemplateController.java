/*
 *
 *
 *
 *
 */
package net.mall.controller.admin;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.mall.Pageable;
import net.mall.service.DeliveryTemplateService;

/**
 * Controller - 快递单模板
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("adminDeliveryTemplateController")
@RequestMapping("/admin/delivery_template")
public class DeliveryTemplateController extends BaseController {

    @Inject
    private DeliveryTemplateService deliveryTemplateService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(Pageable pageable, Model model) {
        model.addAttribute("page", deliveryTemplateService.findPage(pageable));
        return "admin/delivery_template/list";
    }

}