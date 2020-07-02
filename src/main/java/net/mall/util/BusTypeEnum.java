package net.mall.util;

/***
 *
 */
public enum BusTypeEnum {


    SUPPLIER("supplier","供应商"),
    PRODUCT("product","商品"),
    CATEGROY("categroy","商品分类"),
    ORDER("order","订单"),
    PAYORDER("payorder","支付订单"),
    DELIVERY("delivery","发货"),
    GROUPBUY("groupbuy","申请");

    private String code;
    private String name;

    /***
     * 构造函数
     * @param code
     * @param name
     */
    BusTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
