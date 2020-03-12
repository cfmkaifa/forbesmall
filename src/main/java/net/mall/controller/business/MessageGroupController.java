/*
 *
 *
 *
 *
 */
package net.mall.controller.business;

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
import net.mall.entity.Business;
import net.mall.entity.MessageGroup;
import net.mall.exception.UnauthorizedException;
import net.mall.security.CurrentUser;
import net.mall.service.MessageGroupService;

/**
 * Controller - 商家中心 - 消息组
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("businessMessageGroupController")
@RequestMapping("/business/message_group")
public class MessageGroupController extends BaseController {

    @Inject
    private MessageGroupService messageGroupService;

    /**
     * 添加属性
     */
    @ModelAttribute
    public void populateModel(Long messageGroupId, @CurrentUser Business currentUser, ModelMap model) {
        MessageGroup messageGroup = messageGroupService.find(messageGroupId);
        if (messageGroup != null && !currentUser.equals(messageGroup.getUser1()) && !currentUser.equals(messageGroup.getUser2())) {
            throw new UnauthorizedException();
        }
        model.addAttribute("messageGroup", messageGroup);
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(Integer pageNumber, @CurrentUser Business currentUser, Pageable pageable, Model model) {
        model.addAttribute("page", messageGroupService.findPage(currentUser, pageable));
        return "business/message_group/list";
    }

    /**
     * 列表
     */
    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(BaseEntity.BaseView.class)
    public ResponseEntity<?> list(Integer pageNumber, Pageable pageable, @CurrentUser Business currentUser) {
        return ResponseEntity.ok(messageGroupService.findPage(currentUser, pageable).getContent());
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public ResponseEntity<?> delete(@ModelAttribute(binding = false) MessageGroup messageGroup, @CurrentUser Business currentUser) {
        if (messageGroup == null) {
            return Results.NOT_FOUND;
        }

        messageGroupService.delete(messageGroup, currentUser);
        return Results.OK;
    }

}