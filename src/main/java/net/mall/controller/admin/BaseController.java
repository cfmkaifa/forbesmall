/*
 *
 * 
 *
 * 
 */
package net.mall.controller.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import net.mall.Filter;
import net.mall.entity.PluginConfig;
import net.mall.entity.SupplierPluginConfig;
import net.mall.service.SupplierPluginConfigService;
import net.mall.util.ConvertUtils;

/**
 * Controller - 基类
 * 
 * @author huanghy
 * @version 6.1
 */
public class BaseController {

	/**
	 * "验证结果"属性名称
	 */
	private static final String CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME = "constraintViolations";

	@Inject
	private Validator validator;

	@Inject
	private SupplierPluginConfigService supplierPluginConfigService;

	/**
	 * 数据验证
	 * 
	 * @param target
	 *            验证对象
	 * @param groups
	 *            验证组
	 * @return 验证结果
	 */
	protected boolean isValid(Object target, Class<?>... groups) {
		Assert.notNull(target, "[Assertion failed] - target is required; it must not be null");

		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(target, groups);
		if (constraintViolations.isEmpty()) {
			return true;
		}
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		requestAttributes.setAttribute(CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations, RequestAttributes.SCOPE_REQUEST);
		return false;
	}

	/**
	 * 数据验证
	 * 
	 * @param targets
	 *            验证对象
	 * @param groups
	 *            验证组
	 * @return 验证结果
	 */
	protected boolean isValid(Collection<Object> targets, Class<?>... groups) {
		Assert.notEmpty(targets, "[Assertion failed] - targets must not be empty: it must contain at least 1 element");

		for (Object target : targets) {
			if (!isValid(target, groups)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 数据验证
	 * 
	 * @param type
	 *            类型
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @param groups
	 *            验证组
	 * @return 验证结果
	 */
	protected boolean isValid(Class<?> type, String property, Object value, Class<?>... groups) {
		Assert.notNull(type, "[Assertion failed] - type is required; it must not be null");
		Assert.hasText(property, "[Assertion failed] - property must have text; it must not be null, empty, or blank");

		Set<?> constraintViolations = validator.validateValue(type, property, value, groups);
		if (constraintViolations.isEmpty()) {
			return true;
		}
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		requestAttributes.setAttribute(CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations, RequestAttributes.SCOPE_REQUEST);
		return false;
	}

	/**
	 * 数据验证
	 * 
	 * @param type
	 *            类型
	 * @param properties
	 *            属性
	 * @param groups
	 *            验证组
	 * @return 验证结果
	 */
	protected boolean isValid(Class<?> type, Map<String, Object> properties, Class<?>... groups) {
		Assert.notNull(type, "[Assertion failed] - type is required; it must not be null");
		Assert.notEmpty(properties, "[Assertion failed] - properties must not be empty: it must contain at least 1 element");
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			if (!isValid(type, entry.getKey(), entry.getValue(), groups)) {
				return false;
			}
		}
		return true;
	}
	
	/***
	 * receSupplierPluginConfig方法慨述:获取商家自定义
	 * @param supplierId
	 * @return SupplierPluginConfig
	 * @创建人 huanghy
	 * @创建时间 2020年1月6日 下午3:53:49
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
	protected SupplierPluginConfig receSupplierPluginConfig(String pluginId,String supplierId){
		if(!"-1".equals(supplierId)){
			List<Filter> filters = new ArrayList<Filter>();
			filters.add(new Filter("supplierId", Filter.Operator.EQ, supplierId));
			filters.add(new Filter("pluginId", Filter.Operator.EQ, pluginId));
			List<SupplierPluginConfig> supplierPluginConfigs = supplierPluginConfigService.findList(0, 1, filters, null);
			if(ConvertUtils.isNotEmpty(supplierPluginConfigs)){
				return supplierPluginConfigs.get(0);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	
	/***
	 * oprSupplierPluginConfig方法慨述:保存支付
	 * @param pluginConfig
	 * @param supplierId void
	 * @创建人 huanghy
	 * @创建时间 2020年1月6日 下午3:38:27
	 * @修改人 (修改了该文件，请填上修改人的名字)
	 * @修改日期 (请填上修改该文件时的日期)
	 */
	protected void oprSupplierPluginConfig(PluginConfig pluginConfig,String supplierId){
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("supplierId", Filter.Operator.EQ, supplierId));
		filters.add(new Filter("pluginId", Filter.Operator.EQ, pluginConfig.getPluginId()));
		List<SupplierPluginConfig> supplierPluginConfigs = supplierPluginConfigService.findList(0, 1, filters, null);
		SupplierPluginConfig supplierPluginConfig = new SupplierPluginConfig();
		BeanUtils.copyProperties(pluginConfig, supplierPluginConfig);
		if(ConvertUtils.isNotEmpty(supplierPluginConfigs)){
			supplierPluginConfig.setId(supplierPluginConfigs.get(0).getId());
			supplierPluginConfig.setSupplierId(Long.valueOf(supplierId));
			supplierPluginConfigService.update(supplierPluginConfig);
		} else {
			supplierPluginConfig.setId(null);
			supplierPluginConfig.setSupplierId(Long.valueOf(supplierId));
			supplierPluginConfigService.save(supplierPluginConfig);
		}
	}
}