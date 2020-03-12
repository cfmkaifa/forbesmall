/*
 *
 *
 *
 *
 */
package net.mall.controller.member;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.mall.Results;
import net.mall.entity.Member;
import net.mall.entity.Message;
import net.mall.entity.MessageGroup;
import net.mall.entity.User;
import net.mall.entity.User.Type;
import net.mall.exception.UnauthorizedException;
import net.mall.security.CurrentUser;
import net.mall.service.BusinessService;
import net.mall.service.MemberService;
import net.mall.service.MessageGroupService;
import net.mall.service.MessageService;

/**
 * Controller - 会员中心 - 消息
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("memberMessageController")
@RequestMapping("/member/message")
public class MessageController extends BaseController {

    @Inject
    private MessageService messageService;
    @Inject
    private MessageGroupService messageGroupService;
    @Inject
    private MemberService memberService;
    @Inject
    private BusinessService businessService;

    /**
     * 添加属性
     */
    @ModelAttribute
    public void populateModel(Long messageGroupId, @CurrentUser Member currentUser, ModelMap model) {
        MessageGroup messageGroup = messageGroupService.find(messageGroupId);
        if (messageGroup != null && !currentUser.equals(messageGroup.getUser1()) && !currentUser.equals(messageGroup.getUser2())) {
            throw new UnauthorizedException();
        }
        model.addAttribute("messageGroup", messageGroup);
    }

    /**
     * 检查用户名是否合法
     */
    @GetMapping("/check_username")
    public @ResponseBody
    boolean checkUsername(String username, Type type, @CurrentUser Member currentUser) {
        switch (type) {
            case MEMBER:
                return StringUtils.isNotEmpty(username) && !StringUtils.equalsIgnoreCase(username, currentUser.getUsername()) && memberService.usernameExists(username);
            case BUSINESS:
                return StringUtils.isNotEmpty(username) && businessService.usernameExists(username);
            default:
                return false;
        }
    }

    /**
     * 发送
     */
    @GetMapping("/send")
    public String send(Model model) {
        return "member/message/send";
    }

    /**
     * 发送
     */
    @PostMapping("/send")
    public ResponseEntity<?> send(String content, String username, User.Type type, @CurrentUser Member currentUser, HttpServletRequest request) {
        if (!isValid(Message.class, "content", content) || type == null || StringUtils.isEmpty(username)) {
            return Results.UNPROCESSABLE_ENTITY;
        }

        User toUser = null;
        switch (type) {
            case MEMBER:
                toUser = memberService.findByUsername(username);
                if (toUser == null || currentUser.equals(toUser)) {
                    return Results.UNPROCESSABLE_ENTITY;
                }
                break;
            case BUSINESS:
                toUser = businessService.findByUsername(username);
                if (toUser == null) {
                    return Results.UNPROCESSABLE_ENTITY;
                }
                break;
            default:
                return Results.UNPROCESSABLE_ENTITY;
        }

        messageService.send(type, currentUser, toUser, content, request.getRemoteAddr());
        return Results.OK;
    }

    /**
     * 查看
     */
    @GetMapping("/view")
    public String view(@ModelAttribute(binding = false) MessageGroup messageGroup, @CurrentUser Member currentUser, Model model) {
        if (messageGroup == null) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }

        messageService.consult(messageGroup, currentUser);
        model.addAttribute("messageGroupId", messageGroup.getId());
        model.addAttribute("messages", messageService.findList(messageGroup, currentUser));
        return "member/message/view";
    }

    /**
     * 回复
     */
    @PostMapping("/reply")
    public ResponseEntity<?> reply(@ModelAttribute(binding = false) MessageGroup messageGroup, String content, @CurrentUser Member currentUser, HttpServletRequest request) {
        if (!isValid(Message.class, "content", content) || messageGroup == null) {
            return Results.UNPROCESSABLE_ENTITY;
        }

        messageService.reply(messageGroup, currentUser, content, request.getRemoteAddr());
        return Results.OK;
    }

}