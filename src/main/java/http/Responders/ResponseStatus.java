package http.Responders;

public enum ResponseStatus {

    CREATED("201 Created",""),
    FOUND("302 Found", "Redirecting..."),
    IMATEAPOT("418 I'm a teapot","I'm a teapot... you can't get coffee from me!"),
    METHODNOTALLOWED("405 Method Not Allowed", "The requested method is not allowed for this resource."),
    NOTFOUND("404 Not Found", "404 Error - Requested resource not found on this server."),
    OK("200 OK", ""),
    PARTIALCONTENT("206 Partial Content", ""),
    RANGENOTSATISFIABLE("416 Range Not Satisfiable", ""),
    SERVERERROR("500 Internal Server Error", "500 Error - Internal Server Error."),
    UNAUTHORISED("401 Unauthorised", "You have not provided the credentials required for this resource.");

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
