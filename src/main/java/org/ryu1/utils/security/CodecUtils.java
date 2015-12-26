package org.ryu1.utils.security;

import org.apache.commons.codec.binary.Base64;

public final class CodecUtils {

    /**
     * コンストラクタ.
     */
    private CodecUtils() {

    }

    /**
     * <pre>
     * Base64デコード.
     * </pre>
     * 
     * @param bytedata
     *        String
     * @return byte
     */
    public static byte[] getDecodeBase64(final String bytedata) {
        byte[] convertData = Base64.decodeBase64(bytedata.getBytes());
        return convertData;
    }

    /**
     * <pre>
     * Base64デコード.
     * </pre>
     * 
     * @param bytedata
     *        int
     * @return byte
     */
    public static byte[] getDecodeBase64(final int bytedata) {

        return getDecodeBase64(Integer.toString(bytedata));
    }

    /**
     * byte配列をBase64エンコードして文字列に変換する.
     * 
     * @param bytes
     *        オブジェクト
     * @return 文字列
     * @since 2014
     */
    public static String getBase64String(final byte[] bytes) {
        byte[] encoded = Base64.encodeBase64(bytes);
        return new String(encoded);
    }
}
