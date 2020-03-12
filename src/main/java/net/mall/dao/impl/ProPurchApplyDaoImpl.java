package net.mall.dao.impl;

import net.mall.dao.ProPurchApplyDao;
import net.mall.entity.ProPurchApply;
import net.mall.util.ConvertUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository
public class ProPurchApplyDaoImpl extends BaseDaoImpl<ProPurchApply, Long> implements ProPurchApplyDao {


    /****
     *
     * @param proSn
     * @param skuSn
     * @param currentDate
     * @param applyStatus
     * @return
     */
    public ProPurchApply proPurchApply(String proSn, String skuSn, Date currentDate, ProPurchApply.ProApplyStatus applyStatus) {
        String jpql = "SELECT pal FROM ProPurchApply  pal WHERE pal.status = :status AND pal.proSn = :proSn  AND pal.skuSn = :skuSn AND pal.startTime <= :startTime AND pal.endTime >= :endTime";
        Query query = entityManager.createQuery(jpql, ProPurchApply.class);
        query.setParameter("proSn", proSn);
        query.setParameter("skuSn", skuSn);
        query.setParameter("status", applyStatus);
        query.setParameter("startTime", currentDate);
        query.setParameter("endTime", currentDate);
        List<ProPurchApply> proPurchApplys = query.getResultList();
        if (ConvertUtils.isNotEmpty(proPurchApplys)) {
            return proPurchApplys.get(0);
        } else {
            return null;
        }
    }


    /****
     *
     * @param currentDate
     * @param applyStatus
     * @return
     */
    public List<ProPurchApply> proPurchApplys(Date currentDate, ProPurchApply.ProApplyStatus applyStatus) {
        String jpql = " SELECT pal FROM ProPurchApply  pal WHERE pal.status = :status  AND pal.startTime <= :startTime AND pal.endTime >= :endTime";
        return entityManager.createQuery(jpql, ProPurchApply.class)
                .setParameter("status", applyStatus)
                .setParameter("startTime", currentDate)
                .setParameter("endTime", currentDate)
                .getResultList();
    }
}
