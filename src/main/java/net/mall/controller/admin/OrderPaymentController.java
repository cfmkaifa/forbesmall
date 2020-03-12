/*
 *
 *
 *
 *
 */
package net.mall.controller.admin;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.mall.Pageable;
import net.mall.Results;
import net.mall.service.OrderPaymentService;

/**
 * Controller - 订单支付
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("adminOrderPaymentController")
@RequestMapping("/admin/order_payment")
public class OrderPaymentController extends BaseController {

    @Inject
    private OrderPaymentService orderPaymentService;

    /**
     * 查看
     */
    @GetMapping("/view")
    public String view(Long id, ModelMap model) {
        model.addAttribute("orderPayment", orderPaymentService.find(id));
        return "admin/order_payment/view";
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(Pageable pageable, ModelMap model) {
        model.addAttribute("page", orderPaymentService.findPage(pageable));
        return "admin/order_payment/list";
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public ResponseEntity<?> delete(Long[] ids) {
        orderPaymentService.delete(ids);
        return Results.OK;
    }

}