package org.ryu1.utils.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * File Per Size Reader
 * 
 * @since 2014
 * @version 1.0
 * @author 石塚隆一
 */
public class FilePerSizeReader implements FileReader<byte[]> {
    
    private RandomAccessFile file;
    
    private static final String MODE_READONLY = "r";
    
    private FileChannel inChannel;
    
    private long position;
    
    private int length;
    
    private long fileSize;
    
    private long index;
    
    private long maxIndex;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FilePerSizeReader.class);
    
    
    /**
     * インスタンスを生成する。
     * 
     * @param infile 読み込みファイル
     * @param length 分割サイズ
     * @throws IOException ファイルオープン時に例外が発生
     */
    public FilePerSizeReader(final File infile, final int length) throws IOException {
        //パラメータの整合性チェック
        if (length < 1) {
            throw new IllegalArgumentException("Length is illegal value.. :" + length);
        }
        
        file = new RandomAccessFile(infile, MODE_READONLY);
        
        inChannel = file.getChannel();
        fileSize = inChannel.size();
        position = 0;
        this.length = length;
        maxIndex = calcNumOfDivision();
        index = 0;
    }
    
    /**
     * 分割数を取得する.
     * 
     * @return 分割数
     */
    private long calcNumOfDivision() {
        if (fileSize % length == 0) {
            return fileSize / length;
        } else {
            return (fileSize / length) + 1;
        }
    }
    
    @Override
    public void close() throws IOException {
//        lock.release();
        inChannel.close();
        file.close();
    }
    
    /**
     * TODO for ryu
     * 
     * @param buffer buffer
     * @since TODO
     */
    public void debug(ByteBuffer buffer) {
        if (LOGGER.isDebugEnabled()) {
//            LOGGER.debug("buffer.capacity()=" + buffer.capacity());
//            LOGGER.debug("buffer.position()" + buffer.position());
//            LOGGER.debug("buffer.limit()" + buffer.limit());
            
//            LOGGER.debug("buffer.remaining()=" + buffer.remaining());
//
//            LOGGER.debug("buffer.hasArray()=" + buffer.hasArray());
//            LOGGER.debug("buffer.hasRemaining()=" + buffer.hasRemaining());
//
//            LOGGER.debug("buffer.isDirect()=" + buffer.isDirect());
//            LOGGER.debug("buffer.isReadOnly()=" + buffer.isReadOnly());
//
//            LOGGER.debug("buffer.toString()=" + buffer.toString());
        }
        
    }
    
    @Override
    public boolean hasRemaining() {
        // 分割数に達したらfalse
        if (maxIndex <= index) {
            return false;
        }
        return true;
    }
    
    @Override
    public byte[] read() throws IOException {
        LOGGER.debug("Start:this.toString()=" + toString());
        MappedByteBuffer buffer = null;
        
        int tempLength = 0;
        if (length < remaining()) {
            tempLength = length;
        } else {
            tempLength = (int) remaining();
        }
        
        byte[] obtainedData = new byte[tempLength];
        
        buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, position, tempLength);
        
        LOGGER.debug("length=" + length);
        LOGGER.debug("buffer.limit()=" + buffer.limit());
        // 0番目の要素から順にbuffのデータを取得
        for (int i = 0; i < buffer.limit(); i++) {
            obtainedData[i] = buffer.get();
        }
        position += buffer.limit();
        LOGGER.debug("End:this.toString()=" + toString());
        
        // 読み込み済み回数をインクリメント
        index++;
        
        return obtainedData;
    }
    
    private long remaining() {
        return fileSize - position;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
}
