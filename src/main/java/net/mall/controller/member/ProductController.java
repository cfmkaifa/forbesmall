/*
 *
 *
 *
 *
 */
package net.mall.controller.member;

import net.mall.Pageable;
import net.mall.controller.admin.BaseController;
import net.mall.entity.Brand;
import net.mall.entity.Product;
import net.mall.entity.ProductCategory;
import net.mall.entity.ProductTag;
import net.mall.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

/**
 * Controller - 商品
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("memberProductController")
@RequestMapping("/member/product")
public class ProductController extends BaseController {

    @Inject
    private ProductService productService;
    @Inject
    private ProductCategoryService productCategoryService;
    @Inject
    private BrandService brandService;
    @Inject
    private ProductTagService productTagService;
    @Inject
    private StoreService storeService;

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
        model.addAttribute("page", productService.findPage(type, 0,false,null, null, productCategory, null, brand, null, productTag, null, null, null, null, isMarketable, isList, isTop, isActive, isOutOfStock, isStockAlert, null, null, pageable));
        return "member/pro_purch_apply/pro_list";
    }
}