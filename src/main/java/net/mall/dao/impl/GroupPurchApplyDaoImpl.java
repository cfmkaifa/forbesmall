package net.mall.dao.impl;

import net.mall.dao.GroupPurchApplyDao;
import net.mall.entity.GroupPurchApply;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class GroupPurchApplyDaoImpl extends BaseDaoImpl<GroupPurchApply, Long> implements GroupPurchApplyDao {


    public void modyGroupPurchJobStatus(GroupPurchApply.JobStatus jobStatus, Long id) {
        String jpql = "UPDATE GroupPurchApply SET jobStatus = :jobStatus WHERE id = :id";
        entityManager.createQuery(jpql).setParameter("jobStatus", jobStatus).setParameter("id", id).executeUpdate();
    }


    /***
     *
     * @param applyStatus
     * @param jobStatus
     * @param currentDate
     * @return
     */
    public List<GroupPurchApply> putawayGroupPurchApply(GroupPurchApply.ApplyStatus applyStatus, GroupPurchApply.JobStatus jobStatus, Date currentDate) {
        String jpql = " SELECT pal FROM GroupPurchApply  pal WHERE pal.status = :status  AND pal.jobStatus = :jobStatus AND pal.startTime <= :startTime AND pal.endTime >= :endTime";
        return entityManager.createQuery(jpql, GroupPurchApply.class)
                .setParameter("status", applyStatus)
                .setParameter("jobStatus", jobStatus)
                .setParameter("startTime", currentDate)
                .setParameter("endTime", currentDate)
                .getResultList();
    }


    /***
     *
     * @param applyStatus
     * @param currentDate
     * @param proSn
     * @param skuSn
     * @return
     */
    public GroupPurchApply putawayGroupPurchApply(GroupPurchApply.ApplyStatus applyStatus, Date currentDate, String proSn, String skuSn) {
        String jpql = " SELECT pal FROM GroupPurchApply  pal WHERE pal.status = :status  AND pal.proSn = :proSn AND pal.skuSn = :skuSn AND pal.startTime <= :startTime AND pal.endTime >= :endTime";
        return entityManager.createQuery(jpql, GroupPurchApply.class)
                .setParameter("status", applyStatus)
                .setParameter("proSn", proSn)
                .setParameter("skuSn", skuSn)
                .setParameter("startTime", currentDate)
                .setParameter("endTime", currentDate)
                .getSingleResult();
    }


    /***
     *
     * @param applyStatus
     * @param jobStatus
     * @param currentDate
     * @return
     */
    public List<GroupPurchApply> soldOutGroupPurchApply(GroupPurchApply.ApplyStatus applyStatus, GroupPurchApply.JobStatus jobStatus, Date currentDate) {
        String jpql = " SELECT pal FROM GroupPurchApply  pal WHERE pal.status = :status  AND pal.jobStatus = :jobStatus  AND pal.endTime < :endTime";
        return entityManager.createQuery(jpql, GroupPurchApply.class)
                .setParameter("status", applyStatus)
                .setParameter("jobStatus", jobStatus)
                .setParameter("endTime", currentDate)
                .getResultList();
    }
}
