package net.mall.dao;

import net.mall.entity.GroupPurchApply;

import java.util.Date;
import java.util.List;

public interface GroupPurchApplyDao extends BaseDao<GroupPurchApply, Long> {


    /***
     *
     * @param jobStatus
     * @param id
     */
    void modyGroupPurchJobStatus(GroupPurchApply.JobStatus jobStatus, Long id);


    /***
     *
     * @param applyStatus
     * @param jobStatus
     * @param currentDate
     * @return
     */
    List<GroupPurchApply> putawayGroupPurchApply(GroupPurchApply.ApplyStatus applyStatus, GroupPurchApply.JobStatus jobStatus, Date currentDate);


    /***
     *
     * @param applyStatus
     * @param currentDate
     * @param proSn
     * @param skuSn
     * @return
     */
    GroupPurchApply putawayGroupPurchApply(GroupPurchApply.ApplyStatus applyStatus, Date currentDate, String proSn, String skuSn);

    /***
     *
     * @param applyStatus
     * @param jobStatus
     * @param currentDate
     * @return
     */
    List<GroupPurchApply> soldOutGroupPurchApply(GroupPurchApply.ApplyStatus applyStatus, GroupPurchApply.JobStatus jobStatus, Date currentDate);
}
