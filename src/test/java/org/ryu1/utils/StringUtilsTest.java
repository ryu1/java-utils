package org.ryu1.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class StringUtilsTest {

    @Test
    public void testCollectionToDelimitedString() {
        List<String> target = new ArrayList<String>();
        target.add("aaa");
        target.add("bbb");
        target.add("ccc");
        String actual = StringUtils.collectionToDelimitedString(target, ",");
        assertEquals("\"aaa\",\"bbb\",\"ccc\"", actual);
    }
    
    @Test
    public void testWhenContainsNullCollectionToDelimitedString() {
        List<String> target = new ArrayList<String>();
        target.add("aaa");
        target.add("bbb");
        target.add(null);
        String actual = StringUtils.collectionToDelimitedString(target, ",");
        assertEquals("\"aaa\",\"bbb\",\"\"", actual);
    }
    
    @Test
    public void testWhenFormatIsNullCollectionToDelimitedString() {
        List<String> target = new ArrayList<String>();
        target.add("aaa");
        target.add("bbb");
        target.add("ccc");
        String actual = StringUtils.collectionToDelimitedString(target, ",", null);
        assertEquals("aaa,bbb,ccc", actual);
    }

    @Test
    public void testArrayToDelimitedString() {
        String[] target = {
                "aaa",
                "bbb",
                "ccc",
        };
        String actual = StringUtils.arrayToDelimitedString(target, ",");
        assertEquals("\"aaa\",\"bbb\",\"ccc\"", actual);
    }
    
    @Test
    public void testWhenContainsNullArrayToDelimitedString() {
        String[] target = {
                "aaa",
                null,
                "ccc",
        };
        String actual = StringUtils.arrayToDelimitedString(target, ",");
        assertEquals("\"aaa\",\"\",\"ccc\"", actual);
    }
    
    @Test
    public void testWhenFormatIsNullArrayToDelimitedString() {
        String[] target = {
                "aaa",
                "bbb",
                "ccc",
        };
        String actual = StringUtils.arrayToDelimitedString(target, ",", null);
        assertEquals("aaa,bbb,ccc", actual);
    }
}
