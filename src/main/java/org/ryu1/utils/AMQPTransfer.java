package org.ryu1.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * AMQPプロトコルのプロデューサクラス
 * 
 * @since 2014
 * @version 1.0
 * @author 石塚 隆一
 */
public class AMQPTransfer {
    
    private Connection connection;
    
    private Channel channel;
    
    private ConnectionFactory factory;
    
//    @Requires
//    private Config config;
    
    private Integer prefetchCount;
    
    
//    private String host;
//    
//    private Integer port;
//    
//    private String username;
//    
//    private String password;
    
    /**
     * インスタンスを生成する。
     * 
     * @param host ホスト
     * @param port ポート
     * @param virtualHost バーチャルホスト
     * @param username ユーザーネーム
     * @param password パスワード
     * @param prefetchCount QOS
     * @param timeout タイムアウト時間(ms)
     */
    public AMQPTransfer(final String host, final Integer port, final String virtualHost, final String username,
            final String password, final Integer prefetchCount, final int timeout) {
//        this.context = context;
//        config = lookUpConfig();
//        String host = config.getString("amqp.host");
//        Integer port = config.getInteger("amqp.port");
//        String virtualHost = config.getString("amqp.virtual.host");
//        String username = config.getString("amqp.username");
//        String password = config.getString("amqp.password");
        
        this.prefetchCount = prefetchCount;
        
        factory = createFactory(host, port, virtualHost, username, password, timeout);
        
//        exchangeName = config.getString("amqp.exchange.name");
    }
    
    /**
     * インスタンスを生成する。
     * 
     * @param host ホスト
     * @param port ポート
     * @param virtualHost バーチャルホスト
     * @param username ユーザーネーム
     * @param password パスワード
     * @param prefetchCount QOS
     * @param timeout タイムアウト時間(ms)
     * @param protocol TSL
     * @param algorithm SunX509
     * @param keyType JKS
     * @param trustStorePath KeyStoreのファイルパス
     * @param trustStorePassphrase KeyStoreのパスワード
     * @throws KeyStoreException SSLの例外
     * @throws IOException コネクション・エラー
     * @throws FileNotFoundException SSLの例外
     * @throws CertificateException SSLの例外
     * @throws NoSuchAlgorithmException SSLの例外
     * @throws UnrecoverableKeyException SSLの例外
     * @throws KeyManagementException SSLの例外
     */
    public AMQPTransfer(final String host, final Integer port, final String virtualHost, final String username,
            final String password, final Integer prefetchCount, final int timeout, final String protocol,
            final String algorithm, final String keyType, final String trustStorePath, String trustStorePassphrase)
            throws UnrecoverableKeyException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException,
            CertificateException, FileNotFoundException, IOException {
        this.prefetchCount = prefetchCount;
        factory =
                createSSLFactory(host, port, virtualHost, username, password, timeout, protocol, algorithm, keyType,
                        trustStorePath, trustStorePassphrase);
//        exchangeName = config.getString("amqp.exchange.name");
    }
    
    /**
     * 
     * コネクションを閉じる
     * 
     * @throws IOException 例外
     * @since AIF 石塚 隆一
     */
    public void close() throws IOException {
        channel.close();
        connection.close();
    }
    
    /**
     * 
     * Connection Factoryを作成する
     * 
     * @param host ホスト
     * @param port ポート
     * @param virtualHost バーチャル・ホスト
     * @param username ユーザ名
     * @param password パスワード
     * @param timeout タイムアウト(ms)
     * @return factory
     * @since 2014
     */
    private ConnectionFactory createFactory(final String host, final Integer port, final String virtualHost,
            final String username, final String password, final Integer timeout) {
        ConnectionFactory _factory = new ConnectionFactory();
        _factory.setHost(host);
        _factory.setPort(port);
        _factory.setUsername(username);
        _factory.setPassword(password);
        _factory.setVirtualHost(virtualHost);
        _factory.setConnectionTimeout(timeout);
        
        return _factory;
    }
    
