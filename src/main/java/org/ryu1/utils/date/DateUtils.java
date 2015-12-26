package org.ryu1.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by ryu on 14/12/17.
 */
public class DateUtils {

    public static String TIME_ZONE_JST = "JST";
    public static String TIME_ZONE_UTC = "UTC";
    public static String DATE_FORMAT_YYYYMMDDHH24MISSsssTZHTZM = "YYYYMMDDHH24MISSsssTZHTZM";
    public static String DATE_FORMAT_YYYYMMDDHH24MISSsss = "YYYYMMDDHH24MISSsss";

    /** 日付書式(yyyy/MM). */
    public static final String SLASH_YYYYMM = "yyyy/MM";

    /** 日付書式(yyyy/MM/dd). */
    public static final String SLASH_YYYYMMDD = "yyyy/MM/dd";

    /** 日付書式(yyyy/MM/dd HH:mm:ss). */
    public static final String SLASH_YYYYMMDDHHMMSS = "yyyy/MM/dd HH:mm:ss";

    /** 日付書式(yyyyMMddHHmm). */
    public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";

    /** 日付書式(yyyyMMddHHmmss). */
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /** 区切り文字(ハイフン). */
    public static final String SPLIT_HYPHEN = "-";

    /** 区切り文字(スラッシュ). */
    public static final String SPLIT_SLASH = "/";

    /** UTC. */
    public static final String UTC = "UTC";

    /** GMT. */
    public static final String GMT = "GMT";

    /** ６０分. */
    public static final int MINUTES_OF_HOURS = 60;

    /** タイムゾーンチェック用正規表現. */
    private static final String TIME_ZONE_REGEX = "^[\\+,\\-][0-9]{4}$";

    private DateUtils() {
    }

    /**
     * タイムゾーンなしのUTC日時文字列をタイムゾーン付きのUTC日時文字列にフォーマットします.
     * @param dateTimeAsUTCWithoutTimeZone タイムゾーンなしのUTC日時文字列
     * @return タイムゾーン付きのUTC日時文字列
     * @throws java.text.ParseException
     */
    public static String formatDateTimeWithUTCTimeZone(
            final String dateTimeAsUTCWithoutTimeZone) throws ParseException {
        return formatDateTimeAsUTC(
                parseDateTimeAsUTC(dateTimeAsUTCWithoutTimeZone));
    }

    /**
     * タイムゾーンなしのJST日時文字列をタイムゾーン付きのUTC日時文字列にフォーマットします.
     * @param dateTimeAsUTCWithoutTimeZone タイムゾーンなしのUTC日時文字列
     * @return タイムゾーン付きのUTC日時文字列
     * @throws ParseException
     */
    public static String formatDateTimeWithJSTTimeZone(
            final String dateTimeAsUTCWithoutTimeZone) throws ParseException {
        return formatDateTimeAsJST(
                parseDateTimeAsJST(dateTimeAsUTCWithoutTimeZone));
    }

    /**
     * タイムゾーンなしのUTC日時文字列を、Dateオブジェクトにパースします.
     *
     * @param dateTime
     *        String型の日時(YYYYMMDDHH24MISSsss)
     * @return Date型の日時
     * @throws ParseException 変換エラー
     */
    public static Date
    parseDateTimeAsUTC(final String dateTime)
            throws ParseException {

        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHH24MISSsss);
        simpleDateFormat.setTimeZone(TimeZone
                .getTimeZone(TIME_ZONE_UTC));

