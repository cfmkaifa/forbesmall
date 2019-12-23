/*
 *
 * 
 *
 * 
 */
package net.mall.service;

import java.util.List;

import net.mall.entity.SpecificationItem;
import net.mall.entity.SpecificationValue;

/**
 * Service - 规格值
 * 
 * @author huanghy
 * @version 6.1
 */
public interface SpecificationValueService {

	/**
	 * 规格值验证
	 * 
	 * @param specificationItems
	 *            规格项
	 * @param specificationValues
	 *            规格值
	 * @return 验证结果
	 */
	boolean isValid(List<SpecificationItem> specificationItems, List<SpecificationValue> specificationValues);

}