package org.ryu1.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

/**
 * 設定クラス
 * 
 * @since 1.0
 * @version 1.0
 * @author 石塚 隆一
 */
public class ConfigImpl implements Config {
    
    //private static final String FILE_NAME = "application.properties";
    
    private static Properties properties;
    
    static {
        if (properties == null) {
            InputStream input = null;
            
            try {
                //input = ConfigImpl.class.getClassLoader().getResourceAsStream(FILE_NAME);
                String propetyFilePath = System.getProperty("application.configurationFile");
                input = new FileInputStream(new File(propetyFilePath));
                properties = new Properties();
                properties.load(input);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    
    
    @Override
    public Boolean getBoolean(final String key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        return Boolean.valueOf(properties.getProperty(key));
        
    }
    
    @Override
    public Integer getInteger(final String key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        return Integer.parseInt(properties.getProperty(key));
    }
    
    @Override
    public Long getLong(final String key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        return Long.parseLong(properties.getProperty(key));
        
    }
    
    @Override
    public String getString(final String key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        return properties.getProperty(key);
        
    }
    
//    @Validate
//    public void start() {
//        System.out.println("start config");
//        System.out.println(getString("redis.host"));
//    }
//
//    @Invalidate
//    public void stop() {
//        System.out.println("stop config");
//    }
}
