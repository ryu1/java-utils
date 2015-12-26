package org.ryu1.utils.dao;

import static org.osgi.service.log.LogService.LOG_DEBUG;

import java.io.UnsupportedEncodingException;

import jp.co.nec.appf.machine.gateway.core.infrastructure.Config;
import jp.co.nec.appf.machine.gateway.core.infrastructure.dao.MessageDao;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.osgi.service.log.LogService;

import redis.clients.jedis.Jedis;

/**
 * MessageDaoの実装クラス
 * 
 * @since 2014
 * @version 1.0
 * @author AIF 石塚 隆一
 */
public class MessageDaoImpl implements MessageDao {
    
//    private static final MessageDaoImpl _instance = new MessageDaoImpl();
    
    private Jedis jedis;
    
    private String host;
    
    @Requires
    private Config config;
    
    @Requires
    private LogService logger;
    
    private static final String CONFIG_KEY_REDIS_HOST = "redis.host";
    
    
//    private BundleContext context;
    
//    /**
//     * インスタンスの生成
//     * @param host 
//     * 
//     * @return
//     */
//    public static MessageDaoImpl getInstance(final String host) {
//        jedis = new Jedis(host);
//        return _instance;
//    }
    
//    private BundleContext context;
    
//    /**
//     * インスタンスを生成する。
//     * @param context 
//     * 
//     */
//    public MessageDaoImpl(final BundleContext context) {
//        this.context = context;
//    }
    /**
     * インスタンスを生成する。
     * 
     */
    public MessageDaoImpl() {
//        context = bc;
//        logger.log(logger.LOG_DEBUG, "new MessageDaoImpl");
        host = config.getString(CONFIG_KEY_REDIS_HOST);
        jedis = new Jedis(host);
//        logger.log(LOG_DEBUG, toString());
    }
    
    /**
     * インスタンスを生成する。
     * 
     * @param host ホスト
     */
    public MessageDaoImpl(final String host) {
        this.host = host;
        jedis = new Jedis(host);
    }
    
    @Override
    public Boolean del(final String key) {
        jedis.del(key);
        return jedis.exists(key);
    }
    
    @Override
    public Boolean del(final String key, int count, final String value) {
        jedis.lrem(key, count, value);
        return jedis.exists(key);
    }
    
    @Override
    public Boolean del(final String key, final String value) {
        logger.log(LOG_DEBUG, "Redis = del(final String key, final String value)");
        logger.log(LOG_DEBUG, "[ param::key = " + key + ", value = " + value + " ]");
        jedis.lrem(key, 1, value);
        return jedis.exists(key);
    }
    
    @Override
    public byte[] get(final String key) {
        return jedis.get(key).getBytes();
    }
    
    @Override
    public String lindex(final String key, final long index) {
        return jedis.lindex(key, index);
    }
    
    @Override
    public Long llen(String key) {
        return jedis.llen(key);
    }
    
    @Override
    public Long lpush(String key, String... strings) {
        System.out.println("Redis = lpush(String key, String... strings)");
        System.out.println("[ param::key = " + key + ", strings = " + strings + " ]");
        return jedis.lpush(key, strings);
    }
    
    @Override
    public Long lrem(final String key, final long count, final String value) {
        System.out.println("Redis = lrem(final String key, final long count, final String value)");
        System.out.println("[ param::key = " + key + ", count = " + count + "value = " + value + " ]");
        return jedis.lrem(key, count, value);
    }
    
    @Override
    public Long rpush(String key, String... strings) {
        System.out.println("Redis = rpush(String key, String... strings)");
        System.out.println("[ param::key = " + key + ", strings = " + strings + " ]");
        return jedis.rpush(key, strings);
    }
    
    @Override
    public String set(final String key, final byte[] bytes) throws UnsupportedEncodingException {
        String str = null;
        try {
            str = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw e;
        }
        return jedis.set(key, str);
    }
    
    @Override
    public String set(final String key, final String value) {
        return jedis.set(key, value);
    }
    
//    @Validate
//    public void start() {
//        System.out.println("start messageDAOImpl");
//        for (Bundle bundle : context.getBundles()) {
//            System.out.println(bundle.getBundleId());
//        }
//        ServiceReference ref = context.getServiceReference(LogService.class.getName());
//        System.out.println(ref);
//        LogService logger2 = (LogService) context.getService(ref);
//        System.out.println(logger2);
//        logger2.log(logger2.LOG_INFO, "@@@@@@@@@@");
//        logger.log(logger.LOG_INFO, "##########");
//    }
    
//    @Invalidate
//    public void stop() {
//        System.out.println("stop messageDAOImpl");
//    }
    
    @Override
    public String toString() {
        return "MessageDaoImpl [host=" + host + "]";
    }
}
