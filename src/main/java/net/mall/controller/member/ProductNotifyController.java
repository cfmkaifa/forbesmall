/*
 *
 *
 *
 *
 */
package net.mall.controller.member;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonView;

import net.mall.Pageable;
import net.mall.Results;
import net.mall.entity.BaseEntity;
import net.mall.entity.Member;
import net.mall.entity.ProductNotify;
import net.mall.exception.UnauthorizedException;
import net.mall.security.CurrentUser;
import net.mall.service.ProductNotifyService;

/**
 * Controller - 到货通知
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("memberProductNotifyController")
@RequestMapping("/member/product_notify")
public class ProductNotifyController extends BaseController {

    /**
     * 每页记录数
     */
    private static final int PAGE_SIZE = 10;

    @Inject
    private ProductNotifyService productNotifyService;

    /**
     * 添加属性
     */
    @ModelAttribute
    public void populateModel(Long productNotifyId, @CurrentUser Member currentUser, ModelMap model) {
        ProductNotify productNotify = productNotifyService.find(productNotifyId);
        if (productNotify != null && !currentUser.equals(productNotify.getMember())) {
            throw new UnauthorizedException();
        }
        model.addAttribute("productNotify", productNotify);
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(Integer pageNumber, @CurrentUser Member currentUser, Model model) {
        Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
        model.addAttribute("page", productNotifyService.findPage(null, currentUser, null, null, null, pageable));
        return "member/product_notify/list";
    }

    /**
     * 列表
     */
    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(BaseEntity.BaseView.class)
    public ResponseEntity<?> list(Integer pageNumber, @CurrentUser Member currentUser) {
        Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
        return ResponseEntity.ok(productNotifyService.findPage(null, currentUser, null, null, null, pageable).getContent());
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public ResponseEntity<?> delete(@ModelAttribute(binding = false) ProductNotify productNotify) {
        if (productNotify == null) {
            return Results.NOT_FOUND;
        }

        productNotifyService.delete(productNotify);
        return Results.OK;
    }

}