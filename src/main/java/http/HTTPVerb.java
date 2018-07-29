package http;

public enum HTTPVerb {

    NOTRECOGNISED(""),
    GET("GET"),
    HEAD("HEAD");

    private String label;

    HTTPVerb(String label) {
        this.label = label;
    }

    public static HTTPVerb find(String requestedHTTPVerb) {
        for (HTTPVerb httpVerb : HTTPVerb.values()) {
            if (httpVerb.label.equals(requestedHTTPVerb)) {
                return httpVerb;
            }
        }
        return NOTRECOGNISED;
    }
}
