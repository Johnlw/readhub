package cn.peace.readhub.utils;

import cn.peace.readhub.domain.Heartbeat;
import cn.peace.readhub.service.NewsService;
import cn.peace.readhub.service.ProxyService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanUtil implements ApplicationContextAware {

    private static ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        if (ac == null) {
            System.out.println("init ac.........");
            ac = applicationContext;
        }
    }

    public static ApplicationContext getAppContext() {
        return ac;
    }


    public static Heartbeat getHeartbeatBean() {
        return (Heartbeat) ac.getBean("heartBeat");
    }

    public static NewsService getNewsServiceBean() {
        return (NewsService) ac.getBean("newsServiceImpl");
    }

    public static ProxyService getProxyServiceBean() {
        System.out.println("ac.............."+ac);
        return (ProxyService) ac.getBean("proxyServiceImpl");
    }
}
