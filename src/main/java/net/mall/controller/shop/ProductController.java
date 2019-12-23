/*
 *
 * 
 *
 * 
 */
package net.mall.controller.shop;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;

import net.mall.Pageable;
import net.mall.Results;
import net.mall.entity.Attribute;
import net.mall.entity.BaseEntity;
import net.mall.entity.Brand;
import net.mall.entity.Product;
import net.mall.entity.ProductCategory;
import net.mall.entity.ProductTag;
import net.mall.entity.Promotion;
import net.mall.entity.Store;
import net.mall.entity.StoreProductCategory;
import net.mall.exception.ResourceNotFoundException;
import net.mall.service.AttributeService;
import net.mall.service.BrandService;
import net.mall.service.ProductCategoryService;
import net.mall.service.ProductService;
import net.mall.service.ProductTagService;
import net.mall.service.PromotionService;
import net.mall.service.StoreProductCategoryService;
import net.mall.service.StoreService;

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

	/**
	 * 详情
	 */
	@GetMapping("/detail/{productId}")
	public String detail(@PathVariable Long productId, ModelMap model) {
		Product product = productService.find(productId);
		if (product == null || BooleanUtils.isNotTrue(product.getIsActive()) || BooleanUtils.isNotTrue(product.getIsMarketable())) {
			throw new ResourceNotFoundException();
		}
		model.addAttribute("product", product);
		return "shop/product/detail";
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
		model.addAttribute("page", productService.findPage(type, storeType, null, productCategory, null, brand, promotion, productTag, null, attributeValueMap, startPrice, endPrice, true, true, null, true, isOutOfStock, null, null, orderType, pageable));
		return "shop/product/list";
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Product.Type type, Store.Type storeType, Long storeProductCategoryId, Long brandId, Long promotionId, Long productTagId, BigDecimal startPrice, BigDecimal endPrice, Boolean isOutOfStock, Product.OrderType orderType, Integer pageNumber, Integer pageSize, ModelMap model) {
		StoreProductCategory storeProductCategory = storeProductCategoryService.find(storeProductCategoryId);
		Brand brand = brandService.find(brandId);
		Promotion promotion = promotionService.find(promotionId);
		ProductTag productTag = productTagService.find(productTagId);

		if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
			BigDecimal tempPrice = startPrice;
			startPrice = endPrice;
			endPrice = tempPrice;
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
		model.addAttribute("page", productService.findPage(type, storeType, null, null, storeProductCategory, brand, promotion, productTag, null, null, startPrice, endPrice, true, true, null, true, isOutOfStock, null, null, orderType, pageable));
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

		if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
			BigDecimal tempPrice = startPrice;
			startPrice = endPrice;
			endPrice = tempPrice;
		}

		Pageable pageable = new Pageable(pageNumber, pageSize);
		return ResponseEntity.ok(productService.findPage(type, null, null, productCategory, storeProductCategory, brand, promotion, productTag, null, attributeValueMap, startPrice, endPrice, true, true, null, true, null, null, null, orderType, pageable).getContent());
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
		model.addAttribute("page", productService.search(keyword, null, storeType, store, isOutOfStock, null, startPrice, endPrice, orderType, pageable));
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
		return ResponseEntity.ok(productService.search(keyword, null, null, store, null, null, startPrice, endPrice, orderType, pageable).getContent());
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
	public @ResponseBody Long hits(@PathVariable Long productId) {
		if (productId == null) {
			return 0L;
		}

		return productService.viewHits(productId);
	}

}