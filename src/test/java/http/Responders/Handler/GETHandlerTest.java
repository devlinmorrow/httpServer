package http.Responders.Handler;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.ContentType;
import http.Responders.Handlers.GETHandler;
import http.Responders.Response;
import http.Responders.ResponseHeader;
import http.Responders.ResponseStatus;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class GETHandlerTest {

    private String mockFileURI = "src/test/resources/dummyFile1.txt";
    private HashMap<String, String> emptyHeaders = new HashMap<>();
    private String emptyBody = "";

    @Test
    public void respondTo_GETRequest_withFileResource() {
        byte[] dummyFileContents = "file1 contents\n".getBytes();
        Request mockRequest = new Request(HTTPVerb.GET, mockFileURI, emptyHeaders, emptyBody);
        GETHandler getHandler = new GETHandler();

        Response mockResponse = getHandler.handle(mockRequest);

        Assert.assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        Assert.assertArrayEquals(ContentType.TXT.getBytesValue(),
                mockResponse.getHeaders().get(ResponseHeader.CONTENTTYPE));
        assertArrayEquals(dummyFileContents, mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_GETRequest_withNotFound() {
        Request mockRequest = new Request(HTTPVerb.GET,"src/test/resources/no-file.txt", emptyHeaders, emptyBody);
        GETHandler getHandler = new GETHandler();

        Response mockResponse = getHandler.handle(mockRequest);

        assertEquals(ResponseStatus.NOTFOUND, mockResponse.getStatus());
        assertTrue(mockResponse.getHeaders().isEmpty());
        assertArrayEquals(ResponseStatus.NOTFOUND.getStatusBody(), mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_HEADRequest_withFileResource() {
        Request mockRequest = new Request(HTTPVerb.HEAD, mockFileURI, emptyHeaders, emptyBody);
        GETHandler getHandler = new GETHandler();

        Response mockResponse = getHandler.handle(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertTrue(mockResponse.getHeaders().isEmpty());
        assertArrayEquals("".getBytes(), mockResponse.getBodyContent());

    }

    @Test
    public void respondTo_GETDirectoryRequest() {
        byte[] dummyDirectoryContents = ("<html><head></head><body>" +
                "<a href='/dummyFile2.txt'>dummyFile2.txt</a><br>" +
                "</body></html>").getBytes();
        Request mockRequest = new Request(HTTPVerb.GET,"src/test/resources/dummyDirectory", emptyHeaders, emptyBody);

        GETHandler getHandler = new GETHandler();

        Response mockResponse = getHandler.handle(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals(ContentType.HTML.getBytesValue(),
                mockResponse.getHeaders().get(ResponseHeader.CONTENTTYPE));
        assertArrayEquals(dummyDirectoryContents, mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_HEADRequest_withDirectoryResource() {
        Request mockRequest = new Request(HTTPVerb.HEAD,"src/test/resources/dummyDirectory", emptyHeaders, emptyBody);
        GETHandler getHandler = new GETHandler();

        Response mockResponse = getHandler.handle(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertTrue(mockResponse.getHeaders().isEmpty());
        assertArrayEquals("".getBytes(), mockResponse.getBodyContent());
    }
//
//    @Test
//    public void respondTo_GETRequest_PartialContent() {
//        GETHandler getHandler = new GETHandler();
//        HashMap<String, String> rangeHeader = new HashMap<>();
//        rangeHeader.put("Range","bytes=0-4");
//        Request mockRequest = new Request(HTTPVerb.GET, mockFileURI, rangeHeader);
//
//        Response mockResponse = getHandler.handle(mockRequest);
//
//        FileContentConverter fileContentConverter = new FileContentConverter();
//        File file = new File(mockFileURI);
//        byte[] fullContent = fileContentConverter.getContents(file);
//        byte[] expectedContent = Arrays.copyOfRange(fullContent, 0, 4);
//
//        assertEquals(ResponseStatus.PARTIALCONTENT, mockResponse.getStatus());
//        assertArrayEquals("bytes 0-4/15".getBytes(), mockResponse.getHeaders()
//                .get(ResponseHeader.CONTENTRANGE));
//        assertArrayEquals(expectedContent, mockResponse.getBodyContent());
//    }
}