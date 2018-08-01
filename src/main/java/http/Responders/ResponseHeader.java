package http.Responders;

public enum ResponseHeader {

    CONTENTTYPE("Content-Type"),
    ALLOW("Allow"),
    CONTENTRANGE("Content-Range");

    private String label;

    ResponseHeader(String label) {
        this.label = label;
    }

    public byte[] getLabel() {
        return label.getBytes();
    }
}
