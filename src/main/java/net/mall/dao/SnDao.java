/*
 *
 * 
 *
 * 
 */
package net.mall.dao;

import net.mall.entity.Sn;

/**
 * Dao - 序列号
 * 
 * @author huanghy
 * @version 6.1
 */
public interface SnDao {

	/**
	 * 生成序列号
	 * 
	 * @param type
	 *            类型
	 * @return 序列号
	 */
	String generate(Sn.Type type);

}