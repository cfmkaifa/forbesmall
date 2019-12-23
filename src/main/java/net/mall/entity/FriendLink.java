/*
 *
 * 
 *
 * 
 */
package net.mall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

/**
 * Entity - 友情链接
 * 
 * @author huanghy
 * @version 6.1
 */
@Entity
public class FriendLink extends OrderedEntity<Long> {

	private static final long serialVersionUID = 3019642557500517628L;

	/**
	 * 类型
	 */
	public enum Type {

		/**
		 * 文本
		 */
		TEXT,

		/**
		 * 图片
		 */
		IMAGE
	}

	/**
	 * 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/**
	 * 类型
	 */
	@NotNull
	@Column(nullable = false)
	private FriendLink.Type type;

	/**
	 * logo
	 */
	@Length(max = 200)
	@URL
	private String logo;

	/**
	 * 链接地址
	 */
	@NotEmpty
	@Length(max = 200)
	@URL
	@Column(nullable = false)
	private String url;

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	public FriendLink.Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(FriendLink.Type type) {
		this.type = type;
	}

	/**
	 * 获取logo
	 * 
	 * @return logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * 设置logo
	 * 
	 * @param logo
	 *            logo
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * 获取链接地址
	 * 
	 * @return 链接地址
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置链接地址
	 * 
	 * @param url
	 *            链接地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}