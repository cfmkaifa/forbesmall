/*
 *
 * 
 *
 * 
 */
package net.mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.mall.entity.SitemapIndex;
import net.mall.entity.SitemapUrl;
import net.mall.service.SitemapIndexService;
import net.mall.service.SitemapUrlService;

/**
 * Service - Sitemap索引
 * 
 * @author huanghy
 * @version 6.1
 */
@Service
public class SitemapIndexServiceImpl implements SitemapIndexService {

	@Inject
	private SitemapUrlService sitemapUrlService;

	@Override
	@Transactional(readOnly = true)
	public List<SitemapIndex> generate(SitemapUrl.Type type, int maxSitemapUrlSize) {
		Assert.notNull(type, "[Assertion failed] - type is required; it must not be null");
		Assert.state(maxSitemapUrlSize >= 0, "[Assertion failed] - maxSitemapUrlSize must be equal or greater than 0");

		List<SitemapIndex> sitemapIndexs = new ArrayList<>();
		Long sitemapUrlSize = sitemapUrlService.count(type);
		for (int i = 0; i < Math.ceil((double) sitemapUrlSize / (double) maxSitemapUrlSize); i++) {
			SitemapIndex sitemapIndex = new SitemapIndex();
			sitemapIndex.setType(type);
			sitemapIndex.setIndex(i);
			sitemapIndexs.add(sitemapIndex);
		}
		return sitemapIndexs;
	}

}