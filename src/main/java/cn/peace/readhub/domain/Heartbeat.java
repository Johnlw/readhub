package cn.peace.readhub.domain;

import org.springframework.stereotype.Component;

@Component
public class Heartbeat {
    private volatile long newsSpiderBeat;
    private volatile long ipLocaterBeat;

    /**
     *
     * @return the newsSpiderBeat
     *
     */
    public long getNewsSpiderBeat() {
        return newsSpiderBeat;
    }

    /**
     * @param newsSpiderBeat
     *            the newsSpiderBeat to set
     */
    public void setNewsSpiderBeat(long newsSpiderBeat) {
        this.newsSpiderBeat = newsSpiderBeat;
    }

    /**
     *
     * @return the ipLocaterBeat
     *
     */
    public long getIpLocaterBeat() {
        return ipLocaterBeat;
    }

    /**
     * @param ipLocaterBeat
     *            the ipLocaterBeat to set
     */
    public void setIpLocaterBeat(long ipLocaterBeat) {
        this.ipLocaterBeat = ipLocaterBeat;
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
        return "Heartbeat [newsSpiderBeat=" + newsSpiderBeat + ", ipLocaterBeat=" + ipLocaterBeat + "]";
    }
}
