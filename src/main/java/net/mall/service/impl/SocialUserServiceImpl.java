/*
 *
 *
 *
 *
 */
package net.mall.service.impl;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.dao.SocialUserDao;
import net.mall.entity.SocialUser;
import net.mall.entity.User;
import net.mall.service.SocialUserService;

/**
 * Service - 社会化用户
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class SocialUserServiceImpl extends BaseServiceImpl<SocialUser, Long> implements SocialUserService {

    @Inject
    private SocialUserDao socialUserDao;

    @Override
    @Transactional(readOnly = true)
    public SocialUser find(String loginPluginId, String uniqueId) {
        return socialUserDao.find(loginPluginId, uniqueId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SocialUser> findPage(User user, Pageable pageable) {
        return socialUserDao.findPage(user, pageable);
    }

    @Override
    public void bindUser(User user, SocialUser socialUser, String uniqueId) {
        Assert.notNull(socialUser, "[Assertion failed] - socialUser is required; it must not be null");
        Assert.hasText(uniqueId, "[Assertion failed] - uniqueId must have text; it must not be null, empty, or blank");

        if (!StringUtils.equals(socialUser.getUniqueId(), uniqueId) || socialUser.getUser() != null) {
            return;
        }

        socialUser.setUser(user);
    }

}