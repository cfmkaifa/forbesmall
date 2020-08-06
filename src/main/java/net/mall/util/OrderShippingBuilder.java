package net.mall.util;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.OutputStream;
import java.util.List;

/****订单发货
 */
public class OrderShippingBuilder {



    OutputStream outStream;


    List<ExcelWriteOrderShipping> excelWriteOrderShippings;

    /***
     * 订单发货
     */
    public class ExcelWriteOrderShipping extends BaseRowModel {


        /***订单编号
         */
        @ExcelProperty(value = "编号",index = 0)
        private String sn;

        /***价格*/
        @ExcelProperty(value = "配送方式",index = 2)
        private String shippingMethod;

        /***手续费
         */
        @ExcelProperty(value = "物流公司",index = 1)
        private String deliveryCorp;


        @ExcelProperty(value = "物流公司地址",index = 3)
        private String deliveryCorpUrl;

        @ExcelProperty(value = "物流公司代码",index = 2)
        private String deliveryCorpCode;

        @ExcelProperty(value = "运单号",index = 2)
        private String trackingNo;

        @ExcelProperty(value = "物流费用",index = 2)
        private String freight;

        @ExcelProperty(value = "收货人",index = 2)
        private String consignee;

        @ExcelProperty(value = "地区",index = 2)
        private String area;

        @ExcelProperty(value = "地址",index = 2)
        private String address;

        @ExcelProperty(value = "邮编",index = 2)
        private String zipCode;

        @ExcelProperty(value = "电话",index = 2)
        private String phone;

        @ExcelProperty(value = "码单",index = 2)
        private String weightMemo;

        @ExcelProperty(value = "订单号",index = 2)
        private String order;

        @ExcelProperty(value = "SKU编号",index = 2)
        private String skuSn;

        @ExcelProperty(value = "SKU名称",index = 2)
        private String name;

        @ExcelProperty(value = "重量",index = 2)
        private String weight;

        @ExcelProperty(value = "数量",index = 2)
        private String quantity;


        @ExcelProperty(value = "总重量",index = 2)
        private String totalWeight;

    }







    public void writeExcel(String filePath){
        Sheet  sheet = new Sheet(1,1,ExcelWriteOrderShipping.class);
        ExcelWriter excelBuilder = new ExcelWriter(outStream,ExcelTypeEnum.XLSX);
        excelBuilder.write(excelWriteOrderShippings,sheet);
        excelBuilder.finish();
    }
}
