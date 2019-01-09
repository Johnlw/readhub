package cn.peace.readhub.spider.proxy;

import cn.peace.readhub.domain.ParseException;
import cn.peace.readhub.domain.Proxy;
import cn.peace.readhub.spider.AbstractProxySpider;
import cn.peace.readhub.utils.HubConsts;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

public class XiCiProxySpider extends AbstractProxySpider {

    private final int proxySpiderId = 1;

    /*
     * (non Javadoc)
     *
     * @Title: parseHtmlAndSaveProxy
     *
     * @Description: TODO
     *
     * @see cn.john.hub.spider.AbstractProxySpider#parseHtmlAndSaveProxy()
     *
     */
    @Override
    protected List<Proxy> parseHtml(String html) throws ParseException {
        log.info("Start parsing xici html...");
        try {
            Document doc = Jsoup.parse(html);
            Element news = doc.getElementById("ip_list");
            Elements trs = news.getElementsByTag("tr");
            List<HashMap<Integer, Object>> list = new ArrayList<HashMap<Integer, Object>>();
            for (Element e : trs) {
                Elements tds = e.getElementsByTag("td");
                int i = 0;
                HashMap<Integer, Object> map = new HashMap<Integer, Object>();
                for (Element e1 : tds) {
                    i++;
                    if (i == 2 || i == 3 || i == 5 || i == 6) {
                        map.put(i, e1.html());
                    }
                    if (i == 7 || i == 8) {
                        String s = e1.child(0).attr("title").trim();
                        String s1 = s.substring(0, s.length() - 1);
                        map.put(i, s1);
                    }
                }
                if (i > 1) {
                    list.add(map);
                }
            }

            Iterator<HashMap<Integer, Object>> it = list.iterator();
            List<Proxy> proxyList = new LinkedList<Proxy>();
            while (it.hasNext()) {
                HashMap<Integer, Object> map = it.next();
                Proxy proxy = new Proxy();
                proxy.setIpAddr((String) map.get(2));
                proxy.setPort((String) map.get(3));
                proxy.setProxySpiderId(proxySpiderId);
                proxy.setUseCount(0);
                proxyList.add(proxy);
            }
            log.info("parse xici proxy html completed!");
            return proxyList;
        } catch (Exception e) {
            throw new ParseException("Parse xici proxy html failed!", e);
        }

    }

    @Override
    public String toString() {
        return "0----XiCiProxySpider";
    }

    /*
     * (non Javadoc)
     *
     * @Title: site
     *
     * @Description: TODO
     *
     * @return
     *
     * @see cn.john.hub.spider.AbstractSpider#site()
     *
     */
    @Override
    public String site() {
        return HubConsts.PROXY_XICI;
    }

    /*
     * (non Javadoc)
     *
     * @Title: getPossiblity
     *
     * @Description: TODO
     *
     * @return
     *
     * @see cn.john.hub.spider.AbstractProxySpider#getPossiblity()
     *
     */
    @Override
    public int getPossiblity() {
        // TODO Auto-generated method stub
        return 15;
    }

    /*
     * (non Javadoc)
     *
     * @Title: getProxySpiderId
     *
     * @Description: TODO
     *
     * @return
     *
     * @see cn.john.hub.spider.AbstractProxySpider#getProxySpiderId()
     *
     */
    @Override
    public int getProxySpiderId() {
        // TODO Auto-generated method stub
        return proxySpiderId;
    }

    /*
     * (non Javadoc)
     *
     * @Title: listExeInstances
     *
     * @Description: TODO
     *
     * @return
     *
     * @see cn.john.hub.spider.AbstractProxySpider#listExeInstances()
     *
     */
    @Override
    protected List<AbstractProxySpider> listExeInstances() {
        // TODO Auto-generated method stub
        List<AbstractProxySpider> list = new ArrayList<>();
        list.add(this);
        return list;
    }
}
