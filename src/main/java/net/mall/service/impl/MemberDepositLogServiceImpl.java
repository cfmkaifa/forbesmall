/*
 *
 * 
 *
 * 
 */
package net.mall.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.dao.MemberDepositLogDao;
import net.mall.entity.Member;
import net.mall.entity.MemberDepositLog;
import net.mall.service.MemberDepositLogService;

/**
 * Service - 会员预存款记录
 * 
 * @author huanghy
 * @version 6.1
 */
@Service
public class MemberDepositLogServiceImpl extends BaseServiceImpl<MemberDepositLog, Long> implements MemberDepositLogService {

	@Inject
	private MemberDepositLogDao memberDepositLogDao;

	@Override
	@Transactional(readOnly = true)
	public Page<MemberDepositLog> findPage(Member member, Pageable pageable) {
		return memberDepositLogDao.findPage(member, pageable);
	}

}