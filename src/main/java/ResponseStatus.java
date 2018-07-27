public enum ResponseStatus {

    TWOHUNDRED("200 OK", ""),
    FOUROHFOUR("404 Not Found", "404 Error - Requested resource not found on this server.");

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
