package com.example.demo.tcp5g.utils;

/**
 * @author TRH
 * @description:
 * @Package com.example.testdemo.utils
 * @date 2023/3/27 17:51
 */
public class CRCUtils {

    public static String getCRC(String data) {
        data = data.replace(" ", "");
        int len = data.length();
        if (!(len % 2 == 0)) {
            return "0000";
        }
        int num = len / 2;
        byte[] para = new byte[num];
        for (int i = 0; i < num; i++) {
            int value = Integer.valueOf(data.substring(i * 2, 2 * (i + 1)), 16);
            para[i] = (byte) value;
        }
        return getCRC(para);
    }

    /**
     * Calculate the CRC16 check code
     *
     * @param bytes
     *            Byte array
     * @return {@link String} Verification code
     * @since 1.0
     */
    public static String getCRC(byte[] bytes) {
        // The CRC register is all 1
        int CRC = 0x0000ffff;
        // Polynomial check value
        int POLYNOMIAL = 0x0000a001;
        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        // The result is converted to hexadecimal
        String result = Integer.toHexString(CRC).toUpperCase();
        if (result.length() != 4) {
            StringBuffer sb = new StringBuffer("0000");
            result = sb.replace(4 - result.length(), 4, result).toString();
        }
        //A high position precedes a low position
        //return result.substring(2, 4) + " " + result.substring(0, 2);
        // Swap high and low bits, low bits first and high bits last
        return result.substring(2, 4) + " " + result.substring(0, 2);
    }



    /**
     * Converts an int to a byte array, putting the least bit first and the most bit last
     * Changing the high and low bit order is simply a matter of swapping the array number
     */
    public static byte[] getByteCRC(byte[] bytes)  {
        // The CRC register is all 1
        int CRC = 0x0000ffff;
        // Polynomial check value
        int POLYNOMIAL = 0x0000a001;
        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        byte[] src = new byte[2];
        src[1] =  (byte) ((CRC>>8) & 0xFF);
        src[0] =  (byte) (CRC & 0xFF);
        return src;
    }



}
