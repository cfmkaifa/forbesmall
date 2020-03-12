package net.mall.controller.business;

import net.mall.Filter;
import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.GroupPurchApplyLog;
import net.mall.entity.Store;
import net.mall.security.CurrentStore;
import net.mall.service.GroupPurchApplyLogService;
import net.mall.util.ConvertUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

/***
 *
 */
@Controller("businessGroupPurchApplyLogController")
@RequestMapping("/business/group_purch_apply_log")
public class GroupPurchApplyLogController extends BaseController {

    @Inject
    GroupPurchApplyLogService groupPurchApplyLogService;


    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(Pageable pageable, String searchValue, @CurrentStore Store currentStore, ModelMap model) {
        pageable.getFilters().add(new Filter("storeId", Filter.Operator.EQ, currentStore.getId()));
        if (ConvertUtils.isNotEmpty(searchValue)) {
            pageable.setSearchProperty("proName");
        }
        Page<GroupPurchApplyLog> page = groupPurchApplyLogService.findPage(pageable);
        model.addAttribute("page", page);
        return "business/group_purch_apply_log/list";
    }
}
