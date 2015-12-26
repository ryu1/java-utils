package org.ryu1.utils.dao;

import java.io.UnsupportedEncodingException;

/**
 * MessageDaoクラス
 * 
 * @since 2013
 * @version 1.0
 * @author AIF 石塚 隆一
 */
public interface MessageDao {
    
    /**
     * 指定したキーを削除
     * 
     * @param key キー
     * @return Boolean 成否
     * @since 2014
     */
    Boolean del(final String key);
    
    /**
     * 指定したキーを削除
     * 
     * @param key キー
     * @param count 数
     * @param value 値
     * @return Boolean 成否
     * @since 2014
     */
    Boolean del(final String key, int count, final String value);
    
    /**
     * 指定したキーを削除
     * 
     * @param key キー
     * @param value 値
     * @return Boolean 成否
     * @since 2014
     */
    Boolean del(final String key, final String value);
    
    /**
     * 指定したキーの値を取得
     * 
     * @param key キー
     * @return 値のバイト配列
     * @since 2014
     */
    byte[] get(final String key);
    
    /**
     * リストの内容をインデックスを指定して取得
     * 
     * @param key キー
     * @param index インデックス
     * @return String 値
     * @since 2014
     */
    String lindex(final String key, final long index);
    
    /**
     * リスト型の要素数の取得
     * 
     * @param key キー
     * @return Long 要素数
     * @since 2014
     */
    Long llen(final String key);
    
    /**
     * リストの先頭に値を追加
     * 
     * @param key キー
     * @param strings 値の配列
     * @return TODO 何を返す？
     * @since 2014
     */
    Long lpush(final String key, final String... strings);
    
    /**
     * リストの要素を個数と値を指定して削除
     * 
     * @param key キー
     * @param count 要素数
     * @param value 値
     * @return TODO 何を返す？
     * @since 2014
     */
    Long lrem(final String key, final long count, final String value);
    
    /**
     * リストの最後に値を追加
     * 
     * @param key キー
     * @param strings 値の配列
     * @return TODO 何を返す？
     * @since 2014
     */
    Long rpush(final String key, final String... strings);
    
    /**
     * 指定したキーの値の設定 or 更新
     * 
     * @param key キー
     * @param bytes 値のバイト配列
     * @return TODO 何を返す？
     * @throws UnsupportedEncodingException 例外
     * @since 2014
     */
    String set(final String key, final byte[] bytes) throws UnsupportedEncodingException;
    
    /**
     * 指定したキーの値の設定 or 更新
     * 
     * @param key キー
     * @param value 値
     * @return TODO 何を返す？
     * @since 2014
     */
    String set(final String key, final String value);
    
}
