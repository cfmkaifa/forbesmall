package net.mall.entity;

import net.mall.service.SkuService;
import net.mall.util.SpringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "pro_purch_apply")
public class ProPurchApply  extends BaseEntity<Long>{


    private static final long serialVersionUID = 7393486158076413374L;

    /**
     * 状态
     */
    public enum ProApplyStatus {

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
    private ProPurchApply.ProApplyStatus status;


    @Column(nullable = false,name="member_id")
    private Long memberId;

    @Column(nullable = false,name="member_name")
    private String membeName;

    @Column(nullable = false,name="pro_sn")
    private String proSn;

    @Column(name="pro_name")
    private String proName;

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

    @Min(0)
    @Digits(integer = 12, fraction = 3)
    @Column(precision = 21, scale = 6,name = "start_price")
    private BigDecimal startPrice;


    @Min(0)
    @Digits(integer = 12, fraction = 3)
    @Column(precision = 21, scale = 6,name = "end_price")
    private BigDecimal endPrice;



    @Transient
    public Sku getSku() {
        SkuService skuService = SpringUtils.getBean(SkuService.class);
        Sku sku = skuService.findBySn(this.skuSn);
        return sku;
    }

    public ProApplyStatus getStatus() {
        return status;
    }

    public void setStatus(ProApplyStatus status) {
        this.status = status;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMembeName() {
        return membeName;
    }

    public void setMembeName(String membeName) {
        this.membeName = membeName;
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

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public BigDecimal getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(BigDecimal endPrice) {
        this.endPrice = endPrice;
    }
}
