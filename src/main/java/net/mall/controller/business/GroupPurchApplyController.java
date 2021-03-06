package net.mall.controller.business;

import net.mall.Filter;
import net.mall.Page;
import net.mall.Pageable;
import net.mall.Results;
import net.mall.entity.GroupPurchApply;
import net.mall.entity.Product;
import net.mall.entity.Store;
import net.mall.security.CurrentStore;
import net.mall.service.GroupPurchApplyService;
import net.mall.service.ProductService;
import net.mall.util.ConvertUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Controller("businessGroupPurchApplyController")
@RequestMapping("/business/group_purch_apply")
public class GroupPurchApplyController extends BaseController {


    @Inject
    GroupPurchApplyService groupPurchApplyService;

    @Inject
    ProductService productService;


    /***
     * 团购申请
     * @param productId
     * @param model
     * @return
     */
    @GetMapping("/index/{productId}")
    public String applyIndex(@PathVariable Long productId, @CurrentStore Store currentStore,
                             ModelMap model) {
        if (currentStore == null) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }
        Product product = productService.find(productId);
        if (product == null) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }
        model.addAttribute("product", product);
        return "business/group_purch_apply/add";
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity<?> save(GroupPurchApply groupPurchApply,
                                  @CurrentStore Store currentStore) {
        if (groupPurchApply.getStartTime() != null && groupPurchApply.getEndTime() != null
                && groupPurchApply.getStartTime().after(groupPurchApply.getEndTime())) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        Product product = productService.findBySn(groupPurchApply.getProSn());
        if (product == null) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        groupPurchApply.setProName(product.getName());
        groupPurchApply.setStoreId(currentStore.getId());
        groupPurchApply.setStoreName(currentStore.getName());
        groupPurchApply.setStatus(GroupPurchApply.ApplyStatus.PENDING);
        groupPurchApply.setJobStatus(GroupPurchApply.JobStatus.PENDING);
        groupPurchApplyService.save(groupPurchApply);
        return Results.OK;
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(Pageable pageable, String searchValue, @CurrentStore Store currentStore, ModelMap model) {
        pageable.getFilters().add(new Filter("storeId", Filter.Operator.EQ, currentStore.getId()));
        if (ConvertUtils.isNotEmpty(searchValue)) {
            pageable.setSearchProperty("proName");
        }
        Page<GroupPurchApply> page = groupPurchApplyService.findPage(pageable);
        model.addAttribute("page", page);
        return "business/group_purch_apply/list";
    }
}
