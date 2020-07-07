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

import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.Store;
import net.mall.service.StoreService;
import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 广告位
 *
 * @author huanghy
 * @version 6.1
 */
@Component
public class AdFactoryDirective extends BaseDirective {

    /**
     * 变量名称
     */
    private static final String VARIABLE_NAME = "adFactory";

    @Inject
    private StoreService storeService;

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
        Pageable pageable = new Pageable();
        pageable.setPageSize(6);
        Page<Store> stores = storeService.findPage(pageable);
        setLocalVariable(VARIABLE_NAME, stores, env, body);
    }
}