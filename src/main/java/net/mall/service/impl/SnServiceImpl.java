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

import net.mall.dao.SnDao;
import net.mall.entity.Sn;
import net.mall.service.SnService;

/**
 * Service - 序列号
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class SnServiceImpl implements SnService {

    @Inject
    private SnDao snDao;

    @Override
    @Transactional
    public String generate(Sn.Type type) {
        return snDao.generate(type);
    }

}