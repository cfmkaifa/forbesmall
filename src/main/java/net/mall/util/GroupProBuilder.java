package net.mall.util;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.OutputStream;
import java.util.List;

/****团购申请
 */
public class GroupProBuilder {



    OutputStream outStream;


    List<ExcelWriteGroupPro> excelWriteGroupPros;

    /***
     * 团购申请
     */
    public class ExcelWriteGroupPro extends BaseRowModel {


        /***订单编号
         */
        @ExcelProperty(value = "商品名称",index = 0)
        private String proName;

        /***价格*/
        @ExcelProperty(value = "商品编码",index = 2)
        private String proSn;

        /***手续费
         */
        @ExcelProperty(value = "店铺名称",index = 1)
        private String storeName;


        @ExcelProperty(value = "SKU编码",index = 3)
        private String skuSn;

        @ExcelProperty(value = "开始时间",index = 2)
        private String startTime;

        @ExcelProperty(value = "结束时间",index = 2)
        private String endTime;

        @ExcelProperty(value = "备注",index = 2)
        private String remarks;

        @ExcelProperty(value = "起购重量",index = 2)
        private String mqqWeight;

        @ExcelProperty(value = "限购重量",index = 2)
        private String limitWeight;

        @ExcelProperty(value = "团购价",index = 2)
        private String groupPrice;
    }







    public void writeExcel(String filePath){
        Sheet  sheet = new Sheet(1,1,ExcelWriteGroupPro.class);
        ExcelWriter excelBuilder = new ExcelWriter(outStream,ExcelTypeEnum.XLSX);
        excelBuilder.write(excelWriteGroupPros,sheet);
        excelBuilder.finish();
    }
}
