package org.ryu1.utils.binary;

import org.apache.commons.codec.binary.Base64;
import org.ryu1.utils.binary.BinaryConfig;
import org.ryu1.utils.binary.ByteOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;

/**
 * バイト操作のユーティティ.
 * @since 2014
 * @version 1.0
 * @author 石塚 隆一
 */
public final class ByteUtils {


    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(ByteUtil.class);

    /** 改行位置 */
    public static final int LINE_BYTES = 16;

    /** 区切り文字 */
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /** 16進数であることを表す. */
    public static final String PREFIX_OX = "0x";
    
    /** int型の最大サイズ. */
    public static final int INT_MAX_SIZE = 4;
    
    /** short型の最大サイズ. */
    public static final int SHORT_MAX_SIZE = 2;
    
    /**
     * インスタンス生成禁止.
     */
    private ByteUtils() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * byte配列から指定したレンジのbyte配列を抜き出す.
     * @param bytes 対象のバイト配列
     * @param binaryConfig バイナリ設定
     * @return 指定したレンジのbyte配列
     */
    public static byte[] copyOfRange(
            final byte[] bytes, final BinaryConfig binaryConfig) {
        return Arrays.copyOfRange(
                bytes, binaryConfig.getOffset(), binaryConfig.getLimit());
    }
    
    /**
     * バイナリ設定に従い文字列に変換します.
     * @param bytes 変換対象を含むバイト配列
     * @param binaryConfig 設定
     * @return 文字列
     */
    public static String toString(
            final byte[] bytes, final BinaryConfig binaryConfig) {
        byte[] result = copyOfRange(bytes, binaryConfig);
        result = order(result, binaryConfig.getByteOrder());
        switch (binaryConfig.getDataType()) {
        case SHORT:
            return bytesToShortString(result);
        case INT:
            return bytesToIntString(result);
        case HEX_STRING:
            return bytesToHexString(result, null);
        default:
            throw new IllegalArgumentException();
        }
    }
    
    
    /**
     * バイナリ設定に従い文字列に変換します.
     * @param bytes 変換対象を含むバイト配列
     * @param binaryConfig 設定
     * @param format 文字列のフォーマット
     * @return 文字列
     */
    public static String toString(
            final byte[] bytes,
            final BinaryConfig binaryConfig, final String format) {
        byte[] result = copyOfRange(bytes, binaryConfig);
        result = order(result, binaryConfig.getByteOrder());
        switch (binaryConfig.getDataType()) {
        case SHORT:
            return bytesToShortString(result);
        case INT:
            return bytesToIntString(result);
        case HEX_STRING:
            return bytesToHexString(result, format);
        default:
            throw new IllegalArgumentException();
        }
    }
    
    /**
     * バイト配列を並び替えて返却します.
     * @param bytes バイト配列
     * @param byteOrder バイトの並び順
     * @return bytes バイト配列
     */
    public static byte[] order(
            final byte[] bytes, final ByteOrder byteOrder) {
        switch (byteOrder) {
        case BIG_ENDIAN:
            return Arrays.copyOf(bytes, bytes.length);
        case MIDDLE_ENDIAN:
            byte[] result = new byte[bytes.length];
            byte[] upperBytes =
                    Arrays.copyOfRange(bytes, 0, bytes.length / 2);
            byte[] lowerBytes =
                    Arrays.copyOfRange(bytes,
                            bytes.length / 2, bytes.length);
            System.arraycopy(lowerBytes,
                    0, result, 0, lowerBytes.length);
            System.arraycopy(upperBytes,
                    0, result, lowerBytes.length, upperBytes.length);
            return result;
        default:
            throw new IllegalArgumentException();
        }
    }
    
    /**
     * .
     * バイトデータを16進数文字列化して返します。
     * 
     * @param data 文字列化するバイトデータ
     * @param prefix 文字列化したあと先頭につける文字列
     * @return 文字列化したバイトデータ
     */
    public static String bytesToHexString(
            final byte[] data, final String prefix) {
        StringBuffer result = new StringBuffer();
        
        for (int i = 0; i < data.length; i++) {
            result.append(String.format("%02X", data[i]));
        }
        
        return prefix == null ? result.toString() : prefix + result.toString();
    }
    
