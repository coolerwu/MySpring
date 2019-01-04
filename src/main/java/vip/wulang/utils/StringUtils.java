package vip.wulang.utils;

import java.util.Objects;

/**
 * This class is a util of operating {@link String}.
 *
 * @author CoolerWu on 2019/1/5.
 * @version 1.0
 */
public class StringUtils {
    private static final String EMPTY_STRING = "";
    private static final String SPACE_STRING = " ";

    private StringUtils() {
    }

    public static String[] splitString(String var1, String var2) {
        if (Objects.isNull(var1) || Objects.isNull(var2)) {
            return null;
        }

        return var1.split(var2);
    }

    public static String[] splitStringOnlyFirstTime(String var1, String var2) {
        if (Objects.isNull(var1) || Objects.isNull(var2)) {
            return null;
        }

        String[] split = var1.split(var2);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < split.length; i++) {
            if (i != split.length - 1) {
                sb.append(split[i]).append(var2);
            } else {
                sb.append(split[i]);
            }
        }

        String[] new_str = new String[2];
        new_str[0] = split[0];
        new_str[1] = sb.toString();

        return new_str;
    }

    public static String emptySpace(String var1) {
        if (Objects.isNull(var1)) {
            return null;
        }

        return var1.replaceAll(SPACE_STRING, EMPTY_STRING);
    }

    public static String toLowerCase(String var1) {
        if (Objects.isNull(var1)) {
            return null;
        }

        return var1.toLowerCase();
    }

    public static String toUpperCase(String var1) {
        if (Objects.isNull(var1)) {
            return null;
        }

        return var1.toUpperCase();
    }
}
