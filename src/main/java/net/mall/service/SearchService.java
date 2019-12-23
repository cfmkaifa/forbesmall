/*
 *
 * 
 *
 * 
 */
package net.mall.service;

/**
 * Service - 搜索
 * 
 * @author huanghy
 * @version 6.1
 */
public interface SearchService {

	/**
	 * 创建索引
	 * 
	 * @param type
	 *            索引类型
	 */
	void index(Class<?> type);

	/**
	 * 创建索引
	 * 
	 * @param type
	 *            索引类型
	 * @param purgeAll
	 *            是否清空已存在索引
	 */
	void index(Class<?> type, boolean purgeAll);

}