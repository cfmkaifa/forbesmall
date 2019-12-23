/*
 *
 * 
 *
 * 
 */
package net.mall.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Entity - 消息组
 * 
 * @author huanghy
 * @version 6.1
 */
@Entity
public class MessageGroup extends BaseEntity<Long> {

	private static final long serialVersionUID = -7049435815884077484L;

	/**
	 * 用户1
	 */
	@JsonView(BaseView.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private User user1;

	/**
	 * 用户2
	 */
	@JsonView(BaseView.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private User user2;

	/**
	 * 用户1消息状态
	 */
	@JsonView(BaseView.class)
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "isRead", column = @Column(name = "user1MessageStatusIsRead", nullable = false)), @AttributeOverride(name = "isDeleted", column = @Column(name = "user1MessageStatusIsDeleted", nullable = false)) })
	private MessageStatus user1MessageStatus;

	/**
	 * 用户2消息状态
	 */
	@JsonView(BaseView.class)
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "isRead", column = @Column(name = "user2MessageStatusIsRead", nullable = false)), @AttributeOverride(name = "isDeleted", column = @Column(name = "user2MessageStatusIsDeleted", nullable = false)) })
	private MessageStatus user2MessageStatus;

	/**
	 * 消息
	 */
	@OneToMany(mappedBy = "messageGroup", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<Message> messages = new HashSet<>();

	/**
	 * 获取用户1
	 * 
	 * @return 用户1
	 */
	public User getUser1() {
		return user1;
	}

	/**
	 * 设置用户1
	 * 
	 * @param user1
	 *            用户1
	 */
	public void setUser1(User user1) {
		this.user1 = user1;
	}

	/**
	 * 获取用户2
	 * 
	 * @return 用户2
	 */
	public User getUser2() {
		return user2;
	}

	/**
	 * 设置用户2
	 * 
	 * @param user2
	 *            用户2
	 */
	public void setUser2(User user2) {
		this.user2 = user2;
	}

	/**
	 * 获取用户1消息状态
	 * 
	 * @return 用户1消息状态
	 */
	public MessageStatus getUser1MessageStatus() {
		return user1MessageStatus;
	}

	/**
	 * 设置用户1消息状态
	 * 
	 * @param user1MessageStatus
	 *            用户1消息状态
	 */
	public void setUser1MessageStatus(MessageStatus user1MessageStatus) {
		this.user1MessageStatus = user1MessageStatus;
	}

	/**
	 * 获取用户2消息状态
	 * 
	 * @return 用户2消息状态
	 */
	public MessageStatus getUser2MessageStatus() {
		return user2MessageStatus;
	}

	/**
	 * 设置用户2消息状态
	 * 
	 * @param user2MessageStatus
	 *            用户2消息状态
	 */
	public void setUser2MessageStatus(MessageStatus user2MessageStatus) {
		this.user2MessageStatus = user2MessageStatus;
	}

	/**
	 * 获取消息
	 * 
	 * @return 消息
	 */
	public Set<Message> getMessages() {
		return messages;
	}

	/**
	 * 设置消息
	 * 
	 * @param messages
	 *            消息
	 */
	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

}