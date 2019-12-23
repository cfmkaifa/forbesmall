/*
 *
 * 
 *
 * 
 */
package net.mall.dao.impl;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import net.mall.dao.MessageConfigDao;
import net.mall.entity.MessageConfig;

/**
 * Dao - 消息配置
 * 
 * @author huanghy
 * @version 6.1
 */
@Repository
public class MessageConfigDaoImpl extends BaseDaoImpl<MessageConfig, Long> implements MessageConfigDao {

	@Override
	public MessageConfig find(MessageConfig.Type type) {
		if (type == null) {
			return null;
		}
		try {
			String jpql = "select messageConfig from MessageConfig messageConfig where messageConfig.type = :type";
			return entityManager.createQuery(jpql, MessageConfig.class).setParameter("type", type).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}