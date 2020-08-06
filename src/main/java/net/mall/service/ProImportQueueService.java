package net.mall.service;
import net.mall.entity.ProImportQueue;

/***
 * 导入队列
 */
public interface ProImportQueueService extends BaseService<ProImportQueue, Long> {


    /***商品定时导入任务
     */
    void  proImportTask();
}
