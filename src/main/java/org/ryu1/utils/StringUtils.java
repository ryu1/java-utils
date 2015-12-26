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

    /** 指定されたURLに指定されたクエリ文字列を付加して新しいURLを返却する
     *
     * @param url
     * @param paramName　付加するリクエストパラメータの項目名
     * @param paramValue　付加するリクエストパラメータの値
     * @return
     */
    public static String addQueryStringToUrl(String url, String paramName, String paramValue) {
        if (url == null || url.length() == 0) {
            return url;
        }
        String last = url.substring(url.length() - 1);
        System.out.println(last);
        if ("?".equals(last) || "&".equals(last)) {
            return url + paramName + "=" + paramValue;
        } else if (url.indexOf("?") > 0) {
            return url + "&" + paramName + "=" + paramValue;
        } else {
            return url + "?" + paramName + "=" + paramValue;
        }
    }

    /**
     * 指定文字列のn桁以降を取得する。文字列がnullまたはn桁に満たないときはnullを返却する
     *
     * @param string
     * @param n
     *            １以上の整数
     * @return String
     */
    public static String getSubstring(String string, int n) {
        if (n < 1) {
            throw new RuntimeException("桁数は1以上の整数を指定してください n=" + n);
        }
        if (string == null || string.length() < n) {
            return null;
        }
        return string.substring(n - 1);
    }

    /**
     * Turns array of bytes into string
     *
     * @param buf
     *            Array of bytes to convert to hex string
     * @return Generated hex string
     */
    public static String bytes2Hex(byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;

        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10)
                strbuf.append("0");

            strbuf.append(Integer.toString((int) buf[i] & 0xff, 16));
        }

        return strbuf.toString();
    }

    /**
     * バイト配列の比較。
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean equalsBytes(final byte[] a, final byte[] b) {
        if (a == null && b != null) {
            return false;
        } else if (a != null && b == null) {
            return false;
        } else if (a == null && b == null) {
            return true;
        } else if (a != null && b != null) {
            if (a.length != b.length) {
                return false;
            } else {
                int n = a.length;
                for (int i = 0; i < n; i++) {
                    if (a[i] != b[i]) {
                        return false;
                    }
                }
                return true;
            }
        }

        return false;
    }

    /**
     * 文字種類が指定の数だけ満たされているかチェックします。
     *
     * <p>
     * <dl>
     * <dt>aaaaaaaa</dt>
     * <dd>この文字列には文字種類が1種類しかないので、<code>atLeast</code>に2以上が指定されているとfalseが返されます。</dd>
     * <dt>abababab</dt>
     * <dd>この文字列には文字種類が2種類しかないので、<code>atLeast</code>に3以上が指定されているとfalseが返されます。</dd>
     * </dl>
     * </p>
     *
     * @author R.Ishitsuka
     * @param string
     *            チェックする文字列
     * @param atLeast
     *            文字種類の数。必ず正数を指定する必要があります。もし0やマイナスが使われた場合には必ずfalseを返します。
     * @return 指定の数の文字種を含んでいる場合には true を、含んでいない場合には false を返します。
     */
    public static boolean charTypesAtLeast(String string, int atLeast) {
        if (string == null) {
            return false;
        }
        if (atLeast <= 0) {
            return false;
        }
        Set<Character> types = new HashSet<Character>();
        for (int i = 0; i < string.length(); i++) {
            types.add(Character.valueOf(string.charAt(i)));
            if (atLeast <= types.size()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 全角チェック
     *
     * @param String
     *            str チェック元文字列
     * @return boolean true:全角
     */
    public static boolean checkZenkaku(String str) {
        if (str == null) {
            return false;
        }

        return str.matches("^[^ -~｡-ﾟ]+$");
    }

    /**
     * 全角と空白文字チェック
     *
     * @param String
     *            str チェック元文字列
     * @return boolean true:全角と空白文字
     */
    public static boolean checkZenkakuWithSpace(String str) {
        if (str == null) {
            return false;
        }

        return str.matches("^([^ -~｡-ﾟ]|\\s)+$");
    }

    /**
     * 半角チェック
     *
     * @param String
     *            str チェック元文字列
     * @return boolean true:半角
     */
    public static boolean checkHankaku(String str) {
        if (str == null) {
            return false;
        }

        return str.matches("^[ -~｡-ﾟ]+$");
    }

    /**
     * 半角と空白文字チェック
     *
     * @param String
     *            str チェック元文字列
     * @return boolean true:半角と空白文字
     */
    public static boolean checkHankakuWithSpace(String str) {
        if (str == null) {
            return false;
        }

        return str.matches("^([ -~｡-ﾟ]|\\s)+$");
    }

    /**
     * 半角カナチェック
     *
     * @param String
     *            str チェック元文字列
     * @return boolean true:半角カナのみ
     */
    public static boolean checkHankakuKana(String str) {
        if (str == null) {
            return false;
        }

        return str.matches("^[ｱ-ﾝﾞﾟ]+$");
    }

    /**
     * 半角カナと空白文字チェック
     *
     * @param String
     *            str チェック元文字列
     * @return boolean true:半角カナと空白文字
     */
    public static boolean checkHankakuKanaWithSpace(String str) {
        if (str == null) {
            return false;
        }

        return str.matches("^([ｱ-ﾝﾞﾟ]|\\s)+$");
    }

    /**
     * 半角英数字チェック
     *
     * @param String
     *            str チェック元文字列
     * @return boolean true:半角
     */
    public static boolean checkHankakuAlnum(String str) {
        if (str == null) {
            return false;
        }

        return str.matches("^[a-zA-Z0-9]+$");
    }
}
