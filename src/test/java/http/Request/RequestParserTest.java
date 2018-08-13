package http.Request;

import http.util.IOHelper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RequestParserTest {

    private final String filePath = "/testFile1.txt";
    private final String hostData = "Localhost: 5000";
    private final String hostHeader = "Host: " + hostData;
    private final String contentLengthData = "17";
    private final String contentLengthHeader = "Content-Length: " + contentLengthData;
    private final String bodyContent = "some body content";
    private final String emptyBody = "";
    private final RequestParser requestParser = new RequestParser();

    @Test
    public void givenGetRequestInput_buildRequest() throws IOException {
        String requestInput = "GET " + filePath + " HTTP/1.1\n" +
                hostHeader + "\n" + contentLengthHeader + "\n\n" + bodyContent;
        IOHelper clientIO = new IOHelper(requestInput);
        InputStream requestInputStream = clientIO.getIn();

        Request request = requestParser.parse(requestInputStream);

        assertEquals(HTTPVerb.GET, request.getHTTPVerb());
        assertEquals(filePath, request.getResourcePath());
        assertEquals(hostData, request.getHeaders().get("Host"));
        assertEquals(contentLengthData, request.getHeaders().get("Content-Length"));
        assertEquals(bodyContent, request.getBodyContent());
    }

    @Test
    public void givenRequestWithNoBody_buildRequestWithNoBody() throws IOException {
        String requestInput = "GET " + filePath + " HTTP/1.1\n" + hostHeader + "\n";
        IOHelper clientIO = new IOHelper(requestInput);
        InputStream requestInputStream = clientIO.getIn();

        Request request = requestParser.parse(requestInputStream);

        assertEquals(HTTPVerb.GET, request.getHTTPVerb());
        assertEquals(filePath, request.getResourcePath());
        assertEquals(hostData, request.getHeaders().get("Host"));
        assertNull(request.getHeaders().get("Content-Length"));
        assertEquals(emptyBody, request.getBodyContent());
    }
}
