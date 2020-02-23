package net.mall.service.impl;

import net.mall.dao.ProPurchApplyDao;
import net.mall.entity.ProPurchApply;
import net.mall.service.ProPurchApplyService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;

@Service
public class ProPurchApplyServiceImpl extends BaseServiceImpl<ProPurchApply, Long> implements ProPurchApplyService {

    @Inject
    ProPurchApplyDao proPurchApplyDao;

    /****
     *
     * @param proSn
     * @param skuSn
     * @param currentDate
     * @param applyStatus
     * @return
     */
    public ProPurchApply  proPurchApply(String proSn, String skuSn, Date currentDate, ProPurchApply.ProApplyStatus applyStatus){
        return  proPurchApplyDao.proPurchApply(proSn,skuSn,currentDate,applyStatus);
    }
}
