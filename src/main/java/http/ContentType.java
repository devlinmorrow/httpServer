package http;

public enum ContentType {

    TXT("text/plain"),
    GIF("image/gif"),
    JPEG("image/jpeg"),
    PNG("image/png");

    private String contentTypeString;

    ContentType(String contentTypeString) {
        this.contentTypeString = contentTypeString;
    }

    public byte[] getBytesValue() {
        return contentTypeString.getBytes();
    }

    public String getValueAsS() {
        return contentTypeString;
    }
}
