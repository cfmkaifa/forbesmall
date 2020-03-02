/*
 *
 *
 *
 *
 */
package net.mall.service;

import net.mall.entity.Admin;
import net.mall.security.AuthenticationProvider;

/**
 * Service - 管理员
 *
 * @author huanghy
 * @version 6.1
 */
public interface AdminService extends BaseService<Admin, Long>, AuthenticationProvider {

    /**
     * 判断用户名是否存在
     *
     * @param username 用户名(忽略大小写)
     * @return 用户名是否存在
     */
    boolean usernameExists(String username);

    /**
     * 根据用户名查找管理员
     *
     * @param username 用户名(忽略大小写)
     * @return 管理员，若不存在则返回null
     */
    Admin findByUsername(String username);

    /**
     * 判断E-mail是否存在
     *
     * @param email E-mail(忽略大小写)
     * @return E-mail是否存在
     */
    boolean emailExists(String email);

    /**
     * 判断E-mail是否唯一
     *
     * @param id    ID
     * @param email E-mail(忽略大小写)
     * @return E-mail是否唯一
     */
    boolean emailUnique(Long id, String email);

    /**
     * 根据E-mail查找管理员
     *
     * @param email E-mail(忽略大小写)
     * @return 管理员，若不存在则返回null
     */
    Admin findByEmail(String email);

    /**
     * 判断手机是否存在
     *
     * @param mobile 手机(忽略大小写)
     * @return 手机是否存在
     */
    boolean mobileExists(String mobile);

    /**
     * 判断手机是否唯一
     *
     * @param id     ID
     * @param mobile 手机(忽略大小写)
     * @return 手机是否唯一
     */
    boolean mobileUnique(Long id, String mobile);

    /**
     * 根据手机查找管理员
     *
     * @param mobile 手机(忽略大小写)
     * @return 会员，若不存在则返回null
     */
    Admin findByMobile(String mobile);

}