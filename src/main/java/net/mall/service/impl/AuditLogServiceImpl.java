/*
 *
 * 
 *
 * 
 */
package net.mall.service.impl;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import net.mall.dao.AuditLogDao;
import net.mall.entity.AuditLog;
import net.mall.service.AuditLogService;

/**
 * Service - 审计日志
 * 
 * @author huanghy
 * @version 6.1
 */
@Service
public class AuditLogServiceImpl extends BaseServiceImpl<AuditLog, Long> implements AuditLogService {

	@Inject
	private AuditLogDao auditLogDao;

	@Override
	@Async
	public void create(AuditLog auditLog) {
		auditLogDao.persist(auditLog);
	}

	@Override
	public void clear() {
		auditLogDao.removeAll();
	}

}