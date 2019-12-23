/*
 *
 * 
 *
 * 
 */
package net.mall.controller.shop;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import net.mall.entity.Promotion;
import net.mall.exception.ResourceNotFoundException;
import net.mall.service.PromotionService;

/**
 * Controller - 促销
 * 
 * @author huanghy
 * @version 6.1
 */
@Controller("shopPromotionController")
@RequestMapping("/promotion")
public class PromotionController extends BaseController {

	@Inject
	private PromotionService promotionService;

	/**
	 * 详情
	 */
	@GetMapping("/detail/{promotionId}")
	public String detail(@PathVariable Long promotionId, ModelMap model) {
		Promotion promotion = promotionService.find(promotionId);
		if (promotion == null) {
			throw new ResourceNotFoundException();
		}
		model.addAttribute("promotion", promotion);
		return "shop/promotion/detail";
	}

}