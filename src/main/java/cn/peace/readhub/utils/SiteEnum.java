package cn.peace.readhub.utils;

public enum SiteEnum {
    TECH_WEB("TechWeb","http://www.techweb.com.cn/news/#wp");

    public String siteName;
    public String siteAddr;

    private SiteEnum(String siteName,String siteAddr){
        this.siteName = siteName;
        this.siteAddr = siteAddr;
    }
}
