package cn.peace.readhub.service;

import cn.peace.readhub.domain.Proxy;
import cn.peace.readhub.domain.ProxyStatis;

import java.util.List;

public interface ProxyService {
    boolean saveProxy(List<Proxy> pList);
    List<ProxyStatis> listProxyStatis();
}
