package cn.peace.readhub.service.impl;

import cn.peace.readhub.mapper.SiteMapper;
import cn.peace.readhub.domain.SiteDO;
import cn.peace.readhub.service.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SiteServiceImpl implements SiteService {

    private Logger log = LoggerFactory.getLogger(SiteServiceImpl.class);
    @Autowired
    private SiteMapper sMapper;
    /* (non Javadoc)

     * @Title: listSites

     * @Description: TODO

     * @return

     * @see cn.john.hub.service.SiteService#listSites()

     */
    @Override
    public List<SiteDO> listSites() {
        return sMapper.listSites();
    }
}
