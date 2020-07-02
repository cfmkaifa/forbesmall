package net.mall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "pro_import_queue")
public class ProImportQueue extends BaseEntity<Long> {

    private static final long serialVersionUID = -7428235096925905681L;


    @Column(nullable = false, name = "category_name")
    private String categoryName;

    @Column(nullable = false, name = "pro_name")
    private String proName;


    @Column(nullable = false, name = "pro_sn")
    private String proSn;

    @Column(nullable = false, name = "unit")
    private String unit;

    @Column(name = "weight")
    private Integer weight;


    @Column(nullable = false, name = "price")
    private BigDecimal price;

    @Column( name = "introduction")
    private String introduction;

    @Column(name = "product_imgs")
    private String productImgs;

    @Column(name = "product_parameters")
    private String productParameters;

    @Column( name = "product_attributes")
    private String productAttributes;

    @Column(nullable = false, name = "product_specs")
    private String productSpecs;

    @Column(nullable = false, name = "store_id")
    private Long storeId;

    @Column(nullable = false, name = "in_storage")
    private Integer inStorage;


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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Integer getInStorage() {
        return inStorage;
    }

    public void setInStorage(Integer inStorage) {
        this.inStorage = inStorage;
    }

    public String getProSn() {
        return proSn;
    }

    public void setProSn(String proSn) {
        this.proSn = proSn;
    }
}
