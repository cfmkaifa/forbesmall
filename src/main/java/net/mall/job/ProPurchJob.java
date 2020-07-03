package net.mall.job;

import net.mall.Filter;
import net.mall.entity.GroupPurchApply;
import net.mall.entity.Product;
import net.mall.service.ProductService;
import net.mall.util.ConvertUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/***到期自动下架
 */
//@Component
//@Lazy(false)
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
             try{
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
                List<Product> products = productService.searchProApply(true,Product.ProApplyStatus.APPROVED,true,sdf.parse(sdf.format(new Date())));
                if(ConvertUtils.isNotEmpty(products)){
                    products.forEach(product->{
                        productService.modifyMarketable(false,product.getId());
                    });
                }
            }catch(Exception e){
                 e.printStackTrace();
            }
            PUTAWAY_LOCK.writeLock().unlock();
        }
    }
}
