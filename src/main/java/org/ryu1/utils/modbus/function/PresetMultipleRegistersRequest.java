package org.ryu1.utils.modbus.function;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Preset Multiple Registersファンクションのリクエスト・クラス
 *
 * @since 2014
 * @version 1.0
 * @author 石塚 隆一
 */
public class PresetMultipleRegistersRequest extends ModbusFunction {
    
    private int startingAddress; // 0x0000 to 0xFFFF
    
    private int quantityOfRegisters; // 1 - 123 (0x07D0)
    
    private short byteCount;
    
    private byte[] registers;
    
    private static final int OFFSET = 400001;
    
    private static final int MAXIMUM_OF_REGISTERS = 125;
    
    
    /**
     * インスタンスを生成する。
     *
     */
    public PresetMultipleRegistersRequest() {
        super(PRESET_MULTIPLE_REGISTERS);
    }
    
    /**
     * インスタンスを生成する。
     *
     * @param startingAddress 開始アドレス
     * @param quantityOfRegisters レジスタの数
     * @param registers 変更データ
     */
    public PresetMultipleRegistersRequest(final int startingAddress, final int quantityOfRegisters,
            final byte[] registers) {
        super(PRESET_MULTIPLE_REGISTERS);
        
        // maximum of 125 registers
        if (registers.length > MAXIMUM_OF_REGISTERS) {
            throw new IllegalArgumentException();
        }
        
        byteCount = (short) (registers.length * 1);
        this.registers = registers;
        this.startingAddress = startingAddress;
        this.quantityOfRegisters = quantityOfRegisters;
    }
    
    @Override
    public int calculateLength() {
        return 1 + 2 + 2 + 1 + byteCount + 1; // + 1 for Unit Identifier;
    }
    
    @Override
    public void decode(ByteBuf data) {
        startingAddress = data.readUnsignedShort();
        quantityOfRegisters = data.readUnsignedShort();
        byteCount = data.readUnsignedByte();
        
        registers = new byte[byteCount / 2];
        for (int i = 0; i < registers.length; i++) {
//            registers[i] = data.readUnsignedShort();
            registers[i] = data.readByte();
        }
    }
    
    @Override
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer(calculateLength());
        buf.writeByte(getFunctionCode());
        buf.writeShort(startingAddress - OFFSET);
        buf.writeShort(quantityOfRegisters);
        buf.writeByte(byteCount);
        
        for (Object register : registers) {
////            System.out.println("target:" + register);
//            if (register instanceof Integer) {
////                System.out.println("Integer:" + register);
//                buf.writeShort((Integer) register);
//                continue;
//            } else if (register instanceof Character) {
////                System.out.println("Char:" + register);
//                buf.writeChar((Character) register);
//                continue;
////            } else if (register instanceof char[]) {
////                System.out.println("Char[]:" + register);
////                for (char reg : (char[]) register) {
////                    buf.writeChar(reg);
////                }
////                continue;
//            } else if (register instanceof String) {
////                System.out.println("String:" + register);
//                buf.writeBytes(((String) register).getBytes());
//                continue;
//            } else
            if (register instanceof Byte) {
//                System.out.println("Byte:" + register);
                buf.writeByte(((Byte) register).byteValue());
                continue;
            } else if (register instanceof byte[]) {
//                System.out.println("byte[]:" + register);
                buf.writeBytes((byte[]) register);
                continue;
            } else {
//                System.out.println("Unknown:" + register);
                throw new IllegalArgumentException("Unknown type:" + register);
            }
        }
        
        return buf;
    }
    
    /**
     * バイト数を取得する
     *
     * @return byteCount バイト数
     * @since 2014
     */
    public short getByteCount() {
        return byteCount;
    }
    
    /**
     * レジスタの数を取得する
     *
     * @return quantityOfRegisters レジスタの数
     * @since 2014
     */
    public int getQuantityOfRegisters() {
        return quantityOfRegisters;
    }
    
    /**
     * 変更データを取得する
     *
     * @return registers 変更データ
     * @since 2014
     */
    public byte[] getRegisters() {
        return registers;
    }
    
    /**
     * 開始アドレスを取得する
     *
     * @return startingAddress 開始アドレス
     * @since 2014s
     */
    public int getStartingAddress() {
        return startingAddress;
    }
    
    @Override
    public String toString() {
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
        
        return "WriteMultipleRegistersRequest{" + "startingAddress=" + startingAddress + ", quantityOfRegisters="
                + quantityOfRegisters + ", byteCount=" + byteCount + ", registers=" + registersStr + '}';
    }
}
