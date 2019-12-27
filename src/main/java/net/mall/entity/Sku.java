/*
 *
 * 
 *
 * 
 */
package net.mall.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.NumericField;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonView;

import net.mall.BaseAttributeConverter;
import net.mall.Setting;
import net.mall.entity.Specification.Sample;
import net.mall.plugin.GiftPromotionPlugin.GiftAttribute;
import net.mall.util.SystemUtils;

/**
 * Entity - SKU
 * 
 * @author huanghy
 * @version 6.1
 */
@Entity
public class Sku extends BaseEntity<Long> {

	private static final long serialVersionUID = 2167830430439593293L;

	/**
	 * 普通商品验证组
	 */
	public interface General extends Default {

	}

	/**
	 * 兑换商品验证组
	 */
	public interface Exchange extends Default {

	}

	/**
	 * 赠品验证组
	 */
	public interface Gift extends Default {

	}

	/**
	 * 编号
	 */
	@JsonView(BaseView.class)
	@Column(nullable = false, updatable = false, unique = true)
	private String sn;

	/**
	 * 销售价
	 */
	@JsonView(BaseView.class)
	@NotNull(groups = General.class)
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, precision = 21, scale = 6)
	private BigDecimal price;

	/**
	 * 成本价
	 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(precision = 21, scale = 6)
	private BigDecimal cost;

	/**
	 * 市场价
	 */
	@JsonView(BaseView.class)
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, precision = 21, scale = 6)
	private BigDecimal marketPrice;

	/**
	 * 最大佣金
	 */
	@NotNull(groups = General.class)
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, precision = 21, scale = 6)
	private BigDecimal maxCommission;

	/**
	 * 赠送积分
	 */
	@JsonView(BaseView.class)
	@Min(0)
	@Column(nullable = false)
	private Long rewardPoint;

	/**
	 * 兑换积分
	 */
	@JsonView(BaseView.class)
	@NotNull(groups = Exchange.class)
	@Min(0)
	@Column(nullable = false)
	private Long exchangePoint;

	/**
	 * 库存
	 */
	@NotNull(groups = Save.class)
	@Min(0)
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NumericField
	@Column(nullable = false)
	private Integer stock;
	
	
	
	/**
	 * 单位
	 */
	@Field(store = Store.YES, index = Index.NO, analyze = Analyze.NO)
	@Length(max = 200)
	private String unit;
	
	
	
	/**
	 * 单位
	 */
	@Field(store = Store.YES, index = Index.NO, analyze = Analyze.NO)
	@NotNull(groups = General.class)
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(name="total_unit",nullable = false, precision = 21, scale = 6)
	private BigDecimal totalUnit;

	/**
	 * 已分配库存
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NumericField
	@Column(nullable = false)
	private Integer allocatedStock;

	/**
	 * 是否默认
	 */
	@NotNull
	@Column(nullable = false)
	private Boolean isDefault;

	/**
	 * 商品
	 */
	@ContainedIn
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, updatable = false)
	private Product product;

	/**
	 * 规格值
	 */
	@JsonView(BaseView.class)
	@Valid
	@Column(length = 4000)
	@Convert(converter = SpecificationValueConverter.class)
	private List<SpecificationValue> specificationValues = new ArrayList<>();

	/**
	 * 购物车项
	 */
	@OneToMany(mappedBy = "sku", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<CartItem> cartItems = new HashSet<>();

	/**
	 * 订单项
	 */
	@OneToMany(mappedBy = "sku", fetch = FetchType.LAZY)
	private Set<OrderItem> orderItems = new HashSet<>();

	/**
	 * 订单发货项
	 */
	@OneToMany(mappedBy = "sku", fetch = FetchType.LAZY)
	private Set<OrderShippingItem> orderShippingItems = new HashSet<>();

	/**
	 * 到货通知
	 */
	@OneToMany(mappedBy = "sku", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<ProductNotify> productNotifies = new HashSet<>();

	/**
	 * 库存记录
	 */
	@OneToMany(mappedBy = "sku", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<StockLog> stockLogs = new HashSet<>();

	/**
	 * 关联赠品属性
	 */
	@ManyToMany(mappedBy = "gifts", fetch = FetchType.LAZY)
	private Set<GiftAttribute> giftAttributes = new HashSet<>();
	
	
	/****是否样品
	 */
	private Sample sample;


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
	 * 获取销售价
	 * 
	 * @return 销售价
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * 设置销售价
	 * 
	 * @param price
	 *            销售价
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * 获取成本价
	 * 
	 * @return 成本价
	 */
	public BigDecimal getCost() {
		return cost;
	}

	/**
	 * 设置成本价
	 * 
	 * @param cost
	 *            成本价
	 */
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	/**
	 * 获取市场价
	 * 
	 * @return 市场价
	 */
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	/**
	 * 设置市场价
	 * 
	 * @param marketPrice
	 *            市场价
	 */
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	/**
	 * 获取最大佣金
	 * 
	 * @return 最大佣金
	 */
	public BigDecimal getMaxCommission() {
		return maxCommission;
	}

	/**
	 * 设置最大佣金
	 * 
	 * @param maxCommission
	 *            最大佣金
	 */
	public void setMaxCommission(BigDecimal maxCommission) {
		this.maxCommission = maxCommission;
	}

	/**
	 * 获取赠送积分
	 * 
	 * @return 赠送积分
	 */
	public Long getRewardPoint() {
		return rewardPoint;
	}

	/**
	 * 设置赠送积分
	 * 
	 * @param rewardPoint
	 *            赠送积分
	 */
	public void setRewardPoint(Long rewardPoint) {
		this.rewardPoint = rewardPoint;
	}

	/**
	 * 获取兑换积分
	 * 
	 * @return 兑换积分
	 */
	public Long getExchangePoint() {
		return exchangePoint;
	}

	/**
	 * 设置兑换积分
	 * 
	 * @param exchangePoint
	 *            兑换积分
	 */
	public void setExchangePoint(Long exchangePoint) {
		this.exchangePoint = exchangePoint;
	}

	/**
	 * 获取库存
	 * 
	 * @return 库存
	 */
	public Integer getStock() {
		return stock;
	}

	/**
	 * 设置库存
	 * 
	 * @param stock
	 *            库存
	 */
	public void setStock(Integer stock) {
		this.stock = stock;
	}

	/**
	 * 获取已分配库存
	 * 
	 * @return 已分配库存
	 */
	public Integer getAllocatedStock() {
		return allocatedStock;
	}

	/**
	 * 设置已分配库存
	 * 
	 * @param allocatedStock
	 *            已分配库存
	 */
	public void setAllocatedStock(Integer allocatedStock) {
		this.allocatedStock = allocatedStock;
	}

	/**
	 * 获取是否默认
	 * 
	 * @return 是否默认
	 */
	public Boolean getIsDefault() {
		return isDefault;
	}
	
	
	

	/** 
	 * @return totalUnit 
	 */
	public BigDecimal getTotalUnit() {
		return totalUnit;
	}

	/** 
	 * @param totalUnit 要设置的 totalUnit 
	 */
	public void setTotalUnit(BigDecimal totalUnit) {
		this.totalUnit = totalUnit;
	}

	/** 
	 * @param unit 要设置的 unit 
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * 设置是否默认
	 * 
	 * @param isDefault
	 *            是否默认
	 */
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
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
	 * 获取规格值
	 * 
	 * @return 规格值
	 */
	public List<SpecificationValue> getSpecificationValues() {
		return specificationValues;
	}

	/**
	 * 设置规格值
	 * 
	 * @param specificationValues
	 *            规格值
	 */
	public void setSpecificationValues(List<SpecificationValue> specificationValues) {
		this.specificationValues = specificationValues;
	}

	/**
	 * 获取购物车项
	 * 
	 * @return 购物车项
	 */
	public Set<CartItem> getCartItems() {
		return cartItems;
	}

	/**
	 * 设置购物车项
	 * 
	 * @param cartItems
	 *            购物车项
	 */
	public void setCartItems(Set<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	/**
	 * 获取订单项
	 * 
	 * @return 订单项
	 */
	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	/**
	 * 设置订单项
	 * 
	 * @param orderItems
	 *            订单项
	 */
	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	/**
	 * 获取订单发货项
	 * 
	 * @return 订单发货项
	 */
	public Set<OrderShippingItem> getOrderShippingItems() {
		return orderShippingItems;
	}

	/**
	 * 设置订单发货项
	 * 
	 * @param orderShippingItems
	 *            订单发货项
	 */
	public void setOrderShippingItems(Set<OrderShippingItem> orderShippingItems) {
		this.orderShippingItems = orderShippingItems;
	}

	/**
	 * 获取到货通知
	 * 
	 * @return 到货通知
	 */
	public Set<ProductNotify> getProductNotifies() {
		return productNotifies;
	}

	/**
	 * 设置到货通知
	 * 
	 * @param productNotifies
	 *            到货通知
	 */
	public void setProductNotifies(Set<ProductNotify> productNotifies) {
		this.productNotifies = productNotifies;
	}

	/**
	 * 获取库存记录
	 * 
	 * @return 库存记录
	 */
	public Set<StockLog> getStockLogs() {
		return stockLogs;
	}

	/**
	 * 设置库存记录
	 * 
	 * @param stockLogs
	 *            库存记录
	 */
	public void setStockLogs(Set<StockLog> stockLogs) {
		this.stockLogs = stockLogs;
	}

	/**
	 * 获取关联赠品属性
	 * 
	 * @return 关联赠品属性
	 */
	public Set<GiftAttribute> getGiftAttributes() {
		return giftAttributes;
	}

	/**
	 * 设置关联赠品属性
	 * 
	 * @param giftAttributes
	 *            关联赠品属性
	 */
	public void setGiftAttributes(Set<GiftAttribute> giftAttributes) {
		this.giftAttributes = giftAttributes;
	}

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@JsonView(BaseView.class)
	@Transient
	public String getName() {
		return getProduct() != null ? getProduct().getName() : null;
	}

	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	@JsonView(BaseView.class)
	@Transient
	public Product.Type getType() {
		return getProduct() != null ? getProduct().getType() : null;
	}

	/**
	 * 获取单位
	 * 
	 * @return 单位
	 */
	@Transient
	public String getUnit() {
		return getProduct() != null ? getProduct().getUnit() : null;
	}
	
	
	/***
	 * getSkuUnit方法慨述:获取库存单位
	 * @return String
	 * @创建人 huanghy
	 * @创建时间 2019年12月27日 下午12:37:57
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
	@Transient
	public String getSkuUnit() {
		return unit;
	}

	/**
	 * 获取重量
	 * 
	 * @return 重量
	 */
	@Transient
	public Integer getWeight() {
		return getProduct() != null ? getProduct().getWeight() : null;
	}

	/**
	 * 获取是否有效
	 * 
	 * @return 是否有效
	 */
	@Transient
	public boolean getIsActive() {
		return getProduct() != null && BooleanUtils.isTrue(getProduct().getIsActive());
	}

	/**
	 * 获取是否上架
	 * 
	 * @return 是否上架
	 */
	@Transient
	public boolean getIsMarketable() {
		return getProduct() != null && BooleanUtils.isTrue(getProduct().getIsMarketable());
	}

	/**
	 * 获取是否列出
	 * 
	 * @return 是否列出
	 */
	@Transient
	public boolean getIsList() {
		return getProduct() != null && BooleanUtils.isTrue(getProduct().getIsList());
	}

	/**
	 * 获取是否置顶
	 * 
	 * @return 是否置顶
	 */
	@Transient
	public boolean getIsTop() {
		return getProduct() != null && BooleanUtils.isTrue(getProduct().getIsTop());
	}

	/**
	 * 获取是否需要物流
	 * 
	 * @return 是否需要物流
	 */
	@Transient
	public boolean getIsDelivery() {
		return getProduct() != null && BooleanUtils.isTrue(getProduct().getIsDelivery());
	}

	/**
	 * 获取路径
	 * 
	 * @return 路径
	 */
	@JsonView(BaseView.class)
	@Transient
	public String getPath() {
		return getProduct() != null ? getProduct().getPath() : null;
	}

	/**
	 * 获取缩略图
	 * 
	 * @return 缩略图
	 */
	@JsonView(BaseView.class)
	@Transient
	public String getThumbnail() {
		return getProduct() != null ? getProduct().getThumbnail() : null;
	}

	/**
	 * 获取店铺
	 * 
	 * @return 店铺
	 */
	@Transient
	public net.mall.entity.Store getStore() {
		return getProduct() != null ? getProduct().getStore() : null;
	}
	
	
	
	/**获取代销店铺
	 * @return 店铺
	 */
	@Transient
	public net.mall.entity.Store getSampleStore() {
		return getProduct() != null ? getProduct().getSampleStore() : null;
	}

	/**
	 * 获取可用库存
	 * 
	 * @return 可用库存
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NumericField
	@Transient
	public int getAvailableStock() {
		int availableStock = getStock() - getAllocatedStock();
		return availableStock >= 0 ? availableStock : 0;
	}
	
	
	

	/** 
	 * @return sample 
	 */
	public Sample getSample() {
		return sample;
	}

	/** 
	 * @param sample 要设置的 sample 
	 */
	public void setSample(Sample sample) {
		this.sample = sample;
	}

	/**
	 * 获取是否库存警告
	 * 
	 * @return 是否库存警告
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@Transient
	public boolean getIsStockAlert() {
		Setting setting = SystemUtils.getSetting();
		return setting.getStockAlertCount() != null && getAvailableStock() <= setting.getStockAlertCount();
	}

	/**
	 * 获取是否缺货
	 * 
	 * @return 是否缺货
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@Transient
	public boolean getIsOutOfStock() {
		return getAvailableStock() <= 0;
	}

	/**
	 * 获取规格值ID
	 * 
	 * @return 规格值ID
	 */
	@Transient
	public List<Integer> getSpecificationValueIds() {
		List<Integer> specificationValueIds = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(getSpecificationValues())) {
			for (SpecificationValue specificationValue : getSpecificationValues()) {
				specificationValueIds.add(specificationValue.getId());
			}
		}
		return specificationValueIds;
	}

	/**
	 * 获取规格
	 * 
	 * @return 规格
	 */
	@Transient
	public List<String> getSpecifications() {
		List<String> specifications = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(getSpecificationValues())) {
			for (SpecificationValue specificationValue : getSpecificationValues()) {
				specifications.add(specificationValue.getValue());
			}
		}
		return specifications;
	}

	/**
	 * 获取有效促销
	 * 
	 * @return 有效促销
	 */
	@Transient
	public Set<Promotion> getValidPromotions() {
		return getProduct() != null ? new HashSet<>(getProduct().getValidPromotions()) : Collections.<Promotion>emptySet();
	}

	/**
	 * 是否存在规格
	 * 
	 * @return 是否存在规格
	 */
	@Transient
	public boolean hasSpecification() {
		return CollectionUtils.isNotEmpty(getSpecificationValues());
	}

	/**
	 * 判断促销是否有效
	 * 
	 * @param promotion
	 *            促销
	 * @return 促销是否有效
	 */
	@Transient
	public boolean isValid(Promotion promotion) {
		return getProduct() != null ? getProduct().isValid(promotion) : false;
	}

	/**
	 * 持久化前处理
	 */
	@PrePersist
	public void prePersist() {
		setSn(StringUtils.lowerCase(getSn()));
	}

	/**
	 * 获取平台佣金
	 * 
	 * @param type
	 *            类型
	 * @return 平台佣金
	 */
	@Transient
	public BigDecimal getPlatformCommission(net.mall.entity.Store.Type type) {
		BigDecimal commission = BigDecimal.ZERO;
		if (type != null && getProduct() != null && getProduct().getProductCategory() != null) {
			ProductCategory productCategory = getProduct().getProductCategory();
			if (net.mall.entity.Store.Type.GENERAL.equals(type) && productCategory.getGeneralRate() > 0) {
				commission = getPrice().multiply(new BigDecimal(String.valueOf(productCategory.getGeneralRate())).movePointLeft(2));
			} else if (net.mall.entity.Store.Type.SELF.equals(type) && productCategory.getSelfRate() > 0) {
				commission = getPrice().multiply(new BigDecimal(String.valueOf(productCategory.getSelfRate())).movePointLeft(2));
			}
		}
		return commission;
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Set<OrderItem> orderItems = getOrderItems();
		if (orderItems != null) {
			for (OrderItem orderItem : orderItems) {
				orderItem.setSku(null);
			}
		}
		Set<OrderShippingItem> orderShippingItems = getOrderShippingItems();
		if (orderShippingItems != null) {
			for (OrderShippingItem orderShippingItem : getOrderShippingItems()) {
				orderShippingItem.setSku(null);
			}
		}
	}

	/**
	 * 类型转换 - 规格值
	 * 
	 * @author huanghy
	 * @version 6.1
	 */
	@Converter
	public static class SpecificationValueConverter extends BaseAttributeConverter<List<SpecificationValue>> {
	}


	@Transient
	public String getSpecificationVal(){
		List<SpecificationValue> specificationValues = getSpecificationValues();
		StringBuilder specValSb =  new StringBuilder();
		specificationValues.forEach(specificationValue -> {
			specValSb.append(specificationValue.getValue());
			specValSb.append("*");
		});
		if(specValSb.length() > 1){
			return specValSb.toString().substring(0,specValSb.toString().lastIndexOf("*"));
		} else {
			return specValSb.toString();
		}
	}
}