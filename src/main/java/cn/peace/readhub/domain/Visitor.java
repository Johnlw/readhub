package cn.peace.readhub.domain;

public class Visitor {
    private String ip;
    private String addr;
    private Integer count;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Visitor [ip=" + ip + ", addr=" + addr + ", count=" + count + "]";
    }
}
