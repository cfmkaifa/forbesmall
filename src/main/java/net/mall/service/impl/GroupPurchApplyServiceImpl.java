package net.mall.service.impl;

import net.mall.dao.GroupPurchApplyDao;
import net.mall.dao.ProductDao;
import net.mall.dao.SkuDao;
import net.mall.entity.GroupPurchApply;
import net.mall.service.GroupPurchApplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class GroupPurchApplyServiceImpl extends BaseServiceImpl<GroupPurchApply, Long> implements GroupPurchApplyService {

    @Inject
    private GroupPurchApplyDao groupPurchApplyDao;
    @Inject
    private ProductDao productDao;
    @Inject
    private SkuDao skuDao;


    @Override
    @Transactional
    public void putawayGroupPurchApply(String proSn, String skuSn, BigDecimal groupPrice, Long id) {
        productDao.modifyProductGroupPurch(true, proSn);
        skuDao.modifySkuGroupPrice(groupPrice, true, skuSn);
        groupPurchApplyDao.modyGroupPurchJobStatus(GroupPurchApply.JobStatus.HASBEENON, id);
    }

    @Override
    @Transactional
    public void soldOutGroupPurchApply(String proSn, String skuSn, Long id) {
        productDao.modifyProductGroupPurch(false, proSn);
        skuDao.modifySkuGroupPrice(null, false, skuSn);
        groupPurchApplyDao.modyGroupPurchJobStatus(GroupPurchApply.JobStatus.ALREADY, id);
    }


    /***
     *
     * @param applyStatus
     * @param jobStatus
     * @param currentDate
     * @return
     */
    @Override
    public List<GroupPurchApply> putawayGroupPurchApply(GroupPurchApply.ApplyStatus applyStatus, GroupPurchApply.JobStatus jobStatus, Date currentDate) {
        return groupPurchApplyDao.putawayGroupPurchApply(applyStatus, jobStatus, currentDate);
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
        return groupPurchApplyDao.putawayGroupPurchApply(applyStatus, currentDate, proSn, skuSn);
    }

    /***
     *
     * @param applyStatus
     * @param jobStatus
     * @param currentDate
     * @return
     */
    @Override
    public List<GroupPurchApply> soldOutGroupPurchApply(GroupPurchApply.ApplyStatus applyStatus, GroupPurchApply.JobStatus jobStatus, Date currentDate) {
        return groupPurchApplyDao.soldOutGroupPurchApply(applyStatus, jobStatus, currentDate);
    }
}
