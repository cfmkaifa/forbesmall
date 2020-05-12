package net.mall.job;

import net.mall.Filter;
import net.mall.entity.Product;
import net.mall.service.ProductService;
import net.mall.util.ConvertUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/***到期自动下架
 */
@Component
@Lazy(false)
public class ProPurchJob {



    private ReentrantReadWriteLock PUTAWAY_LOCK = new ReentrantReadWriteLock();
    @Inject
    ProductService productService;
    private int MAX_COUNT = 200;

    /***团购申请上架
     */
    @Scheduled(cron = "${job.putaway_purch_apply.cron}")
    public void putawayGroupPurchApply() {
        if (PUTAWAY_LOCK.writeLock().tryLock()) {
            List<Filter> filters = new ArrayList<Filter>();
            filters.add(new Filter("isPurch", Filter.Operator.EQ,true));
            filters.add(new Filter("isAudit", Filter.Operator.EQ, Product.ProApplyStatus.APPROVED));
            filters.add(new Filter("expire", Filter.Operator.GE, new Date()));
           List<Product> products = productService.findList(MAX_COUNT,filters,null);
           if(ConvertUtils.isNotEmpty(products)){
               products.forEach(product->{
                   productService.modifyMarketable(false,product.getId());
               });
           }
            PUTAWAY_LOCK.writeLock().unlock();
        }
    }
}
