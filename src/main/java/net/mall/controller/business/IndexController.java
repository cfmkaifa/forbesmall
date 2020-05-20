/*
 *
 *
 *
 *
 */
package net.mall.controller.business;

import net.mall.entity.Business;
import net.mall.util.SensorsAnalyticsUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.mall.entity.Store;
import net.mall.security.CurrentStore;

import javax.inject.Inject;
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
    /**
     * 首页
     */
    @GetMapping
    public String index(@CurrentStore Store currentStore, ModelMap model) {
        /***供应商登录
         * **/
        Map<String,Object> properties = new HashMap<String,Object>();
        Business business = currentStore.getBusiness();
        properties.put("account",business.getUsername());
        properties.put("is_quick_login",false);
        properties.put("is_success",true);
        properties.put("fail_reason","");
        sensorsAnalyticsUtils.reportData(String.valueOf(business.getId()),"LoginResult",properties);
        return "business/index";
    }

}