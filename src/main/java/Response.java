public class Response {

    private String httpVersion;
    private ResponseStatus responseStatus;
    private String bodyContent;

    public Response() {
        httpVersion = "HTTP/1.1";
    }

    public void setBodyContent(String bodyContent) {
        this.bodyContent = bodyContent;
    }

    public String getBodyContent() {
        return bodyContent;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }
}
