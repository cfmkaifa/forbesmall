/*
 *
 * 
 *
 * 
 */
package net.mall.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.mall.dao.AdminDao;
import net.mall.entity.Admin;
import net.mall.entity.Role;
import net.mall.entity.User;
import net.mall.service.AdminService;

/**
 * Service - 管理员
 * 
 * @author huanghy
 * @version 6.1
 */
@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, Long> implements AdminService {

	@Inject
	private AdminDao adminDao;

	@Override
	@Transactional(readOnly = true)
	public Admin getUser(Object principal) {
		Assert.notNull(principal, "[Assertion failed] - principal is required; it must not be null");
		Assert.isInstanceOf(String.class, principal);

		return findByUsername(String.valueOf(principal));
	}

	@Override
	@Transactional(readOnly = true)
	public Set<String> getPermissions(User user) {
		Assert.notNull(user, "[Assertion failed] - user is required; it must not be null");
		Assert.isInstanceOf(Admin.class, user);

		Admin admin = adminDao.find(user.getId());
		Set<String> result = new HashSet<>();
		if (admin != null && admin.getRoles() != null) {
			for (Role role : admin.getRoles()) {
				if (role.getPermissions() != null) {
					result.addAll(role.getPermissions());
				}
			}
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean supports(Class<?> userClass) {
		return userClass != null && Admin.class.isAssignableFrom(userClass);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean usernameExists(String username) {
		return adminDao.exists("username", StringUtils.lowerCase(username));
	}

	@Override
	@Transactional(readOnly = true)
	public Admin findByUsername(String username) {
		return adminDao.find("username", StringUtils.lowerCase(username));
	}

	@Override
	@Transactional(readOnly = true)
	public boolean emailExists(String email) {
		return adminDao.exists("email", StringUtils.lowerCase(email));
	}

	@Override
	@Transactional(readOnly = true)
	public boolean emailUnique(Long id, String email) {
		return adminDao.unique(id, "email", StringUtils.lowerCase(email));
	}

	@Override
	@Transactional(readOnly = true)
	public Admin findByEmail(String email) {
		return adminDao.find("email", StringUtils.lowerCase(email));
	}

	@Override
	@Transactional(readOnly = true)
	public boolean mobileExists(String mobile) {
		return adminDao.exists("mobile", StringUtils.lowerCase(mobile));
	}

	@Override
	@Transactional(readOnly = true)
	public boolean mobileUnique(Long id, String mobile) {
		return adminDao.unique(id, "mobile", StringUtils.lowerCase(mobile));
	}

	@Override
	@Transactional(readOnly = true)
	public Admin findByMobile(String mobile) {
		return adminDao.find("mobile", StringUtils.lowerCase(mobile));
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public Admin save(Admin admin) {
		return super.save(admin);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public Admin update(Admin admin) {
		return super.update(admin);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public Admin update(Admin admin, String... ignoreProperties) {
		return super.update(admin, ignoreProperties);
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
	public void delete(Admin admin) {
		super.delete(admin);
	}

}