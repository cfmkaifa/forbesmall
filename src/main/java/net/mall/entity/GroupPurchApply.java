package net.mall.entity;

import net.mall.service.SkuService;
import net.mall.util.SpringUtils;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Date;
/***
 * SubsNewsHuman概要说明：团购申请
 * @author Huanghy
 */
@Entity
@Table(name = "group_purch_apply")
public class GroupPurchApply extends BaseEntity<Long>{


	private static final long serialVersionUID = 1066463291338794126L;

	/**
	 * 状态
	 */
	public enum ApplyStatus {

		/**
		 * 等待审核
		 */
		PENDING,

		/**
		 * 审核通过
		 */
		APPROVED,

		/**
		 * 审核失败
		 */
		FAILED
	}

	/**
	 * 状态
	 */
	@Column(nullable = false,name="apply_status")
	private GroupPurchApply.ApplyStatus status;
	
	@Column(nullable = false,name="store_id")
	private Long storeId;
	
	@Column(nullable = false,name="store_name")
	private String storeName;
	
	@Column(nullable = false,name="pro_sn")
	private String proSn;
	
	@Column(name="pro_name")
	private String proName;



	@Transient
	public Sku getSku() {
		SkuService skuService = SpringUtils.getBean(SkuService.class);
		Sku sku = skuService.findBySn(this.skuSn);
		return sku;
	}

	@Column(name="sku_sn")
	private String skuSn;

	/***
	 * 
	 */
	@Column(name="start_time")
	private Date startTime;


	@Column(name="end_time")
	private Date endTime;

	@Column(name="remarks")
	private String remarks;

	/**
	 * 重量
	 */
	@NumericField
	@Min(0)
	@Column(name="mqq_weight")
	private Integer mqqWeight;

	/**
	 * 重量
	 */
	@NumericField
	@Min(1)
	@Column(name="mqq_people")
	private Integer mqqPeople;

	/**
	 * 重量
	 */
	@NumericField
	@Column(name="limit_weight")
	private Integer limitWeight;

	/**
	 * 重量
	 */
	@NumericField
	@Column(name="limit_people")
	private Integer limitPeople;

	public BigDecimal getGroupPrice() {
		return groupPrice;
	}

	public void setGroupPrice(BigDecimal groupPrice) {
		this.groupPrice = groupPrice;
	}

	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(precision = 21, scale = 6,name = "group_price")
	private BigDecimal groupPrice;


	public ApplyStatus getStatus() {
		return status;
	}

	public void setStatus(ApplyStatus status) {
		this.status = status;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getProSn() {
		return proSn;
	}

	public void setProSn(String proSn) {
		this.proSn = proSn;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getSkuSn() {
		return skuSn;
	}

	public void setSkuSn(String skuSn) {
		this.skuSn = skuSn;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getMqqWeight() {
		return mqqWeight;
	}

	public void setMqqWeight(Integer mqqWeight) {
		this.mqqWeight = mqqWeight;
	}

	public Integer getMqqPeople() {
		return mqqPeople;
	}

	public void setMqqPeople(Integer mqqPeople) {
		this.mqqPeople = mqqPeople;
	}

	public Integer getLimitWeight() {
		return limitWeight;
	}

	public void setLimitWeight(Integer limitWeight) {
		this.limitWeight = limitWeight;
	}

	public Integer getLimitPeople() {
		return limitPeople;
	}

	public void setLimitPeople(Integer limitPeople) {
		this.limitPeople = limitPeople;
	}
}