    /**
     * 符号付きShortに変換しStringにフォーマットして返却する.
     * short    16bit   -32768 ～ 32767
     * @param data データのバイト配列
     * @return 文字列
     * @param data
     */
    public static String bytesToShortString(final byte[] data) {
        if (data == null || SHORT_MAX_SIZE < data.length) {
            throw new IllegalArgumentException(
                    "data is null or data.length is more than 2.");
        }
        
        short i = ByteBuffer.wrap(data).getShort();
        return String.valueOf(i);
    }
    
    /**
     * 符号付きintに変換しStringにフォーマットして返却する.
     * int   32bit   -2147483648 ～ 2147483647
     * @param data データのバイト配列
     * @return 文字列
     */
    public static String bytesToIntString(final byte[] data) {
        if (data == null || INT_MAX_SIZE < data.length) {
            throw new IllegalArgumentException(
                    "data is null or data.length is more than 4.");
        }
        int i = ByteBuffer.wrap(data).getInt();
        return String.valueOf(i);
    }




    /**
     * バイト配列をオブジェクトにデシリアライズする
     *
     * @param bytes バイト配列
     * @return object
     * @throws ClassNotFoundException クラスが存在しない
     * @throws IOException IOException
     * @since 2014
     */
    public static Object byteArrayToObject(byte[] bytes) throws ClassNotFoundException, IOException {
        ByteArrayInputStream bais = null;
        ObjectInputStream oin = null;
        try {
            bais = new ByteArrayInputStream(bytes);
        } finally {
            if (bais != null) {
                bais.close();
            }

        }
        Object obj = null;
        try {
            oin = new ObjectInputStream(bais);
            obj = oin.readObject();
        } finally {
            if (oin != null) {
                oin.close();
            }
        }
        return obj;
    }

    /**
     * .
     * バイトデータを文字列化して返します。
     *
     * @param data
     *            文字列化するバイトデータ
     * @return 文字列化したバイトデータ
     */
    public static String toDebuggableHexString(final byte[] data) {
        if (data == null) {
            // パラメータ不正
            return "";
        }

        StringBuffer result = new StringBuffer();

        for (int i = 0; i < data.length; i++) {
            result.append(String.format("%02X ", data[i]));

            if (((i + 1) % LINE_BYTES == 0) && (i + 1 < data.length)) {
                // 16 バイトごとに改行する
                // ただし、一番最後の場合は改行しない
                result.append(LINE_SEPARATOR);
            }
        }
        return result.toString();
    }

    /**
     * Base64文字列をオブジェクトに変換する
     *
     * @param base64Str Base64文字列
     * @return Object オブジェクト
     * @throws ClassNotFoundException 例外
     * @throws IOException 例外
     * @since 2014
     */
    public static Object base64StringToObject(final String base64Str) throws ClassNotFoundException, IOException {
        byte[] bytes = Base64.decodeBase64(base64Str.getBytes());
        return byteArrayToObject(bytes);
    }

    /**
     * BitSetをByte配列に変換する
     *
     * @param bitSet BitSet
     * @return byte[] バイト配列
     * @since 2014
     */
    public static byte[] bitSet2ByteArray(final BitSet bitSet) {
        byte[] bytes = new byte[bitSet.size() / 8];
        for (int i = 0; i < bitSet.size(); i++) {
            int index = i / 8;
            int offset = 7 - i % 8;
            bytes[index] |= (bitSet.get(i) ? 1 : 0) << offset;
        }
        return bytes;
    }

    /**
     * バイト配列をBitSetに変換する
     *
     * @param bytes バイト配列
     * @return BitSet BitSet
     * @since 2014
     */
    public static BitSet byteArray2BitSet(final byte[] bytes) {
        BitSet bitSet = new BitSet(bytes.length * 8);
        int index = 0;
        for (byte b : bytes) {
            for (int j = 7; j >= 0; j--) {
                bitSet.set(index++, (b & (1 << j)) >> j == 1 ? true : false);
            }
        }
        return bitSet;
    }

