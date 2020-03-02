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
import net.mall.entity.Consultation;
import net.mall.service.ConsultationService;
import net.mall.util.FreeMarkerUtils;

/**
 * 模板指令 - 咨询
 *
 * @author huanghy
 * @version 6.1
 */
@Component
public class ConsultationListDirective extends BaseDirective {

    /**
     * "会员ID"参数名称
     */
    private static final String MEMBER_ID_PARAMETER_NAME = "memberId";

    /**
     * "商品ID"参数名称
     */
    private static final String PRODUCT_ID_PARAMETER_NAME = "productId";

    /**
     * 变量名称
     */
    private static final String VARIABLE_NAME = "consultations";

    @Inject
    private ConsultationService consultationService;

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
        Long memberId = FreeMarkerUtils.getParameter(MEMBER_ID_PARAMETER_NAME, Long.class, params);
        Long productId = FreeMarkerUtils.getParameter(PRODUCT_ID_PARAMETER_NAME, Long.class, params);
        Integer count = getCount(params);
        List<Filter> filters = getFilters(params, Consultation.class);
        List<Order> orders = getOrders(params);
        boolean useCache = useCache(params);

        List<Consultation> consultations = consultationService.findList(memberId, productId, true, count, filters, orders, useCache);
        setLocalVariable(VARIABLE_NAME, consultations, env, body);
    }

}