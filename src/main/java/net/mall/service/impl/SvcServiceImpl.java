/*
 *
 *
 *
 *
 */
package net.mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import net.mall.Order;
import net.mall.dao.SnDao;
import net.mall.dao.SvcDao;
import net.mall.entity.Sn;
import net.mall.entity.Store;
import net.mall.entity.StoreRank;
import net.mall.entity.Svc;
import net.mall.service.SvcService;

/**
 * Service - 服务
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class SvcServiceImpl extends BaseServiceImpl<Svc, Long> implements SvcService {

    @Inject
    private SvcDao svcDao;
    @Inject
    private SnDao snDao;

    @Override
    @Transactional(readOnly = true)
    public Svc findBySn(String sn) {
        return svcDao.find("sn", StringUtils.lowerCase(sn));
    }

    @Override
    @Transactional(readOnly = true)
    public Svc findTheLatest(Store store, String promotionPluginId, StoreRank storeRank) {

        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order("createdDate", Order.Direction.DESC));
        List<Svc> serviceOrders = svcDao.find(store, promotionPluginId, storeRank, orderList);

        return CollectionUtils.isNotEmpty(serviceOrders) ? serviceOrders.get(0) : null;
    }

    @Override
    @Transactional
    public Svc save(Svc svc) {
        Assert.notNull(svc, "[Assertion failed] - svc is required; it must not be null");

        svc.setSn(snDao.generate(Sn.Type.PLATFORM_SERVICE));

        return super.save(svc);
    }

}