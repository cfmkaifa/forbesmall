package net.mall.service;

import net.mall.entity.ProPurchApply;

import java.util.Date;

public interface ProPurchApplyService  extends BaseService<ProPurchApply, Long> {


    /****
     *
     * @param proSn
     * @param skuSn
     * @param currentDate
     * @param applyStatus
     * @return
     */
    ProPurchApply  proPurchApply(String proSn, String skuSn, Date currentDate, ProPurchApply.ProApplyStatus applyStatus);
}
