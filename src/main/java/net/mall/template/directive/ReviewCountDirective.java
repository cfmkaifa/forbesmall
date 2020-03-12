/*
 *
 *
 *
 *
 */
package net.mall.template.directive;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import net.mall.entity.Review;
import net.mall.service.ReviewService;
import net.mall.util.FreeMarkerUtils;

/**
 * 模板指令 - 评论数量
 *
 * @author huanghy
 * @version 6.1
 */
@Component
public class ReviewCountDirective extends BaseDirective {

    /**
     * "会员ID"参数名称
     */
    private static final String MEMBER_ID_PARAMETER_NAME = "memberId";

    /**
     * "商品ID"参数名称
     */
    private static final String PRODUCT_ID_PARAMETER_NAME = "productId";

    /**
     * "类型"参数名称
     */
    private static final String TYPE_PARAMETER_NAME = "type";

    /**
     * 变量名称
     */
    private static final String VARIABLE_NAME = "count";

    @Inject
    private ReviewService reviewService;

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
        Review.Type type = FreeMarkerUtils.getParameter(TYPE_PARAMETER_NAME, Review.Type.class, params);

        Long count = reviewService.count(memberId, productId, type, true);
        setLocalVariable(VARIABLE_NAME, count, env, body);
    }

}