/*
 *
 * 
 *
 * 
 */
package net.mall.service;

import java.util.List;

import net.mall.entity.SitemapIndex;
import net.mall.entity.SitemapUrl;

/**
 * Service - Sitemap索引
 * 
 * @author huanghy
 * @version 6.1
 */
public interface SitemapIndexService {

	/**
	 * 生成Sitemap索引
	 * 
	 * @param type
	 *            类型
	 * @param maxSitemapUrlSize
	 *            最大Sitemap URL数量
	 * @return Sitemap索引
	 */
	List<SitemapIndex> generate(SitemapUrl.Type type, int maxSitemapUrlSize);

}