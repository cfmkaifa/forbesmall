/*
 *
 * 
 *
 * 
 */
package net.mall.dao;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.MessageGroup;
import net.mall.entity.User;

/**
 * Dao - 消息组
 * 
 * @author huanghy
 * @version 6.1
 */
public interface MessageGroupDao extends BaseDao<MessageGroup, Long> {

	/**
	 * 查找
	 * 
	 * @param user
	 *            用户
	 * @param pageable
	 *            分页信息
	 * @return 消息组分页
	 */
	Page<MessageGroup> findPage(User user, Pageable pageable);

	/**
	 * 查找
	 * 
	 * @param user1
	 *            用户1
	 * @param user2
	 *            用户2
	 * @return 消息组
	 */
	MessageGroup find(User user1, User user2);

}