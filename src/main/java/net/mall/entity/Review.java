/*
 *
 * 
 *
 * 
 */
package net.mall.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonView;

import net.mall.BaseAttributeConverter;

/**
 * Entity - 评论
 * 
 * @author huanghy
 * @version 6.1
 */
@Entity
public class Review extends BaseEntity<Long> {

	private static final long serialVersionUID = 8795901519290584100L;

	/**
	 * 路径
	 */
	private static final String PATH = "/review/detail/%d";

	/**
	 * 类型
	 */
	public enum Type {

		/**
		 * 好评
		 */
		POSITIVE,

		/**
		 * 中评
		 */
		MODERATE,

		/**
		 * 差评
		 */
		NEGATIVE
	}

	/**
	 * 评分
	 */
	@JsonView(BaseView.class)
	@NotNull
	@Min(1)
	@Max(5)
	@Column(nullable = false, updatable = false)
	private Integer score;

	/**
	 * 内容
	 */
	@JsonView(BaseView.class)
	@Length(max = 200)
	@Column(updatable = false)
	private String content;

	/**
	 * 是否显示
	 */
	@Column(nullable = false)
	private Boolean isShow;

	/**
	 * IP
	 */
	@Column(nullable = false, updatable = false)
	private String ip;

	/**
	 * 会员
	 */
	@JsonView(BaseView.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Member member;

	/**
	 * 商品
	 */
	@JsonView(BaseView.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Product product;

	/**
	 * 店铺
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Store store;

	/**
	 * 回复
	 */
	@JsonView(BaseView.class)
	@OneToMany(mappedBy = "forReview", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createdDate asc")
	private Set<Review> replyReviews = new HashSet<>();

	/**
	 * 评论
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false)
	private Review forReview;

	/**
	 * 规格
	 */
	@JsonView(BaseView.class)
	@Column(updatable = false, length = 4000)
	@Convert(converter = SpecificationConverter.class)
	private List<String> specifications = new ArrayList<>();

	/**
	 * 获取评分
	 * 
	 * @return 评分
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * 设置评分
	 * 
	 * @param score
	 *            评分
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容
	 * 
	 * @param content
	 *            内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取是否显示
	 * 
	 * @return 是否显示
	 */
	public Boolean getIsShow() {
		return isShow;
	}

	/**
	 * 设置是否显示
	 * 
	 * @param isShow
	 *            是否显示
	 */
	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	/**
	 * 获取IP
	 * 
	 * @return IP
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * 设置IP
	 * 
	 * @param ip
	 *            IP
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 获取会员
	 * 
	 * @return 会员
	 */
	public Member getMember() {
		return member;
	}

	/**
	 * 设置会员
	 * 
	 * @param member
	 *            会员
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * 设置商品
	 * 
	 * @param product
	 *            商品
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * 获取店铺
	 * 
	 * @return 店铺
	 */
	public Store getStore() {
		return store;
	}

	/**
	 * 设置店铺
	 * 
	 * @param store
	 *            店铺
	 */
	public void setStore(Store store) {
		this.store = store;
	}

	/**
	 * 获取回复
	 * 
	 * @return 回复
	 */
	public Set<Review> getReplyReviews() {
		return replyReviews;
	}

	/**
	 * 设置回复
	 * 
	 * @param replyReviews
	 *            回复
	 */
	public void setReplyReviews(Set<Review> replyReviews) {
		this.replyReviews = replyReviews;
	}

	/**
	 * 获取评论
	 * 
	 * @return 评论
	 */
	public Review getForReview() {
		return forReview;
	}

	/**
	 * 设置评论
	 * 
	 * @param forReview
	 *            评论
	 */
	public void setForReview(Review forReview) {
		this.forReview = forReview;
	}

	/**
	 * 获取规格
	 * 
	 * @return 规格
	 */
	public List<String> getSpecifications() {
		return specifications;
	}

	/**
	 * 设置规格
	 * 
	 * @param specifications
	 *            规格
	 */
	public void setSpecifications(List<String> specifications) {
		this.specifications = specifications;
	}

	/**
	 * 获取路径
	 * 
	 * @return 路径
	 */
	@JsonView(BaseView.class)
	@Transient
	public String getPath() {
		return String.format(Review.PATH, getProduct().getId());
	}

	/**
	 * 类型转换 - 规格
	 * 
	 * @author huanghy
	 * @version 6.1
	 */
	@Converter
	public static class SpecificationConverter extends BaseAttributeConverter<List<String>> {
	}

	/**
	 * Entity - 条目
	 * 
	 * @author huanghy
	 * @version 6.1
	 */
	public static class Entry implements Serializable {

		private static final long serialVersionUID = 4603334970331213066L;

		/**
		 * 订单项
		 */
		@NotNull
		private OrderItem orderItem;

		/**
		 * 评论
		 */
		@NotNull
		private Review review;

		/**
		 * 获取订单项
		 * 
		 * @return 订单项
		 */
		public OrderItem getOrderItem() {
			return orderItem;
		}

		/**
		 * 设置订单项
		 * 
		 * @param orderItem
		 *            订单项
		 */
		public void setOrderItem(OrderItem orderItem) {
			this.orderItem = orderItem;
		}

		/**
		 * 获取评论
		 * 
		 * @return 评论
		 */
		public Review getReview() {
			return review;
		}

		/**
		 * 设置评论
		 * 
		 * @param review
		 *            评论
		 */
		public void setReview(Review review) {
			this.review = review;
		}

		/**
		 * 重写equals方法
		 * 
		 * @param obj
		 *            对象
		 * @return 是否相等
		 */
		@Override
		public boolean equals(Object obj) {
			return EqualsBuilder.reflectionEquals(this, obj);
		}

		/**
		 * 重写hashCode方法
		 * 
		 * @return HashCode
		 */
		@Override
		public int hashCode() {
			return HashCodeBuilder.reflectionHashCode(this);
		}

	}

}