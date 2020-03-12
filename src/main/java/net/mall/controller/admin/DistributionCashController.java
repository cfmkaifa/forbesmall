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
import net.mall.entity.DistributionCash;
import net.mall.service.DistributionCashService;

/**
 * Controller - 分销提现
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("adminDistributionCashController")
@RequestMapping("/admin/distribution_cash")
public class DistributionCashController extends BaseController {

    @Inject
    private DistributionCashService distributionCashService;

    /**
     * 审核
     */
    @PostMapping("/review")
    public ResponseEntity<?> review(Long id, Boolean isPassed) {
        DistributionCash distributionCash = distributionCashService.find(id);
        if (isPassed == null || distributionCash == null || !DistributionCash.Status.PENDING.equals(distributionCash.getStatus())) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        distributionCashService.review(distributionCash, isPassed);
        return Results.OK;
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(DistributionCash.Status status, Pageable pageable, ModelMap model) {
        model.addAttribute("status", status);
        model.addAttribute("page", distributionCashService.findPage(status, null, null, null, null, pageable));
        return "admin/distribution_cash/list";
    }

}