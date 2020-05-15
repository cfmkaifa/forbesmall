package net.mall.util;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;
/***神策数据分析
 */
@Component
public class SensorsAnalyticsUtils {

    /***数据采集
     */
    final SensorsAnalytics sa = new SensorsAnalytics(new SensorsAnalytics.ConcurrentLoggingConsumer("/opt/logagent/log/sa/logagent_access.log"));

    ExecutorService executorService = null;

    /***
     * 构造函数
     * @throws IOException
     */
    public SensorsAnalyticsUtils()
            throws IOException {
    }

    /***初始化线程池
     */
    @PostConstruct
    public void initPool(){
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<Runnable>();
        executorService = new ThreadPoolExecutor(10,15,1, TimeUnit.MINUTES,workQueue);
    }


    /***
     *发送数据
     * @param distinctId
     * @param eventName
     * @param properties
     */
    public void reportData(String  distinctId,String eventName,Map<String, Object> properties){
        SendData sendData = new SendData(distinctId,eventName,properties);
        executorService.execute(sendData);
    }



    /***发送数据
     */
    class SendData implements Runnable{

        /***事件
         */
        private String  eventName;

        /***实例ID
         */
        private String distinctId;

        /***
         * 数据
         */
        Map<String, Object> properties;


        /***
         * 上报数据
         * @param eventName
         * @param distinctId
         * @param properties
         */
        public SendData(String distinctId, String eventName, Map<String, Object> properties) {
            this.eventName = eventName;
            this.distinctId = distinctId;
            this.properties = properties;
        }

        /***上报数据
         */
        private void reportData(){
            try{
                sa.clearSuperProperties();
                sa.track(distinctId,false,eventName,properties);
                sa.flush();
                sa.shutdown();
            } catch (InvalidArgumentException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            this.reportData();
        }
    }
}
