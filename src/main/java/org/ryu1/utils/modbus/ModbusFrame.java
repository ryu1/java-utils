package org.ryu1.utils.modbus;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Modbusフレーム
 * 
 * @since 2014
 * @version 1.0
 * @author 石塚 隆一
 */
public class ModbusFrame {

    private final ModbusHeader header;

    private ModbusFunction function;

    /**
     * インスタンスを生成する。
     * 
     * @param header
     *        ヘッダー
     * @param function
     *        ファンクション
     */
    public ModbusFrame(final ModbusHeader header, final ModbusFunction function) {
        this.header = header;
        this.function = function;
    }

    /**
     * エンコード
     * 
     * @return ByteBuf
     * @since 2014
     */
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer();

        buf.writeShort(header.getTransactionIdentifier());
        buf.writeShort(header.getProtocolIdentifier());
        buf.writeShort(header.getLength());
        buf.writeByte(header.getUnitIdentifier());
        buf.writeBytes(function.encode());
        return buf;
    }

    /**
     * ファンクションを取得する
     * 
     * @return function
     * @since 2014
     */
    public ModbusFunction getFunction() {
        return function;
    }

    /**
     * Headerを取得する
     * 
     * @return headers
     * @since 2014
     */
    public ModbusHeader getHeader() {
        return header;
    }
    
    @Override
    public String toString() {
        return "ModbusFrame [header=" + header + ", function=" + function + "]";
    }
    
}
