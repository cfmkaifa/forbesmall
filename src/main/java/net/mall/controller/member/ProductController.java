/*
 *
 *
 *
 *
 */
package net.mall.controller.member;

import net.mall.FileType;
import net.mall.Pageable;
import net.mall.Results;
import net.mall.controller.admin.BaseController;
import net.mall.entity.*;
import net.mall.exception.UnauthorizedException;
import net.mall.security.CurrentUser;
import net.mall.service.*;
import net.mall.util.ConvertUtils;
import net.mall.util.SensorsAnalyticsUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    private SpecificationService specificationService;
    @Inject
    private PromotionService promotionService;
    @Inject
    private SpecificationItemService specificationItemService;
    @Inject
    private SkuService skuService;
    @Inject
    private ParameterValueService parameterValueService;
    @Inject
    private AttributeService attributeService;
    @Inject
    private ProductImageService productImageService;
    @Inject
    private FileService fileService;
    @Inject
    SensorsAnalyticsUtils sensorsAnalyticsUtils;



    /**
     * 添加属性
     */
    @ModelAttribute
    public void populateModel(Long productId,@CurrentUser Member currentMember,Long productCategoryId, ModelMap model) {
        Product product = productService.find(productId);
        if (product != null && !currentMember.equals(product.getMember())) {
            throw new UnauthorizedException();
        }
        ProductCategory productCategory = productCategoryService.find(productCategoryId);
        model.addAttribute("product", product);
        model.addAttribute("productCategory", productCategory);
    }

    /**
     * 检查编号是否存在
     */
    @GetMapping("/check_sn")
    public @ResponseBody
    boolean checkSn(String sn) {
        return StringUtils.isNotEmpty(sn) && !productService.snExists(sn);
    }


    /**
     * 获取规格
     */
    @GetMapping("/specifications")
    public @ResponseBody
    List<Map<String, Object>> specifications(@ModelAttribute(binding = false) ProductCategory productCategory) {
        List<Map<String, Object>> data = new ArrayList<>();
        if (productCategory == null || CollectionUtils.isEmpty(productCategory.getSpecifications())) {
            return data;
        }
        for (Specification specification : productCategory.getSpecifications()) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", specification.getName());
            item.put("options", specification.getOptions());
            data.add(item);
        }
        return data;
    }

    /**
     * 获取属性
     */
    @GetMapping("/attributes")
    public @ResponseBody
    List<Map<String, Object>> attributes(@ModelAttribute(binding = false) ProductCategory productCategory) {
        List<Map<String, Object>> data = new ArrayList<>();
        if (productCategory == null || CollectionUtils.isEmpty(productCategory.getAttributes())) {
            return data;
        }
        for (Attribute attribute : productCategory.getAttributes()) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", attribute.getId());
            item.put("name", attribute.getName());
            item.put("options", attribute.getOptions());
            data.add(item);
        }
        return data;
    }

    /**
     * 获取参数
     */
    @GetMapping("/parameters")
    public @ResponseBody
    List<Map<String, Object>> parameters(@ModelAttribute(binding = false) ProductCategory productCategory) {
        List<Map<String, Object>> data = new ArrayList<>();
        if (productCategory == null || CollectionUtils.isEmpty(productCategory.getParameters())) {
            return data;
        }
        for (Parameter parameter : productCategory.getParameters()) {
            Map<String, Object> item = new HashMap<>();
            item.put("group", parameter.getGroup());
            item.put("names", parameter.getNames());
            data.add(item);
        }
        return data;
    }

    /**
     * 上传商品图片
     */
    @PostMapping("/upload_product_image")
    public ResponseEntity<?> uploadProductImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (!fileService.isValid(FileType.IMAGE, file)) {
            return Results.unprocessableEntity("common.upload.invalid");
        }
        ProductImage productImage = productImageService.generate(file);
        if (productImage == null) {
            return Results.unprocessableEntity("common.upload.error");
        }
        return ResponseEntity.ok(productImage);
    }

    /**
     * 添加
     */
    @GetMapping("/add")
    public String add(@CurrentUser Member member, ModelMap model) {
        model.addAttribute("maxProductImageSize", Product.MAX_PRODUCT_IMAGE_SIZE);
        model.addAttribute("maxParameterValueSize", Product.MAX_PARAMETER_VALUE_SIZE);
        model.addAttribute("maxParameterValueEntrySize", ParameterValue.MAX_ENTRY_SIZE);
        model.addAttribute("maxSpecificationItemSize", Product.MAX_SPECIFICATION_ITEM_SIZE);
        model.addAttribute("maxSpecificationItemEntrySize", SpecificationItem.MAX_ENTRY_SIZE);
        model.addAttribute("productCategoryTree", productCategoryService.findTree());
        model.addAttribute("types", Product.Type.values());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("promotions", promotionService.findList(null, null, true));
        model.addAttribute("productTags", productTagService.findAll());
        model.addAttribute("specifications", specificationService.findAll());
        return "member/pro_purch_apply/pro_add";
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(Product.Type type, Long productCategoryId, Long brandId, Long productTagId, Boolean isActive, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isOutOfStock, Boolean isStockAlert, Pageable pageable,@CurrentUser Member member,ModelMap model) {
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
        model.addAttribute("page", productService.findPage(type, 2,false,null, null,member, productCategory, null, brand, null, productTag, null, null, null, null, isMarketable, isList, isTop, isActive, isOutOfStock, isStockAlert, null, null, pageable));
        return "member/pro_purch_apply/pro_list";
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


    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity<?> save(@ModelAttribute(name = "productForm") Product productForm,
                                  @ModelAttribute(binding = false) ProductCategory productCategory,
                                  SkuForm skuForm,SkuListForm skuListForm,
                                  Long brandId, Long[] promotionIds, Long[] productTagIds,HttpServletRequest request,
                                  @CurrentUser Member currentMember) {
        productForm.setType(Product.Type.GENERAL);
        productImageService.filter(productForm.getProductImages());
        parameterValueService.filter(productForm.getParameterValues());
        specificationItemService.filter(productForm.getSpecificationItems());
        skuService.filter(skuListForm.getSkuList());
        if (productCategory == null) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if(ConvertUtils.isNotEmpty(productForm.getExpire())){
            if(productForm.getExpire().before(new Date())){
                return Results.UNPROCESSABLE_ENTITY;
            }
        } else {
            productForm.setExpire(DateUtils.addMonths(new Date(),1));
        }
        productForm.setPurch(true);
        productForm.setIsAudit(Product.ProApplyStatus.PENDING);
        productForm.setMember(currentMember);
        productForm.setIsMarketable(true);
        productForm.setIsTop(true);
        productForm.setIsList(true);
        productForm.setIsActive(true);
        productForm.setIsDelivery(false);
        productForm.setProductCategory(productCategory);
        productForm.setBrand(brandService.find(brandId));
        productForm.setPromotions(new HashSet<>(promotionService.findList(promotionIds)));
        productForm.setProductTags(new HashSet<>(productTagService.findList(productTagIds)));
        productForm.removeAttributeValue();
        for (Attribute attribute : productForm.getProductCategory().getAttributes()) {
            String value = request.getParameter("attribute_" + attribute.getId());
            String attributeValue = attributeService.toAttributeValue(attribute, value);
            productForm.setAttributeValue(attribute, attributeValue);
        }
        if (!isValid(productForm, BaseEntity.Purchase.class)) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (StringUtils.isNotEmpty(productForm.getSn()) && productService.snExists(productForm.getSn())) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (productForm.hasSpecification()) {
            List<Sku> skus = skuListForm.getSkuList();
            if (CollectionUtils.isEmpty(skus) || !isValid(skus, getValidationGroup(productForm.getType()), BaseEntity.Save.class)) {
                return Results.UNPROCESSABLE_ENTITY;
            }
            productService.create(productForm, skus);
        } else {
            Sku sku = skuForm.getSku();
            if (sku == null || !isValid(sku, getValidationGroup(productForm.getType()), BaseEntity.Save.class)) {
                return Results.UNPROCESSABLE_ENTITY;
            }
            productService.create(productForm, sku);
        }
        //神策提交采购埋点上报神策
        if(ConvertUtils.isNotEmpty(productForm)){
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("commodity_name",productForm.getName());
            map.put("first_commodity",productForm.getProductCategory().getName());
            map.put("second_commodity",productForm.getProductCategory().getParent().getName());
            map.put("present_price",productForm.getPrice());
            map.put("member_id",currentMember.getId());
            map.put("member_name",currentMember.getName());
            List<Sku> skus=skuListForm.getSkuList();
            for(Sku item:skus){
                map.put("goods_amount",item.getStock());
                break;
            }
            List<SpecificationItem> specificationItems=productForm.getSpecificationItems();
            for(SpecificationItem temp:specificationItems){
                if(temp.getName().contains("颜色")){
                    List<SpecificationItem.Entry> entries=temp.getEntries();
                    for (SpecificationItem.Entry tempEntry:entries){
                        if(tempEntry.getIsSelected()){
                            map.put("commodity_color",tempEntry.getValue());
                        }
                    }
                }
                if(temp.getName().contains("cm")){
                    List<SpecificationItem.Entry> entries=temp.getEntries();
                    for (SpecificationItem.Entry tempEntry:entries){
                        if(tempEntry.getIsSelected()){
                            map.put("commodity_cm",tempEntry.getValue());
                        }
                    }
                }
                if(temp.getName().contains("gsm")){
                    List<SpecificationItem.Entry> entries=temp.getEntries();
                    for (SpecificationItem.Entry tempEntry:entries){
                        if(tempEntry.getIsSelected()){
                            map.put("commodity_gsm",tempEntry.getValue());
                        }
                    }
                }
                if(temp.getName().contains("纤维")){
                    List<SpecificationItem.Entry> entries=temp.getEntries();
                    for (SpecificationItem.Entry tempEntry:entries){
                        if(tempEntry.getIsSelected()){
                            map.put("commodity_fiber",tempEntry.getValue());
                        }
                    }
                }
            }
            sensorsAnalyticsUtils.reportData(String.valueOf(currentMember.getId()),"FiberpurchApply",map);
        }
        return Results.OK;
    }


    /**
     * 根据类型获取验证组
     *
     * @param type 类型
     * @return 验证组
     */
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
}