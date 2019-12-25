/*
 *
 * 
 *
 * 
 */
package net.mall.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import net.mall.BaseAttributeConverter;

/**
 * Entity - 规格
 * 
 * @author huanghy
 * @version 6.1
 */
@Entity
public class Specification extends OrderedEntity<Long> {

	private static final long serialVersionUID = -6346775052811140926L;

	/**
	 * 最大可选项数量
	 */
	public static final int MAX_OPTION_SIZE = 100;

	/**是否样品
	 */
	public enum Sample {
		NO,
		YES
	}
	
	
	/**
	 * 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;


	/****是否样品
	 */
	private Sample sample;
	
	
	/**
	 * 绑定分类
	 */
	@NotNull(groups = Save.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private ProductCategory productCategory;

	/**
	 * 可选项
	 */
	@NotEmpty
	@Size(max = MAX_OPTION_SIZE)
	@Column(nullable = false, length = 4000)
	@Convert(converter = OptionConverter.class)
	private List<String> options = new ArrayList<>();

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
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取绑定分类
	 * 
	 * @return 绑定分类
	 */
	public ProductCategory getProductCategory() {
		return productCategory;
	}

	/**
	 * 设置绑定分类
	 * 
	 * @param productCategory
	 *            绑定分类
	 */
	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	/**
	 * 获取可选项
	 * 
	 * @return 可选项
	 */
	public List<String> getOptions() {
		return options;
	}

	/**
	 * 设置可选项
	 * 
	 * @param options
	 *            可选项
	 */
	public void setOptions(List<String> options) {
		this.options = options;
	}
	
	
	

	/** 
	 * @return sample 
	 */
	public Sample getSample() {
		return sample;
	}

	/** 
	 * @param sample 要设置的 sample 
	 */
	public void setSample(Sample sample) {
		this.sample = sample;
	}




	/**
	 * 类型转换 - 可选项
	 * 
	 * @author huanghy
	 * @version 6.1
	 */
	@Converter
	public static class OptionConverter extends BaseAttributeConverter<List<String>> {
	}

}