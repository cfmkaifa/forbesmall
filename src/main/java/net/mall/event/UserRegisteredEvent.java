/*
 *
 * 
 *
 * 
 */
package net.mall.event;

import net.mall.entity.User;

/**
 * Event - 用户注册
 * 
 * @author huanghy
 * @version 6.1
 */
public class UserRegisteredEvent extends UserEvent {

	private static final long serialVersionUID = 3930447081075693577L;

	/**
	 * 构造方法
	 * 
	 * @param source
	 *            事件源
	 * @param user
	 *            用户
	 */
	public UserRegisteredEvent(Object source, User user) {
		super(source, user);
	}

}