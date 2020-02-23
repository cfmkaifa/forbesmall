package net.mall.dao;

import net.mall.entity.ProPurchApply;

import java.util.Date;

public interface ProPurchApplyDao extends BaseDao<ProPurchApply, Long>  {

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
