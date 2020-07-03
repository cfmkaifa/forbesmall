package net.mall.job;

import net.mall.Filter;
import net.mall.entity.GroupPurchApply;
import net.mall.entity.Product;
import net.mall.entity.Sku;
import net.mall.service.GroupPurchApplyService;
import net.mall.service.ProductService;
import net.mall.service.SkuService;
import net.mall.util.ConvertUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/***
 * 团购审核定时任务
 */
//@Component
//@Lazy(false)
public class GroupPurchJob {


    private ReentrantReadWriteLock PUTAWAY_LOCK = new ReentrantReadWriteLock();
    private int MAX_COUNT = 200;


    private ReentrantReadWriteLock SOLD_OUT_LOCK = new ReentrantReadWriteLock();

    @Inject
    GroupPurchApplyService groupPurchApplyService;


    /***团购申请上架
     */
    @Scheduled(cron = "${job.putaway_group_purch_apply.cron}")
    public void putawayGroupPurchApply() {
        if (PUTAWAY_LOCK.writeLock().tryLock()) {
            Date currentDate = new Date();
            List<GroupPurchApply> groupPurchApplys = groupPurchApplyService.putawayGroupPurchApply(GroupPurchApply.ApplyStatus.APPROVED, GroupPurchApply.JobStatus.PENDING, currentDate);
            if (ConvertUtils.isNotEmpty(groupPurchApplys)) {
                groupPurchApplys.forEach(groupPurchApply -> {
                    String proSn = groupPurchApply.getProSn();
                    String skuSn = groupPurchApply.getSkuSn();
                    groupPurchApplyService.putawayGroupPurchApply(proSn, skuSn, groupPurchApply.getGroupPrice(), groupPurchApply.getId());
                });
            }
            PUTAWAY_LOCK.writeLock().unlock();
        }
    }


    /***团购申请上架
     */
    @Scheduled(cron = "${job.sold_out_group_purch_apply.cron}")
    public void soldOutGroupPurchApply() {
        if (SOLD_OUT_LOCK.writeLock().tryLock()) {
            Date currentDate = new Date();
            List<GroupPurchApply> groupPurchApplys = groupPurchApplyService.soldOutGroupPurchApply(GroupPurchApply.ApplyStatus.APPROVED, GroupPurchApply.JobStatus.HASBEENON, currentDate);
            if (ConvertUtils.isNotEmpty(groupPurchApplys)) {
                groupPurchApplys.forEach(groupPurchApply -> {
                    String proSn = groupPurchApply.getProSn();
                    String skuSn = groupPurchApply.getSkuSn();
                    groupPurchApplyService.soldOutGroupPurchApply(proSn, skuSn, groupPurchApply.getId());
                });
            }
            SOLD_OUT_LOCK.writeLock().unlock();
        }
    }
}
