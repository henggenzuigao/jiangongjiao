package com.whpe.qrcode.jiangxi_jian.utils;

/**
 * Created by yang on 2018/10/8.
 */

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 加密工具类
 */
public class DESUtils {

    public static final String ALGORITHM_MODE = "DES";
    public static final String ALGORITHM_DES ="DES/CBC/PKCS5Padding";
    public static final String ivString = "12345678";
    public static final String codeType = "UTF-8";


    /**
     * DES加密算法
     * @param data 原始字符串
     * @param pwd 密私钥，长度不能够小于8位
     * @return
     */
    public static String encode(String data,String pwd) {
        if (data == null)
            return null;
        try {
            DESKeySpec dks = new DESKeySpec(pwd.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM_MODE);
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            byte[] bytes = cipher.doFinal(data.getBytes(codeType));
            return Base64.encode(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * DES 解密算法
     * @param data 待加密字符串
     * @param pwd 密码
     * @return 解密后的字符串
     */
    public static String decode(String data,String pwd) {
        if (data == null)
            return null;
        try {
            DESKeySpec dks = new DESKeySpec(pwd.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM_MODE);
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            return new String(cipher.doFinal(Base64.decode(data)),codeType);
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

}