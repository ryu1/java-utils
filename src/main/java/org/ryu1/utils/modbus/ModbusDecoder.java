package org.ryu1.utils.modbus;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.util.List;

/**
 * デコーダ
 * 
 * @since 2014
 * @version 1.0
 * @author 石塚 隆一
 */
public class ModbusDecoder extends ByteToMessageDecoder {
    
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        
        if (in instanceof EmptyByteBuf) {
            // TODO IndexOutOfBoundsExceptionを暫定回避
            return;
        }
        
        // ヘッダーを取得
        ModbusHeader mbapHeader =
                new ModbusHeader(in.readUnsignedShort(), in.readUnsignedShort(), in.readUnsignedShort(),
                        in.readUnsignedByte());
        // ファンクションコードを取得
        short functionCode = in.readUnsignedByte();
        ModbusFunction function = null;
        
        switch (functionCode) {
            case ModbusFunction.READ_HOLDING_REGISTER:
                function = new ReadHoldingRegisterResponse();
                break;
            case ModbusFunction.PRESET_SINGLE_REGISTER:
                function = new PresetSingleRegister();
                break;
            case ModbusFunction.PRESET_MULTIPLE_REGISTERS:
                function = new PresetMultipleRegistersResponse();
                break;
            default:
        }
        
        if (ModbusFunction.isError(functionCode)) {
            function = new ModbusError(functionCode);
        } else if (function == null) {
            // TODO 国際化対応
            throw new CorruptedFrameException("Invalid Function Code: " + functionCode);
        }
        
        // データ部を取得
        function.decode(in.readBytes(in.readableBytes()));
        
        ModbusFrame frame = new ModbusFrame(mbapHeader, function);
        
        out.add(frame);
        
    }
}
