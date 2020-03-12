/*
 *
 *
 *
 *
 */
package net.mall.controller.business;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.mall.Results;
import net.mall.entity.AftersalesSetting;
import net.mall.entity.Store;
import net.mall.security.CurrentStore;
import net.mall.service.AftersalesSettingService;

/**
 * Controller - 售后设置
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("businessAftersalesSettingController")
@RequestMapping("business/aftersales_setting")
public class AftersalesSettingController extends BaseController {

    @Inject
    private AftersalesSettingService aftersalesSettingService;

    /**
     * 查看
     */
    @GetMapping("/view")
    public String view(@CurrentStore Store currentStore, ModelMap model) {
        AftersalesSetting aftersalesSetting = aftersalesSettingService.findByStore(currentStore);
        if (aftersalesSetting == null) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }

        model.addAttribute("aftersalesSetting", aftersalesSetting);
        return "business/aftersales_setting/view";
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public ResponseEntity<?> update(AftersalesSetting aftersalesSettingForm, @CurrentStore Store currentStore) {
        AftersalesSetting aftersalesSetting = aftersalesSettingService.findByStore(currentStore);
        if (aftersalesSetting == null) {
            return Results.UNPROCESSABLE_ENTITY;
        }

        BeanUtils.copyProperties(aftersalesSettingForm, aftersalesSetting, "id", "store");
        aftersalesSettingService.update(aftersalesSetting);
        return Results.OK;
    }

}