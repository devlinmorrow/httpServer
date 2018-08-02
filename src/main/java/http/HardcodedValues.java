package http;

public enum HardcodedValues {

    AUTHENTICATEMESSAGE("Basic realm=\"Access the logs file.\""),
    AUTHORIZATIONHEADER("Authorization"),
    CONTENTTYPEHEADER("Content-Type: "),
    HTTPVERSION("HTTP/1.1"),
    RESOURCEPATH("/Users/devlin/cob_spec/public");

    private String s;

    HardcodedValues(String s) {
        this.s = s;
    }

    public String getS() {
        return s;
    }

    public byte[] getBytes() {
        return s.getBytes();
    }

}
