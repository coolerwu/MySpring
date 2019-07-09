package vip.wulang.spring.exception;

/**
 * @author coolerwu
 * @version 1.0
 */
public class ArrayIndexOutOfBoundsException extends RuntimeException {
    public ArrayIndexOutOfBoundsException() {
        super();
    }

    public ArrayIndexOutOfBoundsException(int index) {
        super("Array index out of range: " + index);
    }

    public ArrayIndexOutOfBoundsException(String message) {
        super(message);
    }
}
