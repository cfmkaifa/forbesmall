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
import net.mall.entity.ArticleCategory;
import net.mall.service.ArticleCategoryService;

/**
 * 模板指令 - 顶级文章分类列表
 * 
 * @author huanghy
 * @version 6.1
 */
@Component
public class ArticleCategoryRootListDirective extends BaseDirective {

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "articleCategories";

	@Inject
	private ArticleCategoryService articleCategoryService;

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
		Integer count = getCount(params);
		boolean useCache = useCache(params);
		long temp=10051;
		List<ArticleCategory> articleCategories = articleCategoryService.findRoots(1, useCache,ArticleCategory.Type.INST);
		ArticleCategory articleCategory=articleCategoryService.findArticleCategory(temp,ArticleCategory.Type.NEWS);
		articleCategories.add(articleCategory);
		if(articleCategories.size()==3){
			articleCategories.remove(1);
		}
		setLocalVariable(VARIABLE_NAME, articleCategories, env, body);
	}
}