/*
 *
 * 
 *
 * 
 */
package net.mall.controller.business;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.mall.Pageable;
import net.mall.Results;
import net.mall.entity.Product;
import net.mall.entity.Store;
import net.mall.entity.StoreProductCategory;
import net.mall.exception.UnauthorizedException;
import net.mall.security.CurrentStore;
import net.mall.service.StoreProductCategoryService;

/**
 * Controller - 店铺商品分类
 * 
 * @author huanghy
 * @version 6.1
 */
@Controller("businessStoreProductCategoryController")
@RequestMapping("/business/store_product_category")
public class StoreProductCategoryController extends BaseController {

	@Inject
	private StoreProductCategoryService storeProductCategoryService;

	/**
	 * 添加属性
	 */
	@ModelAttribute
	public void populateModel(Long storeProductCategoryId, @CurrentStore Store currentStore, ModelMap model) {
		StoreProductCategory storeProductCategory = storeProductCategoryService.find(storeProductCategoryId);
		if (storeProductCategory != null && !currentStore.equals(storeProductCategory.getStore())) {
			throw new UnauthorizedException();
		}
		model.addAttribute("storeProductCategory", storeProductCategory);
	}

	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(@CurrentStore Store currentStore, ModelMap model) {
		model.addAttribute("storeProductCategoryTree", storeProductCategoryService.findTree(currentStore));
		return "business/store_product_category/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public ResponseEntity<?> save(@ModelAttribute(name = "storeProductCategoryForm") StoreProductCategory storeProductCategoryForm, Long parentId, @CurrentStore Store currentStore) {
		StoreProductCategory pStoreProductCategory = storeProductCategoryService.find(parentId);
		if (parentId == null && pStoreProductCategory != null && pStoreProductCategory.getParent() != null) {
			if (!currentStore.equals(pStoreProductCategory.getStore())) {
				return Results.UNPROCESSABLE_ENTITY;
			}
		}
		storeProductCategoryForm.setParent(pStoreProductCategory);
		if (!isValid(storeProductCategoryForm)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		storeProductCategoryForm.setTreePath(null);
		storeProductCategoryForm.setGrade(null);
		storeProductCategoryForm.setChildren(null);
		storeProductCategoryForm.setProducts(null);
		storeProductCategoryForm.setStore(currentStore);
		storeProductCategoryService.save(storeProductCategoryForm);

		return Results.OK;
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(@ModelAttribute(binding = false) StoreProductCategory storeProductCategory, @CurrentStore Store currentStore, ModelMap model) {
		if (storeProductCategory == null) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}

		model.addAttribute("storeProductCategoryTree", storeProductCategoryService.findTree(currentStore));
		model.addAttribute("storeProductCategory", storeProductCategory);
		model.addAttribute("children", storeProductCategoryService.findChildren(storeProductCategory, currentStore, true, null));
		return "business/store_product_category/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public ResponseEntity<?> update(@ModelAttribute(name = "storeProductCategoryForm") StoreProductCategory storeProductCategoryForm, @ModelAttribute(binding = false) StoreProductCategory storeProductCategory, Long parentId, @CurrentStore Store currentStore) {
		StoreProductCategory pParen = storeProductCategoryService.find(parentId);
		if (!isValid(storeProductCategoryForm)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (storeProductCategory == null) {
			return Results.UNPROCESSABLE_ENTITY;
		}

		if (storeProductCategoryForm.getParent() != null) {
			StoreProductCategory parent = storeProductCategoryForm.getParent();
			if (parent.equals(storeProductCategoryForm)) {
				return Results.UNPROCESSABLE_ENTITY;
			}
			List<StoreProductCategory> children = storeProductCategoryService.findChildren(storeProductCategoryForm, currentStore, true, null);
			if (children != null && children.contains(parent)) {
				return Results.UNPROCESSABLE_ENTITY;
			}
			if (!currentStore.equals(pParen.getStore())) {
				return Results.UNPROCESSABLE_ENTITY;
			}
		}
		storeProductCategoryForm.setParent(pParen);

		BeanUtils.copyProperties(storeProductCategoryForm, storeProductCategory, "id", "grade", "treePath", "store", "children", "product");
		storeProductCategoryService.update(storeProductCategory);

		return Results.OK;
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, @CurrentStore Store currentStore, ModelMap model) {
		model.addAttribute("storeProductCategoryTree", storeProductCategoryService.findTree(currentStore));
		return "business/store_product_category/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public ResponseEntity<?> delete(@ModelAttribute(binding = false) StoreProductCategory storeProductCategory) {
		if (storeProductCategory == null) {
			return Results.UNPROCESSABLE_ENTITY;
		}

		Set<StoreProductCategory> children = storeProductCategory.getChildren();
		if (children != null && !children.isEmpty()) {
			return Results.unprocessableEntity("business.storeProductCategory.deleteExistChildrenNotAllowed");
		}
		Set<Product> product = storeProductCategory.getProducts();
		if (product != null && !product.isEmpty()) {
			return Results.unprocessableEntity("business.storeProductCategory.deleteExistProductNotAllowed");
		}
		storeProductCategoryService.delete(storeProductCategory);
		return Results.OK;
	}

}