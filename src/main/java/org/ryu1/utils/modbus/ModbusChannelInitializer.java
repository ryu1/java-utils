package org.ryu1.utils.modbus;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * チャンネルの初期化クラス
 * 
 * @since 2014
 * @version 1.0
 * @author 石塚 隆一
 */
public class ModbusChannelInitializer extends ChannelInitializer<SocketChannel> {
    
    private ModbusEncoder modbusEncoder = null;
    
    private ModbusDecoder modbusDecoder = null;
    
    
    /**
     * インスタンスを生成する。
     * 
     */
    public ModbusChannelInitializer() {
        modbusEncoder = new ModbusEncoder();
        modbusDecoder = new ModbusDecoder();
    }
    
    /**
     * チャンネルを初期化する
     * Modbus TCP Frame Description
     *  - max. 260 Byte (ADU = 7 Byte MBAP + 253 Byte PDU)
     *  - Length field includes Unit Identifier + PDU 
     * 
     * <----------------------------------------------- ADU -------------------------------------------------------->
     * <---------------------------- MBAP -----------------------------------------><------------- PDU ------------->
     * +------------------------+---------------------+----------+-----------------++---------------+---------------+
     * | Transaction Identifier | Protocol Identifier | Length   | Unit Identifier || Function Code | Data          |
     * | (2 Byte)               | (2 Byte)            | (2 Byte) | (1 Byte)        || (1 Byte)      | (1 - 252 Byte |
     * +------------------------+---------------------+----------+-----------------++---------------+---------------+
     */
    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        
        pipeline.addLast("framer", new LengthFieldBasedFrameDecoder(ModbusConstants.ADU_MAX_LENGTH, 4, 2));
        
        //Modbus encoder, decoder
        pipeline.addLast("decoder", modbusDecoder);
        pipeline.addLast("encoder", modbusEncoder);
        pipeline.addLast("handler", new ModbusClientHandler());
    }
    
}
