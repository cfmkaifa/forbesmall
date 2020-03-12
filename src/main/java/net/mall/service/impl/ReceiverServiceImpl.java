/*
 *
 *
 *
 *
 */
package net.mall.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.dao.ReceiverDao;
import net.mall.entity.Member;
import net.mall.entity.Receiver;
import net.mall.service.ReceiverService;

/**
 * Service - 收货地址
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class ReceiverServiceImpl extends BaseServiceImpl<Receiver, Long> implements ReceiverService {

    @Inject
    private ReceiverDao receiverDao;

    @Override
    @Transactional(readOnly = true)
    public Receiver findDefault(Member member) {
        return receiverDao.findDefault(member);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Receiver> findList(Member member) {
        return receiverDao.findList(member);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Receiver> findPage(Member member, Pageable pageable) {
        return receiverDao.findPage(member, pageable);
    }

    @Override
    @Transactional
    public Receiver save(Receiver receiver) {
        Assert.notNull(receiver, "[Assertion failed] - receiver is required; it must not be null");

        if (BooleanUtils.isTrue(receiver.getIsDefault()) && receiver.getMember() != null) {
            receiverDao.clearDefault(receiver.getMember());
        }
        return super.save(receiver);
    }

    @Override
    @Transactional
    public Receiver update(Receiver receiver) {
        Assert.notNull(receiver, "[Assertion failed] - receiver is required; it must not be null");

        Receiver pReceiver = super.update(receiver);
        if (BooleanUtils.isTrue(pReceiver.getIsDefault()) && pReceiver.getMember() != null) {
            receiverDao.clearDefault(pReceiver.getMember(), pReceiver);
        }
        return pReceiver;
    }

}