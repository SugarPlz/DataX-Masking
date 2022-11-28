package com.alibaba.datax.transport.transformer.maskingMethods.cryptology;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

/**
 * 3DES算法
 *
 * @author Wang Zengke
 * @since 2022/11/28 9:15
 */
public class DESedencryption extends CryptologyMasking {

    /**
     * 定义 加密算法,可用
     */
    private static final String ALGORITHM = "DESede";

    @Override
    public double execute(double d) throws Exception {
        return 0;
    }

    /**
     * DES,DESede,Blowfish
     * keybyte为加密密钥，长度为24字节
     * src为被加密的数据缓冲区（源）
     *
     * @param keyByte
     * @param src
     * @return
     */
    public static byte[] encrypt(byte[] keyByte, byte[] src) {
        try {
            // 生成密钥
            SecretKey desKey = new SecretKeySpec(keyByte, ALGORITHM);
            // 加密
            Cipher c1 = Cipher.getInstance(ALGORITHM);
            c1.init(Cipher.ENCRYPT_MODE, desKey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    // keybyte为加密密钥，长度为24字节
    // src为加密后的缓冲区
    public static byte[] decrypt(byte[] keyByte, byte[] src) {
        try {
            // 生成密钥
            SecretKey desKey = new SecretKeySpec(keyByte, ALGORITHM);
            // 解密
            Cipher c1 = Cipher.getInstance(ALGORITHM);
            c1.init(Cipher.DECRYPT_MODE, desKey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 将byte转换成十六进制字符串
     */
    public static String byte2hex(byte[] b) {
        String hexStr = "";
        String tmpStr = "";
        for (int n = 0; n < b.length; n++) {
            //转为十六进制
            tmpStr = (Integer.toHexString(b[n] & 0XFF));
            if (tmpStr.length() == 1) {
                hexStr = hexStr + "0" + tmpStr;
            } else {
                hexStr = hexStr + tmpStr;
            }
            if (n < b.length - 1) {
                hexStr = hexStr + ":";
            }
        }
        return hexStr.toUpperCase();
    }

    public static void main(String[] args) {

        // 添加新安全算法,如果用JCE就要把它添加进去
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        // 24字节的密钥
        final byte[] keyBytes = {
                0x11, 0x22, 0x4F, 0x58, (byte) 0x88, 0x10,
                0x40, 0x38, 0x28, 0x25, 0x79, 0x51,
                (byte) 0xCB, (byte) 0xDD, 0x55, 0x66, 0x77, 0x29,
                0x74, (byte) 0x98, 0x30, 0x40, 0x36, (byte) 0xE2
        };
        String szSrc = "This is a 3DES test. 测试";
        System.out.println("加密前的字符串:" + szSrc);

        byte[] encoded = encrypt(keyBytes, szSrc.getBytes());
        assert encoded != null;
        System.out.println("加密后的字符串:" + new String(encoded));

        byte[] srcBytes = decrypt(keyBytes, encoded);
        assert srcBytes != null;
        System.out.println("解密后的字符串:" + (new String(srcBytes)));
    }
}

