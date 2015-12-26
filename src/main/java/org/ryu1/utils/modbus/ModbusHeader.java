package org.ryu1.utils.modbus;

/**
 * Modbusヘッダー
 *
 * @since 2014
 * @version 1.0
 * @author AIF 石塚 隆一
 */
public class ModbusHeader {
    
    /** プロトコル識別子 0 */
    public static final int PROTOCOL_IDENTIFIER = 0;
    
    private final int transactionIdentifier;
    
    private final int protocolIdentifier;
    
    private final int length;
    
    private final short unitIdentifier;
    
    
    /**
     * インスタンスを生成する。
     *
     * @param transactionIdentifier トランザクション識別子
     * @param protocolIdentifier プロトコル識別子
     * @param length フィールド長
     * @param unitIdentifier ユニット識別子
     */
    public ModbusHeader(int transactionIdentifier, int protocolIdentifier, int length, short unitIdentifier) {
        this.transactionIdentifier = transactionIdentifier;
        this.protocolIdentifier = protocolIdentifier;
        this.length = length;
        this.unitIdentifier = unitIdentifier;
    }
    
    /**
     * フィールド長を取得する
     *
     * @return フィールド長
     * @since 2014
     */
    public int getLength() {
        return length;
    }
    
    /**
     * プロトコル識別子を取得する
     *
     * @return プロトコル識別子
     * @since 2014
     */
    public int getProtocolIdentifier() {
        return protocolIdentifier;
    }
    
    /**
     * トランザクション識別子を取得する
     *
     * @return トランザクション識別子
     * @since 2014
     */
    public int getTransactionIdentifier() {
        return transactionIdentifier;
    }
    
    /**
     * ユニット識別子を取得する
     *
     * @return ユニット識別子
     * @since 2014
     */
    public short getUnitIdentifier() {
        return unitIdentifier;
    }
    
    @Override
    public String toString() {
        return "ModbusHeader{" + "transactionIdentifier=" + transactionIdentifier + ", protocolIdentifier="
                + protocolIdentifier + ", length=" + length + ", unitIdentifier=" + unitIdentifier + '}';
    }
}
