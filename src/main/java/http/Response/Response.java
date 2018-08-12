package http.Response;

import http.util.ContentType;

import java.util.HashMap;
import java.util.Map;

public class Response {

    private byte[] httpVersion;
    private ResponseStatus responseStatus;
    private Map<ResponseHeader, byte[]> headers;
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

    public Map<ResponseHeader, byte[]> getHeaders() {
        return headers;
    }

    public void setContentTypeHeader(ContentType contentType) {
        headers.put(ResponseHeader.CONTENTTYPE, contentType.getBytesValue());
    }

    public void clearBody() {
        bodyContent = new byte[0];
    }

    public void setAllowHeader(String allowedMethods) {
        headers.put(ResponseHeader.ALLOW, allowedMethods.getBytes());
    }

    public void setContentRangeHeader(String contentRangeValue) {
        headers.put(ResponseHeader.CONTENTRANGE, contentRangeValue.getBytes());
    }

    public void setUnauthorisedHeader(String authenticateMessage) {
        headers.put(ResponseHeader.AUTHENTICATION, authenticateMessage.getBytes());
    }

    public void setLocationHeader(String redirectURI) {
        headers.put(ResponseHeader.LOCATION, redirectURI.getBytes());
    }

    public void setCookieHeader(String cookieType) {
        headers.put(ResponseHeader.COOKIE, cookieType.getBytes());
    }
}
