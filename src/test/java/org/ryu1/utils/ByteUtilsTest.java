package org.ryu1.utils;

import static org.junit.Assert.*; // CHECK_IGNORE(Reason:テストソースのため)

import org.junit.Test;

/**
 * ByteUtilsクラスのテスト.
 * @author 石塚 隆一
 *
 */
public class ByteUtilsTest {

//    @Test
//    public void testBytesToHexString() {
//        
//    }
    
    /**
     * Big Endianで並び替えをする.
     */
    @Test
    public void testWhenBigEndianOrder() {
        byte[] target = {
                0x01,
                0x02,
                0x03,
                0x04,
        };
        byte[] actual = ByteUtils.order(target, ByteOrder.BIG_ENDIAN);
        byte[] expected = target;
        
        assertNotSame(target, actual);
        assertEquals(expected[0], actual[0]);
        assertEquals(expected[1], actual[1]);
        assertEquals(expected[2], actual[2]);
        assertEquals(expected[3], actual[3]);
    }
    
    /**
     * Middle Endianで並び替えをする.
     */
    @Test
    public void testWhenMiddleEndianOrder() {
        byte[] target = {
                0x01,
                0x02,
                0x03,
                0x04,
        };
        byte[] actual = ByteUtils.order(target, ByteOrder.MIDDLE_ENDIAN);
        byte[] expected = {
                0x03,
                0x04,
                0x01,
                0x02,
        };
        assertNotSame(target, actual);
        assertEquals(expected[0], actual[0]);
        assertEquals(expected[1], actual[1]);
        assertEquals(expected[2], actual[2]);
        assertEquals(expected[3], actual[3]);
    }
    
    /**
     * 32767のバイト配列をショート文字列に変換する.
     */
    @Test
    public void testWhen32767BytesToShortString() {
        // 32767
        byte[] data = {(byte) 0x7F, (byte) 0xFF}; // CHECK_IGNORE(Reason:テストソースのため)
        String actual = ByteUtils.bytesToShortString(data);
        System.out.println(actual);
        assertEquals("32767", actual);
    }
    
    /**
     * short型がオーバーフローするサイズのバイト配列をショート文字列に変換する.
     */
    @Test
    public void testWhen2147483647BytesToShortString() {
        // 214748364
        byte[] data = {(byte) 0x7F, (byte) 0xFF,  // CHECK_IGNORE(Reason:テストソースのため)
                (byte) 0xFF, (byte) 0xFF};        // CHECK_IGNORE(Reason:テストソースのため)
        Throwable throwable = null;
        try {
            @SuppressWarnings("unused")
            String actual = ByteUtils.bytesToShortString(data);
        } catch (IllegalArgumentException e) {
            throwable = e;
        }
        if (!(throwable instanceof IllegalArgumentException)) {
            fail();
        }
    }
    
    /**
     * -32768のバイト配列を指定してショート文字列に変換する.
     */
    @Test
    public void testWhenMinus32768BytesToShortString() {
        // -32768
        byte[] data = {(byte) 0x80, (byte) 0x00};  // CHECK_IGNORE(Reason:テストソースのため)
        String actual = ByteUtils.bytesToShortString(data);
        System.out.println(actual);
        assertEquals("-32768", actual);
    }

    /**
     * 2147483647のバイト配列を指定してint文字列に変換する.
     */
    @Test
    public void testWhen2147483647BytesToIntString() {
        // 2147483647
        byte[] data = {(byte) 0x7F, (byte) 0xFF,  // CHECK_IGNORE(Reason:テストソースのため)
                (byte) 0xFF, (byte) 0xFF};        // CHECK_IGNORE(Reason:テストソースのため)
        String actual = ByteUtils.bytesToIntString(data);
        System.out.println(actual);
        assertEquals("2147483647", actual);
    }
    
    /**
     * -2147483647のバイト配列を指定してint文字列に変換する.
     */
    @Test
    public void testWhenMinus2147483647BytesToIntString() {
        // -2147483647
        byte[] data = {(byte) 0x80, (byte) 0x00,  // CHECK_IGNORE(Reason:テストソースのため)
                (byte) 0x00, (byte) 0x01};
        String actual = ByteUtils.bytesToIntString(data);
        System.out.println(actual);
        assertEquals("-2147483647", actual);
    }
    
    /**
     * int型がオーバーフローするサイズのバイト配列を指定してint文字列に変換する.
     */
    @Test
    public void testWhenMinus2147483648BytesToIntString() {
        // -2147483647
        byte[] data = {(byte) 0x00, (byte) 0x00,            // CHECK_IGNORE(Reason:テストソースのため)
                (byte) 0x00, (byte) 0x00, (byte) 0x00};     // CHECK_IGNORE(Reason:テストソースのため)
        Throwable throwable = null;
        @SuppressWarnings("unused")
        String actual = null;
        try {
            actual = ByteUtils.bytesToIntString(data);
        } catch (IllegalArgumentException e) {
            throwable = e;
        }
        if (!(throwable instanceof IllegalArgumentException)) {
            fail();
        }
    }

}
