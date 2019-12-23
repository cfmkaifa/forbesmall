/*
 *
 * 
 *
 * 
 */
package net.mall.dao;

import java.util.List;

import net.mall.entity.FriendLink;

/**
 * Dao - 友情链接
 * 
 * @author huanghy
 * @version 6.1
 */
public interface FriendLinkDao extends BaseDao<FriendLink, Long> {

	/**
	 * 查找友情链接
	 * 
	 * @param type
	 *            类型
	 * @return 友情链接
	 */
	List<FriendLink> findList(FriendLink.Type type);

}