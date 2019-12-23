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
import net.mall.dao.PointLogDao;
import net.mall.entity.Member;
import net.mall.entity.PointLog;
import net.mall.service.PointLogService;

/**
 * Service - 积分记录
 * 
 * @author huanghy
 * @version 6.1
 */
@Service
public class PointLogServiceImpl extends BaseServiceImpl<PointLog, Long> implements PointLogService {

	@Inject
	private PointLogDao pointLogDao;

	@Override
	@Transactional(readOnly = true)
	public Page<PointLog> findPage(Member member, Pageable pageable) {
		return pointLogDao.findPage(member, pageable);
	}

}