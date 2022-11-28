package com.alibaba.datax.transport.transformer.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 进制转换
 *
 * @author Wang Zengke
 * @since 2022/11/28 11:10
 */
public class NumerationConvertUtil {
    private static final Log LOG = LogFactory.getLog(NumerationConvertUtil.class);

    /**
     * 二进制转为十六进制
     */
    public static String bin2hex(String input) {
        StringBuilder sb = new StringBuilder();
        int len = input.length();
        LOG.debug("原数据长度" + (len / 8) + "字节");
        for (int i = 0; i < len / 4; i++) {
            //每4个二进制位转换为1个十六进制位
            String temp = input.substring(i * 4, (i + 1) * 4);
            int tempInt = Integer.parseInt(temp, 2);
            String tempHex = Integer.toHexString(tempInt).toUpperCase();
            sb.append(tempHex);
        }
        return sb.toString();
    }

    /**
     * 十六进制转二进制
     */
    public static String hex2bin(String input) {
        StringBuilder sb = new StringBuilder();
        int len = input.length();
        LOG.debug("原数据长度：" + (len / 2) + "字节");
        for (int i = 0; i < len; i++) {
            //每1个十六进制位转换为4个二进制位
            String temp = input.substring(i, i + 1);
            int tempInt = Integer.parseInt(temp, 16);
            String tempBin = Integer.toBinaryString(tempInt);
            //如果二进制数不足4位，补0
            if (tempBin.length() < 4) {
                int num = 4 - tempBin.length();
                for (int j = 0; j < num; j++) {
                    sb.append("0");
                }
            }
            sb.append(tempBin);
        }
        return sb.toString();
    }
}

