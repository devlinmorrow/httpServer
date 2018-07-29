package http;

public enum HTTPVerb {

    NOTRECOGNISED("", false),
    GET("GET", true),
    HEAD("HEAD", true),
    POST("POST", false);

    private String label;
    private boolean allowed;

    HTTPVerb(String label, boolean allowed) {
        this.label = label;
        this.allowed = allowed;
    }

    public static HTTPVerb find(String requestedHTTPVerb) {
        for (HTTPVerb httpVerb : HTTPVerb.values()) {
            if (httpVerb.label.equals(requestedHTTPVerb)) {
                return httpVerb;
            }
        }
        return NOTRECOGNISED;
    }

    public boolean isNotallowed() {
        return !allowed;
    }
}
