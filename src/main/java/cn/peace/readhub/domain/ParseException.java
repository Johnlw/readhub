package cn.peace.readhub.domain;

public class ParseException extends Exception{
    private static final long serialVersionUID = 6617957054563686541L;

    public ParseException() {
        super();
    }

    public ParseException(String msg) {
        super(msg);
    }

    public ParseException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }
}
