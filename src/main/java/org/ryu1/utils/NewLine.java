//  NewLine EOL = NewLine.get(prop.getProperty("EOL"));
public enum EOL {
    CR("\r"),
    LF("\n"),
    CRLF("\r\n"),
    DEFAULT(System.lineSeparator());

    private final String value;

    private NewLine(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    static NewLine get(String name) {
        try {
            return valueOf(name);
        }
        catch (IllegalArgumentException | NullPointerException ignore) {}
        return DEFAULT;
    }
}
