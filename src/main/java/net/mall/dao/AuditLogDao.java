/*
 *
 *
 *
 *
 */
package net.mall.dao;

import net.mall.entity.AuditLog;

/**
 * Dao - 审计日志
 *
 * @author huanghy
 * @version 6.1
 */
public interface AuditLogDao extends BaseDao<AuditLog, Long> {

    /**
     * 删除所有
     */
    void removeAll();

}