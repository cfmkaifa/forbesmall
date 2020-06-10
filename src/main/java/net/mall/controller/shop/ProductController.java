/*
 *
 *
 *
 *
 */
package net.mall.controller.shop;

import com.fasterxml.jackson.annotation.JsonView;
import net.mall.Filter;
import net.mall.Page;
import net.mall.Pageable;
import net.mall.Results;
import net.mall.entity.*;
import net.mall.exception.ResourceNotFoundException;
import net.mall.security.CurrentUser;
import net.mall.service.*;
import net.mall.util.ConvertUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller - 商品
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("shopProductController")
@RequestMapping("/product")
public class ProductController extends BaseController {

    /**
     * 最大对比商品数
     */
    public static final Integer MAX_COMPARE_PRODUCT_COUNT = 4;

    /**
     * 最大浏览记录商品数
     */
    public static final Integer MAX_HISTORY_PRODUCT_COUNT = 10;

    @Inject
    private ProductService productService;
    @Inject
    private SkuService skuService;
    @Inject
    private ProPurchApplyService proPurchApplyService;
    @Inject
    private StoreService storeService;
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
    private AttributeService attributeService;
    @Inject
    private MemberService memberService;

    /***
     * 判断手机
     * @param request
     * @param successCallback
     */
    private void isMobile(HttpServletRequest request, SuccessCallback successCallback){
        /***判断是否手机端
         * **/
        if (request.getAttribute(DeviceUtils.CURRENT_DEVICE_ATTRIBUTE) instanceof Device) {
            Device device = (Device) request.getAttribute(DeviceUtils.CURRENT_DEVICE_ATTRIBUTE);
            if (device.isMobile()
                    || device.isTablet()) {
                successCallback.onSuccess(false);
            }
        }
    }

    /**
     * 详情
     */
    @GetMapping("/detail/{productId}")
    public String detail(@PathVariable Long productId, HttpServletRequest request,@CurrentUser Member currentUser,
                         ModelMap model) {
        Product product = productService.find(productId);
        if (product == null || BooleanUtils.isNotTrue(product.getIsActive()) || BooleanUtils.isNotTrue(product.getIsMarketable())) {
            throw new ResourceNotFoundException();
        }
        isMobile(request,o->{
            product.setSample(true);
        });
        Product sourceProduct=productService.find(product.getSourceProId());
        if(sourceProduct == null){
            model.addAttribute("product", product);
        }else {
            /*product.setCreatedDate(sourceProduct.getCreatedDate());*/
            model.addAttribute("product", product);
        }
        if(currentUser!=null){
            String checkresult= String.valueOf(currentUser.getIsAudit());
            model.addAttribute("checkresult",checkresult);
        }
        model.addAttribute("temp_is_group",ConvertUtils.isNotEmpty(product.getGroup())?product.getGroup():false);
        model.addAttribute("temp_is_purch",ConvertUtils.isNotEmpty(product.getPurch())?product.getPurch():false);
        model.addAttribute("temp_is_sample",ConvertUtils.isNotEmpty(product.getSample())?product.getSample():false);
        Set<Sku> skuSet=product.getSkus();
        for(Sku temp:skuSet){
            List<SpecificationValue> specificationValues=temp.getSpecificationValues();
            for(SpecificationValue spec:specificationValues){
                if(spec.getValue().contains("mm")){
                    model.addAttribute("temp_commodity_length",spec.getValue());
                }else if(spec.getValue().contains("dtex")){
                    model.addAttribute("temp_commodity_dtex",spec.getValue());
                } else if(spec.getValue().contains("kg")){
                    model.addAttribute("temp_commodity_weight",spec.getValue());
                }else{
                    model.addAttribute("temp_commodity_color",spec.getValue());
                }
            }
        }
        return "shop/product/detail";
    }


