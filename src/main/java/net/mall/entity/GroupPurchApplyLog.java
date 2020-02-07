package net.mall.entity;

import net.mall.service.SkuService;
import net.mall.util.SpringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/***
 * 团购下单日志
 */
@Entity
@Table(name = "group_purch_apply_log")
public class GroupPurchApplyLog extends BaseEntity<Long> {

    private static final long serialVersionUID = 296118160291884402L;


    @Column(nullable = false,name="store_id")
    private Long storeId;

    @Column(nullable = false,name="store_name")
    private String storeName;

    @Column(nullable = false,name="pro_sn")
    private String proSn;

    @Column(name="pro_name")
    private String proName;

    @Column(name="sku_sn")
    private String skuSn;

    @Column(nullable = false,name="member_id")
    private Long memberId;

    @Column(nullable = false,name="member_name")
    private String memberName;

    @Column(name="remarks")
    private String remarks;

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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    @Transient
    public Sku getSku() {
        SkuService skuService = SpringUtils.getBean(SkuService.class);
        Sku sku = skuService.findBySn(this.skuSn);
        return sku;
    }
}
