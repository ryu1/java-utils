package org.ryu1.utils.modbus.function;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import jp.co.nec.appf.machine.gateway.modbus.infrastructure.ModbusFunction;

/**
 * Read Holding Registerのレスポンスクラス
 * 
 * @since 2014
 * @version 1.0
 * @author 石塚 隆一
 */
public class ReadHoldingRegisterResponse extends ModbusFunction {
    
    private short byteCount;
    
    private int[] registers;
    
    private static final int MAXIMUM_OF_REGISTERS = 125;
    
    
    /**
     * インスタンスを生成する。
     * 
     */
    public ReadHoldingRegisterResponse() {
        super(READ_HOLDING_REGISTER);
    }
    
    /**
     * インスタンスを生成する。
     * 
     * @param registers レジスタの配列
     */
    public ReadHoldingRegisterResponse(final int[] registers) {
        super(READ_HOLDING_REGISTER);
        
        // maximum of 125 registers
        if (registers.length > MAXIMUM_OF_REGISTERS) {
            throw new IllegalArgumentException();
        }
        
        byteCount = (short) (registers.length * 2);
        this.registers = registers;
    }
    
    @Override
    public int calculateLength() {
        return 1 + 1 + byteCount + 1; // + 1 for Unit Identifier;
    }
    
    @Override
    public void decode(ByteBuf data) {
        byteCount = data.readUnsignedByte();
        
        registers = new int[byteCount / 2];
        for (int i = 0; i < registers.length; i++) {
            registers[i] = data.readUnsignedShort();
        }
    }
    
    @Override
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer(calculateLength());
        buf.writeByte(getFunctionCode());
        buf.writeByte(byteCount);
        
        for (int register : registers) {
            buf.writeShort(register);
        }
        
        return buf;
    }
    
    /**
     * TODO レジスタを取得する
     * 
     * @return registers レジスタ
     * @since 2014
     */
    public int[] getRegisters() {
        return registers;
    }
    
    @Override
    public String toString() {
        // TODO 国際化対応
        StringBuilder registersStr = new StringBuilder();
        registersStr.append("{");
        for (int i = 0; i < registers.length; i++) {
            registersStr.append("register_");
            registersStr.append(i);
            registersStr.append("=");
            registersStr.append(registers[i]);
            registersStr.append(", ");
        }
        registersStr.delete(registersStr.length() - 2, registersStr.length());
        registersStr.append("}");
        
        return "ReadHoldingRegistersResponse{" + "byteCount=" + byteCount + ", inputRegisters=" + registersStr + '}';
    }
}
