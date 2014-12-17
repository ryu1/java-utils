package org.ryu1.utils;

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
}