    /**
     * 详情
     */
    @GetMapping("/sample-detail/{productId}")
    public String sampleDetail(@PathVariable Long productId, HttpServletRequest request,
                               ModelMap model) {
        Product product = productService.find(productId);
        if (product == null || BooleanUtils.isNotTrue(product.getIsActive()) || BooleanUtils.isNotTrue(product.getIsMarketable())) {
            throw new ResourceNotFoundException();
        }
        product.setSample(true);
        model.addAttribute("product", product);
        model.addAttribute("temp_is_group",ConvertUtils.isNotEmpty(product.getGroup())?product.getGroup():false);
        model.addAttribute("temp_is_purch",ConvertUtils.isNotEmpty(product.getPurch())?product.getPurch():false);
        model.addAttribute("temp_is_sample",ConvertUtils.isNotEmpty(product.getSample())?product.getSample():false);
        Set<Sku> skuSet=product.getSkus();
        for(Sku temp:skuSet){
            List<SpecificationValue> specificationValues=temp.getSpecificationValues();
            for(SpecificationValue spec:specificationValues){
                if(spec.getValue().contains("mm")){
                    model.addAttribute("temp_commodity_length",spec.getValue());
                }else if(spec.getValue().contains("dtex")){
                    model.addAttribute("temp_commodity_dtex",spec.getValue());
                } else if(spec.getValue().contains("kg")){
                    model.addAttribute("temp_commodity_weight",spec.getValue());
                }else{
                    model.addAttribute("temp_commodity_color",spec.getValue());
                }
            }
        }
        return "shop/product/sample/detail";
    }

    /**
     * 详情
     */
    @GetMapping("/purch-detail/{productId}")
    public String purchDetail(@PathVariable Long productId, HttpServletRequest request,
                               ModelMap model) {
        Product product = productService.find(productId);
        if (product == null || BooleanUtils.isNotTrue(product.getIsActive()) || BooleanUtils.isNotTrue(product.getIsMarketable())) {
            throw new ResourceNotFoundException();
        }
        product.setSample(true);
        model.addAttribute("product", product);
        model.addAttribute("temp_is_group",ConvertUtils.isNotEmpty(product.getGroup())?product.getGroup():false);
        model.addAttribute("temp_is_purch",ConvertUtils.isNotEmpty(product.getPurch())?product.getPurch():false);
        model.addAttribute("temp_is_sample",ConvertUtils.isNotEmpty(product.getSample())?product.getSample():false);
        Set<Sku> skuSet=product.getSkus();
        for(Sku temp:skuSet){
            List<SpecificationValue> specificationValues=temp.getSpecificationValues();
            for(SpecificationValue spec:specificationValues){
                if(spec.getValue().contains("mm")){
                    model.addAttribute("temp_commodity_length",spec.getValue());
                }else if(spec.getValue().contains("dtex")){
                    model.addAttribute("temp_commodity_dtex",spec.getValue());
                } else if(spec.getValue().contains("kg")){
                    model.addAttribute("temp_commodity_weight",spec.getValue());
                }else{
                    model.addAttribute("temp_commodity_color",spec.getValue());
                }
            }
        }
        return "shop/product/purch/detail";
    }


    /**
     * 团购商品详情
     */
    @GetMapping("/group-purch-detail/{productId}")
    public String groupPurchDetail(@PathVariable Long productId, HttpServletRequest request,
                                   ModelMap model) {
        Product product = productService.find(productId);
        if (product == null || BooleanUtils.isNotTrue(product.getIsActive()) || BooleanUtils.isNotTrue(product.getIsMarketable())) {
            throw new ResourceNotFoundException();
        }
        model.addAttribute("product", product);
        model.addAttribute("temp_is_group",ConvertUtils.isNotEmpty(product.getGroup())?product.getGroup():false);
        model.addAttribute("temp_is_purch",ConvertUtils.isNotEmpty(product.getPurch())?product.getPurch():false);
        model.addAttribute("temp_is_sample",ConvertUtils.isNotEmpty(product.getSample())?product.getSample():false);
        Set<Sku> skuSet=product.getSkus();
        for(Sku temp:skuSet){
            List<SpecificationValue> specificationValues=temp.getSpecificationValues();
            for(SpecificationValue spec:specificationValues){
                if(spec.getValue().contains("mm")){
                    model.addAttribute("temp_commodity_length",spec.getValue());
                }else if(spec.getValue().contains("dtex")){
                    model.addAttribute("temp_commodity_dtex",spec.getValue());
                } else if(spec.getValue().contains("kg")){
                    model.addAttribute("temp_commodity_weight",spec.getValue());
                }else{
                    model.addAttribute("temp_commodity_color",spec.getValue());
                }
            }
        }
        return "shop/product/group_purch/detail";
    }

