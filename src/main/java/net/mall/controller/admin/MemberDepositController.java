/*
 *
 * 
 *
 * 
 */
package net.mall.controller.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.mall.Pageable;
import net.mall.Results;
import net.mall.entity.Admin;
import net.mall.entity.Member;
import net.mall.entity.MemberDepositLog;
import net.mall.security.CurrentUser;
import net.mall.service.MemberDepositLogService;
import net.mall.service.MemberService;

/**
 * Controller - 会员预存款
 * 
 * @author huanghy
 * @version 6.1
 */
@Controller("adminMemberDepositController")
@RequestMapping("/admin/member_deposit")
public class MemberDepositController extends BaseController {

	@Inject
	private MemberDepositLogService memberDepositLogService;
	@Inject
	private MemberService memberService;

	/**
	 * 会员选择
	 */
	@GetMapping("/member_select")
	public ResponseEntity<?> memberSelect(String keyword) {
		List<Map<String, Object>> data = new ArrayList<>();
		if (StringUtils.isEmpty(keyword)) {
			return ResponseEntity.ok(data);
		}
		List<Member> members = memberService.search(keyword, null, null);
		for (Member member : members) {
			Map<String, Object> item = new HashMap<>();
			item.put("id", member.getId());
			item.put("name", member.getUsername());
			item.put("availableBalance", member.getAvailableBalance());
			data.add(item);
		}
		return ResponseEntity.ok(data);
	}

	/**
	 * 调整
	 */
	@GetMapping("/adjust")
	public String adjust() {
		return "admin/member_deposit/adjust";
	}

	/**
	 * 调整
	 */
	@PostMapping("/adjust")
	public ResponseEntity<?> adjust(Long memberId, BigDecimal amount, String memo, @CurrentUser Admin currentUser) {
		Member member = memberService.find(memberId);
		if (member == null) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (amount == null || amount.compareTo(BigDecimal.ZERO) == 0) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (member.getBalance() == null || member.getAvailableBalance().add(amount).compareTo(BigDecimal.ZERO) < 0) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		memberService.addBalance(member, amount, MemberDepositLog.Type.ADJUSTMENT, memo);
		return Results.OK;
	}

	/**
	 * 记录
	 */
	@GetMapping("/log")
	public String log(Long memberId, Pageable pageable, ModelMap model) {
		Member member = memberService.find(memberId);
		if (member != null) {
			model.addAttribute("member", member);
			model.addAttribute("page", memberDepositLogService.findPage(member, pageable));
		} else {
			model.addAttribute("page", memberDepositLogService.findPage(pageable));
		}
		return "admin/member_deposit/log";
	}

}