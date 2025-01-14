package com.alibaba.datax.transport.transformer.maskingMethods.cryptology;
import java.util.Base64;
import org.junit.Test;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import static org.junit.Assert.assertEquals;


/**
 * @author Wang Zengke
 * @since 2022/11/28 11:39
 */
public class DESedeCoder {

    /**
     * 密钥算法
     * Java 6支持密钥长度112位和168位
     * Bouncy Castle支持密钥长度128位和192位
     */
    public static final String KEY_ALGORITHM = "DESede";

    /**
     * 加密/解密算法/工作模式/填充模式
     * Java6 支持PKCS5Padding填充方式
     * Bouncy Castle支持PCKS7Padding填充方式
     */
    public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return 密钥
     * @throws Exception 异常
     */
    private static Key toKey(byte[] key) throws Exception {
        //实例化DES密钥材料
        DESedeKeySpec dks = new DESedeKeySpec(key);
        //实例化秘密密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        //生成秘密密钥
        return keyFactory.generateSecret(dks);
    }

    /**
     * 解密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 解密数据
     * @throws Exception 异常
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        //还原密钥
        Key k = toKey(key);
        /**
         * 实例化
         * 使用PKCS7Padding填充方式，按如下代码实现
         * Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
         */
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        //执行操作
        return cipher.doFinal(data);
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密数据
     * @throws Exception 异常
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        //还原密钥
        Key k = toKey(key);
        /**
         * 实例化， 使用PKCS7Padding填充方式，按照如下代码实现
         * Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
         */
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);
        //执行加密操作
        return cipher.doFinal(data);
    }


    /**
     * 生成密钥
     *
     * @return byte[] 二进制密钥
     * @throws Exception 异常
     */
    public static byte[] initKey() throws Exception {
        /**
         * 实例化
         * 使用128位或192位长度密钥，按照如下代码实现
         * KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM,"BC");
         */
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);

        /**
         * 初始化
         * Java 6支持密钥长度112位和168位
         * 若使用128位或192位长度密钥，按如下代码实现
         * kg.init(128);
         * 或
         * kg.init(192);
         */
        kg.init(168);
        //生成秘密密钥
        SecretKey secretKey = kg.generateKey();
        //获得密钥的二进制编码形式
        return secretKey.getEncoded();
    }

    @Test
    public void DESedeTest() throws Exception {
        String inputStr = "DESede加密/解密算法";
        byte[] inputData = inputStr.getBytes();
        System.out.println("原文：\n" + inputStr);
        //初始化密钥
        byte[] key = DESedeCoder.initKey();
        System.out.println("密钥：\n" + Base64.getEncoder().encodeToString(key));
        //加密
        inputData = DESedeCoder.encrypt(inputData, key);
        System.out.println("加密后：\n" + Base64.getEncoder().encodeToString(inputData));
        //解密
        byte[] outputData = DESedeCoder.decrypt(inputData, key);
        String outputStr = new String(outputData);
        System.out.println("解密后：\n" + outputStr);
        //校验
        assertEquals(inputStr, outputStr);
    }
}
