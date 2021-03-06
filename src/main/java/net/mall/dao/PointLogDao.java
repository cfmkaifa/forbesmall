/*
 *
 *
 *
 *
 */
package net.mall.dao;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.Member;
import net.mall.entity.PointLog;

/**
 * Dao - 积分记录
 *
 * @author huanghy
 * @version 6.1
 */
public interface PointLogDao extends BaseDao<PointLog, Long> {

    /**
     * 查找积分记录分页
     *
     * @param member   会员
     * @param pageable 分页信息
     * @return 积分记录分页
     */
    Page<PointLog> findPage(Member member, Pageable pageable);

}