package org.ryu1.utils.modbus;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * エンコーダ
 * 
 * @since 2014
 * @version 1.0
 * @author 石塚 隆一
 */
public class ModbusEncoder extends MessageToByteEncoder<ModbusFrame> {
    
    @Override
    protected void encode(ChannelHandlerContext ctx, ModbusFrame msg, ByteBuf out) {
        ModbusFrame frame = msg;
        //System.out.println(msg.toString());
        ByteBuf b = frame.encode();
        //System.out.println(ByteUtil.bytesToString(b.array()));
        out.writeBytes(b, b.readerIndex(), b.readableBytes());
    }
    
}
