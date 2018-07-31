package http.Responders;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class GETFileHandlerTest {

    private String mockFileURI = "src/test/resources/dummyFile1.txt";
    private HashMap<String, String> emptyHeaders = new HashMap<>();

    @Test
    public void respondTo_GETRequest_withFileResource() {
        byte[] dummyFileContents = "file1 contents\n".getBytes();
        Request mockRequest = new Request(HTTPVerb.GET, mockFileURI, emptyHeaders);
        GETFileHandler getFileHandler = new GETFileHandler();

        Response mockResponse = getFileHandler.handleRequest(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals(ContentType.TXT.getBytesValue(),
                mockResponse.getHeaders().get(ResponseHeader.CONTENTTYPE));
        assertArrayEquals(dummyFileContents, mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_GETRequest_withNotFound() {
        Request mockRequest = new Request(HTTPVerb.GET,"src/test/resources/no-file.txt", emptyHeaders);
        GETFileHandler getFileHandler = new GETFileHandler();

        Response mockResponse = getFileHandler.handleRequest(mockRequest);

        assertEquals(ResponseStatus.NOTFOUND, mockResponse.getStatus());
        assertTrue(mockResponse.getHeaders().isEmpty());
        assertArrayEquals(ResponseStatus.NOTFOUND.getStatusBody(), mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_HEADRequest_withFileResource() {
        Request mockRequest = new Request(HTTPVerb.HEAD, mockFileURI, emptyHeaders);
        GETFileHandler getFileHandler = new GETFileHandler();

        Response mockResponse = getFileHandler.handleRequest(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertTrue(mockResponse.getHeaders().isEmpty());
        assertArrayEquals("".getBytes(), mockResponse.getBodyContent());

    }

    @Test
    public void respondTo_GETDirectoryRequest() {
        byte[] dummyDirectoryContents = ("<html><head></head><body>" +
                "<a href='/dummyFile2.txt'>dummyFile2.txt</a><br>" +
                "</body></html>").getBytes();
        Request mockRequest = new Request(HTTPVerb.GET,"src/test/resources/dummyDirectory", emptyHeaders);

        GETFileHandler getFileHandler = new GETFileHandler();

        Response mockResponse = getFileHandler.handleRequest(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals(ContentType.HTML.getBytesValue(),
                mockResponse.getHeaders().get(ResponseHeader.CONTENTTYPE));
        assertArrayEquals(dummyDirectoryContents, mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_HEADRequest_withDirectoryResource() {
        Request mockRequest = new Request(HTTPVerb.HEAD,"src/test/resources/dummyDirectory", emptyHeaders);
        GETFileHandler getFileHandler = new GETFileHandler();

        Response mockResponse = getFileHandler.handleRequest(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertTrue(mockResponse.getHeaders().isEmpty());
        assertArrayEquals("".getBytes(), mockResponse.getBodyContent());
    }
}