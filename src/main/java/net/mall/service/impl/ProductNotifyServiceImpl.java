/*
 *
 *
 *
 *
 */
package net.mall.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.mall.Page;
import net.mall.Pageable;
import net.mall.dao.ProductNotifyDao;
import net.mall.entity.Member;
import net.mall.entity.ProductNotify;
import net.mall.entity.Sku;
import net.mall.entity.Store;
import net.mall.service.MailService;
import net.mall.service.ProductNotifyService;

/**
 * Service - 到货通知
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class ProductNotifyServiceImpl extends BaseServiceImpl<ProductNotify, Long> implements ProductNotifyService {

    @Inject
    private ProductNotifyDao productNotifyDao;
    @Inject
    private MailService mailService;

    @Override
    @Transactional(readOnly = true)
    public boolean exists(Sku sku, String email) {
        return productNotifyDao.exists(sku, email);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductNotify> findPage(Store store, Member member, Boolean isMarketable, Boolean isOutOfStock, Boolean hasSent, Pageable pageable) {
        return productNotifyDao.findPage(store, member, isMarketable, isOutOfStock, hasSent, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Long count(Member member, Boolean isMarketable, Boolean isOutOfStock, Boolean hasSent) {
        return productNotifyDao.count(member, isMarketable, isOutOfStock, hasSent);
    }

    @Override
    public int send(List<ProductNotify> productNotifies) {
        if (CollectionUtils.isEmpty(productNotifies)) {
            return 0;
        }

        int count = 0;
        for (ProductNotify productNotify : productNotifies) {
            if (productNotify != null && StringUtils.isNotEmpty(productNotify.getEmail())) {
                mailService.sendProductNotifyMail(productNotify);
                productNotify.setHasSent(true);
                count++;
            }
        }
        return count;
    }

}