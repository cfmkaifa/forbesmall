/*
 *
 *
 *
 *
 */
package net.mall.controller.business;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.mall.Results;
import net.mall.entity.Business;
import net.mall.security.CurrentUser;
import net.mall.service.BusinessService;

/**
 * Controller - 密码
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("businessPasswordController")
@RequestMapping("/business/password")
public class PasswordController extends BaseController {

    @Inject
    private BusinessService businessService;

    /**
     * 验证当前密码
     */
    @GetMapping("/check_current_password")
    public @ResponseBody
    boolean checkCurrentPassword(String currentPassword, @CurrentUser Business currentUser) {
        return StringUtils.isNotEmpty(currentPassword) && currentUser.isValidCredentials(currentPassword);
    }

    /**
     * 编辑
     */
    @GetMapping("/edit")
    public String edit() {
        return "business/password/edit";
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public ResponseEntity<?> update(String currentPassword, String password, @CurrentUser Business currentUser) {
        if (StringUtils.isEmpty(currentPassword) || StringUtils.isEmpty(password)) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (!currentUser.isValidCredentials(currentPassword)) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (!isValid(Business.class, "password", password)) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        currentUser.setPassword(password);
        businessService.update(currentUser);

        return Results.OK;
    }

}