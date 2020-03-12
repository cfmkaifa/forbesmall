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

import net.mall.Pageable;
import net.mall.service.StockLogService;

/**
 * Controller - 库存
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("adminStockController")
@RequestMapping("/admin/stock")
public class StockController extends BaseController {

    @Inject
    private StockLogService stockLogService;

    /**
     * 记录
     */
    @GetMapping("/log")
    public String log(Pageable pageable, ModelMap model) {
        model.addAttribute("page", stockLogService.findPage(pageable));
        return "admin/stock/log";
    }

}