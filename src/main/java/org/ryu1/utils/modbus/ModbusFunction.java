package org.ryu1.utils.modbus;

import io.netty.buffer.ByteBuf;

/**
 * Modbusファンクション
 * 
 * @since 2014
 * @version 1.0
 * @author 石塚 隆一
 */
public abstract class ModbusFunction {
    
    /**
     * 不正なファンクション・コードか
     *
     * @param functionCode ファンクション・コード
     * @return boolean
     *   true 不正なファンクション・コード
     *   false 正常なファンクション・コード
     * @since 2014
     */
    public static boolean isError(final short functionCode) {
        return functionCode - ModbusConstants.ERROR_OFFSET >= 0;
    }
    
    
    /** READ_HOLDING_REGISTER 03 */
    public static final short READ_HOLDING_REGISTER = 0x03;
    
    /** PRESET_SINGLE_REGISTER 06 */
    public static final short PRESET_SINGLE_REGISTER = 0x06;
    
    /** PRESET_MULTIPLE_REGISTERS 16 */
    public static final short PRESET_MULTIPLE_REGISTERS = 0x10;
    
    private final short functionCode;
    
    
    /**
     * インスタンスを生成する。
     *
     * @param functionCode ファンクション・コード
     */
    public ModbusFunction(final short functionCode) {
        this.functionCode = functionCode;
    }
    
    /**
     * フィールド長を返却する
     * 
     * @return int フィールド長
     * @since 2014
     */
    public abstract int calculateLength();
    
    /**
     * データ部をデコードする
     *
     * @param data データ部
     * @since 2014
     */
    public abstract void decode(ByteBuf data);
    
    /**
     * データ部をエンコードする
     * 
     * @return ByteBuf
     * @since 2014
     */
    public abstract ByteBuf encode();
    
    /**
     * ファンクションコードを取得する
     * 
     * @return functionCode
     * @since 2014
     */
    public short getFunctionCode() {
        return functionCode;
    }
    
    @Override
    public String toString() {
        return "ModbusFunction [functionCode=" + functionCode + "]";
    }
}
