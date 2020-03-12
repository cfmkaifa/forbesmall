/*
 *
 *
 *
 *
 */
package net.mall.dao;

import net.mall.entity.Seo;

/**
 * Dao - SEO设置
 *
 * @author huanghy
 * @version 6.1
 */
public interface SeoDao extends BaseDao<Seo, Long> {

    /**
     * 查找SEO设置
     *
     * @param type 类型
     * @return SEO设置
     */
    Seo find(Seo.Type type);

}