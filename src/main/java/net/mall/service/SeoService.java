/*
 *
 *
 *
 *
 */
package net.mall.service;

import net.mall.entity.Seo;

/**
 * Service - SEO设置
 *
 * @author huanghy
 * @version 6.1
 */
public interface SeoService extends BaseService<Seo, Long> {

    /**
     * 查找SEO设置
     *
     * @param type 类型
     * @return SEO设置
     */
    Seo find(Seo.Type type);

    /**
     * 查找SEO设置
     *
     * @param type     类型
     * @param useCache 是否使用缓存
     * @return SEO设置
     */
    Seo find(Seo.Type type, boolean useCache);

}