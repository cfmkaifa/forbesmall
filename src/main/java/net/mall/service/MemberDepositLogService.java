/*
 *
 *
 *
 *
 */
package net.mall.service;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.entity.Member;
import net.mall.entity.MemberDepositLog;

/**
 * Service - 会员预存款记录
 *
 * @author huanghy
 * @version 6.1
 */
public interface MemberDepositLogService extends BaseService<MemberDepositLog, Long> {

    /**
     * 查找会员预存款记录分页
     *
     * @param member   会员
     * @param pageable 分页信息
     * @return 会员预存款记录分页
     */
    Page<MemberDepositLog> findPage(Member member, Pageable pageable);

}