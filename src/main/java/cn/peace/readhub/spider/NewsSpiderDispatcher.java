package cn.peace.readhub.spider;

import cn.peace.readhub.domain.Heartbeat;
import cn.peace.readhub.domain.NewsSpiderWithTime;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Component
public class NewsSpiderDispatcher implements Runnable {
    private final Logger log = LoggerFactory.getLogger(NewsSpiderDispatcher.class);

    private ExecutorService cacheThreadPool;
    private Random rand;
    @SuppressWarnings("rawtypes")
    private List<NewsSpiderWithTime> spiderList;
    private LinkedList<AbstractNewsSpider> executeQueue;
    @Autowired
    private Heartbeat hb;

    @SuppressWarnings("rawtypes")
    public NewsSpiderDispatcher() {
        cacheThreadPool = Executors.newCachedThreadPool();
        spiderList = new ArrayList<NewsSpiderWithTime>();
        executeQueue = new LinkedList<AbstractNewsSpider>();
        rand = new Random();
        registerNewsSpiders();
    }

    private void registerNewsSpiders() {
        // 注册newsspider类的实例
        DateTime now = new DateTime();

        ClassLoader loader = this.getClass().getClassLoader();
        String classPath = this.getClass().getResource("").getFile();

        System.out.println(">>>>>>>>>>>>>"+classPath);

        String packageName = this.getClass().getPackage().getName();

        String newsClassPath = classPath + "news/";
        String newsPackage = packageName + ".news";

        File classes = new File("D:\\WorkSpace\\classes");

        System.out.println(classes.toString());
        System.out.println(classes.isDirectory());
        System.out.println(classes.isFile());
        System.out.println(classes.getName());

        for (File f : classes.listFiles()) {
            String fileName = f.getName();
            // 代码尚不成熟
            if (fileName.contains("Alpha")) {
                continue;
            }
            try {
                String className = newsPackage + "." + fileName.replace(".class", "");
                @SuppressWarnings("unchecked")
                Class<AbstractNewsSpider> clazz = (Class<AbstractNewsSpider>) loader.loadClass(className);
                NewsSpiderWithTime<AbstractNewsSpider> spider = new NewsSpiderWithTime<>();
                spider.setClazz(clazz);
                spider.setExeTime(now.plusMinutes(rand.nextInt(1)));
                spiderList.add(spider);
            } catch (ClassNotFoundException e) {
                log.error("error",e);
            }
        }

        log.info("News Spider List initiated:" + spiderList);

    }

    /*
     * (non Javadoc)
     *
     * @Title: run
     *
     * @Description:10秒执行一次
     *
     *
     * @see java.lang.Runnable#run()
     *
     */
    @Override
    public void run() {
        long timestamp = System.currentTimeMillis();
        hb.setNewsSpiderBeat(timestamp);

        if (executeQueue.size() < 6) {
            offerSpiderToQueue(spiderList);
        }

        if (executeQueue.size() > 0) {
            AbstractNewsSpider newsSpider = executeQueue.poll();
            log.info("Executing " + newsSpider);
            cacheThreadPool.execute(newsSpider);
        }
    }

    @SuppressWarnings("rawtypes")
    private void offerSpiderToQueue(List<NewsSpiderWithTime> spiderList) {
        DateTime now = new DateTime();
        Iterator<NewsSpiderWithTime> it = spiderList.iterator();
        while (it.hasNext()) {

            NewsSpiderWithTime spider = it.next();
            DateTime exeTime = spider.getExeTime();

            if (now.isAfter(exeTime)) {

                AbstractNewsSpider spiderIns = null;
                try {
                    spiderIns = (AbstractNewsSpider) spider.getClazz().newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                executeQueue.offer(spiderIns);
                int delayTime = spiderIns.getDelayFactor() + rand.nextInt(30);
                DateTime nextExeTime = exeTime.plusMinutes(delayTime);
                spider.setExeTime(nextExeTime);

                log.info("Offer " + spider.getClazz().getSimpleName() + " to queue! Next exe time:"
                        + spider.getExeTime());
            }
        }
    }
}
