package cn.peace.readhub.domain;

public class ProxyStatis {
    private String siteName;
    private Double validRate;
    private Integer totalCount;
    private Integer validCount;

    /**
     *
     * @return the siteName
     *
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * @param siteName
     *            the siteName to set
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    /**
     *
     * @return the validRate
     *
     */
    public Double getValidRate() {
        return validRate;
    }

    /**
     * @param validRate
     *            the validRate to set
     */
    public void setValidRate(Double validRate) {
        this.validRate = validRate;
    }

    /**
     *
     * @return the totalCount
     *
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount
     *            the totalCount to set
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    /**
     *
     * @return the validCount
     *
     */
    public Integer getValidCount() {
        return validCount;
    }

    /**
     * @param validCount
     *            the validCount to set
     */
    public void setValidCount(Integer validCount) {
        this.validCount = validCount;
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
        return "ProxyStatis [siteName=" + siteName + ", validRate=" + validRate + ", totalCount=" + totalCount
                + ", validCount=" + validCount + "]";
    }
}
