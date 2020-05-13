package net.mall.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/***对账信息
 */
public class ReconContractModel implements Serializable {

    private static final long serialVersionUID = 2810700671156508336L;

    /***
     */
    protected String title;


    /**订单日期
     */
    protected String orderDate;

    /**订单号
     */
    protected String orderSn;

    /**单位
     */
    protected String unitName;


    /***
     * 商品信息
     */
    public static class ReconContractProduct{

        /***商品
         */
        private String proName;

        /***规格
         */
        private String specs;


        /***单价
         */
        private BigDecimal price;


        public String getProName() {
            return proName;
        }

        public void setProName(String proName) {
            this.proName = proName;
        }

        public String getSpecs() {
            return specs;
        }

        public void setSpecs(String specs) {
            this.specs = specs;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }
    }

    /***
     * 订单商品信息
     */
    protected List<ReconContractProduct> reconContractProducts;


    /***买方名称
     */
    protected String buyerName;

    /***卖方名称
     */
    protected String sellerName;


    /***
     * 收货地址
     */
    protected String receAddress;

    /**
     * 收货人
     */
    protected String receHuman;


    /***电话
     */
    protected String recePhone;


    /***总重量
     */
    protected String totalWeight;

    /***合同订单金额
     */
    protected BigDecimal orderTotalAmount;

    /***
     * 合同首付金额
     */
    protected BigDecimal orderAdownPayment;


    /*** 合同尾款金额
     */
    protected BigDecimal orderFinalAmount;


    /***发货日期
     */
    protected String deliveryDate;

    /***发货信息
     */
    public static class DeliveryPro {

        /***发货金额
         */
        private String proName;

        /***规格
         */
        private String spcecs;

        /***发货总重量
         */
        private String deliveryTotalWeight;

        /***单价
         */
        private BigDecimal price;


        public String getProName() {
            return proName;
        }

        public void setProName(String proName) {
            this.proName = proName;
        }

        public String getSpcecs() {
            return spcecs;
        }

        public void setSpcecs(String spcecs) {
            this.spcecs = spcecs;
        }

        public String getDeliveryTotalWeight() {
            return deliveryTotalWeight;
        }

        public void setDeliveryTotalWeight(String deliveryTotalWeight) {
            this.deliveryTotalWeight = deliveryTotalWeight;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }
    }

    /***发货信息
     */
    protected List<DeliveryPro> deliveryPros;

    /***
     * 运费
     */
    protected BigDecimal freight;

    /***
     * 发货应收金额
     */
    protected BigDecimal deliveryTotalAmount;

    /***
     * 实收货款
     */
    protected BigDecimal paidInAmount;

    /***实发货款金额
     */
    protected BigDecimal paidDeliveryAmount;

    /***差额
     */
    protected BigDecimal difAmount;


    /***订单状态
     */
    protected String orderStasuts;


    public String getTitle() {
        return title;
    }

    public ReconContractModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public ReconContractModel setOrderDate(String orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public ReconContractModel setOrderSn(String orderSn) {
        this.orderSn = orderSn;
        return this;
    }

    public List<ReconContractProduct> getReconContractProducts() {
        return reconContractProducts;
    }

    public ReconContractModel setReconContractProducts(List<ReconContractProduct> reconContractProducts) {
        this.reconContractProducts = reconContractProducts;
        return this;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public ReconContractModel setBuyerName(String buyerName) {
        this.buyerName = buyerName;
        return this;
    }

    public String getSellerName() {
        return sellerName;
    }

    public ReconContractModel setSellerName(String sellerName) {
        this.sellerName = sellerName;
        return this;
    }

    public String getReceAddress() {
        return receAddress;
    }

    public ReconContractModel setReceAddress(String receAddress) {
        this.receAddress = receAddress;
        return this;
    }

    public String getReceHuman() {
        return receHuman;
    }

    public ReconContractModel setReceHuman(String receHuman) {
        this.receHuman = receHuman;
        return this;
    }

    public String getRecePhone() {
        return recePhone;
    }

    public ReconContractModel setRecePhone(String recePhone) {
        this.recePhone = recePhone;
        return this;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public ReconContractModel setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
        return this;
    }


    public String getDeliveryDate() {
        return deliveryDate;
    }

    public ReconContractModel setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public List<DeliveryPro> getDeliveryPros() {
        return deliveryPros;
    }

    public ReconContractModel setDeliveryPros(List<DeliveryPro> deliveryPros) {
        this.deliveryPros = deliveryPros;
        return this;
    }


    public String getOrderStasuts() {
        return orderStasuts;
    }

    public ReconContractModel setOrderStasuts(String orderStasuts) {
        this.orderStasuts = orderStasuts;
        return this;
    }

    public BigDecimal getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public ReconContractModel setOrderTotalAmount(BigDecimal orderTotalAmount) {
        this.orderTotalAmount = orderTotalAmount;
        return this;
    }

    public BigDecimal getOrderAdownPayment() {
        return orderAdownPayment;
    }

    public ReconContractModel setOrderAdownPayment(BigDecimal orderAdownPayment) {
        this.orderAdownPayment = orderAdownPayment;
        return this;
    }

    public BigDecimal getOrderFinalAmount() {
        return orderFinalAmount;
    }

    public ReconContractModel setOrderFinalAmount(BigDecimal orderFinalAmount) {
        this.orderFinalAmount = orderFinalAmount;
        return this;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public ReconContractModel setFreight(BigDecimal freight) {
        this.freight = freight;
        return this;
    }

    public BigDecimal getDeliveryTotalAmount() {
        return deliveryTotalAmount;
    }

    public ReconContractModel setDeliveryTotalAmount(BigDecimal deliveryTotalAmount) {
        this.deliveryTotalAmount = deliveryTotalAmount;
        return this;
    }

    public BigDecimal getPaidInAmount() {
        return paidInAmount;
    }

    public ReconContractModel setPaidInAmount(BigDecimal paidInAmount) {
        this.paidInAmount = paidInAmount;
        return this;
    }

    public BigDecimal getPaidDeliveryAmount() {
        return paidDeliveryAmount;
    }

    public ReconContractModel setPaidDeliveryAmount(BigDecimal paidDeliveryAmount) {
        this.paidDeliveryAmount = paidDeliveryAmount;
        return this;
    }

    public BigDecimal getDifAmount() {
        return difAmount;
    }

    public ReconContractModel setDifAmount(BigDecimal difAmount) {
        this.difAmount = difAmount;
        return this;
    }

    public String getUnitName() {
        return unitName;
    }

    public ReconContractModel setUnitName(String unitName) {
        this.unitName = unitName;
        return this;
    }
}
