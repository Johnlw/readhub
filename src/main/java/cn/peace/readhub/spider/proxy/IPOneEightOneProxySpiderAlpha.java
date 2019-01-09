package cn.peace.readhub.spider.proxy;

import cn.peace.readhub.domain.ParseException;
import cn.peace.readhub.domain.Proxy;
import cn.peace.readhub.spider.AbstractProxySpider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class IPOneEightOneProxySpiderAlpha extends AbstractProxySpider {

    private final int proxySpiderId = 3;

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
        return "http://www.ip181.com/";
    }

    /*
     * (non Javadoc)
     *
     * @Title: parseHtml
     *
     * @Description: TODO
     *
     * @param html
     *
     * @return
     *
     * @throws ParseException
     *
     * @see cn.john.hub.spider.AbstractSpider#parseHtml(java.lang.String)
     *
     */
    @Override
    protected List<Proxy> parseHtml(String html) throws ParseException {
        log.info("Parsing ip181 html...");
        try {
            Document doc = Jsoup.parse(html);
            Element table = doc.getElementsByTag("tbody").get(0);
            Elements trList = table.getElementsByTag("tr");
            trList.remove(0);
            List<Proxy> proxyList = new LinkedList<Proxy>();
            Iterator<Element> it = trList.iterator();
            while (it.hasNext()) {
                Proxy proxy = new Proxy();
                Element tr = it.next();
                Elements tdList = tr.getElementsByTag("td");
                // 该网站只能返回GBK编码，httpClient
                // ResponseHandler目前设置为UTF-8的解码，不好修改。改在这里用 ‘͸��’表示“透明”
                if (!tdList.get(2).text().trim().equals("͸��")) {
                    proxy.setIpAddr(tdList.get(0).text());
                    proxy.setPort(tdList.get(1).text());
                    proxy.setProxySpiderId(proxySpiderId);
                    proxy.setUseCount(0);
                    proxyList.add(proxy);
                }
            }
            log.info("parse ip181 html completed!");
            return proxyList;
        } catch (Exception e) {
            throw new ParseException("Parse ip181 html failed!", e);
        }
    }

    /*
     * (non Javadoc)
     *
     * @Title: toString
     *
     * @Description: TODO
     *
     * @return
     *
     * @see java.lang.Object#toString()
     *
     */
    @Override
    public String toString() {
        return "2----IPOneEightOneProxySpiderAlpha";
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
