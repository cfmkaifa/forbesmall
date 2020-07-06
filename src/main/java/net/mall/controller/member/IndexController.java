/*
 *
 *
 *
 *
 */
package net.mall.controller.member;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
import net.mall.Filter;
import net.mall.Results;
import net.mall.Setting;
import net.mall.entity.*;
import net.mall.model.ResultModel;
import net.mall.service.*;
import net.mall.util.BusTypeEnum;
import net.mall.util.ConvertUtils;
import net.mall.util.RestTemplateUtil;
import net.mall.util.SystemUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.mall.controller.shop.BaseController;
import net.mall.security.CurrentUser;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controller - 首页
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("memberIndexController")
@RequestMapping("/member/index")
public class IndexController extends BaseController {

    /**
     * 最新订单数量
     */
    private static final int NEW_ORDER_SIZE = 3;

    @Inject
    private OrderService orderService;
    @Inject
    private CouponCodeService couponCodeService;
    @Inject
    private MessageService messageService;
    @Inject
    private ProductFavoriteService productFavoriteService;
    @Inject
    private ProductNotifyService productNotifyService;
    @Inject
    private ReviewService reviewService;
    @Inject
    private ConsultationService consultationService;
    @Inject
    private MemberService memberService;


    private final String ONE_ONE_ID_F = "a%s%s";

    /***查询链详情
     * @param dataId
     * @param request
     * @return
     */
    @PostMapping("/chain")
    @ResponseBody
    public ResponseEntity<?> chain(Long dataId, HttpServletRequest request) {
        try {
            ResultModel responseEntity =  RestTemplateUtil.reqTemplate(String.format(ONE_ONE_ID_F,"m",dataId), BusTypeEnum.SUPPLIER.getCode());
            if("000000".equals(responseEntity.getResultCode())){
                return Results.status(HttpStatus.OK,responseEntity.getData());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return Results.OK;
    }

    /**
     * 首页
     */
    @GetMapping
    public String index(@CurrentUser Member currentUser, ModelMap model) {
        model.addAttribute("pendingPaymentOrderCount", orderService.count(null, Order.Status.PENDING_PAYMENT, null, currentUser, null, null, null, null, null, null, false));
        model.addAttribute("pendingShipmentOrderCount", orderService.count(null, Order.Status.PENDING_SHIPMENT, null, currentUser, null, null, null, null, null, null, false));
        model.addAttribute("shippedOrderCount", orderService.count(null, Order.Status.SHIPPED, null, currentUser, null, null, null, null, null, null, null));
        model.addAttribute("unreadMessageCount", messageService.unreadMessageCount(null, currentUser));
        model.addAttribute("couponCodeCount", couponCodeService.count(null, currentUser, null, false, false));
        model.addAttribute("productFavoriteCount", productFavoriteService.count(currentUser));
        model.addAttribute("productNotifyCount", productNotifyService.count(currentUser, null, null, null));
        model.addAttribute("reviewCount", reviewService.count(currentUser, null, null, null));
        model.addAttribute("consultationCount", consultationService.count(currentUser, null, null));
        model.addAttribute("newOrders", orderService.findList(null, null, null, currentUser, null, null, null, null, null, null, null, NEW_ORDER_SIZE, null, null));
        return "member/index";
    }


    /**
     * 首页
     */
    @GetMapping(value = "/account/list")
    public String accountList(@CurrentUser Member currentUser, ModelMap model) {
        return "member/account/list";
    }


    /***
     * 查询账号
     * @param member
     * @param request
     * @return
     */
    @PostMapping("/account/list")
    public ResponseEntity<List<Member>> accountList(@CurrentUser Member member,HttpServletRequest request) {
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(new Filter("parentId",Filter.Operator.EQ,member.getId()));
        List<Member>  members =    memberService.findList(null,filters,null);
        return ResponseEntity.ok(members);
    }
}