import java.util.HashMap;
import java.util.Map;

public class Response {

    private byte[] httpVersion;
    private ResponseStatus responseStatus;
    private byte[] bodyContent;
    private Map<byte[], byte[]> headers;

    public Response() {
        httpVersion = "HTTP/1.1".getBytes();
        headers = new HashMap<>();
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

    public void setContentType(ContentType contentType) {
        headers.put(HardcodedValues.CONTENTTYPEHEADER.getBytes(), contentType.getHeaderKey());
    }

    public Map<byte[], byte[]> getHeaders() {
        return headers;
    }
}
