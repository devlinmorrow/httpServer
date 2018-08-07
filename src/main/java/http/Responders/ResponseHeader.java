package http.Responders;

public enum ResponseHeader {

    ALLOW("Allow"),
    AUTHENTICATE("WWW-Authenticate"),
    CONTENTRANGE("Content-Range"),
    CONTENTTYPE("Content-Type"),
    COOKIE("Set-Cookie"),
    LOCATION("Location");

    private String label;

    ResponseHeader(String label) {
        this.label = label;
    }

    public byte[] getLabel() {
        return label.getBytes();
    }
}
