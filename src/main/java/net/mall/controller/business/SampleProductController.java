/*
 *
 *
 *
 *
 */
package net.mall.controller.business;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.mall.Pageable;
import net.mall.Results;
import net.mall.entity.BaseEntity;
import net.mall.entity.Brand;
import net.mall.entity.ParameterValue;
import net.mall.entity.Product;
import net.mall.entity.ProductCategory;
import net.mall.entity.ProductTag;
import net.mall.entity.Promotion;
import net.mall.entity.Sku;
import net.mall.entity.SpecificationItem;
import net.mall.entity.Store;
import net.mall.entity.StoreProductCategory;
import net.mall.entity.StoreProductTag;
import net.mall.security.CurrentStore;
import net.mall.service.BrandService;
import net.mall.service.ProductCategoryService;
import net.mall.service.ProductService;
import net.mall.service.ProductTagService;
import net.mall.service.PromotionService;
import net.mall.service.SkuService;
import net.mall.service.SpecificationItemService;
import net.mall.service.SpecificationService;
import net.mall.service.StoreProductCategoryService;
import net.mall.service.StoreProductTagService;

/**
 * Controller - 商品
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("sampleProductController")
@RequestMapping("/business/sample")
public class SampleProductController extends BaseController {

    @Inject
    private ProductService productService;
    @Inject
    private SkuService skuService;
    @Inject
    private ProductCategoryService productCategoryService;
    @Inject
    private StoreProductCategoryService storeProductCategoryService;
    @Inject
    private BrandService brandService;
    @Inject
    private PromotionService promotionService;
    @Inject
    private ProductTagService productTagService;
    @Inject
    private StoreProductTagService storeProductTagService;
    @Inject
    private SpecificationItemService specificationItemService;
    @Inject
    private SpecificationService specificationService;


    /**
     * 添加属性
     */
    @ModelAttribute
    public void populateModel(Long productId, Long productCategoryId, @CurrentStore Store currentStore, ModelMap model) {
        Product product = productService.find(productId);
        ProductCategory productCategory = null;
        if (null == productCategoryId
                && null != product) {
            productCategory = product.getProductCategory();
        } else {
            productCategory = productCategoryService.find(productCategoryId);
        }
        model.addAttribute("product", product);
        model.addAttribute("productCategory", productCategory);
    }


    /**
     * 编辑
     */
    @GetMapping("/edit")
    public String edit(@ModelAttribute(binding = false) Product product,
                       @CurrentStore Store currentStore, ModelMap model) {
        if (product == null) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }
        model.addAttribute("maxProductImageSize", Product.MAX_PRODUCT_IMAGE_SIZE);
        model.addAttribute("maxParameterValueSize", Product.MAX_PARAMETER_VALUE_SIZE);
        model.addAttribute("maxParameterValueEntrySize", ParameterValue.MAX_ENTRY_SIZE);
        model.addAttribute("maxSpecificationItemSize", Product.MAX_SPECIFICATION_ITEM_SIZE);
        model.addAttribute("maxSpecificationItemEntrySize", SpecificationItem.MAX_ENTRY_SIZE);
        model.addAttribute("types", Product.Type.values());
        model.addAttribute("productCategoryTree", productCategoryService.findTree());
        model.addAttribute("storeProductCategoryTree", storeProductCategoryService.findTree(currentStore));
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("promotions", promotionService.findList(null, currentStore, true));
        model.addAttribute("productTags", productTagService.findAll());
        model.addAttribute("storeProductTags", storeProductTagService.findList(currentStore, true));
        model.addAttribute("specifications", specificationService.findAll());
        model.addAttribute("product", product);
        return "business/sample/edit";
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public ResponseEntity<?> update(@ModelAttribute("productForm") Product productForm,
                                    @ModelAttribute(binding = false) Product product,
                                    @ModelAttribute(binding = false) ProductCategory productCategory,
                                    SkuForm skuForm, SkuListForm skuListForm, Long brandId, Long[] promotionIds,
                                    Long[] productTagIds, Long[] storeProductTagIds, Long storeProductCategoryId,
                                    HttpServletRequest request, @CurrentStore Store currentStore) {
        specificationItemService.filter(productForm.getSpecificationItems());
        skuService.filter(skuListForm.getSkuList());
        if (product == null) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (productCategory == null) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        List<Promotion> promotions = promotionService.findList(promotionIds);
        if (CollectionUtils.isNotEmpty(promotions)) {
            if (currentStore.getPromotions() == null || !currentStore.getPromotions().containsAll(promotions)) {
                return Results.UNPROCESSABLE_ENTITY;
            }
        }
        if (storeProductCategoryId != null) {
            StoreProductCategory storeProductCategory = storeProductCategoryService.find(storeProductCategoryId);
            if (storeProductCategory == null || !currentStore.equals(storeProductCategory.getStore())) {
                return Results.UNPROCESSABLE_ENTITY;
            }
            productForm.setStoreProductCategory(storeProductCategory);
        }
        productForm.setId(product.getId());
        productForm.setType(product.getType());
        productForm.setIsActive(true);
        productForm.setProductCategory(productCategory);
        productForm.setBrand(brandService.find(brandId));
        productForm.setPromotions(new HashSet<>(promotions));
        productForm.setProductTags(new HashSet<>(productTagService.findList(productTagIds)));
        productForm.setStoreProductTags(new HashSet<>(storeProductTagService.findList(storeProductTagIds)));
        productForm.removeAttributeValue();
        if (productForm.hasSpecification()) {
            List<Sku> skus = skuListForm.getSkuList();
            if (CollectionUtils.isEmpty(skus) || !isValid(skus, getValidationGroup(productForm.getType()), BaseEntity.Update.class)) {
                return Results.UNPROCESSABLE_ENTITY;
            }
            productService.modifySample(productForm, skus, currentStore);
        } else {
            Sku sku = skuForm.getSku();
            if (sku == null || !isValid(sku, getValidationGroup(productForm.getType()), BaseEntity.Update.class)) {
                return Results.UNPROCESSABLE_ENTITY;
            }
            productService.modifySample(productForm, sku, currentStore);
        }
        return Results.OK;
    }


    private Class<?> getValidationGroup(Product.Type type) {
        Assert.notNull(type, "[Assertion failed] - type is required; it must not be null");
        switch (type) {
            case GENERAL:
                return Sku.General.class;
            case EXCHANGE:
                return Sku.Exchange.class;
            case GIFT:
                return Sku.Gift.class;
        }
        return null;
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(@ModelAttribute(name = "productCategory", binding = false) ProductCategory productCategory, Product.Type type, Long brandId, Long promotionId, Long productTagId, Long storeProductTagId, Boolean isActive, Boolean isList, Boolean isTop, Boolean isOutOfStock, Boolean isStockAlert,
                       Pageable pageable, @CurrentStore Store currentStore, ModelMap model) {
        Brand brand = brandService.find(brandId);
        Promotion promotion = promotionService.find(promotionId);
        ProductTag productTag = productTagService.find(productTagId);
        StoreProductTag storeProductTag = storeProductTagService.find(storeProductTagId);
        if (promotion != null) {
            if (!currentStore.equals(promotion.getStore())) {
                return UNPROCESSABLE_ENTITY_VIEW;
            }
        }
        if (storeProductTag != null) {
            if (!currentStore.equals(storeProductTag.getStore())) {
                return UNPROCESSABLE_ENTITY_VIEW;
            }
        }
        model.addAttribute("types", Product.Type.values());
        model.addAttribute("productCategoryTree", productCategoryService.findTree());
        model.addAttribute("allowedProductCategories", productCategoryService.findList(currentStore, null, null, null));
        model.addAttribute("allowedProductCategoryParents", getAllowedProductCategoryParents(currentStore));
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("promotions", promotionService.findList(null, currentStore, true));
        model.addAttribute("productTags", productTagService.findAll());
        model.addAttribute("storeProductTags", storeProductTagService.findList(currentStore, true));
        model.addAttribute("type", type);
        model.addAttribute("productCategoryId", productCategory != null ? productCategory.getId() : null);
        model.addAttribute("brandId", brandId);
        model.addAttribute("promotionId", promotionId);
        model.addAttribute("productTagId", productTagId);
        model.addAttribute("storeProductTagId", storeProductTagId);
        model.addAttribute("isList", isList);
        model.addAttribute("isTop", isTop);
        model.addAttribute("isActive", isActive);
        model.addAttribute("isOutOfStock", isOutOfStock);
        model.addAttribute("isStockAlert", isStockAlert);
        model.addAttribute("page", productService.findPage(type, 1,false, null, currentStore,null, productCategory, null, brand, promotion, productTag, storeProductTag, null, null, null, true, isList, isTop, isActive, isOutOfStock, isStockAlert, null, null, pageable));
        return "business/sample/list";
    }


    /**
     * 获取允许发布商品分类上级分类
     *
     * @param store 店铺
     * @return 允许发布商品分类上级分类
     */
    private Set<ProductCategory> getAllowedProductCategoryParents(Store store) {
        Assert.notNull(store, "[Assertion failed] - store is required; it must not be null");
        Set<ProductCategory> result = new HashSet<>();
        List<ProductCategory> allowedProductCategories = productCategoryService.findList(store, null, null, null);
        for (ProductCategory allowedProductCategory : allowedProductCategories) {
            result.addAll(allowedProductCategory.getParents());
        }
        return result;
    }


    /**
     * FormBean - SKU
     *
     * @author huanghy
     * @version 6.1
     */
    public static class SkuForm {

        /**
         * SKU
         */
        private Sku sku;

        /**
         * 获取SKU
         *
         * @return SKU
         */
        public Sku getSku() {
            return sku;
        }

        /**
         * 设置SKU
         *
         * @param sku SKU
         */
        public void setSku(Sku sku) {
            this.sku = sku;
        }

    }

    /**
     * FormBean - SKU
     *
     * @author huanghy
     * @version 6.1
     */
    public static class SkuListForm {

        /**
         * SKU
         */
        private List<Sku> skuList;

        /**
         * 获取SKU
         *
         * @return SKU
         */
        public List<Sku> getSkuList() {
            return skuList;
        }

        /**
         * 设置SKU
         *
         * @param skuList SKU
         */
        public void setSkuList(List<Sku> skuList) {
            this.skuList = skuList;
        }

    }
}