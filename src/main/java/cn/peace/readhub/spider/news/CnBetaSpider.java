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

public class CnBetaSpider  extends AbstractNewsSpider {

    private static final int newsSpiderId = 2;

    private static final int delayFactor = 10;

    private static final String site = "http://www.cnbeta.com/category/tech.htm";

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
        return "CnBetaSpider";
    }

    /* (non Javadoc)

     * @Title: parseHtml

     * @Description: TODO

     * @param html

     * @see cn.john.hub.spider.AbstractSpider#parseHtml(java.lang.String)

     */
    @Override
    protected List<NewsDO> parseHtml(String html) throws ParseException {
        log.info("Parsing cnbeta html...");
        try {
            Document doc = Jsoup.parse(html);
            Elements items = doc.getElementsByClass("items-area").get(0).children();
            List<NewsDO> newsList = new LinkedList<NewsDO>();
            Iterator<Element> it = items.iterator();
            while (it.hasNext()) {
                Element e = it.next();
                NewsDO news = new NewsDO();
                Element aTag = e.getElementsByTag("dt").get(0).child(0);
                news.setTitle(aTag.text());
                news.setUrl(aTag.attr("href"));
                Element brief = e.getElementsByTag("dd").get(0);
                news.setBrief(brief.text());
                news.setSiteId(2);

                newsList.add(news);
            }
            log.info("Parse cnbeta news completed!size is " + newsList.size());
            return newsList;
        } catch (Exception e) {
            throw new ParseException("Parse cnbeta html failed!html is "+html, e);
        }
    }
}
