package cn.peace.readhub.mapper;

import cn.peace.readhub.domain.NewsDO;
import cn.peace.readhub.domain.NewsSpiderRecord;
import cn.peace.readhub.domain.NewsVO;

import java.util.List;

public interface NewsMapper {
    void saveNews(List<NewsDO> newsList);

    List<NewsVO> listNews();

    int getNewsCount();

    String checkUrlIfExists(String url);

    void saveNewsSpiderRecord(NewsSpiderRecord nsr);

    List<NewsSpiderRecord> listNewsStatis();
}
