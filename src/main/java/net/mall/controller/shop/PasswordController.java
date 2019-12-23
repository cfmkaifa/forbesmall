/*
 *
 * 
 *
 * 
 */
package net.mall.controller.shop;

import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.mall.Results;
import net.mall.Setting;
import net.mall.entity.BaseEntity;
import net.mall.entity.Business;
import net.mall.entity.Member;
import net.mall.entity.SafeKey;
import net.mall.entity.User;
import net.mall.entity.User.Type;
import net.mall.service.BusinessService;
import net.mall.service.MailService;
import net.mall.service.MemberService;
import net.mall.util.SpringUtils;
import net.mall.util.SystemUtils;

/**
 * Controller - 密码
 * 
 * @author huanghy
 * @version 6.1
 */
@Controller("shopPasswordController")
@RequestMapping("/password")
public class PasswordController extends BaseController {

	@Inject
	private BusinessService businessService;
	@Inject
	private MemberService memberService;
	@Inject
	private MailService mailService;

	/**
	 * 忘记密码
	 */
	@GetMapping("/forgot")
	public String forgot(Type type, Model model) {
		model.addAttribute("type", type);
		return "shop/password/forgot";
	}

	/**
	 * 会员忘记密码
	 */
	@PostMapping(path = "/forgot", params = "type=MEMBER")
	public ResponseEntity<?> forgotMember(String username, String email) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(email)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		Member member = memberService.findByUsername(username);
		if (member == null) {
			return Results.unprocessableEntity("shop.password.memberNotExist");
		}
		if (!StringUtils.equalsIgnoreCase(member.getEmail(), email)) {
			return Results.unprocessableEntity("shop.password.invalidEmail");
		}

		Setting setting = SystemUtils.getSetting();
		SafeKey safeKey = new SafeKey();
		safeKey.setValue(DigestUtils.md5Hex(UUID.randomUUID() + RandomStringUtils.randomAlphabetic(30)));
		safeKey.setExpire(setting.getSafeKeyExpiryTime() != 0 ? DateUtils.addMinutes(new Date(), setting.getSafeKeyExpiryTime()) : null);
		member.setSafeKey(safeKey);
		memberService.update(member);
		mailService.sendForgotPasswordMail(member);
		return Results.ok("shop.password.mailSuccess");
	}

	/**
	 * 商家忘记密码
	 */
	@PostMapping(path = "/forgot", params = "type=BUSINESS")
	public ResponseEntity<?> forgotBusiness(String username, String email) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(email)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		Business business = businessService.findByUsername(username);
		if (business == null) {
			return Results.unprocessableEntity("shop.password.businessNotExist");
		}
		if (!StringUtils.equalsIgnoreCase(business.getEmail(), email)) {
			return Results.unprocessableEntity("shop.password.invalidEmail");
		}

		Setting setting = SystemUtils.getSetting();
		SafeKey safeKey = new SafeKey();
		safeKey.setValue(DigestUtils.md5Hex(UUID.randomUUID() + RandomStringUtils.randomAlphabetic(30)));
		safeKey.setExpire(setting.getSafeKeyExpiryTime() != 0 ? DateUtils.addMinutes(new Date(), setting.getSafeKeyExpiryTime()) : null);
		business.setSafeKey(safeKey);
		businessService.update(business);
		mailService.sendForgotPasswordMail(business);
		return Results.ok("shop.password.mailSuccess");
	}

	/**
	 * 会员重置密码
	 */
	@GetMapping(path = "/reset", params = "type=MEMBER")
	public String resetMember(String username, String key, Model model) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(key)) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}
		Member member = memberService.findByUsername(username);
		if (member == null) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}
		SafeKey safeKey = member.getSafeKey();
		if (safeKey == null || safeKey.getValue() == null || !StringUtils.equals(safeKey.getValue(), key)) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}
		if (safeKey.hasExpired()) {
			model.addAttribute("errorMessage", SpringUtils.getMessage("shop.password.hasExpired"));
			return UNPROCESSABLE_ENTITY_VIEW;
		}

		model.addAttribute("user", member);
		model.addAttribute("type", User.Type.MEMBER);
		model.addAttribute("key", key);
		return "shop/password/reset";
	}

	/**
	 * 商家重置密码
	 */
	@GetMapping(path = "/reset", params = "type=BUSINESS")
	public String resetBusiness(String username, String key, Model model) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(key)) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}
		Business business = businessService.findByUsername(username);
		if (business == null) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}
		SafeKey safeKey = business.getSafeKey();
		if (safeKey == null || safeKey.getValue() == null || !StringUtils.equals(safeKey.getValue(), key)) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}
		if (safeKey.hasExpired()) {
			model.addAttribute("errorMessage", SpringUtils.getMessage("shop.password.hasExpired"));
			return UNPROCESSABLE_ENTITY_VIEW;
		}

		model.addAttribute("user", business);
		model.addAttribute("type", User.Type.BUSINESS);
		model.addAttribute("key", key);
		return "shop/password/reset";
	}

	/**
	 * 会员重置密码
	 */
	@PostMapping(path = "/reset", params = "type=MEMBER")
	public ResponseEntity<?> resetMember(String username, String newPassword, String key) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(key)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		Member member = memberService.findByUsername(username);
		if (member == null) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (!isValid(Member.class, "password", newPassword, BaseEntity.Save.class)) {
			return Results.unprocessableEntity("shop.password.invalidPassword");
		}
		SafeKey safeKey = member.getSafeKey();
		if (safeKey == null || safeKey.getValue() == null || !StringUtils.equals(safeKey.getValue(), key)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (safeKey.hasExpired()) {
			return Results.unprocessableEntity("shop.password.hasExpired");
		}
		member.setPassword(newPassword);
		member.setSafeKey(null);
		memberService.update(member);
		return Results.ok("shop.password.resetSuccess");
	}

	/**
	 * 商家重置密码
	 */
	@PostMapping(path = "/reset", params = "type=BUSINESS")
	public ResponseEntity<?> resetBusiness(String username, String newPassword, String key) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(key)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		Business business = businessService.findByUsername(username);
		if (business == null) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (!isValid(Business.class, "password", newPassword, BaseEntity.Save.class)) {
			return Results.unprocessableEntity("shop.password.invalidPassword");
		}
		SafeKey safeKey = business.getSafeKey();
		if (safeKey == null || safeKey.getValue() == null || !StringUtils.equals(safeKey.getValue(), key)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (safeKey.hasExpired()) {
			return Results.unprocessableEntity("shop.password.hasExpired");
		}
		business.setPassword(newPassword);
		business.setSafeKey(null);
		businessService.update(business);
		return Results.ok("shop.password.resetSuccess");
	}

}