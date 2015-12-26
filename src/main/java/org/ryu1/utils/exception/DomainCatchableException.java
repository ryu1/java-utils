package org.ryu1.utils.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ドメインレイヤで発生したエラーのためのExceptionです。
 * 
 * <p>
 * ドメインレイアにおいては続行不可能なエラーが発生した際に、システムログにも残します。それらエラー処理を簡単に行うためにこのExceptionクラスを利用します。
 * </p>
 * 
 * @author Ryuichi.Ishitsuka
 */
public class DomainCatchableException extends Exception {

    Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Enum型エラーコード
     */
    private CommonErrorCode errorCode;

    /**
     * ドメインレイヤにおいて発生したExceptionを処理します。
     * 
     * @author Ryuichi.Ishitsuka
     * @param errorCode
     *            指定するエラーコード
     * @param model
     *            あとでログを追いやすくするために購入履歴情報のモデルなどをセットする。
     */
    public DomainCatchableException(final CommonErrorCode errorCode, final Object[] models) {
        super(errorCode.getErrorCode());
        this.errorCode = errorCode;
        log(errorCode, models, null);
    }

    /**
     * ドメインレイヤにおいて発生したExceptionを処理します。
     * 
     * @author Ryuichi.Ishitsuka
     * @param errorCode
     *            指定するエラーコード
     * @param model
     *            あとでログを追いやすくするために購入履歴情報のモデルなどをセットする。
     */
    public DomainCatchableException(final CommonErrorCode errorCode, final Object model) {
        super(errorCode.getErrorCode());
        this.errorCode = errorCode;
        log(errorCode, (new Object[] { model }), null);
    }

    /**
     * ドメイン層において発生したExceptionを処理します。(複数モデル対応版)
     * 
     * @author Ryuichi.Ishitsuka
     * @param errorCode
     *            指定するエラーコード
     * @param models
     *            あとでログを追いやすくするために購入履歴情報のモデルなどをセットします。複数モデル対応です。
     */
    public DomainCatchableException(final CommonErrorCode errorCode, final Object model, Throwable e) {
        super(errorCode.getErrorCode(), e);
        this.errorCode = errorCode;
        log(errorCode, (new Object[] { model }), e);
    }

    /**
     * ドメイン層において発生したExceptionを処理します。
     * 
     * @author R.Ishitsuka
     * @param errorCode
     *            指定するエラーコード(Enum型)
     * @param models
     *            あとでログを追いやすくするために購入履歴情報のモデルなどをセットします。
     * @param e
     *            別のExceptionが発生しているならそのExceptionをセットします。
     */
    public DomainCatchableException(final CommonErrorCode errorCode,
            final Object[] models, final Throwable e) {
        super(errorCode.getErrorMessage(), e);
        this.errorCode = errorCode;
        log(errorCode, models, e);
    }

    /**
     * エラーをログに出力
     * @param errorCode
     * @param models
     * @param e
     */
    private void log(final CommonErrorCode errorCode,
            final Object[] models, final Throwable e) {
        StringBuilder logBuffer = new StringBuilder("\n");
        logBuffer.append("##################################################\n");
        logBuffer.append("code: " + this.errorCode.getErrorCode() + "\n");
        logBuffer.append("message: " + this.errorCode.getErrorMessage() + "\n");

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
