package net.mall.excel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import javax.persistence.Column;
import java.math.BigDecimal;
public class ProImportQueueExcelMode extends BaseRowModel{


    @ExcelProperty(index = 0)
    private String categoryName;

    @ExcelProperty(index = 1)
    private String proName;

    @ExcelProperty(index = 2)
    private String proSn;

    @ExcelProperty(index = 3)
    private String unit;

    @ExcelProperty(index = 4)
    private String weight;


    @ExcelProperty(index = 5)
    private String price;

    @ExcelProperty(index = 6)
    private String introduction;

    @ExcelProperty(index = 7)
    private String productImgs;

    @ExcelProperty(index = 8)
    private String productParameters;

    @ExcelProperty(index = 9)
    private String productAttributes;

    @ExcelProperty(index = 10)
    private String productSpecs;


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getProductImgs() {
        return productImgs;
    }

    public void setProductImgs(String productImgs) {
        this.productImgs = productImgs;
    }

    public String getProductParameters() {
        return productParameters;
    }

    public void setProductParameters(String productParameters) {
        this.productParameters = productParameters;
    }

    public String getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(String productAttributes) {
        this.productAttributes = productAttributes;
    }

    public String getProductSpecs() {
        return productSpecs;
    }

    public void setProductSpecs(String productSpecs) {
        this.productSpecs = productSpecs;
    }

    public String getProSn() {
        return proSn;
    }

    public void setProSn(String proSn) {
        this.proSn = proSn;
    }
}
