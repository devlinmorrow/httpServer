public class Response {

    private byte[] httpVersion;
    private ResponseStatus responseStatus;
    private byte[] bodyContent;

    public Response() {
        httpVersion = "HTTP/1.1".getBytes();
    }

    public void setBodyContent(byte[] bodyContent) {
        this.bodyContent = bodyContent;
    }

    public byte[] getBodyContent() {
        return bodyContent;
    }

    public byte[] getHttpVersion() {
        return httpVersion;
    }

    public void setStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }
}
