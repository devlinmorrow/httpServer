public class Response {

    private String httpVersion;
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
}