    /**
     * バイト配列をオブジェクトにデシリアライズする
     *
     * @param bytes バイト配列
     * @return object
     * @throws ClassNotFoundException クラスが存在しない
     * @throws IOException IOException
     * @since 2014
     */
    public static Object byteArrayToObject(byte[] bytes) throws ClassNotFoundException, IOException {
        ByteArrayInputStream bais = null;
        ObjectInputStream oin = null;
        try {
            bais = new ByteArrayInputStream(bytes);
        } finally {
            if (bais != null) {
                bais.close();
            }

        }
        Object obj = null;
        try {
            oin = new ObjectInputStream(bais);
            obj = oin.readObject();
        } finally {
            if (oin != null) {
                oin.close();
            }
        }
        return obj;
    }

    /**
     * .
     * バイトデータを文字列化して返します。
     *
     * @param data
     *            文字列化するバイトデータ
     * @return 文字列化したバイトデータ
     */
    public static String bytesToString(final byte[] data) {
        if (data == null) {
            // パラメータ不正
            return "";
        }

        StringBuffer result = new StringBuffer();

        for (int i = 0; i < data.length; i++) {
            result.append(String.format("%02X ", data[i]));

            if (((i + 1) % LINE_BYTES == 0) && (i + 1 < data.length)) {
                // 16 バイトごとに改行する
                // ただし、一番最後の場合は改行しない
                result.append(LINE_SEPARATOR);
            }
        }
        return result.toString();
    }

    /**
     * byte配列をBase64エンコードして文字列に変換する.
     *
     * @param bytes
     *        オブジェクト
     * @return 文字列
     * @since 2014
     */
    public static String encodeBase64String(final byte[] bytes) {
        byte[] encoded = Base64.encodeBase64(bytes);
        return new String(encoded);
    }

    /**
     *
     * 16進数文字列を同じ16進数のbyteに変換します.
     *
     * @param hexString byte化する文字列データ
     * @return byte
     * @since 2014
     */
    public static byte hexStringToByte(String hexString) {
        if (hexString == null || hexString.trim().isEmpty()) {
            throw new IllegalArgumentException("hexString is empty..");
        }
        byte b = 0;
        b = Byte.valueOf(hexString, LINE_BYTES);
        return b;
    }

    /**
     *
     * 16進数文字列の配列を同じ16進数のbyte配列に変換します.
     *
     * @param hexStrings byte化する文字列データ
     * @return byte[]
     * @since 2014
     */
    public static byte[] hexStringToBytes(String[] hexStrings) {
        if (hexStrings == null || !(0 < hexStrings.length)) {
            throw new IllegalArgumentException("hexStrings is null or 0 length..");
        }
        byte[] bytes = new byte[hexStrings.length];
        for (int i = 0; i < hexStrings.length; i++) {
            bytes[i] = hexStringToByte(hexStrings[i]);
        }
        return bytes;
    }

    /**
     * オブジェクトをBase64エンコードして文字列に変換する
     *
     * @param object オブジェクト
     * @return 文字列
     * @throws IOException 例外
     * @since 2014
     */
    public static String objectToBase64String(final Object object) throws IOException {
        byte[] bytes = objectToByteArray(object);
        byte[] encoded = Base64.encodeBase64(bytes);
        return new String(encoded);
    }

    /**
     * オブジェクトをバイト配列にシリアライズします
     *
     * @param obj シリアライズ対象のオブジェクト
     * @return バイト配列
     * @throws IOException IOException
     * @since 2014
     */
    public static byte[] objectToByteArray(Object obj) throws IOException {
        byte[] bytes;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            bytes = baos.toByteArray();
        } catch (IOException e) {
            throw e;
        } finally {
            baos.close();
            if (oos != null) {
                oos.close();
            }
        }
        return bytes;
    }

}
