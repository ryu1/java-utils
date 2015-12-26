package org.ryu1.utils.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ryuichi.Ishitsuka
 * 
 */
public class CommonCatchableException extends Exception {
    // ログ
    private Logger log = LoggerFactory
            .getLogger(this.getClass());

    /**
     * Exceptionが発生した場合
     * 
     * <p>
     * エラーが発生する可能性がある処理の中には <blockquote>
     * 
     * <pre>
     *     try {
     *         // エラーが発生する可能性がある処理。。。。
     *         ........
     *     } catch(Exception ex) {
     *         // エラーが発生した場合
     *         throw new AMSCatchableException(this.getClass(), &quot;エラーメッセージ&quot;, ex);
     *     }
     * </pre>
     * 
     * </blockquote> のようにエラー処理をします。
     * </p>
     * 
     * @author R.Ishitsuka
     * @param clazz
     *            エラーが発生したオブジェクトのClass
     * @param message
     *            エラーメッセージ
     * @param cause
     */
    public CommonCatchableException(Class clazz, String message, Throwable cause) {
        super(message, cause);

        log.error("########################################################");
        log.error("Class : [" + clazz.getName() + "]");
        log.error("Message : [" + message + "]");
        log.error("Cause : ", cause);
        log.error("########################################################\n");
    }

    /**
     * ユーザ指定エラー処理の場合
     * 
     * <p>
     * Exceptionじゃなく処理的にユーザがエラーにしたいの場合。<br />
     * 例えば、Hibernateのエラーとか。。。<br />
     * 処理の中には <blockquote>
     * 
     * <pre>
     * if (tManager == null) {
     *     // 処理的、エラーにしたい場合
     *     throw new AMSCatchableException(this.getClass(), &quot;エラーメッセージ&quot;);
     * }
     * </pre>
     * 
     * </blockquote> のようにエラー処理をします。
     * </p>
     * 
     * @author 金度亨
     * @param clazz
     *            エラーが発生したオブジェクトのClass
     * @param message
     *            エラーメッセージ
     */
    public CommonCatchableException(Class clazz, String message) {
        super(message);

        log.error("########################################################");
        log.error("Class : [" + clazz.getName() + "]");
        log.error("Message : [" + message + "]");
        log.error("########################################################\n");
    }

    public CommonCatchableException(String message, Throwable cause) {
        super(message, cause);

        log.error("########################################################");
        log.error("Message : [" + message + "]");
        log.error("Cause : ", cause);
        log.error("########################################################\n");
    }

    public CommonCatchableException(Throwable cause) {
        super(cause);
    }

    public CommonCatchableException() {
        super();
    }

    public CommonCatchableException(String message) {
        super(message);

        log.error("########################################################");
        log.error("Message : [" + message + "]");
        log.error("########################################################\n");
    }

    public CommonCatchableException(final String message, final Object model, Throwable e) {
        super(message, e);
        log(message, (new Object[] { model }), e);
    }

    public CommonCatchableException(final String message,
            final Object[] models, final Throwable e) {
        super(message, e);
        log(message, models, e);
    }

    public CommonCatchableException(final String message, final Object model) {
        super(message);
        log(message, (new Object[] { model }), null);
    }

    public CommonCatchableException(final String message, final Object[] models) {
        super(message);
        log(message, models, null);
    }

    /**
     * エラーをログに出力
     * @param errorCode
     * @param models
     * @param e
     */
    private void log(final String message,
            final Object[] models, final Throwable e) {
        StringBuilder logBuffer = new StringBuilder("\n");
        logBuffer.append("##################################################\n");
        logBuffer.append("message: " + message + "\n");

        //modelログ表示処理
        if (models != null) {
            int i = 0;
            for (Object model : models) {
                logBuffer.append("models[" + (++i) + "]: ");
                logBuffer.append(model + "\n");
            }
        } else {
            logBuffer.append("models: nothing\n");
        }
        logBuffer.append("cause: " + (e != null ? e.getCause() : "\n"));
        logBuffer.append("stacktrace: \n"
                + (e != null ? "\n" + e.getStackTrace() : ""));
        logBuffer.append("##################################################\n");
        // 通常ログへの出力
        log.error(logBuffer);
    }
}
