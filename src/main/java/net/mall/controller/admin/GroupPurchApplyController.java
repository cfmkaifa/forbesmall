package net.mall.controller.admin;

import net.mall.Filter;
import net.mall.Pageable;
import net.mall.Results;
import net.mall.entity.GroupPurchApply;
import net.mall.service.GroupPurchApplyService;
import net.mall.util.ConvertUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Controller("adminGroupPurchApplyController")
@RequestMapping("/admin/group_purch_apply")
public class GroupPurchApplyController extends BaseController {

    @Inject
    GroupPurchApplyService groupPurchApplyService;

    /**
     * 审核
     */
    @PostMapping("/review")
    public ResponseEntity<?> review(Long id, GroupPurchApply.ApplyStatus status) {
        GroupPurchApply groupPurchApply = groupPurchApplyService.find(id);
        if (groupPurchApply == null
                || !GroupPurchApply.ApplyStatus.PENDING.equals(groupPurchApply.getStatus())
                || (!GroupPurchApply.ApplyStatus.APPROVED.equals(status)
                && !GroupPurchApply.ApplyStatus.FAILED.equals(status))) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        groupPurchApply.setStatus(status);
        if (GroupPurchApply.ApplyStatus.APPROVED.equals(status)) {
            groupPurchApply.setJobStatus(GroupPurchApply.JobStatus.PENDING);
        }
        groupPurchApplyService.update(groupPurchApply);
        return Results.OK;
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(GroupPurchApply.ApplyStatus status, String searchValue, Pageable pageable, ModelMap model) {
        model.addAttribute("status", status);
        if (ConvertUtils.isNotEmpty(status)) {
            pageable.getFilters().add(new Filter("status", Filter.Operator.EQ, status));
        }
        if (ConvertUtils.isNotEmpty(searchValue)) {
            pageable.setSearchProperty("proName");
        }
        model.addAttribute("page", groupPurchApplyService.findPage(pageable));
        return "admin/group_purch_apply/list";
    }
}
