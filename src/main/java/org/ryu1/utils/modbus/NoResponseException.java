package org.ryu1.utils.modbus;

/**
 * レスポンスが存在しない例外
 * @author 石塚 隆一
 */
public class NoResponseException extends Exception {
    
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;
    
    
    /**
     * インスタンスを生成する。
     */
    public NoResponseException() {
        super();
    }
    
    /**
     * インスタンスを生成する。
     * 
     * @param cause 例外が発生した原因
     */
    public NoResponseException(final Throwable cause) {
        super(cause);
    }
}
