package http;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RequestResponderTest {

    private String mockFileURI = "src/test/resources/dummyFile1.txt";

    @Test
    public void respondTo_GETRequest_withFileResource() {
        byte[] dummyFileContents = "file1 contents\n".getBytes();
        RequestResponder requestResponder = new RequestResponder();
        Request mockRequest = new Request(HTTPVerb.GET, mockFileURI);

        Response mockResponse = requestResponder.respondTo(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals(ContentType.TXT.getBytesValue(),
                mockResponse.getHeaders().get(Header.CONTENTTYPE));
        assertArrayEquals(dummyFileContents, mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_GETRequest_withDirectory() {
        byte[] dummyDirectoryContents = ("<html><head></head><body>" +
                "<a href='/dummyFile2.txt'>dummyFile2.txt</a><br>" +
                "</body></html>").getBytes();
        RequestResponder requestResponder = new RequestResponder();
        Request mockRequest = new Request(HTTPVerb.GET,"src/test/resources/dummyDirectory");

        Response mockResponse = requestResponder.respondTo(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals(ContentType.HTML.getBytesValue(),
                mockResponse.getHeaders().get(Header.CONTENTTYPE));
        assertArrayEquals(dummyDirectoryContents, mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_GETRequest_ResourceNotFound() {
        String URI = "src/test/resources/no-file-here";
        RequestResponder requestResponder = new RequestResponder();
        Request mockRequest = new Request(HTTPVerb.GET, URI);

        Response mockResponse = requestResponder.respondTo(mockRequest);

        assertEquals(ResponseStatus.NOTFOUND, mockResponse.getStatus());
        assertTrue(mockResponse.getHeaders().isEmpty());
        assertArrayEquals(ResponseStatus.NOTFOUND.getStatusBody(),
                mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_HEADRequest_withFileResource() {
        RequestResponder requestResponder = new RequestResponder();
        Request mockRequest = new Request(HTTPVerb.HEAD, mockFileURI);

        Response mockResponse = requestResponder.respondTo(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertTrue(mockResponse.getHeaders().isEmpty());
        assertArrayEquals("".getBytes(), mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_HEADRequest_withDirectoryResource() {
        RequestResponder requestResponder = new RequestResponder();
        Request mockRequest = new Request(HTTPVerb.HEAD,"src/test/resources/dummyDirectory");

        Response mockResponse = requestResponder.respondTo(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertTrue(mockResponse.getHeaders().isEmpty());
        assertArrayEquals("".getBytes(), mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_GETRequest_MethodNotAllowedForFile() {
        RequestResponder requestResponder = new RequestResponder();
        Request mockRequest = new Request(HTTPVerb.POST, mockFileURI);

        Response mockResponse = requestResponder.respondTo(mockRequest);

        assertEquals(ResponseStatus.METHODNOTALLOWED, mockResponse.getStatus());
        assertTrue(mockResponse.getHeaders().isEmpty());
        assertArrayEquals(ResponseStatus.METHODNOTALLOWED.getStatusBody(),
                mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_GETRequest_MethodNotAllowedForLogs() {
        RequestResponder requestResponder = new RequestResponder();
        Request mockRequest = new Request(HTTPVerb.DELETE, "src/test/resources/logsDummy");

        Response mockResponse = requestResponder.respondTo(mockRequest);

        assertEquals(ResponseStatus.METHODNOTALLOWED, mockResponse.getStatus());
        assertTrue(mockResponse.getHeaders().isEmpty());
        assertArrayEquals(ResponseStatus.METHODNOTALLOWED.getStatusBody(),
                mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_OPTIONSRequest_NotLogs() {
        RequestResponder requestResponder = new RequestResponder();
        Request mockRequest = new Request(HTTPVerb.OPTIONS, mockFileURI);

        Response mockResponse = requestResponder.respondTo(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals(HTTPVerb.getAllowedMethods().getBytes(),
                mockResponse.getHeaders().get(Header.ALLOW));
        assertArrayEquals("".getBytes(), mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_OPTIONSRequest_ForLogFile() {
        RequestResponder requestResponder = new RequestResponder();
        Request mockRequest = new Request(HTTPVerb.OPTIONS,"src/test/resources/logs" );

        Response mockResponse = requestResponder.respondTo(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals("GET, HEAD, OPTIONS".getBytes(),
                mockResponse.getHeaders().get(Header.ALLOW));
        assertArrayEquals("".getBytes(), mockResponse.getBodyContent());
    }
}