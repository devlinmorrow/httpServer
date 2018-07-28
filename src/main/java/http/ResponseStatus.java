package http;

public enum ResponseStatus {

    OK("200 OK", ""),
    NOTFOUND("404 Not Found", "404 Error - Requested resource not found on this server."),
    SERVERERROR("500 Internal Server Error","500 Error - Internal Server Error.");

    private String phrase;
    private String statusBody;

    ResponseStatus(String phrase, String statusBody) {
        this.phrase = phrase;
        this.statusBody = statusBody;
    }

    public byte[] getPhrase() {
        return phrase.getBytes();
    }

    public byte[] getStatusBody() {
        return statusBody.getBytes();
    }
}
