package net.mall.template.directive;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import net.mall.service.ProductService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;

/**
 * @description: TODO
 * @author: xfx
 * @date: Created in 2020/3/30 10:11
 * @version: 1.0
 * @modified By:
 */
@Component(value = "proAmountDirective")
public class ProductAmountDirective extends BaseDirective{
    /**
     * 变量名称
     */
    private static final String VARIABLE_NAME = "proAmount";

    @Inject
    private ProductService productService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] templateModels, TemplateDirectiveBody body) throws TemplateException, IOException {
        Long id = getId(params);
        boolean useCache = useCache(params);
        Long proAmount = productService.count();
        setLocalVariable(VARIABLE_NAME, proAmount, env, body);
    }
}
