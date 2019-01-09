package cn.peace.readhub.domain;

import cn.peace.readhub.spider.AbstractNewsSpider;
import org.joda.time.DateTime;

public class NewsSpiderWithTime<T extends AbstractNewsSpider> {
    private Class<T> clazz;
    private DateTime exeTime;

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public DateTime getExeTime() {
        return exeTime;
    }

    public void setExeTime(DateTime exeTime) {
        this.exeTime = exeTime;
    }

    @Override
    public String toString() {
        return "NewsSpider [clazz=" + clazz + ", exeTime=" + exeTime + "]";
    }
}
