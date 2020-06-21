package net.mall.util;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.OutputStream;
import java.util.List;

/****支付订单
 */
public class PayOrderBuilder {



    OutputStream outStream;


    List<ExcelWritePayOrder> excelWritePayOrders;

    /***
     * 支付订单
     */
    public class ExcelWritePayOrder extends BaseRowModel {


        /***订单编号
         */
        @ExcelProperty(value = "支付订单号",index = 0)
        private String sn;

        /***价格*/
        @ExcelProperty(value = "支付方式",index = 2)
        private String paymentMethod;

        /***手续费
         */
        @ExcelProperty(value = "收款银行",index = 1)
        private String bank;


        @ExcelProperty(value = "收款账号",index = 3)
        private String account;

        @ExcelProperty(value = "付款金额",index = 2)
        private String amount;

        @ExcelProperty(value = "手续费",index = 2)
        private String fee;

        @ExcelProperty(value = "付款人",index = 2)
        private String payer;

        @ExcelProperty(value = "备注",index = 2)
        private String memo;

        @ExcelProperty(value = "订单编号",index = 2)
        private String order;
    }







    public void writeExcel(String filePath){
        Sheet  sheet = new Sheet(1,1,ExcelWritePayOrder.class);
        ExcelWriter excelBuilder = new ExcelWriter(outStream,ExcelTypeEnum.XLSX);
        excelBuilder.write(excelWritePayOrders,sheet);
        excelBuilder.finish();
    }
}
