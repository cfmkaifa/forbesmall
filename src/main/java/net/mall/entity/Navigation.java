/*
 *
 *
 *
 *
 */
package net.mall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

/**
 * Entity - 导航
 *
 * @author huanghy
 * @version 6.1
 */
@Entity
public class Navigation extends OrderedEntity<Long> {

    private static final long serialVersionUID = -7635757647887646795L;

    /**
     * 名称
     */
    @NotEmpty
    @Length(max = 200)
    @Column(nullable = false)
    private String name;

    /**
     * 链接地址
     */
    @NotEmpty
    @Length(max = 200)
    @URL
    @Column(nullable = false)
    private String url;

    /**
     * 是否新窗口打开
     */
    @NotNull
    @Column(nullable = false)
    private Boolean isBlankTarget;

    /**
     * 导航组
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private NavigationGroup navigationGroup;

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
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
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
     * @param url 链接地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取是否新窗口打开
     *
     * @return 是否新窗口打开
     */
    public Boolean getIsBlankTarget() {
        return isBlankTarget;
    }

    /**
     * 设置是否新窗口打开
     *
     * @param isBlankTarget 是否新窗口打开
     */
    public void setIsBlankTarget(Boolean isBlankTarget) {
        this.isBlankTarget = isBlankTarget;
    }

    /**
     * 获取导航组
     *
     * @return 导航组
     */
    public NavigationGroup getNavigationGroup() {
        return navigationGroup;
    }

    /**
     * 设置导航组
     *
     * @param navigationGroup 导航组
     */
    public void setNavigationGroup(NavigationGroup navigationGroup) {
        this.navigationGroup = navigationGroup;
    }

}