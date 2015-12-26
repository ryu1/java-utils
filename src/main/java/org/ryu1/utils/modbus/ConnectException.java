package org.ryu1.utils.modbus;

/**
 * 通信エラー
 * @author 石塚 隆一
 */
public class ConnectException extends Exception {
    
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;
    
    
    /**
     * インスタンスを生成する。
     * 
     */
    public ConnectException() {
        super();
    }
    
    /**
     * インスタンスを生成する。
     * 
     * @param cause 例外が発生した原因
     */
    public ConnectException(final Throwable cause) {
        super(cause);
    }
}
