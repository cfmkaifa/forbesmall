package net.mall.dao;

import net.mall.entity.ProPurchApply;

import java.util.Date;
import java.util.List;

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




    /****
     *
     * @param currentDate
     * @param applyStatus
     * @return
     */
    List<ProPurchApply> proPurchApplys(Date currentDate, ProPurchApply.ProApplyStatus applyStatus);
}
