package org.ryu1.utils.modbus;

/**
 * エラーレスポンスクラス
 * @author 石塚 隆一
 */
public class ErrorResponseException extends Exception {
    
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;
    
    private int exceptionCode;
    
    
    /**
     * インスタンスを生成する。
     * 
     * @param function ファンクション
     */
    public ErrorResponseException(final ModbusFunction function) {
        super(((ModbusError) function).toString());
        ModbusError modbusError = (ModbusError) function;
        exceptionCode = modbusError.getExceptionCode();
    }
    
    /**
     * exceptionCodeを取得する。
     * @return the exceptionCode
     * @since 2014s
     */
    public int getExceptionCode() {
        return exceptionCode;
    }
}
