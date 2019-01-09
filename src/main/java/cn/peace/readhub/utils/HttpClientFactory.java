package cn.peace.readhub.utils;

import cn.peace.readhub.domain.Proxy;
import cn.peace.readhub.spider.ProxyPool;
import org.apache.http.Header;

import java.util.List;

public class HttpClientFactory {
    public static final HttpClient createUsingLocalIP(String url, List<Header> headers) {
        return new HttpClient(url, headers, HubConsts.GET, null);
    }

    public static final HttpClient createUsingProxy(String url, List<Header> headers, boolean waitWhenNotEnough) {
        ProxyPool proxyPool = ProxyPool.getInstance();
        Proxy proxy = waitWhenNotEnough ? proxyPool.getAndWaitAtSize(20) : proxyPool.getUntilEmpty();
        LogUtil.getSpiderLogger().info("creating httpClient...pool size is " + proxyPool.size());
        return new HttpClient(url, headers, HubConsts.GET, proxy);
    }

    public static final void recycleProxy(Proxy proxy) {
        ProxyPool.getInstance().recycle(proxy);
    }

    public static final void discardProxy(Proxy proxy) {
        ProxyPool.getInstance().discard(proxy);
    }

    public static final void recordLocalHost() {
        Proxy local = new Proxy();
        local.setIpAddr("localhost");
        local.setUseCount(1);
        local.setProxySpiderId(1024);
        ProxyPool.getInstance().discard(local);
    }
}
