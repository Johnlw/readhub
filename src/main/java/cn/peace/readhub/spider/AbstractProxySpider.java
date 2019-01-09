package cn.peace.readhub.spider;

import cn.peace.readhub.domain.Proxy;
import cn.peace.readhub.utils.HeaderUtil;
import cn.peace.readhub.utils.HttpClient;
import cn.peace.readhub.utils.HttpClientFactory;
import org.apache.http.Header;

import java.util.List;

public abstract class AbstractProxySpider extends AbstractSpider<Proxy> {

    protected AbstractProxySpider() {
    }

    @Override
    public void run() {
        super.run();
    }

    protected HttpClient fetchNewHttpClient(String site) {
        List<Header> headers = HeaderUtil.getBrowserLikeHeaders();
        return HttpClientFactory.createUsingProxy(site, headers, false);
    }

    /*
     * (non Javadoc)
     *
     * @Title: consume
     *
     * @Description: 定义代理爬虫的消费者
     *
     * @param list
     *
     * @see cn.john.hub.spider.AbstractSpider#consume(java.util.List)
     *
     */
    @Override
    protected void consume(List<Proxy> list) {
        ProxyPool.getInstance().putAll(list);
    }

    /**
     *
     * @Title: listExeInstances
     *
     * @Description: 不同代理网站，一次能够爬取的代理数量不一样，有的网站需要分页爬取才能有足够的代理数量。
     *               本方法将网站本身的爬取策略和所有代理的调度器调度策略解耦。
     *
     * @return
     *
     * @return: List<AbstractProxySpider>
     *
     */
    protected abstract List<AbstractProxySpider> listExeInstances();

    public abstract int getPossiblity();

    public abstract int getProxySpiderId();
}
