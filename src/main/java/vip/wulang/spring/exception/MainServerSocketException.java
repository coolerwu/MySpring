package vip.wulang.spring.exception;

/**
 * This class is used for intercepting exception of {@link MainServerSocket}.
 *
 * @author CoolerWu on 2019/1/5.
 * @version 1.0
 */
public class MainServerSocketException extends Exception {
    public MainServerSocketException() {
        super();
    }

    public MainServerSocketException(Throwable cause) {
        super(cause);
    }
}
