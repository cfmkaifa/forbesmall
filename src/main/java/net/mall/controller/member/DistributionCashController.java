/*
 *
 * 
 *
 * 
 */
package net.mall.controller.member;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;

import net.mall.Pageable;
import net.mall.Results;
import net.mall.Setting;
import net.mall.entity.BaseEntity;
import net.mall.entity.DistributionCash;
import net.mall.entity.Distributor;
import net.mall.entity.Member;
import net.mall.exception.UnauthorizedException;
import net.mall.security.CurrentUser;
import net.mall.service.DistributionCashService;
import net.mall.util.SystemUtils;

/**
 * Controller - 分销提现
 * 
 * @author huanghy
 * @version 6.1
 */
@Controller("memberDistributionCashController")
@RequestMapping("/member/distribution_cash")
public class DistributionCashController extends BaseController {

	/**
	 * 每页记录数
	 */
	private static final int PAGE_SIZE = 10;

	@Inject
	private DistributionCashService distributionCashService;

	/**
	 * 添加属性
	 */
	@ModelAttribute
	public void populateModel(@CurrentUser Member currentUser, ModelMap model) {
		if (!currentUser.getIsDistributor()) {
			throw new UnauthorizedException();
		}
		model.addAttribute("distributor", currentUser.getDistributor());
	}

	/**
	 * 检查余额
	 */
	@GetMapping("/check_balance")
	public @ResponseBody boolean checkBalance(BigDecimal amount, @CurrentUser Member currentUser) {
		return amount.compareTo(BigDecimal.ZERO) > 0 && currentUser.getAvailableBalance().compareTo(amount) >= 0;
	}

	/**
	 * 申请提现
	 */
	@GetMapping("/application")
	public String cash() {
		return "member/distribution_cash/application";
	}

	/**
	 * 申请提现
	 */
	@PostMapping("/save")
	public ResponseEntity<?> applyCash(DistributionCash distributionCash, @CurrentUser Member currentUser) {
		if (!isValid(distributionCash)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		Setting setting = SystemUtils.getSetting();
		if (currentUser.getAvailableBalance().compareTo(distributionCash.getAmount()) < 0 || distributionCash.getAmount().compareTo(setting.getMemberMinimumCashAmount()) < 0) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		distributionCashService.applyCash(distributionCash, currentUser.getDistributor());
		return Results.OK;
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, @ModelAttribute(binding = false) Distributor distributor, ModelMap model) {
		model.addAttribute("page", distributionCashService.findPage(null, null, null, null, distributor, pageable));
		return "member/distribution_cash/list";
	}

	/**
	 * 列表
	 */
	@GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(BaseEntity.BaseView.class)
	public ResponseEntity<?> list(Integer pageNumber, @ModelAttribute(binding = false) Distributor distributor) {
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		return ResponseEntity.ok(distributionCashService.findPage(null, null, null, null, distributor, pageable).getContent());
	}

}