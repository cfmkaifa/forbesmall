/*
 *
 * 
 *
 * 
 */
package net.mall.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mall.plugin.PromotionPlugin;

/**
 * PromotionPlugin - 促销插件Provider
 * 
 * @author huanghy
 * @version 6.1
 */
public interface PromotionPluginProvider {

	/**
	 * 促销插件
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param handler
	 *            处理器
	 * @return 促销插件
	 */
	PromotionPlugin promotionPlugin(HttpServletRequest request, HttpServletResponse response, Object handler);

}