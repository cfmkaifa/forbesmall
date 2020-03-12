/*
 *
 *
 *
 *
 */
package net.mall.listener;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import net.mall.entity.Article;
import net.mall.entity.Product;
import net.mall.entity.Store;
import net.mall.service.ConfigService;
import net.mall.service.SearchService;

/**
 * Listener - 初始化
 *
 * @author huanghy
 * @version 6.1
 */
@Component
public class InitListener {

    @Value("${system.version}")
    private String systemVersion;

    @Inject
    private ConfigService configService;
    @Inject
    private SearchService searchService;

    /**
     * 事件处理
     *
     * @param contextRefreshedEvent ContextRefreshedEvent
     */
    @EventListener
    public void handle(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext() == null || contextRefreshedEvent.getApplicationContext().getParent() != null) {
            return;
        }
        configService.init();
        searchService.index(Article.class);
        searchService.index(Product.class);
        searchService.index(Store.class);
    }

}