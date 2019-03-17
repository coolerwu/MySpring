package vip.wulang.spring.exception;

/**
 * @author CoolerWu on 2019/3/17.
 * @version 1.0
 */
public class UnknownException extends Exception {
    public UnknownException() {
        super();
    }

    public UnknownException(String message) {
        super(message);
    }
}
