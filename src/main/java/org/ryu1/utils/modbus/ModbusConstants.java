package org.ryu1.utils.modbus;

/**
 * Modbus通信のコンスタンツ
 * @author 石塚 隆一
 */
public final class ModbusConstants {
    
    /** TODO プロパティーファイルに設定する */
    public static final int MODBUS_DEFAULT_PORT = 502;
    
    /** TODO プロパティーファイルに設定する */
    public static final int ERROR_OFFSET = 0x80;
    
    /** TODO プロパティーファイルに設定する */
    public static final int RESPONSE_TIMEOUT = 2000; //milliseconds
    
    /** TODO プロパティーファイルに設定する */
    public static final int TRANSACTION_COUNTER_RESET = 16;
    
    /** TODO プロパティーファイルに設定する */
    public static final int ADU_MAX_LENGTH = 260;
    
    
    private ModbusConstants() {
        throw new UnsupportedOperationException();
    }
}
