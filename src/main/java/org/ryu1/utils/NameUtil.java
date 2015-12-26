package org.ryu1.utils;

import java.security.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 命名などの操作を行うUtil.
 * 
 * @author R.Ishitsuka
 * 
 */
public final class NameUtil {

    /** logger. */
    private static Logger logger = LoggerFactory.getLogger(NameUtil.class);

    /**
     * デフォルトコンストラクタの禁止.
     */
    private NameUtil() {
    }

    /**
     * キャメルケース表記をスネークケース表記（小文字）へ変換.
     * 
     * @param targetString
     *        キャメルケース表記文字列
     * @return スネークケース表記文字列
     */
    public static String camelToSnake(final String targetString) {
        // Nullチェック
        if (StringUtils.isBlank(targetString)) {
            logger.error("camelToSnake method called. but,"
                    + " targetString is nothing.");
            throw new InvalidParameterException(
                    "camelToSnake method called. but,"
                            + " targetString is nothing.");
        }
        String convertString =
                targetString.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
                        .replaceAll("([a-z])([A-Z])", "$1_$2");
        return convertString.toLowerCase();
    }

    /**
     * スネークケース表記をローワーキャメルケース表記へ変換.
     * 
     * @param targetString
     *        スネークケース表記文字列
     * @return キャメルケース表記文字列
     */
    public static String snakeToCamel(final String targetString) {

        // nullチェック
        if (StringUtils.isBlank(targetString)) {
            logger.error("snakeToCamel method called. but,"
                    + " targetString is nothing.");
            throw new InvalidParameterException(
                    "snakeToCamel method called. but,"
                            + " targetString is nothing.");
        }

        Pattern p = Pattern.compile("_([a-z])");
        Matcher m = p.matcher(targetString.toLowerCase());

        StringBuffer sb = new StringBuffer(targetString.length());
        while (m.find()) {
            m.appendReplacement(sb, m.group(1).toUpperCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
