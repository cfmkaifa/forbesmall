/*
 *
 * 
 *
 * 
 */
package net.mall.controller.admin;

import java.math.BigDecimal;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.mall.Pageable;
import net.mall.Results;
import net.mall.audit.Audit;
import net.mall.entity.BaseEntity;
import net.mall.entity.Member;
import net.mall.entity.MemberAttribute;
import net.mall.service.MemberAttributeService;
import net.mall.service.MemberRankService;
import net.mall.service.MemberService;
import net.mall.service.UserService;

/**
 * Controller - 会员
 * 
 * @author huanghy
 * @version 6.1
 */
@Controller("adminMemberController")
@RequestMapping("/admin/member")
public class MemberController extends BaseController {

	@Inject
	private MemberService memberService;
	@Inject
	private UserService userService;
	@Inject
	private MemberRankService memberRankService;
	@Inject
	private MemberAttributeService memberAttributeService;

	/**
	 * 检查用户名是否存在
	 */
	@GetMapping("/check_username")
	public @ResponseBody boolean checkUsername(String username) {
		return StringUtils.isNotEmpty(username) && !memberService.usernameExists(username);
	}

	/**
	 * 检查E-mail是否唯一
	 */
	@GetMapping("/check_email")
	public @ResponseBody boolean checkEmail(Long id, String email) {
		return StringUtils.isNotEmpty(email) && memberService.emailUnique(id, email);
	}

	/**
	 * 检查手机是否唯一
	 */
	@GetMapping("/check_mobile")
	public @ResponseBody boolean checkMobile(Long id, String mobile) {
		return StringUtils.isNotEmpty(mobile) && memberService.mobileUnique(id, mobile);
	}

	/**
	 * 查看
	 */
	@GetMapping("/view")
	public String view(Long id, ModelMap model) {
		Member member = memberService.find(id);
		model.addAttribute("genders", Member.Gender.values());
		model.addAttribute("memberAttributes", memberAttributeService.findList(true, true));
		model.addAttribute("member", member);
		return "admin/member/view";
	}

	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(ModelMap model) {
		model.addAttribute("genders", Member.Gender.values());
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("memberAttributes", memberAttributeService.findList(true, true));
		return "admin/member/add";
	}

	/**
	 * 保存
	 */
	@Audit(action = "auditLog.action.admin.member.save")
	@PostMapping("/save")
	public ResponseEntity<?> save(Member member, Long memberRankId, HttpServletRequest request) {
		member.setMemberRank(memberRankService.find(memberRankId));
		if (!isValid(member, BaseEntity.Save.class)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (memberService.usernameExists(member.getUsername())) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (memberService.emailExists(member.getEmail())) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (memberService.mobileExists(member.getMobile())) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		member.removeAttributeValue();
		for (MemberAttribute memberAttribute : memberAttributeService.findList(true, true)) {
			String[] values = request.getParameterValues("memberAttribute_" + memberAttribute.getId());
			if (!memberAttributeService.isValid(memberAttribute, values)) {
				return Results.UNPROCESSABLE_ENTITY;
			}
			Object memberAttributeValue = memberAttributeService.toMemberAttributeValue(memberAttribute, values);
			member.setAttributeValue(memberAttribute, memberAttributeValue);
		}
		member.setPoint(0L);
		member.setBalance(BigDecimal.ZERO);
		member.setFrozenAmount(BigDecimal.ZERO);
		member.setAmount(BigDecimal.ZERO);
		member.setIsLocked(false);
		member.setLockDate(null);
		member.setLastLoginIp(null);
		member.setLastLoginDate(null);
		member.setDistributor(null);
		member.setSafeKey(null);
		member.setCart(null);
		member.setOrders(null);
		member.setSocialUsers(null);
		member.setPaymentTransactions(null);
		member.setMemberDepositLogs(null);
		member.setCouponCodes(null);
		member.setReceivers(null);
		member.setReviews(null);
		member.setConsultations(null);
		member.setProductFavorites(null);
		member.setProductNotifies(null);
		member.setPointLogs(null);
		member.setStoreFavorites(null);
		member.setFromMessages(null);
		member.setToMessages(null);
		member.setAuditLogs(null);
		member.setAftersales(null);
		member.setUser1MessageGroups(null);
		member.setUser2MessageGroups(null);
		memberService.save(member);
		return Results.OK;
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		Member member = memberService.find(id);
		model.addAttribute("genders", Member.Gender.values());
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("memberAttributes", memberAttributeService.findList(true, true));
		model.addAttribute("member", member);
		return "admin/member/edit";
	}

	/**
	 * 更新
	 */
	@Audit(action = "auditLog.action.admin.member.update")
	@PostMapping("/update")
	public ResponseEntity<?> update(Member member, Long id, Long memberRankId, Boolean unlock, HttpServletRequest request) {
		member.setMemberRank(memberRankService.find(memberRankId));
		if (!isValid(member)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (!memberService.emailUnique(id, member.getEmail())) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (!memberService.mobileUnique(id, member.getMobile())) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		member.removeAttributeValue();
		for (MemberAttribute memberAttribute : memberAttributeService.findList(true, true)) {
			String[] values = request.getParameterValues("memberAttribute_" + memberAttribute.getId());
			if (!memberAttributeService.isValid(memberAttribute, values)) {
				return Results.UNPROCESSABLE_ENTITY;
			}
			Object memberAttributeValue = memberAttributeService.toMemberAttributeValue(memberAttribute, values);
			member.setAttributeValue(memberAttribute, memberAttributeValue);
		}
		Member pMember = memberService.find(id);
		if (pMember == null) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (BooleanUtils.isTrue(pMember.getIsLocked()) && BooleanUtils.isTrue(unlock)) {
			userService.unlock(member);
			memberService.update(member, "username", "encodedPassword", "point", "balance", "frozenAmount", "amount", "lastLoginIp", "lastLoginDate", "loginPluginId", "safeKey", "distributor", "cart", "orders", "socialUsers", "paymentTransactions", "memberDepositLogs", "couponCodes", "receivers",
					"reviews", "consultations", "productFavorites", "productNotifies", "pointLogs");
		} else {
			memberService.update(member, "username", "encodedPassword", "point", "balance", "frozenAmount", "amount", "isLocked", "lockDate", "lastLoginIp", "lastLoginDate", "loginPluginId", "safeKey", "distributor", "cart", "orders", "socialUsers", "paymentTransactions", "memberDepositLogs",
					"couponCodes", "receivers", "reviews", "consultations", "productFavorites", "productNotifies", "pointLogs");
		}
		return Results.OK;
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("memberAttributes", memberAttributeService.findAll());
		model.addAttribute("page", memberService.findPage(pageable));
		return "admin/member/list";
	}

	/**
	 * 删除
	 */
	@Audit(action = "auditLog.action.admin.member.delete")
	@PostMapping("/delete")
	public ResponseEntity<?> delete(Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				Member member = memberService.find(id);
				if (member != null && member.getBalance().compareTo(BigDecimal.ZERO) > 0) {
					return Results.unprocessableEntity("admin.member.deleteExistDepositNotAllowed", member.getUsername());
				}
			}
			memberService.delete(ids);
		}
		return Results.OK;
	}

}