package cn.peace.readhub.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtil {
    private static final Logger spider = LogManager.getLogger("spider");
    private static final Logger web = LogManager.getLogger("web");

    public static Logger getSpiderLogger() {
        return spider;
    }

    public static Logger getWebLogger() {
        return web;
    }
}
