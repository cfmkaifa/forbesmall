package net.mall.dao.impl;

import net.mall.dao.ProPurchApplyDao;
import net.mall.entity.ProPurchApply;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ProPurchApplyDaoImpl  extends BaseDaoImpl<ProPurchApply, Long> implements ProPurchApplyDao {



    /****
     *
     * @param proSn
     * @param skuSn
     * @param currentDate
     * @param applyStatus
     * @return
     */
   public  ProPurchApply  proPurchApply(String proSn, String skuSn, Date currentDate, ProPurchApply.ProApplyStatus applyStatus){
       String jpql = " SELECT pal FROM ProPurchApply  pal WHERE pal.status = :status and pal.proSn = :proSn  and pal.skuSn = :skuSn AND pal.startTime <= :startTime AND pal.endTime >= :endTime";
       return entityManager.createQuery(jpql, ProPurchApply.class)
               .setParameter("proSn",proSn)
               .setParameter("skuSn",skuSn)
               .setParameter("status",applyStatus)
               .setParameter("startTime",currentDate)
               .setParameter("endTime",currentDate)
               .getSingleResult();
    }


    /****
     *
     * @param currentDate
     * @param applyStatus
     * @return
     */
    public List<ProPurchApply> proPurchApplys(Date currentDate, ProPurchApply.ProApplyStatus applyStatus){
        String jpql = " SELECT pal FROM ProPurchApply  pal WHERE pal.status = :status  AND pal.startTime <= :startTime AND pal.endTime >= :endTime";
        return entityManager.createQuery(jpql, ProPurchApply.class)
                .setParameter("status",applyStatus)
                .setParameter("startTime",currentDate)
                .setParameter("endTime",currentDate)
                .getResultList();
    }
}
