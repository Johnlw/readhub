package cn.peace.readhub.mapper;

import cn.peace.readhub.domain.Proxy;
import cn.peace.readhub.domain.ProxyStatis;

import java.util.List;

public interface ProxyMapper {
    void saveProxy(List<Proxy> proxyList);

    List<ProxyStatis> getProxyCounts();
}
