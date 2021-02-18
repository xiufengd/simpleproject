package com.xiufengd.utils.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author: liyang
 * @date：2018/6/4 0004
 * describe：AES对称加密算法
 */
public class AESProcessor {

    private static final Logger logger = LoggerFactory.getLogger(AESProcessor.class);
    private static final String key = "ea07d5c9f0decfcd16f68059fb0a2bda";

    /**
     *
     * 生成密钥
     * @return byte[] 二进制密钥
     * */
    public static byte[] initKey(){
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * 加密数据
     * @param data 待加密数据
     * @param key 密钥
     * @return byte[] 加密后的数据
     * */
    public static byte[] encrypt(byte[] data,byte[] key){
        try {
            SecretKey secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 解密数据
     * @param data 待解密数据
     * @param key 密钥
     * @return byte[] 解密后的数据
     * */
    public static byte[] decrypt(byte[] data,byte[] key) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 直接对字符串进行加密，过滤了二进制byte流处理
     * @param key
     * @param data
     * @return
     */
    public static String encrypt(String key, String data){
        byte[] encryptResult = encrypt(data.getBytes(), ByteToHexUtil.hexStringToByte(key));
        String encryptStr = ByteToHexUtil.fromByteToHex(encryptResult);
        logger.debug("AES 加密: {}", encryptStr);
        return encryptStr.toUpperCase();
    }

    /**
     * 直接对字符串进行解密，过滤了二进制byte流的处理
     * @param key
     * @param data
     * @return
     */
    public static String decrypt(String key, String data){
        byte[] decryptResult = decrypt(ByteToHexUtil.hexStringToByte(data.toUpperCase()), ByteToHexUtil.hexStringToByte(key));
        String str = new String(decryptResult);
        logger.debug("AES 解密: {}", str);
        return str;
    }

    public static String encrypt(String data){
        return encrypt(key, data);
    }

    public static String decrypt(String data){
        return decrypt(key, data);
    }

    public static void main(String[] args) {
        String data = "aaaasddddddea07d5c9f0decfcd1";
        String encdata = encrypt(data);
        System.out.println(encdata);
        System.out.println("------------------------------------------------------");
        System.out.println(decrypt("xxxxx"));
        System.out.println("------------------------------------------------------");
//        test();
    }
}
