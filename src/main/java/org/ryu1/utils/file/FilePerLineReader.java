package org.ryu1.utils.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.ryu1.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * File Per Line Reader
 * 
 * @since 2014
 * @version 1.0
 * @author 石塚隆一
 */
public class FilePerLineReader implements FileReader<byte[]> {
    
    private byte[] delimiter;
    
    private RandomAccessFile file;
    
    private FileChannel inChannel;
    
    private long position;
    
//    private long fileSize;
    
    private static final String CR = "\\r";
    
    private static final String LF = "\\n";
    
    private static final String CRLF = CR + LF;
    
    private static final byte[] CR_BIN = {
        0x0d
    };
    
    private static final byte[] LF_BIN = {
        0x0a
    };
    
    private static final byte[] CRLF_BIN = {
        0x0d,
        0x0a
    };
    
//    private long maxIndex;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FilePerLineReader.class);
    
    private static final String MODE_READONLY = "r";
    
    private int readLines;
    
    private boolean isRemaining;
    
    
    /**
     * インスタンスを生成する。
     * 
     * @param infile 入力ファイル
     * @param delimiter 区切り文字
     * @param number 一度に読み込むレコード数
     * @throws IOException ファイルの読み込み時に例外が発生
     */
    public FilePerLineReader(final File infile, final String delimiter, final int number) throws IOException {
        
        //パラメータの整合性チェック
        if (StringUtils.equals(delimiter, CR)) {
            this.delimiter = CR_BIN;
        } else if (StringUtils.equals(delimiter, LF)) {
            this.delimiter = LF_BIN;
        } else if (StringUtils.equals(delimiter, CRLF)) {
            this.delimiter = CRLF_BIN;
        } else {
            throw new IllegalArgumentException("Delimiter is illegal value.. : " + delimiter);
        }
        
        if (number < 1) {
            throw new IllegalArgumentException("Number is illegal value.. :" + number);
        }
        
        file = new RandomAccessFile(infile, MODE_READONLY);
        
        readLines = number;
        inChannel = file.getChannel();
        position = 0;
//        fileSize = inChannel.size();
//        maxIndex = calcNumOfDivision();
        
        isRemaining = true;
    }
    
//    /**
//     * 分割数を取得する.
//     * TODO for ryu
//     *
//     * @return
//     * @since TODO
//     */
//    private long calcNumOfDivision() {
//        return (fileSize / length) + 1;
//    }
    
    @Override
    public void close() throws IOException {
        inChannel.close();
        file.close();
    }
    
    @Override
    public boolean hasRemaining() {
        // EOFまで達したら終了
        return isRemaining;
    }
    
    @Override
    public byte[] read() throws IOException {
        LOGGER.debug("Start:this.toString()=" + toString());
        
        // 指定行数分ファイル読み込み
        List<Byte> lineData = new ArrayList<Byte>();
        int lineCount = 0;
        while (lineCount < readLines && isRemaining) {
            while (true) {
                // 改行コードまたはEOFまで読み込む
                int oneData = file.read();
                if (oneData == -1) {
                    //EOF ： 読み込み終了
                    isRemaining = false;
                    break;
                } else if ((byte) oneData == LF_BIN[0]) {
                    //LF : 改行コードに区切り文字を設定
                    for (byte element : delimiter) {
                        lineData.add(element);
                    }
                    break;
                } else if ((byte) oneData == CR_BIN[0]) {
                    //CRまたはCRLF : 改行コードに区切り文字を設定
                    for (byte element : delimiter) {
                        lineData.add(element);
                    }
                    //CRLFのチェック
                    long cur = file.getFilePointer();
                    if ((file.read()) != CRLF_BIN[1]) {
                        file.seek(cur);
                    }
                    break;
                } else {
                    // 通常データ
                    lineData.add((byte) oneData);
                }
            }
            
            lineCount++;
        }
        
        // 読み込んだデータをバイト配列に変換
        byte[] data = new byte[lineData.size()];
        for (int i = 0; i < lineData.size(); i++) {
            data[i] = lineData.get(i);
        }
        
        long lastPosition = position;
        position = file.getFilePointer();
        LOGGER.debug("read length=" + (position - lastPosition));
        LOGGER.debug("read lines=" + lineCount);
        
        return data;
    }
    
//    private long remaining() {
//        return fileSize - position;
//    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
}
