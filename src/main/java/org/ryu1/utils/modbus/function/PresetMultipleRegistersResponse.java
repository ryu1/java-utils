package org.ryu1.utils.modbus.function;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import jp.co.nec.appf.machine.gateway.modbus.infrastructure.ModbusFunction;

/**
 * Preset Multiple Registersファンクションのレスポンス・クラス
 *
 * @since 2014
 * @version 1.0
 * @author 石塚 隆一
 */
public class PresetMultipleRegistersResponse extends ModbusFunction {
    
    private int startingAddress; // 0x0000 to 0xFFFF
    
    private int quantityOfRegisters; // 1 - 123 (0x07D0)
    
    private static final int OFFSET = 400001;
    
    
//    private String successMessage;
    
    /**
     * インスタンスを生成する。
     *
     */
    public PresetMultipleRegistersResponse() {
        super(PRESET_MULTIPLE_REGISTERS);
    }
    
    /**
     * インスタンスを生成する。
     *
     * @param startingAddress 開始アドレス
     * @param quantityOfRegisters レジスタの個数
     */
    public PresetMultipleRegistersResponse(final int startingAddress, final int quantityOfRegisters) {
        super(PRESET_MULTIPLE_REGISTERS);
        
        this.startingAddress = startingAddress;
        this.quantityOfRegisters = quantityOfRegisters;
    }
    
    @Override
    public int calculateLength() {
        //Function Code + Quantity Of Coils + Starting Address, in Byte + 1 for Unit Identifier
        return 1 + 2 + 2 + 1;
    }
    
    @Override
    public void decode(ByteBuf data) {
        startingAddress = data.readUnsignedShort() + OFFSET;
        quantityOfRegisters = data.readUnsignedShort();
    }
    
    @Override
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer(calculateLength());
        buf.writeByte(getFunctionCode());
        buf.writeShort(startingAddress);
        buf.writeShort(quantityOfRegisters);
        
        return buf;
    }
    
    /**
     * quantityOfRegistersを取得する
     *
     * @return quantityOfRegisters レジスタの個数
     * @since 2014
     */
    public int getQuantityOfRegisters() {
        return quantityOfRegisters;
    }
    
    /**
     * startingAddressを取得する
     *
     * @return startingAddress 開始アドレス
     * @since 2014
     */
    public int getStartingAddress() {
        return startingAddress;
    }
    
//    /**
//     * successMessageを取得する
//     *
//     * @return successMessage 成功時のメッセージ
//     * @since 2014
//     */
//    public String getSuccessMessage() {
//        return successMessage;
//    }
//
//    /**
//     * successMessageを設定する
//     * @param successMessage 成功時のメッセージを設定
//     * @since 2014
//     */
//    public void setSuccessMessage(String successMessage) {
//        this.successMessage = successMessage;
//    }
    
    @Override
    public String toString() {
        return "WriteMultipleRegistersResponse{" + "startingAddress=" + startingAddress + ", quantityOfRegisters="
                + quantityOfRegisters + '}';
    }
}
