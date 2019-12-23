/*
 *
 * 
 *
 * 
 */
package net.mall.controller.shop;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import net.mall.entity.SitemapIndex;
import net.mall.entity.SitemapUrl;
import net.mall.service.SitemapIndexService;
import net.mall.service.SitemapUrlService;

/**
 * Controller - Sitemap
 * 
 * @author huanghy
 * @version 6.1
 */
@Controller("shopSitemapController")
@RequestMapping("/sitemap")
public class SitemapController extends BaseController {

	/**
	 * 最大Sitemap URL数量
	 */
	private static final Integer MAX_SITEMAP_URL_SIZE = 10000;

	/**
	 * 更新频率
	 */
	private static final SitemapUrl.Changefreq CHANGEFREQ = SitemapUrl.Changefreq.DAILY;

	/**
	 * 权重
	 */
	private static final float PRIORITY = 0.6F;

	@Value("${xml_content_type}")
	private String xmlContentType;

	@Inject
	private SitemapIndexService sitemapIndexService;
	@Inject
	private SitemapUrlService sitemapUrlService;

	/**
	 * 索引
	 */
	@GetMapping("/index.xml")
	public String index(HttpServletResponse response, ModelMap model) {
		List<SitemapIndex> sitemapIndexs = new ArrayList<>();
		sitemapIndexs.addAll(sitemapIndexService.generate(SitemapUrl.Type.ARTICLE, MAX_SITEMAP_URL_SIZE));
		sitemapIndexs.addAll(sitemapIndexService.generate(SitemapUrl.Type.PRODUCT, MAX_SITEMAP_URL_SIZE));
		model.put("sitemapIndexs", sitemapIndexs);
		response.setContentType(xmlContentType);
		return "shop/sitemap/index";
	}

	/**
	 * URL
	 */
	@GetMapping("/{type}/{index}.xml")
	public String url(@PathVariable SitemapUrl.Type type, @PathVariable Integer index, HttpServletResponse response, ModelMap model) {
		model.addAttribute("sitemapUrls", sitemapUrlService.generate(type, CHANGEFREQ, PRIORITY, index * MAX_SITEMAP_URL_SIZE, MAX_SITEMAP_URL_SIZE));
		response.setContentType(xmlContentType);
		return "shop/sitemap/url";
	}

}