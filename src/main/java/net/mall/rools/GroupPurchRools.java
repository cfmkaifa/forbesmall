package net.mall.rools;

import net.mall.entity.*;
import net.mall.service.GroupPurchApplyLogService;
import net.mall.util.ConvertUtils;
import net.mall.util.SpringUtils;

import java.io.Serializable;

/***
 *团购申请规则
 */
public class GroupPurchRools implements Serializable {

    private static final long serialVersionUID = -8742466068527685553L;

    /**总量
     */
    private Long  totalWeight;

    /***总人数
     */
    private Integer  totalCount;

    /****消息
     */
    private String message;


    private  boolean isCreate = true;

    private Store   store;

    private Product product;

    private Sku sku;

    private Member member;


    /***
     *
     * @param state
     */
    public void incoreMessage(int state,String current){
        switch (state){
            case 0:
                message = "起订重量:"+current+"用户下单总重量为:" + this.totalWeight;
                break;
            case 1:
                message = "限制重量:"+current+"用户下单总重量为:" + this.totalWeight;
                break;
            case 2:
                message = "此团购当前下单总人数:"+this.totalCount;
                break;
        }
    }

    /***增加日志
     */
    public void insertGroupPurchApplyLog(){
        GroupPurchApplyLogService groupPurchApplyLogService = SpringUtils.getBean(GroupPurchApplyLogService.class);
        if(ConvertUtils.isNotEmpty(groupPurchApplyLogService)){
            GroupPurchApplyLog  groupPurchApplyLog = new GroupPurchApplyLog();
            groupPurchApplyLog.setStoreId(this.store.getId());
            groupPurchApplyLog.setStoreName(this.store.getName());
            groupPurchApplyLog.setProSn(this.product.getSn());
            groupPurchApplyLog.setProName(this.product.getName());
            groupPurchApplyLog.setSkuSn(this.sku.getSn());
            groupPurchApplyLog.setMemberId(this.member.getId());
            groupPurchApplyLog.setMemberName(this.member.getName());
            groupPurchApplyLog.setRemarks(this.message);
            groupPurchApplyLogService.save(groupPurchApplyLog);
        }
        this.isCreate = false;
    }


    public Long getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Long totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCreate() {
        return isCreate;
    }

    public void setCreate(boolean create) {
        isCreate = create;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
