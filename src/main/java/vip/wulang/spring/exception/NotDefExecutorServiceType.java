package vip.wulang.spring.exception;

/**
 * This class is used for intercepting exception that class not define type in initialization
 * about construct of class.
 *
 * @author CoolerWu on 2019/1/5.
 * @version 1.0
 */
public class NotDefExecutorServiceType extends Exception {
    public NotDefExecutorServiceType() {
        super();
    }
}
