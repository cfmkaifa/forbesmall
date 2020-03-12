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
import net.mall.entity.Navigation;
import net.mall.service.NavigationService;
import net.mall.util.FreeMarkerUtils;

/**
 * 模板指令 - 导航列表
 *
 * @author huanghy
 * @version 6.1
 */
@Component
public class NavigationListDirective extends BaseDirective {

    /**
     * "导航组ID"参数名称
     */
    private static final String NAVIGATION_GROUP_ID_PARAMETER_NAME = "navigationGroupId";

    /**
     * 变量名称
     */
    private static final String VARIABLE_NAME = "navigations";

    @Inject
    private NavigationService navigationService;

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
        Long navigationGroupId = FreeMarkerUtils.getParameter(NAVIGATION_GROUP_ID_PARAMETER_NAME, Long.class, params);
        Integer count = getCount(params);
        List<Filter> filters = getFilters(params, Navigation.class);
        List<Order> orders = getOrders(params);
        boolean useCache = useCache(params);

        List<Navigation> navigations = navigationService.findList(navigationGroupId, count, filters, orders, useCache);
        setLocalVariable(VARIABLE_NAME, navigations, env, body);
    }

}