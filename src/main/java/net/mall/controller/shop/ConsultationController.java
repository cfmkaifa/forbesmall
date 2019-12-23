/*
 *
 * 
 *
 * 
 */
package net.mall.controller.shop;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonView;

import net.mall.Pageable;
import net.mall.Results;
import net.mall.Setting;
import net.mall.entity.BaseEntity;
import net.mall.entity.Consultation;
import net.mall.entity.Member;
import net.mall.entity.Product;
import net.mall.exception.ResourceNotFoundException;
import net.mall.security.CurrentUser;
import net.mall.service.ConsultationService;
import net.mall.service.ProductService;
import net.mall.util.SystemUtils;

/**
 * Controller - 咨询
 * 
 * @author huanghy
 * @version 6.1
 */
@Controller("shopConsultationController")
@RequestMapping("/consultation")
public class ConsultationController extends BaseController {

	/**
	 * 每页记录数
	 */
	private static final int PAGE_SIZE = 10;

	@Inject
	private ConsultationService consultationService;
	@Inject
	private ProductService productService;

	/**
	 * 列表
	 */
	@GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(BaseEntity.BaseView.class)
	public ResponseEntity<?> list(Long productId, Integer pageNumber) {
		Product product = productService.find(productId);
		if (product == null || BooleanUtils.isNotTrue(product.getIsActive()) || BooleanUtils.isNotTrue(product.getIsMarketable())) {
			return Results.UNPROCESSABLE_ENTITY;
		}

		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		return ResponseEntity.ok(consultationService.findPage(null, product, null, true, pageable).getContent());
	}

	/**
	 * 发表
	 */
	@GetMapping("/add/{productId}")
	public String add(@PathVariable Long productId, ModelMap model) {
		Setting setting = SystemUtils.getSetting();
		if (!setting.getIsConsultationEnabled()) {
			throw new ResourceNotFoundException();
		}
		Product product = productService.find(productId);
		if (product == null || BooleanUtils.isNotTrue(product.getIsActive()) || BooleanUtils.isNotTrue(product.getIsMarketable())) {
			throw new ResourceNotFoundException();
		}

		model.addAttribute("product", product);
		return "shop/consultation/add";
	}

	/**
	 * 详情
	 */
	@GetMapping("/detail/{productId}")
	public String detail(@PathVariable Long productId, Integer pageNumber, ModelMap model) {
		Setting setting = SystemUtils.getSetting();
		if (!setting.getIsConsultationEnabled()) {
			throw new ResourceNotFoundException();
		}
		Product product = productService.find(productId);
		if (product == null || BooleanUtils.isNotTrue(product.getIsActive()) || BooleanUtils.isNotTrue(product.getIsMarketable())) {
			throw new ResourceNotFoundException();
		}

		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("product", product);
		model.addAttribute("page", consultationService.findPage(null, product, product.getStore(), true, pageable));
		return "shop/consultation/detail";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public ResponseEntity<?> save(Long productId, String content, @CurrentUser Member currentUser, HttpServletRequest request) {
		Setting setting = SystemUtils.getSetting();
		if (!setting.getIsConsultationEnabled()) {
			return Results.unprocessableEntity("shop.consultation.disabled");
		}
		Product product = productService.find(productId);
		if (product == null || BooleanUtils.isNotTrue(product.getIsActive()) || BooleanUtils.isNotTrue(product.getIsMarketable())) {
			return Results.NOT_FOUND;
		}
		if (!isValid(Consultation.class, "content", content)) {
			return Results.UNPROCESSABLE_ENTITY;
		}

		Consultation consultation = new Consultation();
		consultation.setContent(content);
		consultation.setIp(request.getRemoteAddr());
		consultation.setMember(currentUser);
		consultation.setProduct(product);
		consultation.setReplyConsultations(null);
		consultation.setForConsultation(null);
		consultation.setStore(product.getStore());
		consultation.setIsShow(!setting.getIsConsultationCheck());
		consultationService.save(consultation);
		return Results.ok(setting.getIsConsultationCheck() ? "shop.consultation.check" : "shop.consultation.success");
	}

}