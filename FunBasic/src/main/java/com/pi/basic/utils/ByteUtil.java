package com.pi.basic.utils;


import android.util.Log;

import com.pi.basic.log.LogHelper;

/**
 * @描述：     @字节操作工具类
 * @作者：     @蒋诗朋
 * @创建时间： @2018-01-22
 */
public final class ByteUtil {
    private static final String TAG = "ByteUtil";

    /**
     * 整形转字节
     * @param ary
     * @param offset
     * @return
     */
    public static final int bytesToInt(byte[] ary, int offset) {
        int value;
        value = (int) ((ary[offset]&0xFF)
                | ((ary[offset+1]<<8) & 0xFF00)
                | ((ary[offset+2]<<16)& 0xFF0000)
                | ((ary[offset+3]<<24) & 0xFF000000));
        LogHelper.v(TAG, "bytesToInt value : "+value);
        return value;
    }

    /**
     * 整形转字节
     * @param value
     * @return
     */
    public static final byte[] intToBytes(int value) {
        byte[] byte_src = new byte[4];
        byte_src[3] = (byte) ((value & 0xFF000000)>>24);
        byte_src[2] = (byte) ((value & 0x00FF0000)>>16);
        byte_src[1] = (byte) ((value & 0x0000FF00)>>8);
        byte_src[0] = (byte) ((value & 0x000000FF));
        return byte_src;
    }

    /**
     * 字节转整形
     * @param src
     * @param offset
     * @return
     */
    public int bytesToInt2(byte[] src, int offset) {
        int value;
        value = (int) ( ((src[offset] & 0xFF)<<24)
                |((src[offset+1] & 0xFF)<<16)
                |((src[offset+2] & 0xFF)<<8)
                |(src[offset+3] & 0xFF));
        Log.d(TAG, "bytesToInt2 value : "+value);
        return value;
    }

    /**
     * 字节转整形
     * @param src
     * @param offset
     * @return
     */
    public static final int bytesToInt3(byte[] src, int offset) {
        int value;
        value = (int) ((src[offset] & 0xFF)
                | ((src[offset+1] & 0xFF)<<8)
                | ((src[offset+2] & 0xFF)<<16)
                | ((src[offset+3] & 0xFF)<<24));
        LogHelper.v(TAG, "bytesToInt3 value : "+value);
        return value;
    }

    /**
     * 字节转整形
     * @param bytes
     * @param off
     * @return
     */
    public static final int byte4ToInt(byte[] bytes, int off) {
        int b0 = bytes[off] & 0xFF;
        int b1 = bytes[off + 1] & 0xFF;
        int b2 = bytes[off + 2] & 0xFF;
        int b3 = bytes[off + 3] & 0xFF;
        return (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
    }

    /**
     * 字节转十六进制工具类
     * @param src
     * @return
     */
    public static final String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
