package org.ryu1.utils;

/**
 * 設定ファイルクラス
 * 
 * @since 1.0
 * @version 1.0
 * @author AIF 石塚 隆一
 */
public interface Config {
    
    /**
     * 設定ファイルからBoolean型で値を取得する
     * 
     * @param key プロパティーのキー
     * @return Boolean プロパティーの値
     * @since 2014
     */
    public Boolean getBoolean(final String key); // CHECKSTYLE IGNORE THIS LINE
    
    /**
     * 設定ファイルからInteger型で値を取得する
     * 
     * @param key プロパティーのキー
     * @return Integer プロパティーの値
     */
    public Integer getInteger(final String key); // CHECKSTYLE IGNORE THIS LINE
    
    /**
     * 設定ファイルからLong型で値を取得する
     * 
     * @param key プロパティーのキー
     * @return Integer プロパティーの値
     * @since 2014
     */
    public Long getLong(final String key); // CHECKSTYLE IGNORE THIS LINE
    
    /**
     * 設定ファイルからString型で値を取得する
     * 
     * @param key プロパティーのキー
     * @return String プロパティーの値
     */
    public String getString(final String key); // CHECKSTYLE IGNORE THIS LINE
}
