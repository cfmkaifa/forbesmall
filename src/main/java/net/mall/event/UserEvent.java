/*
 *
 *
 *
 *
 */
package net.mall.event;

import org.springframework.context.ApplicationEvent;

import net.mall.entity.User;

/**
 * Event - 用户
 *
 * @author huanghy
 * @version 6.1
 */
public abstract class UserEvent extends ApplicationEvent {

    private static final long serialVersionUID = 7432438231982667326L;

    /**
     * 用户
     */
    private User user;

    /**
     * 构造方法
     *
     * @param source 事件源
     * @param user   用户
     */
    public UserEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    /**
     * 获取用户
     *
     * @return 用户
     */
    public User getUser() {
        return user;
    }

}