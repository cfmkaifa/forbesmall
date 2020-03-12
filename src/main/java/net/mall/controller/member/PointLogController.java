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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonView;

import net.mall.Pageable;
import net.mall.entity.BaseEntity;
import net.mall.entity.Member;
import net.mall.security.CurrentUser;
import net.mall.service.PointLogService;

/**
 * Controller - 我的积分
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("memberPointLogController")
@RequestMapping("/member/point_log")
public class PointLogController extends BaseController {

    /**
     * 每页记录数
     */
    private static final int PAGE_SIZE = 10;

    @Inject
    private PointLogService pointLogService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(Integer pageNumber, @CurrentUser Member currentUser, ModelMap model) {
        Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
        model.addAttribute("page", pointLogService.findPage(currentUser, pageable));
        return "member/point_log/list";
    }

    /**
     * 列表
     */
    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(BaseEntity.BaseView.class)
    public ResponseEntity<?> list(Integer pageNumber, @CurrentUser Member currentUser) {
        Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
        return ResponseEntity.ok(pointLogService.findPage(currentUser, pageable).getContent());
    }

}