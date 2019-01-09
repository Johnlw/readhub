package cn.peace.readhub.spider;

import cn.peace.readhub.domain.NewsDO;
import cn.peace.readhub.utils.BeanUtil;
import cn.peace.readhub.utils.HeaderUtil;
import cn.peace.readhub.utils.HttpClient;
import cn.peace.readhub.utils.HttpClientFactory;
import org.apache.http.Header;

import java.util.List;

public abstract class AbstractNewsSpider extends AbstractSpider<NewsDO> {

    public AbstractNewsSpider() {
    }

    @Override
    public void run() {
        super.run();
    }

    protected HttpClient fetchNewHttpClient(String site) {
        List<Header> headers = HeaderUtil.getBrowserLikeHeaders();
        return HttpClientFactory.createUsingProxy(site, headers, true);
    }

    /*
     * (non Javadoc)
     *
     * @Title: consume
     *
     * @Description: 定义新闻
     *
     * @param list
     *
     * @see cn.john.hub.spider.AbstractSpider#consume(java.util.List)
     *
     */
    @Override
    protected void consume(List<NewsDO> list) {
        BeanUtil.getNewsServiceBean().saveNews(list, this);
    }

    // 用于调度
    public abstract int getDelayFactor();

    public abstract int getSpiderId();
}
