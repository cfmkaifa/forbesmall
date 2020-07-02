/*
 *
 *
 *
 *
 */
package net.mall.controller.business;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
import net.mall.Results;
import net.mall.entity.Business;
import net.mall.service.BusinessService;
import net.mall.util.BusTypeEnum;
import net.mall.util.RestTemplateUtil;
import net.mall.util.SensorsAnalyticsUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.mall.entity.Store;
import net.mall.security.CurrentStore;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller - 商家中心
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("businessIndexController")
@RequestMapping("/business/index")
public class IndexController extends BaseController {


    @Inject
    private SensorsAnalyticsUtils sensorsAnalyticsUtils;


    private final String ONE_ONE_ID_F = "b%s%s";

    /***查询链详情
     * @param dataId
     * @param request
     * @return
     */
    @PostMapping("/chain")
    @ResponseBody
    public ResponseEntity<?> chain(Long dataId, HttpServletRequest request) {
        try {
            ResponseEntity<RestTemplateUtil.BusResult>  responseEntity = RestTemplateUtil.reqTemplate(String.format(ONE_ONE_ID_F,"b",dataId), BusTypeEnum.SUPPLIER.getCode());
            return responseEntity;
        }catch (Exception e){
            e.printStackTrace();
        }
        return Results.OK;
    }


    /**
     * 首页
     */
    @GetMapping
    public String index(@CurrentStore Store currentStore, ModelMap model, HttpServletRequest request) throws InvalidArgumentException, UnsupportedEncodingException {
        model.addAttribute("currentStore", currentStore);
        /***供应商登录
         * **/
        Map<String,Object> properties = new HashMap<String,Object>();
        Business business = currentStore.getBusiness();
        properties.put("account",business.getUsername());
        properties.put("is_quick_login",false);
        properties.put("is_success",true);
        properties.put("fail_reason","");
        sensorsAnalyticsUtils.reportData(String.valueOf(business.getId()),"LoginResult",properties);

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
        return "business/index";
    }

}