package vip.wulang.spring.exception;

/**
 * @author CoolerWu on 2019/3/16.
 * @version 1.0
 */
public class EmptyStringException extends NullPointerException {
    public EmptyStringException() {
        super();
    }

    public EmptyStringException(String s) {
        super(s);
    }
}
