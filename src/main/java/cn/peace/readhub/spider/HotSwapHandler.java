package cn.peace.readhub.spider;

import java.io.File;
import java.util.HashMap;

public class HotSwapHandler {
    private void scanChange() {
        HashMap<File, Long> map = new HashMap<File, Long>();
        File dir = new File("");
        for (File f : dir.listFiles()) {
            map.put(f, f.lastModified());
        }

    }
}
