package cn.peace.readhub.controller;

import cn.peace.readhub.domain.AccRcdDO;
import cn.peace.readhub.domain.NewsVO;
import cn.peace.readhub.service.NewsService;
import cn.peace.readhub.spider.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class NewsController {
    @Autowired
    private NewsService nService;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/news")
    @ResponseBody
    public List<NewsVO> getNewsJsp(HttpServletRequest req) {
        AccRcdDO acc = new AccRcdDO();
        acc.setIp(req.getRemoteAddr());
        Queue.accQueue.add(acc);
        return nService.listNews();
    }
}
