package net.mall.service;

import net.mall.entity.GroupPurchApply;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface GroupPurchApplyService extends BaseService<GroupPurchApply, Long>  {


    /***
     *
     * @param proSn
     * @param skuSn
     * @param id
     */
    void putawayGroupPurchApply(String proSn, String skuSn, BigDecimal groupPrice, Long id);


    /***
     *
     * @param proSn
     * @param skuSn
     * @param id
     */
    void soldOutGroupPurchApply(String proSn, String skuSn, Long id);


    /***
     *
     * @param applyStatus
     * @param jobStatus
     * @param currentDate
     * @return
     */
    List<GroupPurchApply>  putawayGroupPurchApply(GroupPurchApply.ApplyStatus applyStatus, GroupPurchApply.JobStatus jobStatus, Date currentDate);

    /***
     *
     * @param applyStatus
     * @param currentDate
     * @param proSn
     * @param skuSn
     * @return
     */
    GroupPurchApply  putawayGroupPurchApply(GroupPurchApply.ApplyStatus applyStatus, Date currentDate,String proSn,String skuSn);

    /***
     *
     * @param applyStatus
     * @param jobStatus
     * @param currentDate
     * @return
     */
    List<GroupPurchApply>  soldOutGroupPurchApply(GroupPurchApply.ApplyStatus applyStatus, GroupPurchApply.JobStatus jobStatus, Date currentDate);

}
