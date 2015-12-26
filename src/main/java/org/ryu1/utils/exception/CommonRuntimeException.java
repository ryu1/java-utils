package org.ryu1.utils.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ryuichi.Ishitsuka
 * 
 */
public class CommonRuntimeException extends RuntimeException {
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
    public CommonRuntimeException(Class clazz, String message, Throwable cause) {
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
     * @author R.Ishitsuka
     * @param clazz
     *            エラーが発生したオブジェクトのClass
     * @param message
     *            エラーメッセージ
     */
    public CommonRuntimeException(Class clazz, String message) {
        super(message);

        log.error("########################################################");
        log.error("Class : [" + clazz.getName() + "]");
        log.error("Message : [" + message + "]");
        log.error("########################################################\n");
    }

    public CommonRuntimeException(String message, Throwable cause) {
        super(message, cause);

        log.error("########################################################");
        log.error("Message : [" + message + "]");
        log.error("Cause : ", cause);
        log.error("########################################################\n");
    }

    public CommonRuntimeException(Throwable cause) {
        super(cause);
    }

    public CommonRuntimeException() {
        super();
    }

    public CommonRuntimeException(String message) {
        super(message);

        log.error("########################################################");
        log.error("Message : [" + message + "]");
        log.error("########################################################\n");
    }
}
