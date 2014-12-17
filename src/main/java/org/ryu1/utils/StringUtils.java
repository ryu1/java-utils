package org.ryu1.utils;

import java.util.List;

/**
 * 文字列操作のユティリティークラス.
 * @author 石塚 隆一
 *
 */
public final class StringUtils extends org.apache.commons.lang3.StringUtils {
    
    /** フォーマット: ダブルコーテーションで囲む. */
    public static final String FORMAT_ENCLOSED_IN_DOUBLE_QUOTES  = "\"%s\"";
    
    /** 空文字. */
    public static final String EMPTY = "";
    
    /**
     * インスタンス生成禁止.
     */
    private StringUtils() {
    }
    
    /**
     * 文字列のListを指定されたデリミタで区切って文字列を作成します.
     * @param values 文字列のList
     * @param delemiter デリミタ
     * @return デリミタで区切られた文字列
     */
    public static String collectionToDelimitedString(
            final List<String> values, final String delemiter) {
        return collectionToDelimitedString(
                values, delemiter, FORMAT_ENCLOSED_IN_DOUBLE_QUOTES);
    }
    
    /**
     * 文字列のListを指定されたデリミタで区切って文字列を作成します.
     * @param values 文字列のList
     * @param delemiter デリミタ
     * @param fieldFormat valueのフォーマットト
     * @return デリミタで区切られた文字列
     */
    public static String collectionToDelimitedString(
            final List<String> values, final String delemiter,
            final String fieldFormat) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < values.size(); i++) {
            
            if (values.get(i) != null) {
                final String field  = isEmpty(fieldFormat)
                        ? values.get(i) : String.format(
                                fieldFormat, values.get(i));
                buf.append(field);
            } else {
                final String field  = isEmpty(fieldFormat)
                        ? EMPTY : String.format(
                                fieldFormat, EMPTY);
                buf.append(field);
            }
            
            if (i != values.size() - 1) {
                buf.append(delemiter);
            }
        }
        return buf.toString();
    }
    
    /**
     * 文字列の配列を指定されたデリミタで区切って文字列を作成します.
     * @param values 文字列の配列
     * @param delemiter デリミタ
     * @return デリミタで区切られた文字列
     */
    public static String arrayToDelimitedString(
            final String[] values, final String delemiter) {
        return arrayToDelimitedString(
                values, delemiter, FORMAT_ENCLOSED_IN_DOUBLE_QUOTES);
    }
    
    /**
     * 文字列の配列を指定されたデリミタで区切って文字列を作成します.
     * @param values 文字列の配列
     * @param delemiter デリミタ
     * @param fieldFormat valueのフォーマットト
     * @return デリミタで区切られた文字列
     */
    public static String arrayToDelimitedString(
            final String[] values, final String delemiter,
            final String fieldFormat) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < values.length; i++) {

            if (values[i] != null) {
                final String field  = isEmpty(fieldFormat)
                        ? values[i] : String.format(fieldFormat, values[i]);
                buf.append(field);
            } else {
                final String field  = isEmpty(fieldFormat)
                        ? EMPTY : String.format(fieldFormat, EMPTY);
                buf.append(field);
            }
            
            if (i != values.length - 1) {
                buf.append(delemiter);
            }
        }
        return buf.toString();
    }
}
