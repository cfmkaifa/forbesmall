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

import net.mall.Pageable;
import net.mall.Results;
import net.mall.entity.Review;
import net.mall.entity.Store;
import net.mall.exception.UnauthorizedException;
import net.mall.security.CurrentStore;
import net.mall.service.ReviewService;

/**
 * Controller - 评论
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("businessReviewController")
@RequestMapping("/business/review")
public class ReviewController extends BaseController {

    @Inject
    private ReviewService reviewService;

    /**
     * 添加属性
     */
    @ModelAttribute
    public void populateModel(Long reviewId, @CurrentStore Store currentStore, ModelMap model) {
        Review review = reviewService.find(reviewId);
        if (review != null && !currentStore.equals(review.getStore())) {
            throw new UnauthorizedException();
        }
        model.addAttribute("review", review);
    }

    /**
     * 回复
     */
    @GetMapping("/reply")
    public String reply(@ModelAttribute(binding = false) Review review, ModelMap model) {
        if (review == null) {
            return UNPROCESSABLE_ENTITY_VIEW;
        }

        model.addAttribute("review", review);
        return "business/review/reply";
    }

    /**
     * 回复
     */
    @PostMapping("/reply")
    public ResponseEntity<?> reply(@ModelAttribute(binding = false) Review review, String content, HttpServletRequest request) {
        if (!isValid(Review.class, "content", content)) {
            return Results.UNPROCESSABLE_ENTITY;
        }
        if (review == null) {
            return Results.UNPROCESSABLE_ENTITY;
        }

        Review replyReview = new Review();
        replyReview.setContent(content);
        replyReview.setIp(request.getRemoteAddr());
        reviewService.reply(review, replyReview);
        reviewService.update(review);

        return Results.OK;
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(Review.Type type, Pageable pageable, @CurrentStore Store currentStore, ModelMap model) {
        model.addAttribute("type", type);
        model.addAttribute("types", Review.Type.values());
        model.addAttribute("page", reviewService.findPage(null, null, currentStore, type, null, pageable));
        return "business/review/list";
    }

    /**
     * 删除回复
     */
    @PostMapping("/delete_reply")
    public ResponseEntity<?> deleteReply(@ModelAttribute(binding = false) Review review) {
        if (review == null || review.getForReview() == null) {
            return Results.UNPROCESSABLE_ENTITY;
        }

        reviewService.delete(review);
        return Results.OK;
    }

}