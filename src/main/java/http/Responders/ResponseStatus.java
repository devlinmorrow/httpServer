package http.Responders;

public enum ResponseStatus {

    OK("200 OK", ""),
    PARTIALCONTENT("206 Partial Content", ""),
    NOTFOUND("404 Not Found", "404 Error - Requested resource not found on this server."),
    METHODNOTALLOWED("405 Method Not Allowed", "The requested method is not allowed for this resource."),
    SERVERERROR("500 Internal Server Error", "500 Error - Internal Server Error.");

    private String phrase;
    private String statusBody;

    ResponseStatus(String phrase, String statusBody) {
        this.phrase = phrase;
        this.statusBody = statusBody;
    }

    public byte[] getPhrase() {
        return phrase.getBytes();
    }

    public String getPhraseAsS() {
        return phrase;
    }

    public byte[] getStatusBody() {
        return statusBody.getBytes();
    }
}