    /**
     * 对比栏
     */
    @GetMapping("/compare_bar")
    public ResponseEntity<?> compareBar(Long[] productIds) {
        List<Map<String, Object>> data = new ArrayList<>();
        if (ArrayUtils.isEmpty(productIds) || productIds.length > MAX_COMPARE_PRODUCT_COUNT) {
            return ResponseEntity.ok(data);
        }

        List<Product> products = productService.findList(productIds);
        for (Product product : products) {
            if (product != null && BooleanUtils.isTrue(product.getIsActive()) && BooleanUtils.isTrue(product.getIsMarketable())) {
                Map<String, Object> item = new HashMap<>();
                item.put("id", product.getId());
                item.put("name", product.getName());
                item.put("price", product.getPrice());
                item.put("marketPrice", product.getMarketPrice());
                item.put("thumbnail", product.getThumbnail());
                item.put("path", product.getPath());
                data.add(item);
            }
        }
        return ResponseEntity.ok(data);
    }

    /**
     * 添加对比
     */
    @GetMapping("/add_compare")
    public ResponseEntity<?> addCompare(Long productId) {
        Map<String, Object> data = new HashMap<>();
        Product product = productService.find(productId);
        if (product == null || BooleanUtils.isNotTrue(product.getIsActive()) || BooleanUtils.isNotTrue(product.getIsMarketable())) {
            return Results.UNPROCESSABLE_ENTITY;
        }

        data.put("id", product.getId());
        data.put("name", product.getName());
        data.put("price", product.getPrice());
        data.put("marketPrice", product.getMarketPrice());
        data.put("thumbnail", product.getThumbnail());
        data.put("path", product.getPath());
        return ResponseEntity.ok(data);
    }

    /**
     * 浏览记录
     */
    @GetMapping("/history")
    public ResponseEntity<?> history(Long[] productIds) {
        List<Map<String, Object>> data = new ArrayList<>();
        if (ArrayUtils.isEmpty(productIds) || productIds.length > MAX_HISTORY_PRODUCT_COUNT) {
            return ResponseEntity.ok(data);
        }

        List<Product> products = productService.findList(productIds);
        for (Product product : products) {
            if (product != null && BooleanUtils.isTrue(product.getIsActive()) && BooleanUtils.isTrue(product.getIsMarketable())) {
                Map<String, Object> item = new HashMap<>();
                item.put("id", product.getId());
                item.put("name", product.getName());
                item.put("price", product.getPrice());
                item.put("thumbnail", product.getThumbnail());
                item.put("path", product.getPath());
                data.add(item);
            }
        }
        return ResponseEntity.ok(data);
    }

