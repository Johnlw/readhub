package cn.peace.readhub.controller;

import cn.peace.readhub.domain.Heartbeat;
import cn.peace.readhub.domain.NewsSpiderRecord;
import cn.peace.readhub.domain.ProxyStatis;
import cn.peace.readhub.domain.Visitor;
import cn.peace.readhub.service.AccessService;
import cn.peace.readhub.service.NewsService;
import cn.peace.readhub.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
@Controller
public class MonitorController {
    @Autowired
    private AccessService aService;
    @Autowired
    private Heartbeat hb;
    @Autowired
    private ProxyService pService;
    @Autowired
    private NewsService nService;

    @RequestMapping("/monitor")
    public String monitor() {
        return "monitor";
    }

    @RequestMapping("/listMonitorItems")
    @ResponseBody
    public HashMap<String, Object> listMonitorItems() {
        HashMap<String, Object> map = new HashMap<>();
        List<Visitor> ipList = aService.listAccessRecordToday();
        List<ProxyStatis> psList = pService.listProxyStatis();
        List<NewsSpiderRecord> nsList = nService.listNewsStatis();
        if (ipList.size() > 0) {
            map.put("visitor", ipList);
        }

        map.put("psList", psList);
        map.put("nsList", nsList);

        long now = System.currentTimeMillis();

        if ((now - hb.getNewsSpiderBeat()) / 1000 > 60) {
            map.put("nsd", "Unknown");
        } else {
            map.put("nsd", "Running");
        }
        if ((now - hb.getIpLocaterBeat()) / 1000 > 300) {
            map.put("ip", "Unknown");
        } else {
            map.put("ip", "Running");
        }

        return map;
    }

    @RequestMapping("/checkClasses")
    @ResponseBody
    public String chekcClasses() {
        ClassLoader loader = this.getClass().getClassLoader();
        String thisFolder = this.getClass().getResource("").getFile();
        String newsFolder = thisFolder.replaceAll("controller", "spider") + "news/";
        File dir = new File(newsFolder.substring(1, newsFolder.length()));
        for (File f : dir.listFiles()) {
            try {
                Class<?> clazz = loader.loadClass("cn.john.hub.spider.news.Test");
                Method[] method = clazz.getMethods();
                for (Method m : method) {
                    System.out.println(m.getName());
                }
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
}
