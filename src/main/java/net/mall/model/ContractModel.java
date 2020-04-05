package net.mall.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***采购合同
 */
public class ContractModel implements Serializable {

    private static final long serialVersionUID = -7943269752279090011L;

    /***
     * 公司标题
     */
    protected  String companyTtile;
    /***
     * 卖方
     * */
    protected  String sellerName;
    /***买方
     * */
    protected  String buyName;
    /***
     * 订单号
     */
    protected  String orderNo;
    /****
     * 商品明细
     * ***/
    protected List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
    /***订单金额
     * **/
    protected BigDecimal orderTotalAmount;
    /*********采购商*******/
    protected String memberName;
    protected String memberLegalPerson;
    protected String memberTaxNo;
    protected String memberBankAddress;
    protected String memberDate;
    protected String memberAddress;
    protected String memberPhone;
    protected  String memberBankName;
    protected  String memberBankAccount;
    /********供应商********/
    protected String storeName;
    protected String storeLegalPerson;
    protected String storeTaxNo;
    protected String storeBankAddress;
    protected String storeAdress;
    protected String storePhone;
    protected String storeBankName;
    protected String storeBankAccount;
    protected String storeDate;


    public ContractModel setCompanyTtile(String companyTtile) {
        this.companyTtile = companyTtile;
        return this;
    }

    public ContractModel setSellerName(String sellerName) {
        this.sellerName = sellerName;
        return this;
    }

    public ContractModel setBuyName(String buyName) {
        this.buyName = buyName;
        return this;
    }

    public ContractModel setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public ContractModel setItems(List<Map<String, Object>> items) {
        this.items = items;
        return this;
    }

    public ContractModel setMemberName(String memberName) {
        this.memberName = memberName;
        return this;
    }

    public ContractModel setMemberLegalPerson(String memberLegalPerson) {
        this.memberLegalPerson = memberLegalPerson;
        return this;
    }

    public ContractModel setMemberTaxNo(String memberTaxNo) {
        this.memberTaxNo = memberTaxNo;
        return this;
    }

    public ContractModel setMemberBankAddress(String memberBankAddress) {
        this.memberBankAddress = memberBankAddress;
        return this;
    }

    public ContractModel setMemberDate(String memberDate) {
        this.memberDate = memberDate;
        return this;
    }

    public ContractModel setMemberAddress(String memberAddress) {
        this.memberAddress = memberAddress;
        return this;
    }

    public ContractModel setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
        return this;
    }

    public ContractModel setMemberBankName(String memberBankName) {
        this.memberBankName = memberBankName;
        return this;
    }

    public ContractModel setMemberBankAccount(String memberBankAccount) {
        this.memberBankAccount = memberBankAccount;
        return this;
    }

    public ContractModel setStoreName(String storeName) {
        this.storeName = storeName;
        return this;
    }

    public ContractModel setStoreLegalPerson(String storeLegalPerson) {
        this.storeLegalPerson = storeLegalPerson;
        return this;
    }

    public ContractModel setStoreTaxNo(String storeTaxNo) {
        this.storeTaxNo = storeTaxNo;
        return this;
    }

    public ContractModel setStoreBankAddress(String storeBankAddress) {
        this.storeBankAddress = storeBankAddress;
        return this;
    }

    public ContractModel setStoreAdress(String storeAdress) {
        this.storeAdress = storeAdress;
        return this;
    }

    public ContractModel setStorePhone(String storePhone) {
        this.storePhone = storePhone;
        return this;
    }

    public ContractModel setStoreBankName(String storeBankName) {
        this.storeBankName = storeBankName;
        return this;
    }

    public ContractModel setStoreBankAccount(String storeBankAccount) {
        this.storeBankAccount = storeBankAccount;
        return this;
    }

    public ContractModel setStoreDate(String storeDate) {
        this.storeDate = storeDate;
        return this;
    }

    public ContractModel setOrderTotalAmount(BigDecimal orderTotalAmount) {
        this.orderTotalAmount = orderTotalAmount;
        return this;
    }
}
