package org.ryu1.utils;

public class CharsetConverter {

    public static String encUTF8toMS932(String before) {
        StringBuffer ret = new StringBuffer();
        char c = 0x0000;
        for (int i = 0; i < before.length(); i++) {
            c = before.charAt(i);
            switch (c) {
            case 0x301c: // WAVE DASH
                c = 0xff5e;
                break;
            case 0x2016: // DOUBLE VERTICAL LINE
                c = 0x2225;
                break;
            case 0x2212: // MINUS SIGN
                c = 0xFF0D;
                break;
            case 0x00A2: // CENT SIGN
                c = 0xFFE0;
                break;
            case 0x00A3: // POUND SIGN
                c = 0xFFE1;
                break;
            case 0x00AC: // NOT SIGN
                c = 0xFFE2;
                break;
            }
            ret.append(c);
        }
        return ret.toString();
    }

    public static String encMS932toUTF8(String before) {
        StringBuffer ret = new StringBuffer();
        char c = 0x0000;
        for (int i = 0; i < before.length(); i++) {
            c = before.charAt(i);
            switch (c) {
            case 0xff5e: // WAVE DASH
                c = 0x301c;
                break;
            case 0x2225: // DOUBLE VERTICAL LINE
                c = 0x2016;
                break;
            case 0xFF0D: // MINUS SIGN
                c = 0x2212;
                break;
            case 0xFFE0: // CENT SIGN
                c = 0x00A2;
                break;
            case 0xFFE1: // POUND SIGN
                c = 0x00A3;
                break;
            case 0xFFE2: // NOT SIGN
                c = 0x00AC;
                break;
            }
            ret.append(c);
        }
        return ret.toString();
    }
}
