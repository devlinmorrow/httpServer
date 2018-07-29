package http;

import java.util.HashMap;
import java.util.Map;

public class Response {

    private byte[] httpVersion;
    private ResponseStatus responseStatus;
    private Map<Header, byte[]> headers;
    private byte[] bodyContent;

    public Response() {
        httpVersion = "HTTP/1.1".getBytes();
        this.responseStatus = ResponseStatus.SERVERERROR;
        headers = new HashMap<>();
        bodyContent = new byte[0];
    }

    public byte[] getHttpVersion() {
        return httpVersion;
    }

    public void setStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public ResponseStatus getStatus() {
        return responseStatus;
    }

    public void setBodyContent(byte[] bodyContent) {
        this.bodyContent = bodyContent;
    }

    public byte[] getBodyContent() {
        return bodyContent;
    }

    public Map<Header, byte[]> getHeaders() {
        return headers;
    }

    public void setContentTypeHeader(ContentType contentType) {
        headers.put(Header.CONTENTTYPE, contentType.getBytesValue());
    }

    public void clearAllExceptStatusLine() {
        headers.clear();
        bodyContent = new byte[0];
    }
}
