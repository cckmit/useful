package me.andpay.ac.wpn.inter.api.util;

import me.andpay.ti.base.AppRtException;
import me.andpay.ti.util.HexUtil;
import me.andpay.ti.util.StringUtil;

import java.security.MessageDigest;

/**
 * Created by cen on 16/11/9.
 */
public class SimpleDigestUtil {

    /**
     * 数据签名
     * @param algorithm
     * @param str
     * @return
     */
    public static String simpleDigest(String algorithm, String str) {
        if (StringUtil.isBlank(str)) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(str.getBytes());
            return HexUtil.encodeHex(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
