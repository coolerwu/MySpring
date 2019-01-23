package vip.wulang.spring.token.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This is a util for operating token.
 *
 * @author CoolerWu on 2019/1/14.
 * @version 1.0
 */
public class TokenUtil {
    private TokenUtil() {
    }

    /**
     * Conversion with MD5.
     * @param original info of user
     * @return encrypted String
     * @throws NoSuchAlgorithmException exception
     */
    public static String messageDigest(String original) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(original.getBytes());
        return byteToHex(md.digest());
    }

    private static String byteToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        String aByteResult;
        for (byte aByte : bytes) {
            aByteResult = Integer.toHexString(aByte & 0xFF);
            if (aByteResult.length() == 1) {
                result.append("0").append(aByteResult);
            } else {
                result.append(aByteResult);
            }
        }

        return result.toString();
    }
}
