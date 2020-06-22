package net.mall.controller.admin;
import net.mall.Pageable;
import net.mall.Results;
import net.mall.entity.*;
import net.mall.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import java.util.Arrays;

@Controller("adminProPurchApplyController")
@RequestMapping("/admin/pro_purch_apply")
public class ProPurchApplyController extends BaseController {


    @Inject
    private ProductService productService;
    @Inject
    private ProductCategoryService productCategoryService;
    @Inject
    private BrandService brandService;
    @Inject
    private ProductTagService productTagService;

    /**
     * 审核
     */
    @PostMapping("/review")
    public ResponseEntity<?> review(Long id, Product.ProApplyStatus status) {
        Product product = productService.find(id);
        if (product == null) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        product.setIsMarketable(true);
        long statusCount =  Arrays.asList(Product.ProApplyStatus.values()).stream().filter(proApplyStatus -> proApplyStatus.equals(status)).count();
        if(statusCount == 0 ){
            return Results.UNPROCESSABLE_ENTITY;
        }
        productService.modifyProductAudit(status,id);
        return Results.OK;
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(Product.Type type, Long productCategoryId, Long brandId, Long productTagId, Boolean isActive, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isOutOfStock, Boolean isStockAlert, Pageable pageable, ModelMap model) {
        ProductCategory productCategory = productCategoryService.find(productCategoryId);
        Brand brand = brandService.find(brandId);
        ProductTag productTag = productTagService.find(productTagId);
        model.addAttribute("types", Product.Type.values());
        model.addAttribute("productCategoryTree", productCategoryService.findTree());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("productTags", productTagService.findAll());
        model.addAttribute("type", type);
        model.addAttribute("productCategoryId", productCategoryId);
        model.addAttribute("brandId", brandId);
        model.addAttribute("productTagId", productTagId);
        model.addAttribute("isMarketable", isMarketable);
        model.addAttribute("isList", isList);
        model.addAttribute("isTop", isTop);
        model.addAttribute("isActive", isActive);
        model.addAttribute("isOutOfStock", isOutOfStock);
        model.addAttribute("isStockAlert", isStockAlert);
        model.addAttribute("page", productService.findPage(type, 2,false, null, null,null,productCategory, null, brand, null, productTag, null, null, null, null, isMarketable, isList, isTop, isActive, isOutOfStock, isStockAlert, null, null, pageable));
        return "admin/pro_purch_apply/list";
    }
}
