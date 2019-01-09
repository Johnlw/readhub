package cn.peace.readhub.service.impl;

import cn.peace.readhub.mapper.ProxyMapper;
import cn.peace.readhub.domain.Proxy;
import cn.peace.readhub.domain.ProxyStatis;
import cn.peace.readhub.service.ProxyService;
import cn.peace.readhub.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProxyServiceImpl  implements ProxyService {
    @Autowired
    private ProxyMapper pMapper;

    /*
     * (non Javadoc)
     *
     * @Title: saveProxy
     *
     * @Description: TODO
     *
     * @param pList
     *
     * @return
     *
     * @see cn.john.hub.service.ProxyService#saveProxy(java.util.List)
     *
     */
    @Override
    public boolean saveProxy(List<Proxy> pList) {
        try {
            pMapper.saveProxy(pList);
            return true;
        } catch (Exception e) {
            LogUtil.getSpiderLogger().error("Error saving proxy!", e);
            return false;
        }
    }

    /*
     * (non Javadoc)
     *
     * @Title: getProxyStatis
     *
     * @Description: TODO
     *
     * @return
     *
     * @see cn.john.hub.service.ProxyService#getProxyStatis()
     *
     */
    @Override
    public List<ProxyStatis> listProxyStatis() {
        return pMapper.getProxyCounts();
    }
}
