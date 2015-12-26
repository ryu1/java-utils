package org.ryu1.utils.modbus;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * ModbusTCP通信クラス
 *
 * @since 2014
 * @version 1.0
 * @author 石塚 隆一
 */
public class ModbusTCPClient {
    
    private final String host;
    
    private final int port;
    
    private int lastTransactionIdentifier = 0;
    
    private Channel channel;
    
    private Bootstrap bootstrap;
    
    private EventLoopGroup workerGroup;
    
    private ChannelFuture connectFuture;
    
    
    /**
     * インスタンスを生成する。
     *
     * @param host 通信先IPアドレス
     * @param port 通信先ポート
     */
    public ModbusTCPClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    /**
     * トランザクションIDを取得する
     *
     * @return lastTransactionIdentifier
     * @since 2014
     */
    public synchronized int calculateTransactionIdentifier() {
        if (lastTransactionIdentifier < ModbusConstants.TRANSACTION_COUNTER_RESET) {
            lastTransactionIdentifier++;
        } else {
            lastTransactionIdentifier = 1;
        }
        return lastTransactionIdentifier;
    }
    
    /**
     * 送信する
     *
     * @param unitIndetifier ユニット識別子
     * @param function リクエスト
     * @return function レスポンス
     * @throws ConnectException 通信例外
     * @throws ErrorResponseException エラーレスポンス例外
     * @throws NoResponseException レスポンスが存在しない例外
     * @since 2014
     */
    public ModbusFunction callModbusFunction(int unitIndetifier, ModbusFunction function) throws ConnectException,
            NoResponseException, ErrorResponseException {
        //System.out.println("channel.isOpen(): " + channel.isOpen());
        if (!channel.isOpen()) {
            // log出力
            throw new ConnectException();
        }
        
        int transactionId = calculateTransactionIdentifier();
        int protocolId = ModbusHeader.PROTOCOL_IDENTIFIER;
        //length of the Function in byte
        int length = function.calculateLength();
        
        ModbusHeader header = new ModbusHeader(transactionId, protocolId, length, (short) unitIndetifier);
        ModbusFrame frame = new ModbusFrame(header, function);
        
        channel.writeAndFlush(frame);
        // Get the handler instance to retrieve the answer.
        ModbusClientHandler handler = (ModbusClientHandler) channel.pipeline().last();
        
        return handler.getResponse(transactionId).getFunction();
    }
    
    /**
     * 送信する
     *
     * @param unitIdentifier ユニット識別子
     * @param function リクエスト
     * @return function レスポンス
     * @throws ConnectException 通信例外
     * @throws ErrorResponseException エラーレスポンス例外
     * @throws NoResponseException レスポンスが存在しない例外
     * @since 2014
     */
    public ModbusFunction callModbusFunction(short unitIdentifier, ModbusFunction function) throws ConnectException,
            NoResponseException, ErrorResponseException {
        //System.out.println("channel.isOpen(): " + channel.isOpen());
        if (!channel.isOpen()) {
            // log出力
            throw new ConnectException();
        }
        
        int transactionId = calculateTransactionIdentifier();
        int protocolId = ModbusHeader.PROTOCOL_IDENTIFIER;
        //length of the Function in byte
        int length = function.calculateLength();
        
        ModbusHeader header = new ModbusHeader(transactionId, protocolId, length, unitIdentifier);
        ModbusFrame frame = new ModbusFrame(header, function);
        
        channel.writeAndFlush(frame);
        // Get the handler instance to retrieve the answer.
        ModbusClientHandler handler = (ModbusClientHandler) channel.pipeline().last();
        
        return handler.getResponse(transactionId).getFunction();
    }
    
    /**
     *
     * コネクションを閉じる
     *
     * @since 2014
     */
    public void close() {
        if (channel != null) {
            channel.close();
            channel.closeFuture().awaitUninterruptibly();
        }
        workerGroup.shutdownGracefully();
    }
    
    /**
     * コネクションを開く
     * @throws Exception 例外が発生
     *
     * @throws InterruptedException スレッドへの割り込みが発生
     * @since 2014
     */
    public void open() throws Exception {
        // Configure the client.
        workerGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        
        bootstrap.handler(new ModbusChannelInitializer());
        
        connectFuture = bootstrap.connect(host, port);
        channel = connectFuture.awaitUninterruptibly().channel();
        
        if (!connectFuture.isSuccess()) {
            throwException(connectFuture.cause());
        }
    }
    
//    /**
//     * PRESET MULTIPLE REGISTERSを実行する
//     *
//     * @param unitIndetifier ユニット識別子
//     * @param startAddress 開始アドレス
//     * @param quantityOfInputRegisters レジスタ数
//     * @param values 変更データの配列
//     * @return PresetMultipleRegistersResponse レスポンス
//     * @throws Exception 例外が発生
//     * @since 2014
//     */
//    public PresetMultipleRegistersResponse presetMultipleRegisters(final int unitIndetifier, final int startAddress,
//            final int quantityOfInputRegisters, final Object[] values) throws Exception {
//
//        PresetMultipleRegistersRequest frame =
//                new PresetMultipleRegistersRequest(startAddress, quantityOfInputRegisters, values);
//        return (PresetMultipleRegistersResponse) callModbusFunction(unitIndetifier, frame);
//    }
//
//    /**
//     * PRESET SINGLE REGISTERを実行する
//     *
//     * @param unitIndetifier ユニット識別子
//     * @param address アドレス
//     * @param value 変更データ
//     * @return PresetSingleRegister レスポンス
//     * @throws Exception 例外が発生
//     * @since 2014
//     */
//    public PresetSingleRegister presetSingleRegister(short unitIndetifier, int address, int value) throws Exception {
//        PresetSingleRegister frame = new PresetSingleRegister(address, value);
//        return (PresetSingleRegister) callModbusFunction(unitIndetifier, frame);
//    }
//
//    /**
//     * READ HOLDING REGISTERを実行します
//     *
//     * @param unitIdentifier ユニット識別子
//     * @param startAddress 開始アドレス
//     * @param quantityOfInputRegisters レジスタ数
//     * @return ReadHoldingRegisterResponse レスポンス
//     * @throws Exception 例外が発生
//     * @since 2014
//     */
//    public ReadHoldingRegisterResponse readHoldingRegister(short unitIdentifier, int startAddress,
//            int quantityOfInputRegisters) throws Exception {
//        ReadHoldingRegisterRequest request = new ReadHoldingRegisterRequest(startAddress, quantityOfInputRegisters);
//        return (ReadHoldingRegisterResponse) callModbusFunction(unitIdentifier, request);
//    }
    
    private void throwException(Throwable e) throws Exception {
        if (e instanceof Exception) {
            throw (Exception) e;
        } else if (e instanceof Error) {
            throw (Error) e;
        } else if (e instanceof RuntimeException) {
            throw (RuntimeException) connectFuture.cause();
        } else {
            throw new Error(e);
        }
    }
    
}
