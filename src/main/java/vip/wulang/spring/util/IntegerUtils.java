package vip.wulang.spring.util;

/**
 * Integer utils.
 *
 * @author CoolerWu on 2019/3/17.
 * @version 1.0
 */
public class IntegerUtils {
    private IntegerUtils() {
    }

    public static boolean isZero(int var) {
        return var == 0;
    }

    public static boolean lessThanZero(int var) {
        return var < 0;
    }

    public static boolean lessThanOrEqualZero(int var) {
        return var <= 0;
    }
}
