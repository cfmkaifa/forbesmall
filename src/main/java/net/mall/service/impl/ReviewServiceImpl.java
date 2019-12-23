/*
 *
 * 
 *
 * 
 */
package net.mall.service.impl;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.mall.Filter;
import net.mall.Page;
import net.mall.Pageable;
import net.mall.Setting;
import net.mall.dao.MemberDao;
import net.mall.dao.OrderItemDao;
import net.mall.dao.ProductDao;
import net.mall.dao.ReviewDao;
import net.mall.entity.Member;
import net.mall.entity.Order;
import net.mall.entity.OrderItem;
import net.mall.entity.Product;
import net.mall.entity.Review;
import net.mall.entity.Review.Entry;
import net.mall.entity.Review.Type;
import net.mall.entity.Sku;
import net.mall.entity.Store;
import net.mall.service.ReviewService;
import net.mall.util.SystemUtils;

/**
 * Service - 评论
 * 
 * @author huanghy
 * @version 6.1
 */
@Service
public class ReviewServiceImpl extends BaseServiceImpl<Review, Long> implements ReviewService {

	@Inject
	private ReviewDao reviewDao;
	@Inject
	private MemberDao memberDao;
	@Inject
	private ProductDao productDao;
	@Inject
	private OrderItemDao orderItemDao;

	@Override
	@Transactional(readOnly = true)
	public List<Review> findList(Member member, Product product, Review.Type type, Boolean isShow, Integer count, List<Filter> filters, List<net.mall.Order> orders) {
		return reviewDao.findList(member, product, type, isShow, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "review", condition = "#useCache")
	public List<Review> findList(Long memberId, Long productId, Review.Type type, Boolean isShow, Integer count, List<Filter> filters, List<net.mall.Order> orders, boolean useCache) {
		Member member = memberDao.find(memberId);
		if (memberId != null && member == null) {
			return Collections.emptyList();
		}
		Product product = productDao.find(productId);
		if (productId != null && product == null) {
			return Collections.emptyList();
		}
		return reviewDao.findList(member, product, type, isShow, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Review> findPage(Member member, Product product, Store store, Review.Type type, Boolean isShow, Pageable pageable) {
		return reviewDao.findPage(member, product, store, type, isShow, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count(Member member, Product product, Review.Type type, Boolean isShow) {
		return reviewDao.count(member, product, type, isShow);
	}

	@Override
	public Long count(Long memberId, Long productId, Type type, Boolean isShow) {
		Member member = memberDao.find(memberId);
		if (memberId != null && member == null) {
			return 0L;
		}
		Product product = productDao.find(productId);
		if (productId != null && product == null) {
			return 0L;
		}
		return reviewDao.count(member, product, type, isShow);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "review" }, allEntries = true)
	public void create(Order order, List<Entry> reviewEntries, String ip, Member memebr) {
		Assert.notNull(order, "[Assertion failed] - order is required; it must not be null");
		Assert.notEmpty(reviewEntries, "[Assertion failed] - reviewEntries must not be empty: it must contain at least 1 element");
		Assert.notNull(ip, "[Assertion failed] - ip is required; it must not be null");
		Assert.notNull(memebr, "[Assertion failed] - memebr is required; it must not be null");

		Setting setting = SystemUtils.getSetting();
		for (Review.Entry reviewEntry : reviewEntries) {
			OrderItem orderItem = reviewEntry.getOrderItem();
			Review review = reviewEntry.getReview();
			if (orderItem == null || review == null) {
				return;
			}
			OrderItem pOrderItem = orderItemDao.find(orderItem.getId());
			if (!order.equals(pOrderItem.getOrder())) {
				return;
			}
			Sku sku = pOrderItem.getSku();
			if (sku == null) {
				continue;
			}

			Review pReview = new Review();
			pReview.setScore(review.getScore());
			pReview.setContent(review.getContent());
			pReview.setIp(ip);
			pReview.setMember(memebr);
			pReview.setProduct(sku.getProduct());
			pReview.setStore(sku.getProduct().getStore());
			pReview.setReplyReviews(null);
			pReview.setForReview(null);
			pReview.setSpecifications(pOrderItem.getSpecifications());
			pReview.setIsShow(setting.getIsReviewCheck() ? false : true);
			reviewDao.persist(pReview);
		}
		order.setIsReviewed(true);

	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "review" }, allEntries = true)
	public Review save(Review review) {
		Assert.notNull(review, "[Assertion failed] - review is required; it must not be null");

		Review pReview = super.save(review);
		Product product = pReview.getProduct();
		if (product != null) {
			reviewDao.flush();
			long totalScore = reviewDao.calculateTotalScore(product);
			long scoreCount = reviewDao.calculateScoreCount(product);
			product.setTotalScore(totalScore);
			product.setScoreCount(scoreCount);
		}
		return pReview;
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "review" }, allEntries = true)
	public Review update(Review review) {
		Assert.notNull(review, "[Assertion failed] - review is required; it must not be null");

		Review pReview = super.update(review);
		Product product = pReview.getProduct();
		if (product != null) {
			reviewDao.flush();
			long totalScore = reviewDao.calculateTotalScore(product);
			long scoreCount = reviewDao.calculateScoreCount(product);
			product.setTotalScore(totalScore);
			product.setScoreCount(scoreCount);
		}
		return pReview;
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "review" }, allEntries = true)
	public Review update(Review review, String... ignoreProperties) {
		return super.update(review, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "review" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "review" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "review" }, allEntries = true)
	public void delete(Review review) {
		if (review != null) {
			super.delete(review);
			Product product = review.getProduct();
			if (product != null) {
				reviewDao.flush();
				long totalScore = reviewDao.calculateTotalScore(product);
				long scoreCount = reviewDao.calculateScoreCount(product);
				product.setTotalScore(totalScore);
				product.setScoreCount(scoreCount);
			}
		}
	}

	@Override
	@CacheEvict(value = { "product", "review" }, allEntries = true)
	public void reply(Review review, Review replyReview) {
		if (review != null && replyReview != null) {
			replyReview.setIsShow(review.getIsShow());
			replyReview.setProduct(review.getProduct());
			replyReview.setForReview(review);
			replyReview.setStore(review.getStore());
			replyReview.setScore(review.getScore());
			replyReview.setMember(review.getMember());
			reviewDao.persist(replyReview);
		}
	}

}