    /**
     * 列表
     */
    @GetMapping("/list/{productCategoryId}")
    public String list(@PathVariable Long productCategoryId, Product.Type type, Store.Type storeType, Long brandId, Long promotionId, Long productTagId, BigDecimal startPrice, BigDecimal endPrice, Boolean isOutOfStock, Product.OrderType orderType, Integer pageNumber, Integer pageSize,
                       HttpServletRequest request, ModelMap model) {
        ProductCategory productCategory = productCategoryService.find(productCategoryId);
        if (productCategory == null) {
            throw new ResourceNotFoundException();
        }
        Brand brand = brandService.find(brandId);
        Promotion promotion = promotionService.find(promotionId);
        ProductTag productTag = productTagService.find(productTagId);
        Map<Attribute, String> attributeValueMap = new HashMap<>();
        Set<Attribute> attributes = productCategory.getAttributes();
        if (CollectionUtils.isNotEmpty(attributes)) {
            for (Attribute attribute : attributes) {
                String value = request.getParameter("attribute_" + attribute.getId());
                String attributeValue = attributeService.toAttributeValue(attribute, value);
                if (attributeValue != null) {
                    attributeValueMap.put(attribute, attributeValue);
                }
            }
        }
        if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
            BigDecimal tempPrice = startPrice;
            startPrice = endPrice;
            endPrice = tempPrice;
        }
        Pageable pageable = new Pageable(pageNumber, pageSize);
        final Boolean[] isSample = {false};
        /**判断手机
         * */
       /* isMobile(request,o->{
            isSample[0] = true;
        });*/
       if(ConvertUtils.isEmpty(orderType)){
           orderType = Product.OrderType.DATE_DESC;
       }
        model.addAttribute("orderTypes", Product.OrderType.values());
        model.addAttribute("productCategory", productCategory);
        model.addAttribute("type", type);
        model.addAttribute("storeType", storeType);
        model.addAttribute("brand", brand);
        model.addAttribute("promotion", promotion);
        model.addAttribute("productTag", productTag);
        model.addAttribute("attributeValueMap", attributeValueMap);
        model.addAttribute("startPrice", startPrice);
        model.addAttribute("endPrice", endPrice);
        model.addAttribute("endPrice", endPrice);
        model.addAttribute("isOutOfStock", isOutOfStock);
        model.addAttribute("orderType", orderType);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("page", productService.findPage(type, 3, isSample[0], storeType, null,null, productCategory, null, brand, promotion, productTag, null, attributeValueMap, startPrice, endPrice, true, true, null, true, isOutOfStock, null, null, orderType, pageable));
        return "shop/product/list";
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(HttpServletRequest request,Product.Type type, Store.Type storeType, Long storeProductCategoryId, Long brandId, Long promotionId, Long productTagId, BigDecimal startPrice, BigDecimal endPrice, Boolean isOutOfStock, Product.OrderType orderType, Integer pageNumber, Integer pageSize, ModelMap model) {
        StoreProductCategory storeProductCategory = storeProductCategoryService.find(storeProductCategoryId);
        Brand brand = brandService.find(brandId);
        Promotion promotion = promotionService.find(promotionId);
        ProductTag productTag = productTagService.find(productTagId);

        if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
            BigDecimal tempPrice = startPrice;
            startPrice = endPrice;
            endPrice = tempPrice;
        }
        final Boolean[] isSample = {false};
        /**判断手机
         * */
        /*isMobile(request,o->{
            isSample[0] = true;
        });*/
        if(ConvertUtils.isEmpty(orderType)){
            orderType = Product.OrderType.DATE_DESC;
        }
        Pageable pageable = new Pageable(pageNumber, pageSize);
        model.addAttribute("orderTypes", Product.OrderType.values());
        model.addAttribute("type", type);
        model.addAttribute("storeType", storeType);
        model.addAttribute("storeProductCategory", storeProductCategory);
        model.addAttribute("brand", brand);
        model.addAttribute("promotion", promotion);
        model.addAttribute("productTag", productTag);
        model.addAttribute("startPrice", startPrice);
        model.addAttribute("endPrice", endPrice);
        model.addAttribute("isOutOfStock", isOutOfStock);
        model.addAttribute("orderType", orderType);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("page", productService.findPage(type, 3,isSample[0], storeType, null, null,null, storeProductCategory, brand, promotion, productTag, null, null, startPrice, endPrice, true, true, null, true, isOutOfStock, null, null, orderType, pageable));
        return "shop/product/list";
    }

    /**
     * 列表
     */
    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(BaseEntity.BaseView.class)
    public ResponseEntity<?> list(Long productCategoryId, Product.Type type, Long storeProductCategoryId, Long brandId, Long promotionId, Long productTagId, BigDecimal startPrice, BigDecimal endPrice, Product.OrderType orderType, Integer pageNumber, Integer pageSize, HttpServletRequest request) {
        ProductCategory productCategory = productCategoryService.find(productCategoryId);
        StoreProductCategory storeProductCategory = storeProductCategoryService.find(storeProductCategoryId);
        Brand brand = brandService.find(brandId);
        Promotion promotion = promotionService.find(promotionId);
        ProductTag productTag = productTagService.find(productTagId);
        Map<Attribute, String> attributeValueMap = new HashMap<>();
        if (productCategory != null) {
            Set<Attribute> attributes = productCategory.getAttributes();
            if (CollectionUtils.isNotEmpty(attributes)) {
                for (Attribute attribute : attributes) {
                    String value = request.getParameter("attribute_" + attribute.getId());
                    String attributeValue = attributeService.toAttributeValue(attribute, value);
                    if (attributeValue != null) {
                        attributeValueMap.put(attribute, attributeValue);
                    }
                }
            }
        }
        /**判断手机
         * */
        final Boolean[] isSample = {false};
       /* isMobile(request,o->{
            isSample[0] = true;
        });*/
        if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
            BigDecimal tempPrice = startPrice;
            startPrice = endPrice;
            endPrice = tempPrice;
        }

        Pageable pageable = new Pageable(pageNumber, pageSize);
        return ResponseEntity.ok(productService.findPage(type, 0,isSample[0], null, null,null, productCategory, storeProductCategory, brand, promotion, productTag, null, attributeValueMap, startPrice, endPrice, true, true, null, true, null, null, null, orderType, pageable).getContent());
    }

    /**
     * 搜索
     */
    @GetMapping("/search")
    public String search(String keyword, Store.Type storeType, Long storeId, Boolean isOutOfStock, BigDecimal startPrice, BigDecimal endPrice, Product.OrderType orderType, Integer pageNumber, Integer pageSize, ModelMap model) {
        if (StringUtils.isEmpty(keyword)) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }

        if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
            BigDecimal tempPrice = startPrice;
            startPrice = endPrice;
            endPrice = tempPrice;
        }
        Store store = storeService.find(storeId);

        Pageable pageable = new Pageable(pageNumber, pageSize);
        model.addAttribute("orderTypes", Product.OrderType.values());
        model.addAttribute("productKeyword", keyword);
        model.addAttribute("storeType", storeType);
        model.addAttribute("store", store);
        model.addAttribute("isOutOfStock", isOutOfStock);
        model.addAttribute("startPrice", startPrice);
        model.addAttribute("endPrice", endPrice);
        model.addAttribute("orderType", orderType);
        model.addAttribute("searchType", "PRODUCT");
        Page<Product> page=productService.search(keyword, null, storeType, store, isOutOfStock, null, startPrice, endPrice, orderType, pageable);
        model.addAttribute("page",page);
        model.addAttribute("key_word",keyword);
        model.addAttribute("result_number",page.getContent().size());
        return "shop/product/search";
    }

    /**
     * 搜索
     */
    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(BaseEntity.BaseView.class)
    public ResponseEntity<?> search(String keyword, Long storeId, BigDecimal startPrice, BigDecimal endPrice, Product.OrderType orderType, Integer pageNumber, Integer pageSize) {
        if (StringUtils.isEmpty(keyword)) {
            return Results.NOT_FOUND;
        }

        if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
            BigDecimal tempPrice = startPrice;
            startPrice = endPrice;
            endPrice = tempPrice;
        }
        Store store = storeService.find(storeId);
        Pageable pageable = new Pageable(pageNumber, pageSize);
        return ResponseEntity.ok(productService.search(keyword, null, null, store, null, null, startPrice, endPrice, Product.OrderType.DATE_DESC, pageable).getContent());
    }

    /**
     * 对比
     */
    @GetMapping("/compare")
    public String compare(Long[] productIds, ModelMap model) {
        if (ArrayUtils.isEmpty(productIds) || productIds.length > MAX_COMPARE_PRODUCT_COUNT) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }

        List<Product> products = productService.findList(productIds);
        CollectionUtils.filter(products, new Predicate() {
            @Override
            public boolean evaluate(Object obj) {
                Product product = (Product) obj;
                return BooleanUtils.isTrue(product.getIsActive()) && BooleanUtils.isTrue(product.getIsMarketable());
            }
        });
        if (CollectionUtils.isEmpty(products)) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }

        model.addAttribute("products", products);
        return "shop/product/compare";
    }

    /**
     * 点击数
     */
    @GetMapping("/hits/{productId}")
    public @ResponseBody
    Long hits(@PathVariable Long productId) {
        if (productId == null) {
            return 0L;
        }

        return productService.viewHits(productId);
    }


    /**
     * 团购列表
     */
    @GetMapping("/pro_purch/list")
    public String proPurchlist(Product.Type type, Store.Type storeType, Long storeProductCategoryId, Long brandId, Long promotionId, Long productTagId, BigDecimal startPrice, BigDecimal endPrice, Boolean isOutOfStock, Product.OrderType orderType, Integer pageNumber, Integer pageSize, ModelMap model) {
        StoreProductCategory storeProductCategory = storeProductCategoryService.find(storeProductCategoryId);
        Brand brand = brandService.find(brandId);
        Promotion promotion = promotionService.find(promotionId);
        ProductTag productTag = productTagService.find(productTagId);

        if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
            BigDecimal tempPrice = startPrice;
            startPrice = endPrice;
            endPrice = tempPrice;
        }
        if(ConvertUtils.isEmpty(orderType)){
            orderType = Product.OrderType.DATE_DESC;
        }
        Pageable pageable = new Pageable(pageNumber, pageSize);
        model.addAttribute("orderTypes", Product.OrderType.values());
        model.addAttribute("type", type);
        model.addAttribute("storeType", storeType);
        model.addAttribute("storeProductCategory", storeProductCategory);
        model.addAttribute("brand", brand);
        model.addAttribute("promotion", promotion);
        model.addAttribute("productTag", productTag);
        model.addAttribute("startPrice", startPrice);
        model.addAttribute("endPrice", endPrice);
        model.addAttribute("isOutOfStock", isOutOfStock);
        model.addAttribute("orderType", orderType);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        pageable.getFilters().add(new Filter("isAudit", Filter.Operator.EQ, Product.ProApplyStatus.APPROVED));
        model.addAttribute("page", productService.findPage(type, 2,false, null, null, null,null, storeProductCategory, brand, promotion, productTag, null, null, startPrice, endPrice, true, true, null, true, isOutOfStock, null, null, orderType, pageable));
        return "shop/product/purch/list";
    }

    /**
     * 团购列表
     */
    @GetMapping(path = "/pro_purch/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(BaseEntity.BaseView.class)
    public ResponseEntity<?> proPurchlist(Long productCategoryId, Product.Type type, Long storeProductCategoryId, Long brandId, Long promotionId, Long productTagId, BigDecimal startPrice, BigDecimal endPrice, Product.OrderType orderType, Integer pageNumber, Integer pageSize, HttpServletRequest request) {
        ProductCategory productCategory = productCategoryService.find(productCategoryId);
        StoreProductCategory storeProductCategory = storeProductCategoryService.find(storeProductCategoryId);
        Brand brand = brandService.find(brandId);
        Promotion promotion = promotionService.find(promotionId);
        ProductTag productTag = productTagService.find(productTagId);
        Map<Attribute, String> attributeValueMap = new HashMap<>();
        if (productCategory != null) {
            Set<Attribute> attributes = productCategory.getAttributes();
            if (CollectionUtils.isNotEmpty(attributes)) {
                for (Attribute attribute : attributes) {
                    String value = request.getParameter("attribute_" + attribute.getId());
                    String attributeValue = attributeService.toAttributeValue(attribute, value);
                    if (attributeValue != null) {
                        attributeValueMap.put(attribute, attributeValue);
                    }
                }
            }
        }
        if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
            BigDecimal tempPrice = startPrice;
            startPrice = endPrice;
            endPrice = tempPrice;
        }
        if(ConvertUtils.isEmpty(orderType)){
            orderType = Product.OrderType.DATE_DESC;
        }
        Pageable pageable = new Pageable(pageNumber, pageSize);
        pageable.getFilters().add(new Filter("isAudit", Filter.Operator.EQ, Product.ProApplyStatus.APPROVED));
        return ResponseEntity.ok(productService.findPage(type, 2,false,null, null,null, productCategory, storeProductCategory, brand, promotion, productTag, null, attributeValueMap, startPrice, endPrice, true, true, null, true, null, null, null, orderType, pageable).getContent());
    }


    /**
     * 团购列表
     */
    @GetMapping("/group_purch/list")
    public String groupPurchlist(Product.Type type, Store.Type storeType, Long storeProductCategoryId, Long brandId, Long promotionId, Long productTagId, BigDecimal startPrice, BigDecimal endPrice, Boolean isOutOfStock, Product.OrderType orderType, Integer pageNumber, Integer pageSize, ModelMap model) {
        StoreProductCategory storeProductCategory = storeProductCategoryService.find(storeProductCategoryId);
        Brand brand = brandService.find(brandId);
        Promotion promotion = promotionService.find(promotionId);
        ProductTag productTag = productTagService.find(productTagId);
        if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
            BigDecimal tempPrice = startPrice;
            startPrice = endPrice;
            endPrice = tempPrice;
        }
        if(ConvertUtils.isEmpty(orderType)){
            orderType = Product.OrderType.DATE_DESC;
        }
        Pageable pageable = new Pageable(pageNumber, pageSize);
        model.addAttribute("orderTypes", Product.OrderType.values());
        model.addAttribute("type", type);
        model.addAttribute("storeType", storeType);
        model.addAttribute("storeProductCategory", storeProductCategory);
        model.addAttribute("brand", brand);
        model.addAttribute("promotion", promotion);
        model.addAttribute("productTag", productTag);
        model.addAttribute("startPrice", startPrice);
        model.addAttribute("endPrice", endPrice);
        model.addAttribute("isOutOfStock", isOutOfStock);
        model.addAttribute("orderType", orderType);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        pageable.getFilters().add(new Filter("isGroup", Filter.Operator.EQ, true));
        model.addAttribute("page", productService.findPage(type, 0,false, storeType, null, null,null, storeProductCategory, brand, promotion, productTag, null, null, startPrice, endPrice, true, true, null, true, isOutOfStock, null, null, orderType, pageable));
        return "shop/product/group_purch/list";
    }

    /**
     * 团购列表
     */
    @GetMapping(path = "/group_purch/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(BaseEntity.BaseView.class)
    public ResponseEntity<?> groupPurchlist(Long productCategoryId, Product.Type type, Long storeProductCategoryId, Long brandId, Long promotionId, Long productTagId, BigDecimal startPrice, BigDecimal endPrice, Product.OrderType orderType, Integer pageNumber, Integer pageSize, HttpServletRequest request) {
        ProductCategory productCategory = productCategoryService.find(productCategoryId);
        StoreProductCategory storeProductCategory = storeProductCategoryService.find(storeProductCategoryId);
        Brand brand = brandService.find(brandId);
        Promotion promotion = promotionService.find(promotionId);
        ProductTag productTag = productTagService.find(productTagId);
        Map<Attribute, String> attributeValueMap = new HashMap<>();
        if (productCategory != null) {
            Set<Attribute> attributes = productCategory.getAttributes();
            if (CollectionUtils.isNotEmpty(attributes)) {
                for (Attribute attribute : attributes) {
                    String value = request.getParameter("attribute_" + attribute.getId());
                    String attributeValue = attributeService.toAttributeValue(attribute, value);
                    if (attributeValue != null) {
                        attributeValueMap.put(attribute, attributeValue);
                    }
                }
            }
        }
        if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
            BigDecimal tempPrice = startPrice;
            startPrice = endPrice;
            endPrice = tempPrice;
        }
        if(ConvertUtils.isEmpty(orderType)){
            orderType = Product.OrderType.DATE_DESC;
        }
        Pageable pageable = new Pageable(pageNumber, pageSize);
        pageable.getFilters().add(new Filter("isGroup", Filter.Operator.EQ, true));
        return ResponseEntity.ok(productService.findPage(type, 0,false,null, null,null, productCategory, storeProductCategory, brand, promotion, productTag, null, attributeValueMap, startPrice, endPrice, true, true, null, true, null, null, null, orderType, pageable).getContent());
    }
}