    /**
     * 
     * Connection Factoryを作成する
     * 
     * @param host ホスト
     * @param port ポート
     * @param virtualHost バーチャル・ホスト
     * @param username ユーザ名
     * @param password パスワード
     * @param timeout タイムアウト(ms)
     * @param protocol TSL
     * @param algorithm SunX509
     * @param keyType JKS
     * @param trustStorePath KeyStoreのファイルパス
     * @param trustStorePassphrase KeyStoreのパスワード
     * @return factory
     * @throws KeyStoreException SSLの例外
     * @throws IOException SSLの例外
     * @throws FileNotFoundException SSLの例外
     * @throws CertificateException SSLの例外
     * @throws NoSuchAlgorithmException SSLの例外
     * @throws UnrecoverableKeyException SSLの例外
     * @throws KeyManagementException SSLの例外
     * @since 2014
     */
    private ConnectionFactory createSSLFactory(final String host, final Integer port, final String virtualHost,
            final String username, final String password, final Integer timeout, final String protocol,
            final String algorithm, final String keyType, final String trustStorePath, String trustStorePassphrase)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException,
            IOException, UnrecoverableKeyException, KeyManagementException {
        ConnectionFactory _factory = new ConnectionFactory();
        _factory.setHost(host);
        _factory.setPort(port);
        _factory.setUsername(username);
        _factory.setPassword(password);
        _factory.setVirtualHost(virtualHost);
        _factory.setConnectionTimeout(timeout);
        
        KeyStore keyStore = KeyStore.getInstance(keyType);
        keyStore.load(new FileInputStream(trustStorePath), trustStorePassphrase.toCharArray());
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(algorithm);
        trustManagerFactory.init(keyStore);
        
        SSLContext c = SSLContext.getInstance(protocol);
        c.init(null, trustManagerFactory.getTrustManagers(), null);
        
        _factory.useSslProtocol(c);
        
        return _factory;
    }
    
//    /**
//     * インスタンスを生成する。
//     * 
//     * @param host 共通基盤のホスト名
//     * @throws IOException 
//     */
//    public AMQPProducerImpl(final String host, final Integer port, final String username, final String password)
//            throws IOException {
//        factory = createFactory(host, port, username, password);
//        
//    }
    
//    private Config lookUpConfig() {
//        ServiceReference ref = context.getServiceReference(Config.class.getName());
//        
//        if (ref == null) {
//            // TODO カスタム・エクセプション作成する
//            throw new RuntimeException(Config.class.getName() + " not found");
//        }
//        Config config = (Config) context.getService(ref);
//        
//        return config;
//    }
    
    /**
     * コネクションを開く
     * 
     * @throws IOException 例外
     * @throws InterruptedException 例外 
     * @since 2014
     */
    public void open() throws IOException, InterruptedException {
        if (connection == null || !connection.isOpen()) {
            connection = factory.newConnection();
            channel = connection.createChannel();
        }
        if (channel == null || !channel.isOpen()) {
            //System.out.println("Channel is null");
            channel = connection.createChannel();
            channel.basicQos(prefetchCount);
            // TODO これはなに？
            //channel.addShutdownListener(listener);
        }
    }
    
    /**
     * メッセージを共通基盤に送信する
     * @param exchangeName エクスチェンジ名
     * @param routingKey ローティングキー
     * @param message メッセージのバイト配列
     * @throws IOException 通信エラー
     * @since 2014
     */
    public void publish(final String exchangeName, final String routingKey, final byte[] message) throws IOException {
//        System.out.println("channel.basicPublish start");
//        System.out.println(toString());
//        System.out.println(routingKey);
//        System.out.println(message);
        // エクスチェンジの作成
        //channel.exchangeDeclare(exchangeName, EXCHANGE_TYPE_TOPIC);
        channel.basicPublish(exchangeName, routingKey, null, message);
//        System.out.println("channel.basicPublish end");
    }
    
    /**
     * キューからメッセージを取得する
     * 
     * @param queueName キューの名称
     * @return message メッセージのバイト配列
     * @throws IOException 例外
     * @throws ShutdownSignalException 例外
     * @throws ConsumerCancelledException 例外
     * @throws InterruptedException 例外
     * @since 2014
     */
    public byte[] subscribe(final String queueName) throws IOException, ShutdownSignalException,
            ConsumerCancelledException, InterruptedException {
//        String queueName = channel.queueDeclare().getQueue();
        
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, false, consumer);
        
        byte[] message = null;
        
        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        
        //TODO メッセージの型はバイトか
        message = delivery.getBody();
        //String tmpRoutingKey = delivery.getEnvelope().getRoutingKey();
        
        final long deliveryTag = delivery.getEnvelope().getDeliveryTag();
        channel.basicAck(deliveryTag, false);
        
        return message;
    }
    
    @Override
    public String toString() {
        return "AMQPProducerImpl [connection=" + connection + ", channel=" + channel + ", factory=" + factory + "]";
    }
    
//    /**
//     * メッセージを共通基盤に送信する
//     * 
//     * @param exchangeName エクスチェンジ名称
//     * @param routingKey ローティングキー
//     * @param messgae
//     * @throws IOException
//     */
//    public void store(final String exchangeName, final String routingKey, final byte[] messgae) throws IOException {
//        // エクスチェンジの作成
//        //channel.exchangeDeclare(exchangeName, EXCHANGE_TYPE_TOPIC);
//        channel.basicPublish(exchangeName, routingKey, null, messgae);
//    }
}
