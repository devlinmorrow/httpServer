package http.Requesters;

public enum HTTPVerb {

    DELETE("DELETE", true, false),
    GET("GET", true, true),
    HEAD("HEAD", true, true),
    NOTRECOGNISED("", false, false),
    OPTIONS("OPTIONS", true, true),
    POST("POST", false, false),
    PUT("PUT", true, false);

    private String label;
    private boolean allowed;
    private boolean allowedForLogs;

    HTTPVerb(String label, boolean allowed, boolean allowedForLogs) {
        this.label = label;
        this.allowed = allowed;
        this.allowedForLogs = allowedForLogs;
    }

    public static HTTPVerb find(String requestedHTTPVerb) {
        for (HTTPVerb verb : HTTPVerb.values()) {
            if (verb.label.equals(requestedHTTPVerb)) {
                return verb;
            }
        }
        return NOTRECOGNISED;
    }

    public static String getAllowedMethods() {
        StringBuilder allowedMethods = new StringBuilder();
        String previousSeparator = "";
        for (HTTPVerb verb : HTTPVerb.values()) {
            if (verb.allowed) {
                allowedMethods.append(previousSeparator).append(verb.label);
                previousSeparator = ", ";
            }
        }
        return new String(allowedMethods);
    }

    public static String getAllowedForLogsMethods() {
        StringBuilder allowedMethods = new StringBuilder();
        String previousSeparator = "";
        for (HTTPVerb verb : HTTPVerb.values()) {
            if (verb.allowedForLogs) {
                allowedMethods.append(previousSeparator).append(verb.label);
                previousSeparator = ", ";
            }
        }
        return new String(allowedMethods);
    }

    public boolean isNotAllowed() {
        return !allowed;
    }

    public boolean isNotAllowedForLogs() {
        return !allowedForLogs;
    }
}
