package cn.peace.readhub.spider;

import cn.peace.readhub.domain.NewsDO;
import cn.peace.readhub.domain.ParseException;
import cn.peace.readhub.domain.Proxy;
import cn.peace.readhub.utils.HttpClient;
import cn.peace.readhub.utils.HttpClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class AbstractSpider<T> implements Runnable {

    protected static final Logger log = LoggerFactory.getLogger(AbstractSpider.class);

    private HttpClient httpClient;

    protected AbstractSpider() {
    }

    /**
     *
     * @Title: getHtml
     *
     * @Description: 向site发起http请求。循环获取新的httpClient不断尝试直到成功请求到html页面，
     *               同时将可用的代理ip回收入池。
     *
     * @param site
     * @return
     *
     * @return: String
     *
     */
    protected String getHtml(String site) {
        String html = null;
        Proxy p = null;
        while (html == null) {
            log.info(this + " trying getting html...");
            httpClient = fetchNewHttpClient(site);
            html = httpClient.getData();
            p = httpClient.getProxy();
            if (p != null) {
                if (html == null) {
                    HttpClientFactory.discardProxy(httpClient.getProxy());
                } else {
                    // 如果该httpClient使用了代理，并且该代理请求网站成功返回，证明代理可用，重新入池。
                    HttpClientFactory.recycleProxy(p);
                }
            } else {
                HttpClientFactory.recordLocalHost();
            }
        }
        log.info(this + " html got!");
        return html;
    }

    /**
     *
     * @Title: fetchNewHttpClient
     *
     * @Description: 获取新的httpClient。具体的获取策略由子类实现。
     *
     * @param site
     * @return
     *
     * @return: HttpClient
     *
     */
    protected abstract HttpClient fetchNewHttpClient(String site);

    /**
     *
     * @Title: site
     *
     * @Description: 返回站点地址，由子类实现
     *
     * @return
     *
     * @return: String
     *
     */
    protected abstract String site();

    /**
     *
     * @Title: parseHtml
     *
     * @Description: 解析html，由子类实现
     *
     * @param html
     * @return
     * @throws ParseException
     *
     * @return: List<T>
     *
     */
    protected abstract List<T> parseHtml(String html) throws ParseException, ParseException;

    protected abstract void consume(List<T> list);

    /*
     * (non Javadoc)
     *
     * @Title: call
     *
     * @Description: 线程执行方法
     *
     * @return
     *
     * @throws ParseException
     *
     * @see java.util.concurrent.Callable#call()
     *
     */
    @Override
    public void run() {
        try {
            List<T> list = (List<T>) parseHtml(getHtml(site()));
            consume(list);
        } catch (ParseException e) {
            log.error(this + "---" + e.getMessage());
            HttpClientFactory.discardProxy(httpClient.getProxy());
        }
    }
}
