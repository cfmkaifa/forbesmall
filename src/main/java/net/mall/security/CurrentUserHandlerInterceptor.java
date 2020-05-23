/*
 *
 *
 *
 *
 */
package net.mall.security;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mall.entity.Business;
import net.mall.entity.Member;
import net.mall.entity.Order;
import net.mall.util.ConvertUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import net.mall.entity.User;
import net.mall.service.UserService;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Security - 当前用户拦截器
 *
 * @author huanghy
 * @version 6.1
 */
public class CurrentUserHandlerInterceptor extends HandlerInterceptorAdapter {

    /**
     * 默认"当前用户"属性名称
     */
    public static final String DEFAULT_CURRENT_USER_ATTRIBUTE_NAME = "currentUser";

    /**
     * 用户类型
     */
    private Class<? extends User> userClass;

    /**
     * "当前用户"属性名称
     */
    private String currentUserAttributeName = DEFAULT_CURRENT_USER_ATTRIBUTE_NAME;

    @Inject
    private UserService userService;

    /**
     * 请求后处理
     *
     * @param request      HttpServletRequest
     * @param response     HttpServletResponse
     * @param handler      处理器
     * @param modelAndView 数据视图
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = userService.getCurrent(getUserClass());
        request.setAttribute(getCurrentUserAttributeName(), user);
        if(ConvertUtils.isNotEmpty(user)){
            request.getSession().setAttribute("isLogin","true");
            //最后登录日期
            request.getSession().setAttribute("lastLoginDate",user.getLastLoginDate());
        }else {
            request.getSession().setAttribute("isLogin","false");
        }
         if(user instanceof Member){
             request.getSession().setAttribute("userType","member");
             Member member= (Member) userService.getCurrent(getUserClass());
             request.getSession().setAttribute("email",member.getEmail());
             request.getSession().setAttribute("phone_number",member.getMobile());
             request.getSession().setAttribute("register_time",member.getCreatedDate());
             request.getSession().setAttribute("username",member.getUsername());
             Set<Order> ordersList=member.getOrders();
             List<Date> dateList=ordersList.stream().map(Order::getCreatedDate).collect(Collectors.toList());
             request.getSession().setAttribute("first_order_time", Collections.min(dateList));
             request.getSession().setAttribute("last_order_time", Collections.max(dateList));
             request.getSession().setAttribute("vip_level", member.getMemberRank().getName());
         }
        if(user instanceof Business){
            request.getSession().setAttribute("userType","business");
            Business business=(Business) userService.getCurrent(getUserClass());
            request.getSession().setAttribute("email",business.getEmail());
            request.getSession().setAttribute("phone_number",business.getMobile());
            request.getSession().setAttribute("register_time",business.getCreatedDate());
            request.getSession().setAttribute("username",business.getUsername());
            request.getSession().setAttribute("first_order_time","无");
            request.getSession().setAttribute("last_order_time","无");
            request.getSession().setAttribute("vip_level", business.getStore().getStoreRank().getName());
        }

    }

    /**
     * 获取用户类型
     *
     * @return 用户类型
     */
    public Class<? extends User> getUserClass() {
        return userClass;
    }

    /**
     * 设置用户类型
     *
     * @param userClass 用户类型
     */
    public void setUserClass(Class<? extends User> userClass) {
        this.userClass = userClass;
    }

    /**
     * 获取"当前用户"属性名称
     *
     * @return "当前用户"属性名称
     */
    public String getCurrentUserAttributeName() {
        return currentUserAttributeName;
    }

    /**
     * 设置"当前用户"属性名称
     *
     * @param currentUserAttributeName "当前用户"属性名称
     */
    public void setCurrentUserAttributeName(String currentUserAttributeName) {
        this.currentUserAttributeName = currentUserAttributeName;
    }

}