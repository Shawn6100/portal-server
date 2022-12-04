package cn.edu.gdufs.util;

import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Description: MD5加密工具类
 * Author: 严仕鹏
 * Date: 2022/12/4
 */
public class MD5Util {

    private MD5Util() {}

    /**
     * MD5加密基础方法
     * @param string 需要加密的字符串
     * @param salt 加密盐值
     * @return 加密后的字符串
     */
    public static String encode(String string, String salt) {
        try {
            byte[] bytes = MessageDigest.getInstance("md5").digest((string + salt).getBytes());
            return DigestUtils.md5DigestAsHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成随机的盐值，长度为16位，大小写字母和数字组合
     * @return 盐值
     */
    public static String getSalt() {
        String str = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int temp = (int) (Math.random() * (str.length()));
            sb.append(str.charAt(temp));
        }
        return sb.toString();
    }
}