        simpleDateFormat.setLenient(false);
        return simpleDateFormat.parse(dateTime);
    }

    /**
     * タイムゾーンなしのJST日時文字列を、Dateオブジェクトにパースします.
     *
     * @param dateTime
     *        String型の日時(YYYYMMDDHH24MISSsss)
     * @return Date型の日時
     * @throws ParseException 変換エラー
     */
    public static Date
    parseDateTimeAsJST(final String dateTime)
            throws ParseException {
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHH24MISSsss);
        simpleDateFormat.setTimeZone(TimeZone
                .getTimeZone(TIME_ZONE_JST));

        simpleDateFormat.setLenient(false);
        return simpleDateFormat.parse(dateTime);
    }

    /**
     * タイムゾーンなしのJST日時文字列を、Dateオブジェクトにパースします.
     *
     * @param dateTime
     *        String型の日時(YYYYMMDDHH24MISSsss)
     * @return Date型の日時
     * @throws ParseException 変換エラー
     */
    public static Date
    parseDateTimeZeroMills(final String dateTime)
            throws ParseException {

        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHH24MISSsss);
        simpleDateFormat.setTimeZone(TimeZone
                .getTimeZone(TIME_ZONE_JST));

        simpleDateFormat.setLenient(false);
        return simpleDateFormat.parse(dateTime);
    }

    /**
     * タイムゾーンつきの日時文字列を、Dateオブジェクトにパースします.
     *
     * @param dateTime
     *        String型の日時(DATE_FORMAT_YYYYMMDDHH24MISSsssTZHTZM)
     * @return Date型の日時
     * @throws ParseException 変換エラー
     */
    public static Date
    parseDateTimeWithTimeZone(final String dateTime)
            throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                DATE_FORMAT_YYYYMMDDHH24MISSsssTZHTZM);
        simpleDateFormat.setLenient(false);
        return simpleDateFormat.parse(dateTime);
    }

    /**
     * UTCタイムゾーンつきの日時文字列にフォーマットします.
     *
     * @param dateTime
     *        Date型の日時
     * @return String型の日時
     */
    public static String formatDateTimeAsUTC(final Date dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                DATE_FORMAT_YYYYMMDDHH24MISSsssTZHTZM);
        simpleDateFormat.setTimeZone(TimeZone
                .getTimeZone(TIME_ZONE_UTC));

        simpleDateFormat.setLenient(false);
        return simpleDateFormat.format(dateTime);
    }

    /**
     * JSTタイムゾーンつきの日時文字列にフォーマットします.
     *
     * @param dateTime
     *        Date型の日時
     * @return String型の日時
     */
    public static String formatDateTimeAsJST(final Date dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                DATE_FORMAT_YYYYMMDDHH24MISSsssTZHTZM);
        simpleDateFormat.setTimeZone(TimeZone
                .getTimeZone(TIME_ZONE_JST));

        simpleDateFormat.setLenient(false);
        return simpleDateFormat.format(dateTime);
    }

    /**
     * 日付を指定したpatternにフォーマットします.
     * @param dateTime 日付
     * @param pattern パターン
     * @return 日付文字列
     */
    public static String formatDateTime(
            final Date dateTime, final String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.setLenient(false);
        return simpleDateFormat.format(dateTime);
    }

    /**
     * 日付文字列は指定したフォーマットか.
     * @param dateTime String型の日時
     * @param pattern パターン
     * @return true or false
     */
    public static boolean isFormatableDateTime(
            final String dateTime, final String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(dateTime);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * 指定された形式で時刻文字列を取得する.
     *
     * @param millTime
     *        ミリ秒
     * @param format
     *        表示形式
     * @return 文字列
     */
    public static String getString(final long millTime, final String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date(millTime));
    }

    /**
     * 現在日付を取得する.
     *
     * @return String 現在日付(yyyy/MM/dd HH:mi:ss)
     */
    public static String getCurrentDate() {
        return getCurrentDate(SLASH_YYYYMMDDHHMMSS);
    }

    /**
     * 現在日付を取得する.
     *
     * @param format
     *        フォーマット
     * @return String 現在日付
     */
    public static String getCurrentDate(final String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * 日付の文字列をLong型にする.
     *
     * @param strDate
     *        日付文字列
     * @return long
     */
    public static long getTime(final String strDate) {
        if (checkDate(strDate.replace(SPLIT_HYPHEN, SPLIT_SLASH))) {
            Date date = parseDate(strDate);
            if (null == date) {
                date = parseDateTime(strDate);
            }
            return date.getTime();
        }
        return 0;
    }

    /**
     * ハイフン区切り日付(yyyy/MM/dd)の妥当性をチェックする.
     *
     * @param strDate
     *        日付文字列
     * @return true:妥当, false:妥当ではない(月末、閏年)
     */
    public static boolean checkDate(final String strDate) {
        if (strDate == null) {
            return true;
        }

        String[] datetimeArray = strDate.split(SPLIT_SLASH);
        if (datetimeArray.length != 3) {
            return false;
        }

        String year = datetimeArray[0];
        String month = datetimeArray[1];
        String date = datetimeArray[2];

        if (String.valueOf(year).length() != 4
                || String.valueOf(month).length() != 2
                || String.valueOf(date).length() != 2) {
            return false;
        }

        try {
            int intYear = Integer.parseInt(year);
            int intMonth = Integer.parseInt(month);
            int intDate = Integer.parseInt(date);

            if (intYear < 0) {
                return false;
            }
            if (intMonth < 0 || intMonth > 12 || intMonth == 0) {
                return false;
            }
            if (intDate < 0 || intDate > 31 || intDate == 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        String trimDate = strDate.trim();
        trimDate += " 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat(SLASH_YYYYMMDDHHMMSS);
        sdf.setLenient(false);

        if (strDate.contains(SPLIT_HYPHEN)) {
            return false;
        }

        try {
            sdf.parse(trimDate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * 日付のフォーマットチェック.
     *
     * @param strDate
     *        日付文字列
     * @param format
     *        フォーマット
     * @return boolean true:フォーマット一致, false:フォーマット不一致
     */
    public static boolean checkDateFormat(final String strDate,
            final String format) {

        if (CheckUtil.isEmpty(strDate)) {
            return false;
        }

        if (parseDate(strDate, format, false) == null) {
            return false;
        }

        return true;
    }

    /**
     * 指定された形式の日付(文字列)にする.
     *
     * @param timestamp
     *        日付
     * @param format
     *        表示形式
     * @return String 指定した表示形式の日付(文字列)
     */
    public static String parseString(final Timestamp timestamp,
            final String format) {

        if (CheckUtil.isEmpty(timestamp) || CheckUtil.isEmpty(format)) {
            return null;
        }

        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date(timestamp.getTime()));
    }

    /**
     * 指定された形式の時刻文字列を日付型にする.
     *
     * @param strDate
     *        日付文字列
     * @param format
     *        表示形式
     * @param bLenient
     *        厳密指定
     * @return 日付
     */
    private static Date parseDate(final String strDate, final String format,
            final boolean... bLenient) {

        if (CheckUtil.isEmpty(strDate) || CheckUtil.isEmpty(format)) {
            return null;
        }

        DateFormat df = new SimpleDateFormat(format);
        Date date = null;

        if ((bLenient != null) && (bLenient.length > 0)) {
            df.setLenient(bLenient[0]);
        }

        try {
            date = df.parse(strDate);

            String formatDate = df.format(df.parse(strDate)).toString();
            if (!strDate.equals(formatDate)) {
                return null;
            }
        } catch (ParseException e) {
            return null;
        }
        return date;
    }

    /**
     * [日付]の文字列を日付型にする.
     *
     * @param strDate
     *        日付文字列
     * @return 日付
     */
    public static Date parseDate(final String strDate) {

        if (CheckUtil.isEmpty(strDate)) {
            return null;
        }

        return parseDate(strDate.replace(SPLIT_HYPHEN, SPLIT_SLASH),
                SLASH_YYYYMMDD);
    }

    /**
     * [日付+時刻]の文字列を日付型にする.
     *
     * @param strDate
     *        日付文字列
     * @return 日付
     */
    public static Date parseDateTime(final String strDate) {

        if (CheckUtil.isEmpty(strDate)) {
            return null;
        }

        return parseDate(strDate.replace(SPLIT_HYPHEN, SPLIT_SLASH),
                SLASH_YYYYMMDDHHMMSS);
    }

    /**
     * タイムゾーン(文字列)から分単位にした数値を返す.
     *
     * @param timeZone
     *        タイムゾーン
     * @return Integer タイムゾーン(分単位)
     */
    public static int getTimeZoneMinutes(final String timeZone) {
        // 正規表現によるチェック
        Pattern p = Pattern.compile(TIME_ZONE_REGEX);
        Matcher m = p.matcher(timeZone);

        // 不一致の場合0を返す
        if (!m.matches()) {
            return 0;
        }

        // 時間と分を取得
        final String symbol = timeZone.substring(0, 1);
        final Integer hour = Integer.parseInt(timeZone.substring(1, 3));
        final Integer minutes = Integer.parseInt(timeZone.substring(3));

        // 分単位に変換
        Integer result = hour * MINUTES_OF_HOURS + minutes;
        if (SPLIT_HYPHEN.equals(symbol)) {
            result = 0 - result;
        }

        return result;
    }
}
