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
import net.mall.service.PromotionService;

/**
 * Controller - 促销
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("adminPromotionController")
@RequestMapping("/admin/promotion")
public class PromotionController extends BaseController {

    @Inject
    private PromotionService promotionService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(Pageable pageable, ModelMap model) {
        model.addAttribute("page", promotionService.findPage(pageable));
        return "admin/promotion/list";
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public ResponseEntity<?> delete(Long[] ids) {
        promotionService.delete(ids);
        return Results.OK;
    }

}