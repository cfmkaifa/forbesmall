package net.mall.util;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.OutputStream;
import java.util.List;

/****商品分类
 */
public class ProductCategoryBuilder {



    OutputStream outStream;


    List<ExcelWriteProductCategory> excelWriteProductCategorys;

    /***
     * 商品分类
     */
    public class ExcelWriteProductCategory extends BaseRowModel {


        /***商品分类
         */
        @ExcelProperty(value = "分类名称",index = 1)
        private String productCategoryName;


        @ExcelProperty(value = "关键字",index = 3)
        private String seoKeywords;

        /***店铺商品分类*/
        @ExcelProperty(value = "描述",index = 2)
        private String seoDescription;


        /***店铺商品分类*/
        @ExcelProperty(value = "层级",index = 2)
        private String grade;

    }







    public void writeExcel(String filePath){
        Sheet  sheet = new Sheet(1,1,ExcelWriteProductCategory.class);
        ExcelWriter excelBuilder = new ExcelWriter(outStream,ExcelTypeEnum.XLSX);
        excelBuilder.write(excelWriteProductCategorys,sheet);
        excelBuilder.finish();
    }
}
