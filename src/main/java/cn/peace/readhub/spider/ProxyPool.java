package cn.peace.readhub.spider;

import cn.peace.readhub.domain.Proxy;
import cn.peace.readhub.utils.BeanUtil;
import cn.peace.readhub.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProxyPool {

    private static volatile ProxyPool proxyPool;
    private LinkedBlockingDeque<Proxy> pool = new LinkedBlockingDeque<Proxy>();
    private ReentrantLock lock = new ReentrantLock();
    private Condition notEnough = lock.newCondition();
    private ExecutorService ste = Executors.newSingleThreadExecutor();
    private ProxySpiderDispatcher psd = new ProxySpiderDispatcher();

    private ProxyPool() {
        init();
    }

    /**
     * @return
     * @Title: getInstance
     * @Description: 可以将ProxyPool交给spring管理，这里学习下双重检查锁定创建单例
     * @return: ProxyPool
     */
    public static ProxyPool getInstance() {
        if (proxyPool == null) {
            synchronized (ProxyPool.class) {
                if (proxyPool == null) {
                    proxyPool = new ProxyPool();
                }
            }
        }
        return proxyPool;
    }

    private void init() {
        LogUtil.getSpiderLogger().info("Pool initiation...Lack of proxy!Triggering proxy spider...");
        ste.submit(psd);
    }

    /**
     * @param proxy
     * @Title: put
     * @Description: 向代理池添加代理，如果池满则等待直到可以添加
     * @return: void
     */
    public void recycle(Proxy proxy) {
        if (proxy.getUseCount() == null) {
            proxy.setUseCount(1);
        } else {
            proxy.setUseCount(proxy.getUseCount() + 1);
        }
        pool.offerFirst(proxy);
    }

    public void discard(Proxy proxy) {
        List<Proxy> list = new ArrayList<>();
        list.add(proxy);
        BeanUtil.getProxyServiceBean().saveProxy(list);
    }

    public void putAll(List<Proxy> proxyList) {
        pool.addAll(proxyList);
    }

    public Proxy getUntilEmpty() {
        return pool.poll();
    }

    public Proxy peek() {
        return pool.peek();
    }

    /**
     * @param size
     * @return
     * @Title: getAndWaitAt
     * @Description: 当代理数量少于size时等待
     * @return: Proxy
     */
    public Proxy getAndWaitAtSize(int size) {
        if (pool.size() > (size * 2)) {
            return pool.poll();
        }
        lock.lock();
        try {
            if (pool.size() < size) {
                LogUtil.getSpiderLogger()
                        .info("Pool size is " + pool.size() + " Lack of proxy!Triggering proxy spider and wait!");
                ste.submit(psd);
                notEnough.awaitUninterruptibly();
                LogUtil.getSpiderLogger().info("Pool size is " + pool.size() + " Unpark and carry on!");
            }
        } finally {
            lock.unlock();
        }
        return pool.poll();
    }

    public int size() {
        return pool.size();
    }

    public void signalEnough() {
        lock.lock();
        try {
            LogUtil.getSpiderLogger().info("Signal proxy enough!And " + "pool size is " + pool.size());
            notEnough.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
