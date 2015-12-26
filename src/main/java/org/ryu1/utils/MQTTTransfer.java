package org.ryu1.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

/**
 * MQTT通信クラス
 * 
 * @since 2014
 * @version 1.0
 * @author 石塚 隆一
 */
public class MQTTTransfer {
    
    private MqttAsyncClient client;
    
    private String clientId;
    
    private final String scheme_tcp = "tcp://";
    
    private final String scheme_ssl = "ssl://";
    
    private String brokerUrl;
    
    private MqttConnectOptions conOpt;
    
    private String password;
    
    private String userName;
    
    private MqttCallback callback;
    
    private boolean isUseSSL;
    
    private String keyType;
    
    private String trustStorePath;
    
    private String algorithm;
    
    private String protocol;
    
    private String trustStorePassphrase;
    
    
//    /**
//     * インスタンスを生成する。
//     * 
//     * @param host ホスト
//     * @param port ポート
//     * @param userName ユーザー名
//     * @param password パスワード
//     * @param clientId クライアントID
//     * @throws MqttException 例外
//     */
//    public MQTTTransfer(String host, Integer port, String userName, String password, final String clientId)
//            throws MqttException {
//        this.userName = userName;
//        this.password = password;
//        this.clientId = clientId;
//        
//        brokerUrl = protocol + host + ":" + port;
//        
//    }
    
    /**
     * インスタンスを生成する。
     * 
     * @param host ホスト
     * @param port ポート
     * @param userName ユーザー名
     * @param password パスワード
     * @param clientId クライアントID
     * @param callback サブスクライブのハンドラ
     */
    public MQTTTransfer(String host, Integer port, String userName, String password, final String clientId,
            final MqttCallback callback) {
        this.userName = userName;
        this.password = password;
        this.clientId = clientId;
        this.callback = callback;
        isUseSSL = false;
        brokerUrl = scheme_tcp + host + ":" + port;
        
    }
    
    /**
     * インスタンスを生成する。
     * 
     * @param host ホスト
     * @param port ポート
     * @param userName ユーザー名
     * @param password パスワード
     * @param clientId クライアントID
     * @param callback サブスクライブのハンドラ
     * @param protocol TSL
     * @param algorithm SunX509
     * @param keyType JKS
     * @param trustStorePath KeyStoreのファイルパス
     * @param trustStorePassphrase KeyStoreのパスワード 
     */
    public MQTTTransfer(final String host, final Integer port, final String userName, final String password,
            final String clientId, final MqttCallback callback, final String keyType, final String trustStorePath,
            final String trustStorePassphrase, final String algorithm, final String protocol) {
        this.userName = userName;
        this.password = password;
        this.clientId = clientId;
        this.callback = callback;
        this.keyType = keyType;
        this.trustStorePath = trustStorePath;
        this.algorithm = algorithm;
        this.protocol = protocol;
        this.trustStorePassphrase = trustStorePassphrase;
        
        isUseSSL = true;
        
        brokerUrl = scheme_ssl + host + ":" + port;
        
    }
    
    /**
     * コネクションを閉じる
     * 
     * @throws IOException 例外
     * @throws MqttException 例外
     * @since 2014
     */
    public void close() throws IOException, MqttException {
        try {
            // Disconnect the client
            IMqttToken token = client.disconnect();
            token.waitForCompletion();
        } catch (MqttException e) {
            throw e;
        }
    }
    
//    public void init() {
//        final String tmpDir = System.getProperty("java.io.tmpdir");
//        MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);
//        
//        try {
//            conOpt = new MqttConnectOptions();
//            conOpt.setCleanSession(false);
//            if (password != null) {
//                conOpt.setPassword(password.toCharArray());
//            }
//            if (userName != null) {
//                conOpt.setUserName(userName);
//            }
//            
//            // Construct an MQTT blocking mode client
//            client = new MqttClient(brokerUrl, clientId, dataStore);
//            
//            // Set this wrapper as the callback handler
//            client.setCallback(this);
//            
//        } catch (MqttException e) {
//            e.printStackTrace();
//            System.exit(1);
//        }
//    }
    
//    private Config lookUpConfig() {
//        
//        Config config = null;
//        if (context == null || context.equals("")) {
//            return config;
//        }
//        
//        final ServiceReference ref = context.getServiceReference(Config.class.getName());
//        
//        if (ref == null) {
//            // TODO カスタム・エクセプション作成する
//            throw new RuntimeException(Config.class.getName() + " not found");
//        }
//        config = (Config) context.getService(ref);
//        
//        return config;
//    }
    
