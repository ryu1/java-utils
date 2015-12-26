package org.ryu1.utils;

/**
 * 国際化クラスのインターフェース
 * 
 * @since 2014
 * @version 1.0
 * @author 石塚 隆一
 */
public interface I18n {
    
    /**
     * 文字列を取得する
     * 
     * @param key プロパティーキー
     * @return 文字列
     * @since 2014
     */
    String getString(final String key);
    
    /**
     * 文字列を取得する
     * 
     * @param key プロパティーキー
     * @param params フォーマットのパラメタ
     * @return 文字列
     * @since 2014
     */
    String getString(final String key, Object... params);
}
