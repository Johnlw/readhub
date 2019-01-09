package cn.peace.readhub.domain;

public class NewsSpiderRecord {
    private Integer id;
    private String siteName;
    private Integer newsSpiderId;
    private String exeTime;
    private Integer total;
    private Double repe_Rate;
    private Integer newItemCount;

    /**
     *
     * @return the id
     *
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

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
     * @return the newsSpiderId
     *
     */
    public Integer getNewsSpiderId() {
        return newsSpiderId;
    }

    /**
     * @param newsSpiderId
     *            the newsSpiderId to set
     */
    public void setNewsSpiderId(Integer newsSpiderId) {
        this.newsSpiderId = newsSpiderId;
    }

    /**
     *
     * @return the exeTime
     *
     */
    public String getExeTime() {
        return exeTime;
    }

    /**
     * @param exeTime
     *            the exeTime to set
     */
    public void setExeTime(String exeTime) {
        this.exeTime = exeTime;
    }

    /**
     *
     * @return the total
     *
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * @param total
     *            the total to set
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     *
     * @return the repe_Rate
     *
     */
    public Double getRepe_Rate() {
        return repe_Rate;
    }

    /**
     * @param repe_Rate
     *            the repe_Rate to set
     */
    public void setRepe_Rate(Double repe_Rate) {
        this.repe_Rate = repe_Rate;
    }

    /**
     *
     * @return the newItemCount
     *
     */
    public Integer getNewItemCount() {
        return newItemCount;
    }

    /**
     * @param newItemCount
     *            the newItemCount to set
     */
    public void setNewItemCount(Integer newItemCount) {
        this.newItemCount = newItemCount;
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
        return "NewsSpiderRecord [id=" + id + ", siteName=" + siteName + ", newsSpiderId=" + newsSpiderId + ", exeTime="
                + exeTime + ", total=" + total + ", repe_Rate=" + repe_Rate + ", newItemCount=" + newItemCount + "]";
    }
}
