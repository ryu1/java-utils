package org.ryu1.utils.file;

import java.io.IOException;

/**
 * ファイル読み込みインターフェイス
 * 
 * @since 2014
 * @version 1.0
 * @author 石塚隆一
 * @param <T> 読み込みデータの型
 */
public interface FileReader<T> {
    
    /**
     * ファイルを閉じる
     * 
     * @throws IOException ファイル操作時に例外が発生
     */
    void close() throws IOException;
    
    /**
     * 読み込み残しチェック
     * 
     * @return ファイル読み込み残しがある場合はtrueを返す
     * @throws IOException ファイル操作時に例外が発生
     */
    boolean hasRemaining() throws IOException;
    
    /**
     * ファイル読み込み
     * 
     * @return 読み込みデータ
     * @throws IOException ファイル操作時に例外が発生
     */
    T read() throws IOException;
}
