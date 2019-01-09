package cn.peace.readhub.service.impl;

import cn.peace.readhub.domain.NewsDO;
import cn.peace.readhub.domain.NewsSpiderRecord;
import cn.peace.readhub.domain.NewsVO;
import cn.peace.readhub.mapper.NewsMapper;
import cn.peace.readhub.service.NewsService;
import cn.peace.readhub.spider.AbstractNewsSpider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NewsServiceImpl  implements NewsService {

    private Logger log = LoggerFactory.getLogger(NewsServiceImpl.class);
    @Autowired
    private NewsMapper nMapper;

    /*
     * (non Javadoc)
     *
     * @Title: saveNews
     *
     * @Description: TODO
     *
     * @param newsList
     *
     * @return
     *
     * @see cn.john.hub.service.NewsService#saveNews(java.util.List)
     *
     */
    @Override
    public boolean saveNews(List<NewsDO> newsList, AbstractNewsSpider ans) {
        int total = newsList.size();
        int count = 0;
        for (NewsDO n : newsList) {
            if (nMapper.checkUrlIfExists(n.getUrl()) != null) {
                count++;
            }
        }
        NewsSpiderRecord nsr = new NewsSpiderRecord();
        nsr.setTotal(total);
        nsr.setNewsSpiderId(ans.getSpiderId());
        nsr.setRepe_Rate((count * 1.0) / total);
        nsr.setNewItemCount(total - count);
        nMapper.saveNewsSpiderRecord(nsr);
        try {
            nMapper.saveNews(newsList);
            return true;
        } catch (Exception e) {
            log.error("Error saving news!", e.getMessage());
        }
        return false;
    }

    /*
     * (non Javadoc)
     *
     * @Title: listNews
     *
     * @Description: TODO
     *
     * @return
     *
     * @see cn.john.hub.service.NewsService#listNews()
     *
     */
    @Override
    public List<NewsVO> listNews() {
        return nMapper.listNews();
    }

    /*
     * (non Javadoc)
     *
     * @Title: listNewsStatis
     *
     * @Description: TODO
     *
     * @return
     *
     * @see cn.john.hub.service.NewsService#listNewsStatis()
     *
     */
    @Override
    public List<NewsSpiderRecord> listNewsStatis() {
        // TODO Auto-generated method stub
        return nMapper.listNewsStatis();
    }
}
