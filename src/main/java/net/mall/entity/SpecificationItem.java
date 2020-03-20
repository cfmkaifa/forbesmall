/*
 *
 *
 *
 *
 */
package net.mall.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity - 规格项
 *
 * @author huanghy
 * @version 6.1
 */
@Indexed
public class SpecificationItem implements Serializable {

    private static final long serialVersionUID = 7623999885848444842L;

    /**
     * 最大条目数量
     */
    public static final int MAX_ENTRY_SIZE = 100;

    /**
     * 名称
     */
    @NotEmpty
    @Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
    @Length(max = 200)
    private String name;

    /**
     * 条目
     */
    @Valid
    @Size(max = MAX_ENTRY_SIZE)
    @NotEmpty
    private List<SpecificationItem.Entry> entries = new ArrayList<>();

    /**
     * 获取名称
     *
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取条目
     *
     * @return 条目
     */
    public List<SpecificationItem.Entry> getEntries() {
        return entries;
    }

    /**
     * 设置条目
     *
     * @param entries 条目
     */
    public void setEntries(List<SpecificationItem.Entry> entries) {
        this.entries = entries;
    }

    /**
     * 判断是否已选
     *
     * @return 是否已选
     */
    @JsonIgnore
    public boolean isSelected() {
        if (CollectionUtils.isNotEmpty(getEntries())) {
            for (SpecificationItem.Entry entry : getEntries()) {
                if (entry.getIsSelected()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断规格值是否有效
     *
     * @param specificationValue 规格值
     * @return 规格值是否有效
     */
    public boolean isValid(SpecificationValue specificationValue) {
        if (specificationValue != null && specificationValue.getId() != null && StringUtils.isNotEmpty(specificationValue.getValue()) && CollectionUtils.isNotEmpty(getEntries())) {
            for (SpecificationItem.Entry entry : getEntries()) {
                if (entry != null && entry.getIsSelected() && specificationValue.getId().equals(entry.getId()) && StringUtils.equals(entry.getValue(), specificationValue.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 重写equals方法
     *
     * @param obj 对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * 重写hashCode方法
     *
     * @return HashCode
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Entity - 条目
     *
     * @author huanghy
     * @version 6.1
     */
    public static class Entry implements Serializable {

        private static final long serialVersionUID = 4793372555875531705L;

        /**
         * ID
         */
        @NotNull
        private Integer id;

        /**
         * 值
         */
        @NotEmpty
        @Length(max = 200)
        private String value;

        /**
         * 是否已选
         */
        @NotNull
        private Boolean isSelected;

        /**
         * 获取ID
         *
         * @return ID
         */
        public Integer getId() {
            return id;
        }

        /**
         * 设置ID
         *
         * @param id ID
         */
        public void setId(Integer id) {
            this.id = id;
        }

        /**
         * 获取值
         *
         * @return 值
         */
        public String getValue() {
            return value;
        }

        /**
         * 设置值
         *
         * @param value 值
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * 获取是否已选
         *
         * @return 是否已选
         */
        public Boolean getIsSelected() {
            return isSelected;
        }

        /**
         * 设置是否已选
         *
         * @param isSelected 是否已选
         */
        public void setIsSelected(Boolean isSelected) {
            this.isSelected = isSelected;
        }

        /**
         * 重写equals方法
         *
         * @param obj 对象
         * @return 是否相等
         */
        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }

        /**
         * 重写hashCode方法
         *
         * @return HashCode
         */
        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }
    }

}