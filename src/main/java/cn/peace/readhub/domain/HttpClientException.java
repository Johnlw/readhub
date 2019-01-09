package cn.peace.readhub.domain;

public class HttpClientException extends Exception{

    private static final long serialVersionUID = 3950732397625062959L;

    public HttpClientException() {
        super();
    }

    public HttpClientException(String msg) {
        super(msg);
    }

    public HttpClientException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public HttpClientException(Throwable cause) {
        super(cause);
    }
}
