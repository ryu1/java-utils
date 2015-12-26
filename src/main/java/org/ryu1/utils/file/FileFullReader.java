package org.ryu1.utils.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * File Full Reader
 * 
 * @since 2014
 * @version 1.0
 * @author 石塚隆一
 */
public class FileFullReader implements FileReader<byte[]> {
    
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(FileFullReader.class);
    
    private InputStream inputStream;
    
    private boolean isRemaining;
    
    private static final int ONCE_READ_SIZE = 1024;
    
    
    /**
     * コンストラクタ
     * 
     * @param infile 読み込みファイル
     * @throws IOException ファイルオープン時に例外が発生
     */
    public FileFullReader(File infile) throws IOException {
        // ファイルオープン
        inputStream = new FileInputStream(infile);
        // 読み込み残りあり
        isRemaining = true;
    }
    
    @Override
    public void close() throws IOException {
        // ファイルを閉じる
        inputStream.close();
    }
    
    @Override
    public boolean hasRemaining() {
        return isRemaining;
    }
    
    @Override
    public byte[] read() throws IOException {
        // ファイルをすべて読み込む
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        // 一度にすべて読み込めない可能性を考慮し、1024バイトずつ読み込む
        byte[] buffer = new byte[ONCE_READ_SIZE];
        while (true) {
            int len = inputStream.read(buffer);
            if (len < 0) {
                break;
            }
            data.write(buffer, 0, len);
        }
        // 読み込み残りなし
        isRemaining = false;
        return data.toByteArray();
    }
    
}
