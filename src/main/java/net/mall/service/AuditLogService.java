/*
 *
 * 
 *
 * 
 */
package net.mall.service;

import net.mall.entity.AuditLog;

/**
 * Service - 审计日志
 * 
 * @author huanghy
 * @version 6.1
 */
public interface AuditLogService extends BaseService<AuditLog, Long> {

	/**
	 * 创建审计日志(异步)
	 * 
	 * @param auditLog
	 *            审计日志
	 */
	void create(AuditLog auditLog);

	/**
	 * 清空审计日志
	 */
	void clear();

}