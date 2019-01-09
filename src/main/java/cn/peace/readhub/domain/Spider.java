package cn.peace.readhub.domain;

import sun.net.www.http.HttpClient;

import java.util.List;
import java.util.concurrent.Callable;

public interface Spider<T> extends Callable<List<T>> {

    void setHttpClient(HttpClient httpClient);

    String getSite();
}
