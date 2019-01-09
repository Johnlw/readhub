package cn.peace.readhub.utils;

import cn.peace.readhub.domain.Proxy;
import cn.peace.readhub.service.impl.SiteServiceImpl;
import com.sun.deploy.net.HttpResponse;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class HttpClient {
    private final static Logger log = LoggerFactory.getLogger(HttpClient.class);

    private Proxy proxy;

    private CloseableHttpClient httpClient;

    private RequestConfig defaultReqCfg;

    private ResponseHandler<String> responseHandler;

    private HttpClientBuilder builder;

    private HttpRequestBase httpResq;

    public HttpClient(String url, List<Header> headers, String method) {
        this(url, headers, method, null);
    }

    public HttpClient(String url, List<Header> headers, String method, Proxy proxy) {

        builder = HttpClients.custom();

        defaultReqCfg = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000)
                .setConnectionRequestTimeout(20000).build();

        responseHandler = new ResponseHandler<String>() {
            @Override
            public String handleResponse(org.apache.http.HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity, Consts.UTF_8) : null;
                } else {
                    throw new ClientProtocolException("Unexcepted response status:" + status);
                }
            }

        };

        if (proxy != null) {
            log.info("Building httpClient using proxy: " + proxy);
            this.proxy = proxy;
            HttpHost proxyHost = new HttpHost(proxy.getIpAddr(), Integer.parseInt(proxy.getPort()));
            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxyHost);
            httpClient = builder.setDefaultRequestConfig(defaultReqCfg).setRoutePlanner(routePlanner).build();
        } else {
            log.info("Building httpClient using local ip...");
            httpClient = builder.setDefaultRequestConfig(defaultReqCfg).build();
        }

        switch (method) {
            case HubConsts.GET:
                httpResq = new HttpGet(url);
                break;
            case HubConsts.POST:
                httpResq = new HttpPost(url);
                break;
            default:
                httpResq = new HttpGet(url);
                break;
        }

        httpResq.setHeaders(headers.toArray(new Header[0]));
    }

    public String getData() {

        try {
            return httpClient.execute(httpResq, responseHandler);
        } catch (Exception e) {
            log.error("HttpClient Error!", e.getMessage());
            return null;
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("error",e);
            }
        }
    }

    public Proxy getProxy(){
        return proxy;
    }
}
