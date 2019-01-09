package cn.peace.readhub.spider.proxy;

import cn.peace.readhub.domain.ParseException;
import cn.peace.readhub.domain.Proxy;
import cn.peace.readhub.spider.AbstractProxySpider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

public class DoubleSixProxySpiderAlpha extends AbstractProxySpider {

    private final int proxySpiderId = 2;

    private Random rand = new Random();

    /*
     * (non Javadoc)
     *
     * @Title: parseHtml
     *
     * @Description: TODO
     *
     * @param html
     *
     * @see cn.john.hub.spider.AbstractProxySpider#parseHtml(java.lang.String)
     *
     */
    @Override
    protected List<Proxy> parseHtml(String html) throws ParseException {
        log.info("Parsing  double six proxy html...");
        try {
            Document doc = Jsoup.parse(html);
            Element table = doc.getElementsByTag("table").get(2);
            Elements trList = table.getElementsByTag("tr");
            trList.remove(0);
            List<Proxy> proxyList = new LinkedList<Proxy>();
            Iterator<Element> it = trList.iterator();
            while (it.hasNext()) {
                Proxy proxy = new Proxy();
                Element tr = it.next();
                Elements tdList = tr.getElementsByTag("td");
                proxy.setIpAddr(tdList.get(0).text());
                proxy.setPort(tdList.get(1).text());
                proxy.setProxySpiderId(proxySpiderId);
                proxy.setUseCount(0);
                proxyList.add(proxy);
            }
            log.info("parse  double six proxy html completed!");
            return proxyList;
        } catch (Exception e) {
            throw new ParseException("Parse double six proxy html failed!", e);
        }
    }

    @Override
    public String site() {
        return "http://www.66ip.cn/areaindex_" + (rand.nextInt(34) + 1) + "/1.html";
    }

    @Override
    public String toString() {
        return "1----DoubleSixProxySpiderAlpha";
    }

    /* (non Javadoc)

     * @Title: getPossiblity

     * @Description: TODO

     * @return

     * @see cn.john.hub.spider.AbstractProxySpider#getPossiblity()

     */
    @Override
    public int getPossiblity() {
        // TODO Auto-generated method stub
        return 50;
    }

    /* (non Javadoc)

     * @Title: getProxySpiderId

     * @Description: TODO

     * @return

     * @see cn.john.hub.spider.AbstractProxySpider#getProxySpiderId()

     */
    @Override
    public int getProxySpiderId() {
        // TODO Auto-generated method stub
        return proxySpiderId;
    }

    /* (non Javadoc)

     * @Title: listExeInstances

     * @Description: TODO

     * @return

     * @see cn.john.hub.spider.AbstractProxySpider#listExeInstances()

     */
    @Override
    protected List<AbstractProxySpider> listExeInstances() {
        // TODO Auto-generated method stub
        List<AbstractProxySpider> list = new ArrayList<>();
        list.add(this);
        return list;
    }
}
