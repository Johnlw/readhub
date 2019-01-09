package cn.peace.readhub.spider;

import cn.peace.readhub.domain.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
@Component
public class ProxySpiderDispatcher implements Runnable {

    private static Logger log = LoggerFactory.getLogger(ProxySpiderDispatcher.class);

    private ThreadPoolExecutor cacheThreadPool;
    private HashMap<Integer, AbstractProxySpider> proxyMap;
    private Random rand = new Random();
    private List<Class<? extends AbstractProxySpider>> spiderCache;

    public ProxySpiderDispatcher() {
        cacheThreadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        proxyMap = new HashMap<Integer, AbstractProxySpider>();
        spiderCache = new LinkedList<>();
        registerSpiders();
    }

    // 注册已有的代理爬虫实例对象
    private void registerSpiders() {

        Class<? extends ProxySpiderDispatcher> clazz = this.getClass();

        ClassLoader loader = clazz.getClassLoader();
        String classPath = clazz.getResource("").getFile();
        String classPackage = clazz.getPackage().getName();

        String proxyPackage = classPackage + ".proxy";
        String proxyPath = classPath + "proxy/";
        String proxyPathtmp = "D:\\WorkSpace\\IDEA WorkSpace\\readhub\\target\\classes\\cn\\peace\\readhub\\spider\\proxy";

        File classes = new File(proxyPathtmp);
        String className = null;
        for (File f : classes.listFiles()) {
            String fileName = f.getName();
            if (fileName.contains("Alpha")) {
                continue;
            }

            className = proxyPackage + "." + fileName.replace(".class", "");
            try {
                AbstractProxySpider aps = (AbstractProxySpider) loader.loadClass(className).newInstance();
                proxyMap.put(aps.getProxySpiderId(), aps);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        log.info("proxy map initialized!Detail: " + proxyMap);
    }

    /*
     * (non Javadoc)
     *
     * @Title: run
     *
     * @Description: 10秒执行一次
     *
     *
     * @see java.lang.Runnable#run()
     *
     */
    @Override
    public void run() {
        ProxyPool proxyPool = ProxyPool.getInstance();
        // 启动代理爬虫的策略
        while (proxyPool.size() < 20) {
            // 最多同时启动【两种】代理爬虫，具体个数根据不同代理网站自己的爬取策略而定
            if (spiderCache.size() < 3) {
                Class<? extends AbstractProxySpider> clazz = launchSpider();
                spiderCache.add(clazz);
            }
            // 代理爬虫已经启动至少一个，给它时间完成任务
            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        proxyPool.signalEnough();
        spiderCache.clear();
    }

    /**

     * @Title: launchSpider

     * @Description: 启动代理爬虫

     * @return

     * @return: Class<? extends AbstractProxySpider>

     */
    private Class<? extends AbstractProxySpider> launchSpider() {
        // 随机选择代理爬虫，尽量不去爬一个代理ip的源站
        Proxy p = ProxyPool.getInstance().peek();
        AbstractProxySpider proxySpider = proxyMap.get(selectSpider(p));
        log.info("Proxy spider selected: " + proxySpider + " And coming proxy is " + p);
        List<AbstractProxySpider> list = proxySpider.listExeInstances();
        if (list.size() > 5) {
            for (AbstractProxySpider aps : list) {
                cacheThreadPool.execute(aps);
                try {
                    TimeUnit.SECONDS.sleep(rand.nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (AbstractProxySpider aps : list) {
                cacheThreadPool.execute(aps);
            }
        }
        return proxySpider.getClass();
    }

    /**
     *
     * @Title: selectSpider
     *
     * @Description: 检查proxy来源，尽量不用某个proxy去爬它的来源站。
     *
     * @param p
     * @return
     *
     * @return: int
     *
     */
    private int selectSpider(Proxy p) {
        int randKey = -1;
        if (p != null) {
            int id = p.getProxySpiderId();
            List<Possiblity> list = new ArrayList<>();
            Set<Map.Entry<Integer, AbstractProxySpider>> entrySet = proxyMap.entrySet();

            for (Map.Entry<Integer, AbstractProxySpider> entry : entrySet) {
                if (entry.getKey() != id) {
                    Possiblity psb = new Possiblity();
                    psb.setProxySpiderId(entry.getKey());
                    psb.setPossiblity(entry.getValue().getPossiblity());
                    list.add(psb);
                }
            }
            // 重新按照概率比在排除了源站的spider列表中随机选取proxy spider
            randKey = genRandKey(list);
        }

        // 如果返回-1 则随机选取一个
        if (randKey == -1) {
            Integer[] arr = proxyMap.keySet().toArray(new Integer[0]);
            randKey = arr[rand.nextInt(arr.length)];
        }
        return randKey;
    }

    /**
     *
     * @Title: genRandKey
     *
     * @Description: list中的元素都指定了概率，根据概率随机选取List中的一个id。
     *
     * @param psbList
     * @return
     *
     * @return: int
     *
     */
    private int genRandKey(List<Possiblity> psbList) {

        double sum = 0.0;

        for (Possiblity p : psbList) {
            sum += p.getPossiblity();
        }

        for (Possiblity p : psbList) {
            p.setPossiblity(p.getPossiblity() * 100 / sum);
        }

        int randInt = rand.nextInt(100);

        double psum = 0.0;
        for (Possiblity p : psbList) {
            psum += p.getPossiblity();
            if (randInt < psum) {
                return p.getProxySpiderId();
            }
        }
        // 由于精度问题不能完全保证不等式一定涵盖所有情况
        return -1;
    }

    private class Possiblity {
        private int proxySpiderId;
        private double possiblity;

        public int getProxySpiderId() {
            return proxySpiderId;
        }

        public void setProxySpiderId(int proxySpiderId) {
            this.proxySpiderId = proxySpiderId;
        }

        public double getPossiblity() {
            return possiblity;
        }

        public void setPossiblity(double possiblity) {
            this.possiblity = possiblity;
        }

        @Override
        public String toString() {
            return "Possiblity [proxySpiderId=" + proxySpiderId + ", possiblity=" + possiblity + "]";
        }

    }
}
