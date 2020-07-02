package net.mall.util;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.OutputStream;
import java.util.List;

/****采购订单
 */
public class OrderBuilder {



    OutputStream outStream;


    List<ExcelWriteOrder> excelWriteOrders;

    /***
     * 采购订单
     */
    public class ExcelWriteOrder extends BaseRowModel {


        /***订单编号
         */
        @ExcelProperty(value = "订单编号",index = 0)
        private String sn;

        /***价格*/
        @ExcelProperty(value = "价格",index = 2)
        private String price;

        /***手续费
         */
        @ExcelProperty(value = "手续费",index = 1)
        private String fee;


        @ExcelProperty(value = "运费",index = 3)
        private String freight;

        @ExcelProperty(value = "税金",index = 2)
        private String tax;

        @ExcelProperty(value = "订单金额",index = 2)
        private String amount;

        @ExcelProperty(value = "已支付金额",index = 2)
        private String amountPaid;

        @ExcelProperty(value = "退款金额",index = 2)
        private String refundAmount;

        @ExcelProperty(value = "重量",index = 2)
        private String weight;

        @ExcelProperty(value = "数量",index = 2)
        private String quantity;

        @ExcelProperty(value = "已发货数量",index = 2)
        private String shippedQuantity;

        @ExcelProperty(value = "已退货数量",index = 2)
        private String returnedQuantity;

        @ExcelProperty(value = "收货人",index = 2)
        private String consignee;

        @ExcelProperty(value = "地区名称",index = 2)
        private String areaName;

        @ExcelProperty(value = "地址",index = 2)
        private String address;

        @ExcelProperty(value = "邮编",index = 2)
        private String zipCode;

        @ExcelProperty(value = "车牌号",index = 2)
        private String plate;

        @ExcelProperty(value = "司机姓名",index = 2)
        private String driver;

        @ExcelProperty(value = "司机电话",index = 2)
        private String driverPhone;

        @ExcelProperty(value = "电话",index = 2)
        private String phone;

        @ExcelProperty(value = "支付凭证",index = 2)
        private String certificatePath;

        @ExcelProperty(value = "发票地址",index = 2)
        private String invoicePath;

        @ExcelProperty(value = "对账单地址",index = 2)
        private String statPath;

        @ExcelProperty(value = "合同地址",index = 2)
        private String contractPath;


        @ExcelProperty(value = "盖章合同地址",index = 2)
        private String sealContract;


        @ExcelProperty(value = "备注",index = 2)
        private String memo;

        @ExcelProperty(value = "过期时间",index = 2)
        private String expire;

        @ExcelProperty(value = "支付方式",index = 2)
        private String paymentMethodName;

        @ExcelProperty(value = "配送方式",index = 2)
        private String shippingMethodName;

        @ExcelProperty(value = "发票抬头",index = 2)
        private String title;

        @ExcelProperty(value = "税号",index = 2)
        private String taxNumber;


        @ExcelProperty(value = "内容",index = 2)
        private String content;

        @ExcelProperty(value = "会员名称",index = 2)
        private String member;

        @ExcelProperty(value = "店铺名称",index = 2)
        private String store;

        @ExcelProperty(value = "商品名称",index = 2)
        private String proName;

        @ExcelProperty(value = "商品价格",index = 2)
        private String proPrice;

        @ExcelProperty(value = "商品重量",index = 2)
        private String proWeight;

        @ExcelProperty(value = "商品缩略图",index = 2)
        private String thumbnail;

        @ExcelProperty(value = "商品数量",index = 2)
        private String proQuantity;

        @ExcelProperty(value = "商品已发货数量",index = 2)
        private String proShippedQuantity;

        @ExcelProperty(value = "商品已退货数量",index = 2)
        private String proReturnedQuantity;

        @ExcelProperty(value = "商品规格信息",index = 2)
        private String skus;
    }







    public void writeExcel(String filePath){
        Sheet  sheet = new Sheet(1,1,ExcelWriteOrder.class);
        ExcelWriter excelBuilder = new ExcelWriter(outStream,ExcelTypeEnum.XLSX);
        excelBuilder.write(excelWriteOrders,sheet);
        excelBuilder.finish();
    }
}
