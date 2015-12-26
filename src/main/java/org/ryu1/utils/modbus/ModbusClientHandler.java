package org.ryu1.utils.modbus;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Modbus通信のハンドラクラス
 * @author 石塚 隆一
 */
public class ModbusClientHandler extends SimpleChannelInboundHandler<ModbusFrame> {
    
    // private static final Logger logger =
    // Logger.getLogger(ModbusClientHandler.class.getSimpleName());
    
    private final Map<Integer, ModbusFrame> responses = new HashMap<Integer, ModbusFrame>(
            ModbusConstants.TRANSACTION_COUNTER_RESET);
    
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ModbusFrame msg) {
        ModbusFrame response = msg;
        responses.put(response.getHeader().getTransactionIdentifier(), response);
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // TODO ログ出力
        cause.printStackTrace();
        ctx.close();
    }
    
    /**
     * レスポンスを取得する
     * 
     * @param transactionIdentifier トランザクションID
     * @return response
     * @throws NoResponseException レスポンスが取得できない
     * @throws ErrorResponseException 例外レスポンスが発生しました  
     * @since 2014
     */
    public ModbusFrame getResponse(final int transactionIdentifier) throws NoResponseException, ErrorResponseException {
        long timeoutTime = System.currentTimeMillis() + ModbusConstants.RESPONSE_TIMEOUT;
        ModbusFrame frame = null;
        do {
            frame = responses.get(transactionIdentifier);
        } while (frame == null && (timeoutTime - System.currentTimeMillis()) > 0);
//        } while (frame == null);
//        System.out.println(frame == null ? null : frame.toString());
        
        if (frame != null) {
            responses.remove(transactionIdentifier);
        }
        
        if (frame == null) {
            throw new NoResponseException();
        } else if (ModbusFunction.isError(frame.getFunction().getFunctionCode())) {
            throw new ErrorResponseException(frame.getFunction());
        }
        
        return frame;
    }
    
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // logger.info(evt.toString());
        super.userEventTriggered(ctx, evt);
    }
}
