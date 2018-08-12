package http.Request;

import http.util.IOHelper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestParserTest {

    @Test
    public void givenGetRequestInput_buildRequest() {
        String filePath = "/testFile1.txt";
        String localHostHeader = "Host: Localhost: 5000";
        String contentLengthHeader = "Content-Length: 17";
        String bodyContent = "some body content";
        String requestInput = "GET " + filePath + " HTTP/1.1\n" +
                localHostHeader + "\n" + contentLengthHeader + "\n\n" + bodyContent;
        IOHelper clientIO = new IOHelper(requestInput);

        RequestParser requestParser = new RequestParser();
        Request request = requestParser.parse(clientIO.getIn());

        assertEquals(HTTPVerb.GET, request.getHTTPVerb());
        assertEquals(filePath, request.getResourcePath());
        assertEquals("Localhost: 5000", request.getHeaders().get("Host"));
        assertEquals("17", request.getHeaders().get("Content-Length"));
        assertEquals(bodyContent, request.getBodyContent());
    }
}