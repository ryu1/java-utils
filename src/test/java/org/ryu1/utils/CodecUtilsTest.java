package org.ryu1.utils;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Test;

/**
 * 
 */
public class CodecUtilsTest {

    @Test
    public void test() throws IOException {
        String st = "テスト用文字列";
        String encString = CodecUtils.getBase64String(st.getBytes());
        System.out.println("encString ->" + encString);
        byte[] myByte = CodecUtils.getDecodeBase64(encString);
        String returnSt = new String(myByte);
        System.out.println("returnSt ->" + returnSt);
        assertEquals(st, returnSt);
    }

}
