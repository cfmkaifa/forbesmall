/*
 *
 *
 *
 *
 */
package net.mall.controller.business;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.mall.Pageable;
import net.mall.Results;
import net.mall.entity.Consultation;
import net.mall.entity.Store;
import net.mall.exception.UnauthorizedException;
import net.mall.security.CurrentStore;
import net.mall.service.ConsultationService;

/**
 * Controller - 咨询
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("businessConsultationController")
@RequestMapping("/business/consultation")
public class ConsultationController extends BaseController {

    @Inject
    private ConsultationService consultationService;

    /**
     * 添加属性
     */
    @ModelAttribute
    public void populateModel(Long consultationId, @CurrentStore Store currentStore, ModelMap model) {
        Consultation consultation = consultationService.find(consultationId);
        if (consultation != null && !currentStore.equals(consultation.getStore())) {
            throw new UnauthorizedException();
        }
        model.addAttribute("consultation", consultation);
    }

    /**
     * 回复
     */
    @GetMapping("/reply")
    public String reply(@ModelAttribute(binding = false) Consultation consultation, ModelMap model) {
        if (consultation == null) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }

        model.addAttribute("consultation", consultation);
        return "business/consultation/reply";
    }

    /**
     * 回复
     */
    @PostMapping("/reply")
    public ResponseEntity<?> reply(@ModelAttribute(binding = false) Consultation consultation, String content, HttpServletRequest request) {
        if (!isValid(Consultation.class, "content", content)) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (consultation == null) {
            return Results.UNPROCESSABLE_ENTITY;
        }

        Consultation replyConsultation = new Consultation();
        replyConsultation.setContent(content);
        replyConsultation.setIp(request.getRemoteAddr());
        consultationService.reply(consultation, replyConsultation);

        return Results.OK;
    }

    /**
     * 编辑
     */
    @GetMapping("/edit")
    public String edit(@ModelAttribute(binding = false) Consultation consultation, ModelMap model) {
        if (consultation == null) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }

        model.addAttribute("consultation", consultation);
        return "business/consultation/edit";
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public ResponseEntity<?> update(@ModelAttribute(binding = false) Consultation consultation, @RequestParam(defaultValue = "false") Boolean isShow) {
        if (consultation == null) {
            return Results.UNPROCESSABLE_ENTITY;
        }

        if (isShow != consultation.getIsShow()) {
            consultation.setIsShow(isShow);
            consultationService.update(consultation);
        }

        return Results.OK;
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(Pageable pageable, @CurrentStore Store currentStore, ModelMap model) {
        model.addAttribute("page", consultationService.findPage(null, null, currentStore, null, pageable));
        return "business/consultation/list";
    }

    /**
     * 删除回复
     */
    @PostMapping("/delete_reply")
    public ResponseEntity<?> deleteReply(@ModelAttribute(binding = false) Consultation consultation) {
        if (consultation == null || consultation.getForConsultation() == null) {
            return Results.UNPROCESSABLE_ENTITY;
        }

        consultationService.delete(consultation);
        return Results.OK;
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public ResponseEntity<?> delete(Long[] ids, @CurrentStore Store currentStore) {
        for (Long id : ids) {
            Consultation consultation = consultationService.find(id);
            if (consultation == null || !currentStore.equals(consultation.getStore())) {
                return Results.UNPROCESSABLE_ENTITY;
            }
        }
        consultationService.delete(ids);
        return Results.OK;
    }

}