    /**
     * SSL向けのソケットファクトリを作成する
     * 
     * @param protocol TSL
     * @param algorithm SunX509
     * @param keyType JKS
     * @param trustStorePath KeyStoreのファイルパス
     * @param trustStorePassphrase KeyStoreのパスワード 
     * 
     * @return socketFactory
     * @throws KeyStoreException SSLSocket作成時の例外
     * @throws IOException SSLSocket作成時の例外
     * @throws FileNotFoundException SSLSocket作成時の例外
     * @throws CertificateException SSLSocket作成時の例外
     * @throws NoSuchAlgorithmException SSLSocket作成時の例外
     * @throws KeyManagementException SSLSocket作成時の例外
     * @since 2014
     */
    private SocketFactory createSSLSocketFactory(final String keyType, final String trustStorePath,
            final String trustStorePassphrase, final String algorithm, final String protocol) throws KeyStoreException,
            NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException {
        KeyStore keyStore = KeyStore.getInstance(keyType);
        keyStore.load(new FileInputStream(trustStorePath), trustStorePassphrase.toCharArray());
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(algorithm);
        trustManagerFactory.init(keyStore);
        
        SSLContext c = SSLContext.getInstance(protocol);
        c.init(null, trustManagerFactory.getTrustManagers(), null);
        return c.getSocketFactory();
    }
    
    /**
     * コネクションを閉じる
     * 
     * @throws MqttException 例外
     * @throws KeyStoreException SSLSocket作成時の例外
     * @throws IOException SSLSocket作成時の例外
     * @throws CertificateException SSLSocket作成時の例外
     * @throws NoSuchAlgorithmException SSLSocket作成時の例外
     * @throws KeyManagementException SSLSocket作成時の例外
     * @since 2014
     */
    public void open() throws IOException, MqttException, KeyManagementException, KeyStoreException,
            NoSuchAlgorithmException, CertificateException {
        final String tmpDir = System.getProperty("java.io.tmpdir");
        MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);
        
        conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(false);
        conOpt.setPassword(password.toCharArray());
        conOpt.setUserName(userName);
        
        if (isUseSSL) {
            SocketFactory socketFactory =
                    createSSLSocketFactory(keyType, trustStorePath, trustStorePassphrase, algorithm, protocol);
            conOpt.setSocketFactory(socketFactory);
        }
        
        client = new MqttAsyncClient(brokerUrl, clientId, dataStore);
        // Set this wrapper as the callback handler
        client.setCallback(callback);
        
        try {
            // Connect to the MQTT server
            IMqttToken token = client.connect(conOpt);
            token.waitForCompletion();
        } catch (MqttSecurityException e) {
            throw e;
        } catch (MqttException e) {
            throw e;
        }
    }
    
    /**
     * メッセージを送信する
     * 
     * @param topicName トピック名称
     * @param qos QOS
     * @param payload メッセージのバイト配列
     * @throws IOException 例外
     * @throws MqttException 例外
     * @since 2014
     */
    public void publish(final String topicName, final int qos, final byte[] payload) throws IOException, MqttException {
        
        try {
            // Create and configure a message
            final MqttMessage message = new MqttMessage(payload);
            message.setQos(qos);
            message.setRetained(true);
            IMqttDeliveryToken token = client.publish(topicName, message);
            token.waitForCompletion();
            
        } catch (MqttSecurityException e) {
            throw e;
        } catch (MqttException e) {
            throw e;
        }
        
    }
    
    /**
     * メッセージを購読する
     * 
     * @param topicName トピック名称
     * @param qos QOS
     * @throws IOException 例外
     * @throws MqttException 例外
     * @since 2014
     */
    public void subscribe(final String topicName, final int qos) throws IOException, MqttException {
        //System.out.println(toString());
        try {
            IMqttToken token = client.subscribe(topicName, qos);
            token.waitForCompletion();
        } catch (MqttSecurityException e1) {
            throw e1;
        } catch (MqttException e1) {
            throw e1;
        }
    }
    
    @Override
    public String toString() {
        return "MQTTTransfer [client=" + client + ", clientId=" + clientId + ", protocol=" + protocol + ", brokerUrl="
                + brokerUrl + ", conOpt=" + conOpt + ", password=" + password + ", userName=" + userName
                + ", callback=" + callback + "]";
    }
    
}
