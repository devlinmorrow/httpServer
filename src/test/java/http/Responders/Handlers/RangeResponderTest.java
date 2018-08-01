package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.FileContentConverter;
import http.Responders.Response;
import http.Responders.ResponseHeader;
import http.Responders.ResponseStatus;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.*;

public class RangeResponderTest {

    private String mockFileURI = "src/test/resources/dummyFile1.txt";
    private HashMap<String, String> emptyHeaders = new HashMap<>();
    private String emptyBody = "";

    @Test
    public void respondTo_GETRequest_PartialContent() {
        GETHandler getHandler = new GETHandler();
        HashMap<String, String> rangeHeader = new HashMap<>();
        rangeHeader.put("Range","bytes=0-4");
        Request mockRequest = new Request(HTTPVerb.GET, mockFileURI, rangeHeader, emptyBody);

        Response mockResponse = getHandler.handle(mockRequest);

        FileContentConverter fileContentConverter = new FileContentConverter();
        File file = new File(mockFileURI);
        byte[] fullContent = fileContentConverter.getFullContents(file);
        byte[] expectedContent = Arrays.copyOfRange(fullContent, 0, 5);

        assertEquals(ResponseStatus.PARTIALCONTENT, mockResponse.getStatus());
        assertArrayEquals("bytes 0-4/15".getBytes(), mockResponse.getHeaders()
                .get(ResponseHeader.CONTENTRANGE));
        assertArrayEquals(expectedContent, mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_GETRequest_PartialContent_onlyStartIndex() {
        GETHandler getHandler = new GETHandler();
        HashMap<String, String> rangeHeader = new HashMap<>();
        rangeHeader.put("Range","bytes=11-");
        Request mockRequest = new Request(HTTPVerb.GET, mockFileURI, rangeHeader, emptyBody);

        Response mockResponse = getHandler.handle(mockRequest);

        FileContentConverter fileContentConverter = new FileContentConverter();
        File file = new File(mockFileURI);
        byte[] fullContent = fileContentConverter.getFullContents(file);
        byte[] expectedContent = Arrays.copyOfRange(fullContent, 11, fullContent.length);

        assertEquals(ResponseStatus.PARTIALCONTENT, mockResponse.getStatus());
        assertArrayEquals("bytes 11-14/15".getBytes(), mockResponse.getHeaders()
                .get(ResponseHeader.CONTENTRANGE));
        assertArrayEquals(expectedContent, mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_GETRequest_PartialContent_onlyEndNumber() {
        GETHandler getHandler = new GETHandler();
        HashMap<String, String> rangeHeader = new HashMap<>();
        rangeHeader.put("Range","bytes=-6");
        Request mockRequest = new Request(HTTPVerb.GET, mockFileURI, rangeHeader, emptyBody);

        Response mockResponse = getHandler.handle(mockRequest);

        FileContentConverter fileContentConverter = new FileContentConverter();
        File file = new File(mockFileURI);
        byte[] fullContent = fileContentConverter.getFullContents(file);
        byte[] expectedContent = Arrays.copyOfRange(fullContent, 9, fullContent.length);

        assertEquals(ResponseStatus.PARTIALCONTENT, mockResponse.getStatus());
        assertArrayEquals("bytes 9-14/15".getBytes(), mockResponse.getHeaders()
                .get(ResponseHeader.CONTENTRANGE));
        assertArrayEquals(expectedContent, mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_GETRequest_PartialContent_RangeNotSatisfiable() {
        GETHandler getHandler = new GETHandler();
        HashMap<String, String> rangeHeader = new HashMap<>();
        rangeHeader.put("Range","bytes=10-20");
        Request mockRequest = new Request(HTTPVerb.GET, mockFileURI, rangeHeader, emptyBody);

        Response mockResponse = getHandler.handle(mockRequest);

        assertEquals(ResponseStatus.RANGENOTSATISFIABLE, mockResponse.getStatus());
        assertArrayEquals("bytes */15".getBytes(), mockResponse.getHeaders()
                .get(ResponseHeader.CONTENTRANGE));
    }
}