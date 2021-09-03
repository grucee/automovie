package com.grucee.util;

import java.awt.image.BufferedImage;

/**
 * 工具类
 */
public class ConvertUtils {
    private ConvertUtils(){}

    /**
     * ARGB整数表示的像素值转ABGR数组表示
     * @param data
     * @return
     */
    public static byte[] itoabgr(int[] data) {

        byte[] rtn = new byte[data.length * 4];
        for (int i = 0; i < data.length; i++) {
            //整数表示的像素值
            int pixel = data[i];
            //alpha
            rtn[i * 4]     = (byte)((pixel >> 24) & 0xff);
            //blue
            rtn[i * 4 + 1] = (byte)((pixel      ) & 0xff);
            //green
            rtn[i * 4 + 2] = (byte)((pixel >>  8) & 0xff);
            //red
            rtn[i * 4 + 3] = (byte)((pixel >> 16) & 0xff);
        }
        return rtn;
    }
}
