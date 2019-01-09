package cn.peace.readhub.spider.news;

import cn.peace.readhub.domain.NewsDO;
import cn.peace.readhub.domain.ParseException;
import cn.peace.readhub.spider.AbstractNewsSpider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TaiMediaSpider extends AbstractNewsSpider {

    private static final int newsSpiderId = 3;

    private static final int delayFactor = 15;

    private static final String site = "http://www.tmtpost.com/column/2581216";

    /* (non Javadoc)

     * @Title: getDelayFactor

     * @Description: TODO

     * @return

     * @see cn.john.hub.spider.AbstractNewsSpider#getDelayFactor()

     */
    @Override
    public int getDelayFactor() {
        // TODO Auto-generated method stub
        return delayFactor;
    }

    /* (non Javadoc)

     * @Title: getSerialNumber

     * @Description: TODO

     * @return

     * @see cn.john.hub.spider.AbstractNewsSpider#getSerialNumber()

     */
    @Override
    public int getSpiderId() {
        // TODO Auto-generated method stub
        return newsSpiderId;
    }

    /* (non Javadoc)

     * @Title: site

     * @Description: TODO

     * @return

     * @see cn.john.hub.spider.AbstractSpider#site()

     */
    @Override
    protected String site() {
        // TODO Auto-generated method stub
        return site;
    }

    @Override
    public String toString() {
        return "TaiMediaSpider";
    }

    /* (non Javadoc)

     * @Title: parseHtml

     * @Description: TODO

     * @param html

     * @see cn.john.hub.spider.AbstractSpider#parseHtml(java.lang.String)

     */
    @Override
    protected List<NewsDO> parseHtml(String html) throws ParseException {
        log.info("Parsing taimedia news...");
        try {
            Document doc = Jsoup.parse(html, site);
            Elements lis = doc.getElementsByClass("mod-article-list clear").get(0).child(0).children();
            List<NewsDO> newsList = new LinkedList<>();
            Iterator<Element> it = lis.iterator();
            while (it.hasNext()) {
                Element e = it.next();
                NewsDO news = new NewsDO();
                Element aTag = e.getElementsByTag("h3").get(0).child(0);
                String title = aTag.attr("title");
                String url = aTag.attr("abs:href");
                String brief = e.getElementsByTag("p").get(0).text();
                news.setBrief(brief);
                news.setUrl(url);
                news.setTitle(title);
                news.setSiteId(3);
                newsList.add(news);
            }
            log.info("Parsing taimedia news completed!");
            return newsList;
        } catch (Exception e) {
            throw new ParseException("Parse taimedia html failed!html is"+html, e);
        }
    }
}
