/*
 *
 *
 *
 *
 */
package net.mall.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import net.mall.Filter;
import net.mall.Order;
import net.mall.entity.Attribute;
import net.mall.service.AttributeService;
import net.mall.util.FreeMarkerUtils;

/**
 * 模板指令 - 属性
 *
 * @author huanghy
 * @version 6.1
 */
@Component
public class AttributeListDirective extends BaseDirective {

    /**
     * "商品分类ID"参数名称
     */
    private static final String PRODUCT_CATEGORY_ID_PARAMETER_NAME = "productCategoryId";

    /**
     * 变量名称
     */
    private static final String VARIABLE_NAME = "attributes";

    @Inject
    private AttributeService attributeService;

    /**
     * 执行
     *
     * @param env      环境变量
     * @param params   参数
     * @param loopVars 循环变量
     * @param body     模板内容
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        Long productCategoryId = FreeMarkerUtils.getParameter(PRODUCT_CATEGORY_ID_PARAMETER_NAME, Long.class, params);
        Integer count = getCount(params);
        List<Filter> filters = getFilters(params, Attribute.class);
        List<Order> orders = getOrders(params);
        boolean useCache = useCache(params);

        List<Attribute> attributes = attributeService.findList(productCategoryId, count, filters, orders, useCache);
        setLocalVariable(VARIABLE_NAME, attributes, env, body);
    }

}