package cn.peace.readhub.spider.news;

import cn.peace.readhub.domain.NewsDO;
import cn.peace.readhub.domain.ParseException;
import cn.peace.readhub.spider.AbstractNewsSpider;
import cn.peace.readhub.utils.SiteEnum;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TechWebSpider extends AbstractNewsSpider {

    private static final int newsSpiderId = 1;

    private static final int delayFactor = 20;

    private static final String site = SiteEnum.TECH_WEB.siteAddr;

    /*
     * (non Javadoc)
     *
     * @Title: crawlNews
     *
     * @Description: TODO
     *
     * @return
     *
     * @see cn.john.hub.spider.NewsSpider#crawlNews()
     *
     */
    @Override
    protected List<NewsDO> parseHtml(String html) throws ParseException {
        log.info("Parsing techweb news...");
        try {
            Document doc = Jsoup.parse(html);
            Elements news = doc.getElementsByClass("con_list");
            Elements news1 = news.last().getElementsByClass("con_one");
            List<NewsDO> newsList = new LinkedList<NewsDO>();
            Iterator<Element> it = news1.iterator();
            while (it.hasNext()) {
                Element e = it.next();
                Element h2 = e.getElementsByTag("h2").last();
                Element a = h2.getElementsByTag("a").last();
                String url = a.attr("href");
                String title = a.html();
                NewsDO newsDO = new NewsDO();
                newsDO.setTitle(title);
                newsDO.setUrl(url);
                Elements txt = e.getElementsByClass("con_txt");
                String brief = txt.last().getElementsByTag("p").html();
                newsDO.setBrief(brief);
                newsDO.setSiteId(1);

                newsList.add(newsDO);
            }
            log.info("Parse completed!size is " + newsList.size());
            return newsList;
        } catch (Exception e) {
            throw new ParseException("Parse techweb html failed!html is"+html, e);
        }
    }

    @Override
    public String toString() {
        return "TechWebSpider";
    }

    /*
     * (non Javadoc)
     *
     * @Title: getFactor
     *
     * @Description: TODO
     *
     * @return
     *
     * @see cn.john.hub.spider.AbstractNewsSpider#getFactor()
     *
     */
    @Override
    public int getDelayFactor() {
        // TODO Auto-generated method stub
        return delayFactor;
    }

    /*
     * (non Javadoc)
     *
     * @Title: getSerialNumber
     *
     * @Description: TODO
     *
     * @return
     *
     * @see cn.john.hub.spider.AbstractNewsSpider#getSerialNumber()
     *
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
}
