package com.qg.newsapp.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>密码加密工具类</p>
 * <p>采用MD5加密算法</p>
 */
public class DigestUtil {

    /**
     * 进行MD5加密
     *
     * @param plainText 待加密的字符串
     * @return 加密后的字符串
     */
    public static String encryption(String plainText) {
        byte[] bytes = null;
        try {
            bytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new BigInteger(1, bytes).toString(16); // 把加密后的数组用16进制表示
    }

}
