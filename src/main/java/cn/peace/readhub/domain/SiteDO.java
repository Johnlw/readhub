package cn.peace.readhub.domain;

public class SiteDO {
    private Integer siteId;
    private String siteName;
    private String siteAddr;

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteAddr() {
        return siteAddr;
    }

    public void setSiteAddr(String siteAddr) {
        this.siteAddr = siteAddr;
    }

    @Override
    public String toString() {
        return "SiteDO [siteId=" + siteId + ", siteName=" + siteName + ", siteAddr=" + siteAddr + "]";
    }
}
