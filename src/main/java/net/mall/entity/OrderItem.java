/*
 *
 * 
 *
 * 
 */
package net.mall.entity;

import java.math.BigDecimal;
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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;

import net.mall.BaseAttributeConverter;

/**
 * Entity - 订单项
 * 
 * @author huanghy
 * @version 6.1
 */
@Entity
public class OrderItem extends BaseEntity<Long> {

	private static final long serialVersionUID = -4999926022604479334L;

	/**
	 * 编号
	 */
	@Column(nullable = false, updatable = false)
	private String sn;

	/**
	 * 名称
	 */
	@JsonView(BaseView.class)
	@Column(nullable = false, updatable = false)
	private String name;

	/**
	 * 类型
	 */
	@JsonView(BaseView.class)
	@Column(nullable = false, updatable = false)
	private Product.Type type;

	/**
	 * 价格
	 */
	@JsonView(BaseView.class)
	@Column(nullable = false, updatable = false, precision = 21, scale = 6)
	private BigDecimal price;

	/**
	 * 重量
	 */
	@Column(updatable = false)
	private Integer weight;

	/**
	 * 是否需要物流
	 */
	@Column(nullable = false, updatable = false)
	private Boolean isDelivery;

	/**
	 * 缩略图
	 */
	@JsonView(BaseView.class)
	@Column(updatable = false)
	private String thumbnail;

	/**
	 * 数量
	 */
	@Column(nullable = false, updatable = false)
	private Integer quantity;

	/**
	 * 已发货数量
	 */
	@Column(nullable = false)
	private Integer shippedQuantity;

	/**
	 * 已退货数量
	 */
	@Column(nullable = false)
	private Integer returnedQuantity;

	/**
	 * 平台佣金总计
	 */
	@Column(nullable = false, precision = 21, scale = 6)
	private BigDecimal platformCommissionTotals;

	/**
	 * 分销佣金小计
	 */
	@Column(nullable = false, precision = 21, scale = 6)
	private BigDecimal distributionCommissionTotals;

