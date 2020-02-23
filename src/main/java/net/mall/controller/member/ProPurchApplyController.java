package net.mall.controller.member;

import net.mall.Filter;
import net.mall.Page;
import net.mall.Pageable;
import net.mall.Results;
import net.mall.controller.business.BaseController;
import net.mall.entity.Member;
import net.mall.entity.ProPurchApply;
import net.mall.entity.Product;
import net.mall.security.CurrentUser;
import net.mall.service.ProPurchApplyService;
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
import java.util.Date;

@Controller("memberProPurchApplyController")
@RequestMapping("/member/pro_purch_apply")
public class ProPurchApplyController extends BaseController {


    @Inject
    ProPurchApplyService proPurchApplyService;

    @Inject
    ProductService productService;


    /***
     * 采购购申请
     * @param productId
     * @param model
     * @return
     */
    @GetMapping("/index/{productId}")
    public String applyIndex(@PathVariable Long productId, @CurrentUser Member currentMember,
                             ModelMap model) {
        if (currentMember == null) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }
        Product product = productService.find(productId);
        if (product == null) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }
        model.addAttribute("product", product);
        return "member/pro_purch_apply/add";
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity<?> save(ProPurchApply purchApply,
                                  @CurrentUser Member currentMember) {
        if (purchApply.getStartTime() != null && purchApply.getEndTime() != null
                && purchApply.getStartTime().after(purchApply.getEndTime())) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        Product product = productService.findBySn(purchApply.getProSn());
        if (product == null) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        String proSn = product.getSn();
        String skuSn = purchApply.getSkuSn();
        Date currentDate = new Date();
        ProPurchApply oldProPurchApply = proPurchApplyService.proPurchApply(proSn,skuSn,currentDate,ProPurchApply.ProApplyStatus.PENDING);
        if(ConvertUtils.isNotEmpty(oldProPurchApply)){
            return Results.UNPROCESSABLE_ENTITY;
        }
        ProPurchApply olProPurchApply = proPurchApplyService.proPurchApply(proSn,skuSn,currentDate,ProPurchApply.ProApplyStatus.APPROVED);
        if(ConvertUtils.isNotEmpty(olProPurchApply)){
            return Results.UNPROCESSABLE_ENTITY;
        }
        purchApply.setProName(product.getName());
        purchApply.setMemberId(currentMember.getId());
        purchApply.setMembeName(currentMember.getName());
        purchApply.setStatus(ProPurchApply.ProApplyStatus.PENDING);
        proPurchApplyService.save(purchApply);
        return Results.OK;
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(Pageable pageable, String searchValue, @CurrentUser Member currentMember, ModelMap model) {
        pageable.getFilters().add(new Filter("memberId",Filter.Operator.EQ,currentMember.getId()));
        if(ConvertUtils.isNotEmpty(searchValue)){
            pageable.setSearchProperty("proName");
        }
        Page<ProPurchApply> page = proPurchApplyService.findPage(pageable);
        model.addAttribute("page",page);
        return "member/pro_purch_apply/list";
    }
}
