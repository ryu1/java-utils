package org.ryu1.utils.modbus.function;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Preset Single Registerのリクエスト及びレスポンスクラス
 * @author 石塚 隆一
 */
public class PresetSingleRegister extends ModbusFunction {
    
    private int registerAddress;
    
    private int registerValue;
    
    private static final int OFFSET = 400001;
    
    
    /**
     * インスタンスを生成する。
     * 
     */
    public PresetSingleRegister() {
        super(PRESET_SINGLE_REGISTER);
    }
    
    /**
     * インスタンスを生成する。
     * 
     * @param outputAddress レジスタのアドレス
     * @param value 変更データ
     */
    public PresetSingleRegister(final int outputAddress, final int value) {
        super(PRESET_SINGLE_REGISTER);
        
        registerAddress = outputAddress;
        registerValue = value;
    }
    
    @Override
    public int calculateLength() {
        //Function Code + Output Address + Output Value, in Byte + 1 for Unit Identifier
        return 1 + 2 + 2 + 1;
    }
    
    @Override
    public void decode(ByteBuf data) {
        registerAddress = data.readUnsignedShort() + OFFSET;
        registerValue = data.readUnsignedShort();
    }
    
    @Override
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer(calculateLength());
        buf.writeByte(getFunctionCode());
        buf.writeShort(registerAddress - OFFSET);
        buf.writeShort(registerValue);
        
        return buf;
    }
    
    /**
     * レジスタのアドレスを取得する
     * 
     * @return registerAddress レジスタのアドレス
     * @since 2014
     */
    public int getRegisterAddress() {
        return registerAddress;
    }
    
    /**
     * 変更データを取得する
     * 
     * @return registerValue 変更データ
     * @since 2014
     */
    public int getRegisterValue() {
        return registerValue;
    }
    
    @Override
    public String toString() {
        // TODO 国際化対応
        return "WriteSingleInputRegister{" + "registerAddress=" + registerAddress + ", registerValue=" + registerValue
                + '}';
    }
}
