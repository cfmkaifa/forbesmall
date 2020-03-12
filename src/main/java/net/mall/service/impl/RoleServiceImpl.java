/*
 *
 *
 *
 *
 */
package net.mall.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.mall.entity.Role;
import net.mall.service.RoleService;

/**
 * Service - 角色
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService {

    @Override
    @Transactional
    @CacheEvict(value = "authorization", allEntries = true)
    public Role save(Role role) {
        return super.save(role);
    }

    @Override
    @Transactional
    @CacheEvict(value = "authorization", allEntries = true)
    public Role update(Role role) {
        return super.update(role);
    }

    @Override
    @Transactional
    @CacheEvict(value = "authorization", allEntries = true)
    public Role update(Role role, String... ignoreProperties) {
        return super.update(role, ignoreProperties);
    }

    @Override
    @Transactional
    @CacheEvict(value = "authorization", allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "authorization", allEntries = true)
    public void delete(Long... ids) {
        super.delete(ids);
    }

    @Override
    @Transactional
    @CacheEvict(value = "authorization", allEntries = true)
    public void delete(Role role) {
        super.delete(role);
    }

}