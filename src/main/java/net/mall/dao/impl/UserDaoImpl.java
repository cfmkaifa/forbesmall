/*
 *
 * 
 *
 * 
 */
package net.mall.dao.impl;

import org.springframework.stereotype.Repository;

import net.mall.dao.UserDao;
import net.mall.entity.User;

/**
 * Dao - 用户
 * 
 * @author huanghy
 * @version 6.1
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao {

}