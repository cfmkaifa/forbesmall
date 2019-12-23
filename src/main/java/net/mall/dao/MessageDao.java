/*
 *
 * 
 *
 * 
 */
package net.mall.dao;

import java.util.List;

import net.mall.entity.Message;
import net.mall.entity.MessageGroup;
import net.mall.entity.User;

/**
 * Dao - 消息
 * 
 * @author huanghy
 * @version 6.1
 */
public interface MessageDao extends BaseDao<Message, Long> {

	/**
	 * 查找
	 * 
	 * @param messageGroup
	 *            消息组
	 * @param user
	 *            用户
	 * @return 消息
	 */
	List<Message> findList(MessageGroup messageGroup, User user);

	/**
	 * 未读消息数量
	 * 
	 * @param messageGroup
	 *            消息组
	 * @param user
	 *            用户
	 * @return 未读消息数量
	 */
	Long unreadMessageCount(MessageGroup messageGroup, User user);

}