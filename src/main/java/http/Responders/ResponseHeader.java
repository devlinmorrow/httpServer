package http.Responders;

public enum ResponseHeader {

    CONTENTTYPE("Content-Type"),
    ALLOW("Allow");

    private String label;

    ResponseHeader(String label) {
        this.label = label;
    }

    public byte[] getLabel() {
        return label.getBytes();
    }
}
