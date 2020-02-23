/*
 *
 * 
 *
 * 
 */
package net.mall.template.directive;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import net.mall.Filter;
import net.mall.entity.ProPurchApply;
import net.mall.service.ProPurchApplyService;
import net.mall.util.FreeMarkerUtils;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;

/**
 * 模板指令 - 经营分类申请数量
 * 
 * @author huanghy
 * @version 6.1
 */
@Component
public class ProPurchApplyCountDirective extends BaseDirective {

	/**
	 * "状态"参数名称
	 */
	private static final String STATUS_PARAMETER_NAME = "status";


	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "count";

	@Inject
	ProPurchApplyService proPurchApplyService;

	/**
	 * 执行
	 * 
	 * @param env
	 *            环境变量
	 * @param params
	 *            参数
	 * @param loopVars
	 *            循环变量
	 * @param body
	 *            模板内容
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		ProPurchApply.ProApplyStatus status = FreeMarkerUtils.getParameter(STATUS_PARAMETER_NAME, ProPurchApply.ProApplyStatus.class, params);
		Long count = proPurchApplyService.count(new Filter(STATUS_PARAMETER_NAME,Filter.Operator.EQ,status));
		setLocalVariable(VARIABLE_NAME, count, env, body);
	}

}