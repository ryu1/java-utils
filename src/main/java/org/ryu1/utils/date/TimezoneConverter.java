package org.ryu1.utils.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * タイムゾーンを変換するクラス.
 * 
 * @author R.Ishitsuka
 * 
 */
@Component
public class TimezoneConverter {

    /**
     * Logger.
     */
    private Logger logger = LoggerFactory.getLogger(TimezoneConverter.class);

    /**
     * タイムゾーン変換で使用する日時のフォーマット.
     */
    @Value("${default.datetime.format}")
    private String defaultDateTimeFormat;

    /**
     * 変換前のタイムゾーン　（DBのタイムゾーン）.
     */
    @Value("${default.timezone.src}")
    private String timezoneSrc;

    /**
     * 変換後のタイムゾーン　（画面に表示する際のタイムゾーン）.
     */
    @Value("${default.timezone.dst}")
    private String timezoneDst;

    /**
     * 指定された日付文字列のタイムゾーンを変換して返却する.
     * 
     * <pre>
     * 変換前のタイムゾーン、変換後のタイムゾーン、日付文字列のフォーマットは定義ファイルから取得する
     * </pre>
     * 
     * @param dateString
     *        変換前の日付文字列
     * @return 変換後の日付文字列
     * @throws ParseException
     *         変換例外
     */
    public String convert(final String dateString) throws ParseException {
        if (logger.isDebugEnabled()) {
            logger.debug("timezoneSrc:" + timezoneSrc);
            logger.debug("timezoneDst:" + timezoneDst);
            logger.debug("dateString:" + dateString);
        }
        if (StringUtils.isBlank(dateString)) {
            return dateString;
        }
        DateFormat df = new SimpleDateFormat(defaultDateTimeFormat);
        df.setTimeZone(TimeZone.getTimeZone(timezoneSrc));
        Date date;
        date = df.parse(dateString);
        df.setLenient(false);
        df.setTimeZone(TimeZone.getTimeZone(timezoneDst));

        return df.format(date);
    }

}
