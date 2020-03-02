package net.mall.controller.admin;

import net.mall.Filter;
import net.mall.Pageable;
import net.mall.Results;
import net.mall.entity.ProPurchApply;
import net.mall.service.ProPurchApplyService;
import net.mall.util.ConvertUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Controller("adminProPurchApplyController")
@RequestMapping("/admin/pro_purch_apply")
public class ProPurchApplyController extends BaseController {

    @Inject
    ProPurchApplyService proPurchApplyService;

    /**
     * 审核
     */
    @PostMapping("/review")
    public ResponseEntity<?> review(Long id, ProPurchApply.ProApplyStatus status) {
        ProPurchApply proPurchApply = proPurchApplyService.find(id);
        if (proPurchApply == null
                || !ProPurchApply.ProApplyStatus.PENDING.equals(proPurchApply.getStatus())
                || (!ProPurchApply.ProApplyStatus.APPROVED.equals(status)
                && !ProPurchApply.ProApplyStatus.FAILED.equals(status))) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        proPurchApply.setStatus(status);
        proPurchApplyService.update(proPurchApply);
        return Results.OK;
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(ProPurchApply.ProApplyStatus status, String searchValue, Pageable pageable, ModelMap model) {
        model.addAttribute("status", status);
        if (ConvertUtils.isNotEmpty(status)) {
            pageable.getFilters().add(new Filter("status", Filter.Operator.EQ, status));
        }
        if (ConvertUtils.isNotEmpty(searchValue)) {
            pageable.setSearchProperty("proName");
        }
        model.addAttribute("page", proPurchApplyService.findPage(pageable));
        return "admin/pro_purch_apply/list";
    }
}
