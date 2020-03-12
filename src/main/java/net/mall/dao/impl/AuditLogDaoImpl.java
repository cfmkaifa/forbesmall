/*
 *
 *
 *
 *
 */
package net.mall.dao.impl;

import org.springframework.stereotype.Repository;

import net.mall.dao.AuditLogDao;
import net.mall.entity.AuditLog;

/**
 * Dao - 审计日志
 *
 * @author huanghy
 * @version 6.1
 */
@Repository
public class AuditLogDaoImpl extends BaseDaoImpl<AuditLog, Long> implements AuditLogDao {

    @Override
    public void removeAll() {
        String jpql = "delete from AuditLog auditLog";
        entityManager.createQuery(jpql).executeUpdate();
    }

}