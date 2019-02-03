package vip.wulang.spring.utils;

import vip.wulang.spring.utils.exception.DoubleStringToHashMapFailException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Operate map utils.
 *
 * @author CoolerWu on 2018/12/27.
 * @version 1.0
 */
public class MapUtils {

    public static Map<String, String> doubleStringToHashMap(String[] strArr)
            throws DoubleStringToHashMapFailException {
        Map<String, String> map = new HashMap<>();
        if (Objects.isNull(strArr) || strArr.length == 0) {
            return map;
        }

        if (strArr.length % 2 != 0) {
            throw new DoubleStringToHashMapFailException("Not double string.");
        }

        for (int i = 0; i < strArr.length; i++) {
            map.put(strArr[i], strArr[++i]);
        }

        return map;
    }

}
