package org.ryu1.utils.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractFileWriter {

    public static String UTF8 = "UTF-8";

    public static String SJIS = "Shift_JIS";

    public static String EUCJP = "EUC-JP";

    public static String CRLF = "\r\n";

    public static String LF = "\n";

    public static String CR = "\r";

    public static String PIPE = "|";

    public static String TAB = "\t";

    public static String COMMA = ",";

    private Logger log = LoggerFactory.getLogger(AbstractFileReader.class);

    private File file;

    private BufferedWriter writer;

    protected String delimiter = COMMA;

    protected String eol = CRLF;

    private String filepath;

    private String filename;

    private String charSet = SJIS;

    /** separator */
    public static final String LINESEPARATOR_LF = "\n";

    public AbstractFileWriter(String filepath, String filename) throws IOException {
        this.filepath = filepath;
        this.filename = filename;
        open();
    }

    public AbstractFileWriter(String filepath, String filename, String charSet, String eol, String delimiter) throws IOException {
        this.filepath = filepath;
        this.filename = filename;
        this.charSet = charSet;
        this.eol = eol;
        this.delimiter = delimiter;
        open();
    }

    private void open() throws IOException {
        file = new File(filepath, filename);

        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
            log.debug("directrory success making");
        }

        writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(file),charSet));
    }


    public void setEol(String eol) {
        this.eol = eol;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public void appendLine(String[] line) throws IOException {
        int i = 0;
        for (String col : line) {
            i++;
            StringBuilder builder = new StringBuilder();
            builder.append(col);
            if (line.length != i) {
                builder.append(delimiter);
            } else {
                builder.append(eol);
            }
            writer.write(builder.toString());

            writer.flush();
        }
    }

    public void appendLine(LineBean line) throws IOException {
        writer.write(build(line));
        writer.flush();
    }

    protected abstract String build(LineBean line);

    public interface LineBean {
        public String toString();
    }

    public void close() throws IOException {
        writer.close();
    }
}
