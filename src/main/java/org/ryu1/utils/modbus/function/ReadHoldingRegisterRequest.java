package org.ryu1.utils.modbus.function;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import jp.co.nec.appf.machine.gateway.modbus.infrastructure.ModbusFunction;

/**
 * Read Holding Registerのリクエストクラス
 * 
 * @since 2014
 * @version 1.0
 * @author 石塚 隆一
 */
public class ReadHoldingRegisterRequest extends ModbusFunction {
    
    private int startingAddress; // 0x0000 to 0xFFFF
    
    private int quantityOfInputRegisters; // 1 - 125
    
    private static final int OFFSET = 400001;
    
    
    /**
     * インスタンスを生成する。
     * 
     */
    public ReadHoldingRegisterRequest() {
        super(READ_HOLDING_REGISTER);
    }
    
    /**
     * インスタンスを生成する。
     * 
     * @param startingAddress 開始アドレス
     * @param quantityOfInputRegisters レジスタ数
     */
    public ReadHoldingRegisterRequest(int startingAddress, int quantityOfInputRegisters) {
        super(READ_HOLDING_REGISTER);
        
        this.startingAddress = startingAddress;
        this.quantityOfInputRegisters = quantityOfInputRegisters;
    }
    
    @Override
    public int calculateLength() {
        //Function Code + Quantity Of Coils + Starting Address, in Byte + 1 for Unit Identifier
        return 1 + 2 + 2 + 1;
    }
    
    @Override
    public void decode(ByteBuf data) {
        startingAddress = data.readUnsignedShort();
        quantityOfInputRegisters = data.readUnsignedShort();
    }
    
    @Override
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer(calculateLength());
        buf.writeByte(getFunctionCode());
        buf.writeShort(startingAddress - OFFSET);
        buf.writeShort(quantityOfInputRegisters);
        
        return buf;
    }
    
    /**
     * 入力レジスタ数を取得する
     * 
     * @return quantityOfInputRegisters 入力レジスタ数
     * @since 2014s
     */
    public int getQuantityOfInputRegisters() {
        return quantityOfInputRegisters;
    }
    
    /**
     * 開始アドレスを取得する
     * 
     * @return startingAddress 開始アドレス
     * @since 2014
     */
    public int getStartingAddress() {
        return startingAddress;
    }
    
    @Override
    public String toString() {
        // TODO 国際化対応
        return "ReadHoldingRegistersRequest{" + "startingAddress=" + startingAddress + ", quantityOfInputRegisters="
                + quantityOfInputRegisters + '}';
    }
}
