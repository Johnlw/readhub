package cn.peace.readhub.service;

import cn.peace.readhub.domain.NewsDO;
import cn.peace.readhub.domain.NewsSpiderRecord;
import cn.peace.readhub.domain.NewsVO;
import cn.peace.readhub.spider.AbstractNewsSpider;

import java.util.List;

public interface NewsService {
    boolean saveNews(List<NewsDO> newsList, AbstractNewsSpider ans);

    List<NewsVO> listNews();

    List<NewsSpiderRecord> listNewsStatis();
}
