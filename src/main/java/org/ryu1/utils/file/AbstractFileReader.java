package org.ryu1.utils.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractFileReader {
    
    public static String UTF8 = "UTF-8";

    public static String SJIS = "Shift_JIS";

    public static String EUCJP = "EUC-JP";

//    public static String CRLF = "\r\n";
//    
//    public static String LF = "\n";
//    
//    public static String CR = "\r";
    
    public static String PIPE = "\\|";
    
    public static String TAB = "\t";

    public static String COMMA = ",";

    private Logger log = LoggerFactory.getLogger(AbstractFileReader.class);

    private File file;

    private BufferedReader reader;

    protected String delimiter = COMMA;

//    private String eol = CRLF;

    private String filepath;

    private String filename;

    private String charSet = SJIS;

    /** separator */
//    public static final String LINESEPARATOR_LF = "\n";
    
    public AbstractFileReader(String filepath, String filename) throws IOException {
        this.filepath = filepath;
        this.filename = filename;
        open();
    }

    public AbstractFileReader(String filepath, String filename, String charSet) throws IOException {
        this.filepath = filepath;
        this.filename = filename;
        this.charSet = charSet;
//        this.eol = eol;
//        this.delimiter = delimiter;
        open();
    }

    public AbstractFileReader(String filepath, String filename, String charSet, String delimiter) throws IOException {
        this.filepath = filepath;
        this.filename = filename;
        this.charSet = charSet;
//        this.eol = eol;
        this.delimiter = delimiter;
        open();
    }

    private void open() throws FileNotFoundException, UnsupportedEncodingException {

        file = new File(filepath, filename);
        if (!file.exists()) {
            log.info("�t�@�C�������݂��܂���B�F" + file.getName());
            throw new FileNotFoundException();
        }

        reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charSet));
    }

    public List<LineBean> readAll() throws IOException {
        String line;
        List<LineBean> list = new ArrayList<LineBean>();

        while ((line = reader.readLine()) != null) {
           LineBean lineBean = parse(line);
           if (lineBean != null) {
               list.add(lineBean);
           }
        }

        if (list == null || list.size() < 1) {
            return null;
        }
        return list;
    }

    protected abstract LineBean parse(String line);

    public interface LineBean {
        public String toString();
    }

    public void close() throws IOException {
        reader.close();
    }

}
