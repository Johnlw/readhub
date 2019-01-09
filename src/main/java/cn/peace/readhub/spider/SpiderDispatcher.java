package cn.peace.readhub.spider;

import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
@Component
public class SpiderDispatcher {
    private static Logger log = LoggerFactory.getLogger(SpiderDispatcher.class);
    public static ExecutorService cacheThreadPool = Executors.newCachedThreadPool();

    @Autowired
    private NewsSpiderDispatcher nsd;

    @PostConstruct
    private void startDeamon() {
        Thread t = new Thread() {
            public void run() {
                ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(3);
                // 负责爬取新闻信息
                stpe.scheduleWithFixedDelay(nsd, 5, 5, TimeUnit.SECONDS);
            }
        };
        t.setDaemon(true);
        t.start();
    }
}
