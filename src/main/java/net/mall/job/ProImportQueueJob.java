package net.mall.job;

import net.mall.service.ProImportQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
@Lazy(false)
public class ProImportQueueJob {



    private ReentrantReadWriteLock PRO_IMPORT_QUEUE_LOCK = new ReentrantReadWriteLock();
    @Autowired
    ProImportQueueService proImportQueueService;


    /***
     * 定时爬取商品
     */
    @Scheduled(cron = "${job.pro_import_queue.cron}")
    public void proImportQueueTask() {
        boolean isLocked = false;
       try {
           if (PRO_IMPORT_QUEUE_LOCK.writeLock().tryLock()) {
               isLocked = true;
               proImportQueueService.proImportTask();
           }
       }catch (Exception e){
           e.printStackTrace();
       } finally {
           if(isLocked){
               PRO_IMPORT_QUEUE_LOCK.writeLock().unlock();
           }
       }
    }
}
