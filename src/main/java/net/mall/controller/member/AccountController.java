package net.mall.controller.member;

import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
import net.mall.Filter;
import net.mall.Results;
import net.mall.Setting;
import net.mall.entity.*;
import net.mall.exception.UnauthorizedException;
import net.mall.security.CurrentUser;
import net.mall.service.MemberAttributeService;
import net.mall.service.MemberService;
import net.mall.service.UserService;
import net.mall.util.ConvertUtils;
import net.mall.util.SystemUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller("memberAccountController")
@RequestMapping("/member/account")
public class AccountController extends BaseController {

    @Inject
    private UserService userService;
    @Inject
    private MemberService memberService;
    @Inject
    private MemberAttributeService memberAttributeService;


    /**
     * 首页
     */
    @GetMapping(value = "/list")
    public String accountList(@CurrentUser Member currentUser, ModelMap model) {
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(new Filter("parentId",Filter.Operator.EQ,currentUser.getId()));
        List<Member>  members =    memberService.findList(null,filters,null);
        model.put("members",members);
        return "member/account/list";
    }


    /**
     * 添加属性
     */
    @ModelAttribute
    public void populateModel(Long accountId, ModelMap model) {
        Member member = memberService.find(accountId);
        model.addAttribute("member", member);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public ResponseEntity<?> delete(@ModelAttribute(binding = false) Member member) {
        if (member == null) {
            return Results.NOT_FOUND;
        }
        memberService.delete(member);
        return Results.OK;
    }


    /***
     * 增加账号
     * @param username
     * @param password
     * @param member
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @throws InvalidArgumentException
     */
    @PostMapping("/add_account")
    public ResponseEntity<?> submit(String username,String name,String mobile, String password, @CurrentUser Member member, HttpServletRequest request)
            throws UnsupportedEncodingException, InvalidArgumentException {
        Setting setting = SystemUtils.getSetting();
        if(ConvertUtils.isNotEmpty(member.getParentId())){
            return Results.unprocessableEntity("member.register.suAccount");
        }
        if (!ArrayUtils.contains(setting.getAllowedRegisterTypes(), Setting.RegisterType.MEMBER)) {
            return Results.unprocessableEntity("member.register.disabled");
        }
        if (!isValid(Member.class, "username", username, BaseEntity.Save.class) || !isValid(Member.class, "password", password, BaseEntity.Save.class)) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (memberService.usernameExists(username)) {
            return Results.unprocessableEntity("member.register.usernameExist");
        }
        if (memberService.mobileExists(username)) {
            return Results.unprocessableEntity("member.register.mobileExist");
        }
        Member tmember = new Member();
        for (MemberAttribute memberAttribute : memberAttributeService.findList(true, true)) {
            Object  memberAttributeValue =  member.getAttributeValue(memberAttribute);
            if(ConvertUtils.isNotEmpty(memberAttributeValue)){
                tmember.setAttributeValue(memberAttribute, memberAttributeValue);
            }
        }
        tmember.setParentId(member.getId());
        tmember.setName(name);
        tmember.setMobile(mobile);
        tmember.setPhone(mobile);
        tmember.setUsername(username);
        tmember.setPassword(password);
        tmember.setPoint(member.getPoint());
        tmember.setBalance(BigDecimal.ZERO);
        tmember.setFrozenAmount(BigDecimal.ZERO);
        tmember.setAmount(BigDecimal.ZERO);
        tmember.setIsEnabled(true);
        tmember.setIsLocked(false);
        tmember.setLockDate(null);
        tmember.setLastLoginIp(request.getRemoteAddr());
        tmember.setLastLoginDate(new Date());
        tmember.setSafeKey(null);
        tmember.setMemberRank(member.getMemberRank());
        tmember.setDistributor(null);
        tmember.setCart(null);
        tmember.setOrders(null);
        tmember.setPaymentTransactions(null);
        tmember.setMemberDepositLogs(null);
        tmember.setCouponCodes(null);
        tmember.setReceivers(null);
        tmember.setReviews(null);
        tmember.setConsultations(null);
        tmember.setProductFavorites(null);
        tmember.setProductNotifies(null);
        tmember.setSocialUsers(null);
        tmember.setPointLogs(null);
        tmember.setIsAudit(User.CheckStatus.SUCCESS);
        userService.register(tmember);
        return Results.OK;
    }
}
