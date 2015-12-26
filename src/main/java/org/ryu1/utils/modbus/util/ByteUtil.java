package org.ryu1.utils.modbus.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.BitSet;

import org.apache.commons.codec.binary.Base64;

/**
 * バイト操作のユーティティ
 *
 * @since 2014
 * @version 1.0
 * @author 石塚 隆一
 */
public final class ByteUtil {
    
    /** 改行位置 */
    public static final int LINE_BYTES = 16;
    
    /** 区切り文字 */
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    
    
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
    
    private ByteUtil() {
        throw new UnsupportedOperationException();
    }
    
}
