package org.ryu1.utils.binary;

import org.ryu1.utils.binary.ByteOrder;

/**
 * バイナリ解析の設定.
 * @author 石塚 隆一
 *
 */
public interface BinaryConfig {
    
//    /** 開始位置. */
//    int offset = 0;
//    /** サイズ. */
//    int size = 0;
//    /** 型. */
//    DataType dataType = null;
//    /** 並び順. */
//    ByteOrder byteOrder = null;
    /**
     * offsetを取得する.
     * @return offset
     */
    int getOffset();
    
    /**
     * sizeを取得する.
     * @return size
     */
    int getSize();
    
    /**
     * limitを取得する.
     * @return limit
     */
    int getLimit();
    
    /**
     * dataTypeを取得する.
     * @return dataType
     */
    DataType getDataType();
    
    /**
     * byteOrderを取得する.
     * @return byteOrder
     */
    ByteOrder getByteOrder();
}
