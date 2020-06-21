package net.mall.util;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.ExcelBuilder;
import com.alibaba.excel.write.ExcelBuilderImpl;
import net.mall.excel.ProImportQueueExcelMode;

import java.io.OutputStream;
import java.util.List;

/****产品写入
 */
public class ProductBuilder {



    OutputStream outStream;


    List<ExcelWriteProduct> excelWriteProducts;

    /***
     * 产品写入
     */
    public class ExcelWriteProduct extends BaseRowModel {


        /***店铺名称
         */
        @ExcelProperty(value = "店铺名称",index = 0)
        private String storeName;

        /***商品分类名称
         */
        @ExcelProperty(value = "商品分类",index = 1)
        private String productCategoryName;

        /***店铺商品分类*/
        @ExcelProperty(value = "店铺商品分类",index = 2)
        private String storeProductCategoryName;

        @ExcelProperty(value = "类型",index = 3)
        private String type;

        @ExcelProperty(value = "编号",index = 2)
        private String sn;

        @ExcelProperty(value = "名称",index = 2)
        private String name;

        @ExcelProperty(value = "副标题",index = 2)
        private String caption;

        @ExcelProperty(value = "销售价",index = 2)
        private String price;

        @ExcelProperty(value = "成本价",index = 2)
        private String cost;

        @ExcelProperty(value = "市场价",index = 2)
        private String marketPrice;

        @ExcelProperty(value = "单位",index = 2)
        private String unit;

        @ExcelProperty(value = "重量",index = 2)
        private String weight;

        @ExcelProperty(value = "是否上架",index = 2)
        private String isMarketable;

        @ExcelProperty(value = "商品介绍",index = 2)
        private String introduction;

        @ExcelProperty(value = "商品图片",index = 2)
        private String productImages;

        @ExcelProperty(value = "商品参数",index = 2)
        private String parameterValues;

        @ExcelProperty(value = "评分",index = 2)
        private String score;

        @ExcelProperty(value = "周点击数",index = 2)
        private String weekHits;

        @ExcelProperty(value = "月点击数",index = 2)
        private String monthHits;

        @ExcelProperty(value = "点击数",index = 2)
        private String hits;

        @ExcelProperty(value = "周销量",index = 2)
        private String weekSales;

        @ExcelProperty(value = "月销量",index = 2)
        private String monthSales;

        @ExcelProperty(value = "销量",index = 2)
        private String sales;

        @ExcelProperty(value = "商品规格",index = 2)
        private String specifications;
    }







    public void writeExcel(String filePath){
        Sheet  sheet = new Sheet(1,1,ExcelWriteProduct.class);
        ExcelWriter excelBuilder = new ExcelWriter(outStream,ExcelTypeEnum.XLSX);
        excelBuilder.write(excelWriteProducts,sheet);
        excelBuilder.finish();
    }
}