	/**
	 * SKU
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Sku sku;

	/**
	 * 订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orders", nullable = false, updatable = false)
	private Order order;

	/**
	 * 售后项
	 */
	@OneToMany(mappedBy = "orderItem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<AftersalesItem> aftersalesItems = new HashSet<>();

	/**
	 * 规格
	 */
	@JsonView(BaseView.class)
	@Column(updatable = false, length = 4000)
	@Convert(converter = SpecificationConverter.class)
	private List<String> specifications = new ArrayList<>();

	/**
	 * 获取编号
	 * 
	 * @return 编号
	 */
	public String getSn() {
		return sn;
	}

	/**
	 * 设置编号
	 * 
	 * @param sn
	 *            编号
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	public Product.Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(Product.Type type) {
		this.type = type;
	}

	/**
	 * 获取价格
	 * 
	 * @return 价格
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * 设置价格
	 * 
	 * @param price
	 *            价格
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * 获取重量
	 * 
	 * @return 重量
	 */
	public Integer getWeight() {
		return weight;
	}

	/**
	 * 设置重量
	 * 
	 * @param weight
	 *            重量
	 */
	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	/**
	 * 获取是否需要物流
	 * 
	 * @return 是否需要物流
	 */
	public Boolean getIsDelivery() {
		return isDelivery;
	}

	/**
	 * 设置是否需要物流
	 * 
	 * @param isDelivery
	 *            是否需要物流
	 */
	public void setIsDelivery(Boolean isDelivery) {
		this.isDelivery = isDelivery;
	}

	/**
	 * 获取缩略图
	 * 
	 * @return 缩略图
	 */
	public String getThumbnail() {
		return thumbnail;
	}

	/**
	 * 设置缩略图
	 * 
	 * @param thumbnail
	 *            缩略图
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * 获取数量
	 * 
	 * @return 数量
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * 设置数量
	 * 
	 * @param quantity
	 *            数量
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * 获取已发货数量
	 * 
	 * @return 已发货数量
	 */
	public Integer getShippedQuantity() {
		return shippedQuantity;
	}

	/**
	 * 设置已发货数量
	 * 
	 * @param shippedQuantity
	 *            已发货数量
	 */
	public void setShippedQuantity(Integer shippedQuantity) {
		this.shippedQuantity = shippedQuantity;
	}

	/**
	 * 获取已退货数量
	 * 
	 * @return 已退货数量
	 */
	public Integer getReturnedQuantity() {
		return returnedQuantity;
	}

	/**
	 * 设置已退货数量
	 * 
	 * @param returnedQuantity
	 *            已退货数量
	 */
	public void setReturnedQuantity(Integer returnedQuantity) {
		this.returnedQuantity = returnedQuantity;
	}

	/**
	 * 获取平台佣金总计
	 * 
	 * @return 平台佣金总计
	 */
	public BigDecimal getPlatformCommissionTotals() {
		return platformCommissionTotals;
	}

	/**
	 * 设置平台佣金总计
	 * 
	 * @param platformCommissionTotals
	 *            平台佣金总计
	 */
	public void setPlatformCommissionTotals(BigDecimal platformCommissionTotals) {
		this.platformCommissionTotals = platformCommissionTotals;
	}

	/**
	 * 获取分销佣金小计
	 * 
	 * @return 分销佣金小计
	 */
	public BigDecimal getDistributionCommissionTotals() {
		return distributionCommissionTotals;
	}

	/**
	 * 设置分销佣金小计
	 * 
	 * @param distributionCommissionTotals
	 *            分销佣金小计
	 */
	public void setDistributionCommissionTotals(BigDecimal distributionCommissionTotals) {
		this.distributionCommissionTotals = distributionCommissionTotals;
	}

	/**
	 * 获取SKU
	 * 
	 * @return SKU
	 */
	public Sku getSku() {
		return sku;
	}

	/**
	 * 设置SKU
	 * 
	 * @param sku
	 *            SKU
	 */
	public void setSku(Sku sku) {
		this.sku = sku;
	}

	/**
	 * 获取订单
	 * 
	 * @return 订单
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * 设置订单
	 * 
	 * @param order
	 *            订单
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * 获取售后项
	 * 
	 * @return 售后项
	 */
	public Set<AftersalesItem> getAftersalesItems() {
		return aftersalesItems;
	}

	/**
	 * 设置售后项
	 * 
	 * @param aftersalesItems
	 *            售后项
	 */
	public void setAftersalesItems(Set<AftersalesItem> aftersalesItems) {
		this.aftersalesItems = aftersalesItems;
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
	 * 获取总重量
	 * 
	 * @return 总重量
	 */
	@Transient
	public int getTotalWeight() {
		if (getWeight() != null && getQuantity() != null) {
			return getWeight() * getQuantity();
		} else {
			return 0;
		}
	}

	/**
	 * 获取小计
	 * 
	 * @return 小计
	 */
	@Transient
	public BigDecimal getSubtotal() {
		if (getPrice() != null && getQuantity() != null) {
			return getPrice().multiply(new BigDecimal(getQuantity()));
		} else {
			return BigDecimal.ZERO;
		}
	}
	
	/***
	 * getProduct方法慨述:获取商品
	 * @return Product
	 * @创建人 huanghy
	 * @创建时间 2019年12月30日 下午1:35:08
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
	@Transient
	public Product getProduct(){
	  return  this.getSku().getProduct();
	}

	/**
	 * 获取可发货数
	 * 
	 * @return 可发货数
	 */
	@Transient
	public int getShippableQuantity() {
		int shippableQuantity = getQuantity() - getShippedQuantity();
		return shippableQuantity >= 0 ? shippableQuantity : 0;
	}

	/**
	 * 获取可退货数
	 * 
	 * @return 可退货数
	 */
	@Transient
	public int getReturnableQuantity() {
		int returnableQuantity = getShippedQuantity() - getReturnedQuantity();
		return returnableQuantity >= 0 ? returnableQuantity : 0;
	}

	/**
	 * 获取可申请售后数量
	 * 
	 * @return 可申请售后数量
	 */
	@Transient
	public int getAllowApplyAftersalesQuantity() {
		int allowApplyAftersalesQuantity = getQuantity();

		for (AftersalesItem aftersalesItem : getAftersalesItems()) {
			Aftersales aftersales = aftersalesItem.getAftersales();
			if (Aftersales.Status.PENDING.equals(aftersales.getStatus()) || Aftersales.Status.APPROVED.equals(aftersales.getStatus())) {
				allowApplyAftersalesQuantity -= aftersalesItem.getQuantity();
			} else if (Aftersales.Type.AFTERSALES_RETURNS.equals(aftersales.getType()) && Aftersales.Status.COMPLETED.equals(aftersales.getStatus())) {
				allowApplyAftersalesQuantity -= aftersalesItem.getQuantity();
			}
		}
		return allowApplyAftersalesQuantity;
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

}