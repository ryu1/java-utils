package org.ryu1.utils;

import static org.osgi.service.log.LogService.LOG_DEBUG;
import static org.osgi.service.log.LogService.LOG_ERROR;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.osgi.service.log.LogService;

/**
 * MQTTハンドラ
 * 
 * @since 2014
 * @version 1.0
 * @author 石塚 隆一
 */
public class MQTTHandler implements MqttCallback {
    
    @Requires
    private LogService logger;
    
    @Requires
    private DownStreamMessageRepository downStreamMessageRepository;
    
    
    /**
     * @see MqttCallback#connectionLost(Throwable)
     */
    @Override
    public void connectionLost(final Throwable cause) {
        // TODO log出力
        System.out.println("call connectionLost");
        logger.log(LOG_ERROR, "", cause);
    }
    
    /**
     * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
     */
    @Override
    public void deliveryComplete(final IMqttDeliveryToken token) {
        System.out.println("call deliveryComplete");
        logger.log(LOG_DEBUG, "Delivery complete callback: Publish Completed " + token.getTopics());
    }
    
    /**
     * @see MqttCallback#messageArrived(String, MqttMessage)
     */
    @Override
    public void messageArrived(final String topic, final MqttMessage message) {
        System.out.println("call messageArrived");
        logger.log(LOG_DEBUG, "Time:\t" + new Timestamp(System.currentTimeMillis()).toString() + "  Topic:\t" + topic
                + "  Message:\t" + new String(message.getPayload()) + "  QoS:\t" + message.getQos());
        byte[] bytes = message.getPayload();
        Map<?, ?> data = null;
        try {
            data = (Map<?, ?>) ByteUtil.byteArrayToObject(bytes);
        } catch (ClassNotFoundException e) {
            // TODO ログ出力
            logger.log(LOG_ERROR, "", e);
        } catch (IOException e) {
            // TODO ログ出力
            logger.log(LOG_ERROR, "", e);
        }
        DownStreamMessage downStreamMessage = new DownStreamMessage(data);
        logger.log(LOG_DEBUG, downStreamMessage.toString());
        try {
            downStreamMessageRepository.save(downStreamMessage);
        } catch (Exception e) {
            // TODO ログ出力
            logger.log(LOG_ERROR, "", e);
        }
    }
}
