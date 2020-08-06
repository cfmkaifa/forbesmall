package net.mall.template.directive;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import net.mall.entity.ArticleCategory;
import net.mall.service.ArticleCategoryService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @description: TODO
 * @author: xfx
 * @date: Created in 2020/7/13 14:52
 * @version:
 * @modified By:
 */
@Component
public class VideoCategoryDirective  extends BaseDirective {
    /**
     * 变量名称
     */
    private static final String VARIABLE_NAME = "videoCategories";

    @Inject
    private ArticleCategoryService articleCategoryService;

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
        List<ArticleCategory> videoCategories = articleCategoryService.findRoots(6, ArticleCategory.Type.VIDEO);
        setLocalVariable(VARIABLE_NAME, videoCategories, env, body);
    }
}
