/*
 *
 *
 *
 *
 */
package net.mall.event;

import net.mall.entity.User;

/**
 * Event - 用户注销
 *
 * @author huanghy
 * @version 6.1
 */
public class UserLoggedOutEvent extends UserEvent {

    private static final long serialVersionUID = 8560275705072178478L;

    /**
     * 构造方法
     *
     * @param source 事件源
     * @param user   用户
     */
    public UserLoggedOutEvent(Object source, User user) {
        super(source, user);
    }

}