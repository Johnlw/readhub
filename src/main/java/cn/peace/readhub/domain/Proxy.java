package cn.peace.readhub.domain;

public class Proxy {
    private String ipAddr;
    private String port;
    private String anonymity;
    private Integer proxySpiderId;
    private Integer useCount;
    private Integer validCount;
    private Integer totalCount;

    /**
     *
     * @return the ipAddr
     *
     */
    public String getIpAddr() {
        return ipAddr;
    }

    /**
     * @param ipAddr
     *            the ipAddr to set
     */
    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    /**
     *
     * @return the port
     *
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port
     *            the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     *
     * @return the anonymity
     *
     */
    public String getAnonymity() {
        return anonymity;
    }

    /**
     * @param anonymity
     *            the anonymity to set
     */
    public void setAnonymity(String anonymity) {
        this.anonymity = anonymity;
    }

    /**
     *
     * @return the proxySpiderId
     *
     */
    public Integer getProxySpiderId() {
        return proxySpiderId;
    }

    /**
     * @param proxySpiderId
     *            the proxySpiderId to set
     */
    public void setProxySpiderId(Integer proxySpiderId) {
        this.proxySpiderId = proxySpiderId;
    }

    /**
     *
     * @return the useCount
     *
     */
    public Integer getUseCount() {
        return useCount;
    }

    /**
     * @param useCount
     *            the useCount to set
     */
    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
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
        return "Proxy [ipAddr=" + ipAddr + ", port=" + port + ", anonymity=" + anonymity + ", proxySpiderId="
                + proxySpiderId + ", useCount=" + useCount + ", validCount=" + validCount + ", totalCount=" + totalCount
                + "]";
    }
}
