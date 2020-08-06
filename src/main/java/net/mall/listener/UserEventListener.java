/*
 *
 *
 *
 *
 */
package net.mall.listener;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
import net.mall.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import net.mall.Setting;
import net.mall.entity.Business;
import net.mall.entity.Cart;
import net.mall.entity.Member;
import net.mall.entity.PointLog;
import net.mall.entity.SocialUser;
import net.mall.entity.User;
import net.mall.event.UserLoggedInEvent;
import net.mall.event.UserRegisteredEvent;
import net.mall.service.CartService;
import net.mall.service.MemberService;
import net.mall.service.SocialUserService;

/**
 * Listener - 用户事件
 *
 * @author huanghy
 * @version 6.1
 */
@Component
public class UserEventListener {

    @Inject
    private MemberService memberService;
    @Inject
    private CartService cartService;
    @Inject
    private SocialUserService socialUserService;

    /**
     * 事件处理
     *
     * @param userRegisteredEvent 用户注册事件
     */
    @EventListener
    public void handle(UserRegisteredEvent userRegisteredEvent) {
        User user = userRegisteredEvent.getUser();
        HttpServletRequest request = WebUtils.getRequest();

        if (user instanceof Member) {
            String socialUserId = request.getParameter("socialUserId");
            String uniqueId = request.getParameter("uniqueId");
            if (StringUtils.isNotEmpty(socialUserId) && StringUtils.isNotEmpty(uniqueId)) {
                SocialUser socialUser = socialUserService.find(Long.parseLong(socialUserId));
                if (socialUser != null && socialUser.getUser() == null) {
                    socialUserService.bindUser(user, socialUser, uniqueId);
                }
            }

            Member member = (Member) user;
            Setting setting = SystemUtils.getSetting();
            if (setting.getRegisterPoint() > 0) {
                memberService.addPoint(member, setting.getRegisterPoint(), PointLog.Type.REWARD, null);
            }
        }
    }

    /**
     * 事件处理
     *
     * @param userLoggedInEvent 用户登录事件
     */
    @EventListener
    public void handle(UserLoggedInEvent userLoggedInEvent) throws UnsupportedEncodingException, InvalidArgumentException {
        User user = userLoggedInEvent.getUser();
        HttpServletRequest request = WebUtils.getRequest();
        if (user instanceof Member) {
            String socialUserId = request.getParameter("socialUserId");
            String uniqueId = request.getParameter("uniqueId");
            if (StringUtils.isNotEmpty(socialUserId) && StringUtils.isNotEmpty(uniqueId)) {
                SocialUser socialUser = socialUserService.find(Long.parseLong(socialUserId));
                if (socialUser != null && socialUser.getUser() == null) {
                    socialUserService.bindUser(user, socialUser, uniqueId);
                }
            }
            Member member = (Member) user;
            Subject subject = SecurityUtils.getSubject();
            sessionFixationProtection(subject);
            Cart cart = member.getCart();
            cartService.merge(cart != null ? cart : cartService.create());
            /**采购商登录**/
            Map<String,Object> properties = new HashMap<String,Object>();
            properties.put("account",member.getUsername());
            properties.put("is_quick_login",false);
            properties.put("is_success",true);
            properties.put("fail_reason","");
            SensorsAnalyticsUtils sensorsAnalyticsUtils = SpringUtils.getBean(SensorsAnalyticsUtils.class);
            if(ConvertUtils.isNotEmpty(sensorsAnalyticsUtils)){
                sensorsAnalyticsUtils.reportData(String.valueOf(member.getId()),"LoginResult",properties);
            }

            /***
             * 上报数据调用登录接口
             */
            Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
            Cookie[] cookies =request.getCookies();
            if (null != cookies){
                for(Cookie cookie:cookies){
                    cookieMap.put(cookie.getName(),cookie);
                }
            }
            String temp="sensorsdata2015jssdkcross";
            String tempObj=null;
            if(cookieMap.containsKey(temp)){
                tempObj=java.net.URLDecoder.decode(cookieMap.get(temp).getValue(), "UTF-8");
                JSONObject jsonObject= JSON.parseObject(tempObj);
                sensorsAnalyticsUtils.reportSignUp(member.getId().toString(),jsonObject.getString("distinct_id"));
            }
        } else if (user instanceof Business) {
            Subject subject = SecurityUtils.getSubject();
            sessionFixationProtection(subject);
            /**供应商商登录**/
            Business business = (Business) user;
            Map<String,Object> properties = new HashMap<String,Object>();
            properties.put("account",business.getUsername());
            properties.put("is_quick_login",false);
            properties.put("is_success",true);
            properties.put("fail_reason","");
            SensorsAnalyticsUtils sensorsAnalyticsUtils = SpringUtils.getBean(SensorsAnalyticsUtils.class);
            if(ConvertUtils.isNotEmpty(sensorsAnalyticsUtils)){
                sensorsAnalyticsUtils.reportData(String.valueOf(business.getId()),"LoginResult",properties);
            }

            /***
             * 上报数据调用登录接口
             */
            Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
            Cookie[] cookies =request.getCookies();
            if (null != cookies){
                for(Cookie cookie:cookies){
                    cookieMap.put(cookie.getName(),cookie);
                }
            }
            String temp="sensorsdata2015jssdkcross";
            String tempObj=null;
            if(cookieMap.containsKey(temp)){
                tempObj=java.net.URLDecoder.decode(cookieMap.get(temp).getValue(), "UTF-8");
                JSONObject jsonObject= JSON.parseObject(tempObj);
                sensorsAnalyticsUtils.reportSignUp(business.getId().toString(),jsonObject.getString("distinct_id"));
            }
        }

    }

    /**
     * Session固定攻击防护
     *
     * @param subject Subject
     */
    private void sessionFixationProtection(Subject subject) {
        Assert.notNull(subject, "[Assertion failed] - subject is required; it must not be null");

        Session session = subject.getSession();
        Map<Object, Object> attributes = new HashMap<>();
        Collection<Object> attributeKeys = session.getAttributeKeys();
        for (Object attributeKey : attributeKeys) {
            attributes.put(attributeKey, session.getAttribute(attributeKey));
        }
        session.stop();
        session = subject.getSession(true);
        for (Map.Entry<Object, Object> entry : attributes.entrySet()) {
            session.setAttribute(entry.getKey(), entry.getValue());
        }
    }

}