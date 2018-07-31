package http.Responders;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RequestResponderTest {

    private String mockFileURI = "src/test/resources/dummyFile1.txt";
    private HashMap<String, String> emptyHeaders = new HashMap<>();

    @Test
    public void respondTo_GETRequest_MethodNotAllowedForFile() {
        RequestResponder requestResponder = new RequestResponder();
        Request mockRequest = new Request(HTTPVerb.POST, mockFileURI, emptyHeaders);

        Response mockResponse = requestResponder.respondTo(mockRequest);

        assertEquals(ResponseStatus.METHODNOTALLOWED, mockResponse.getStatus());
        assertTrue(mockResponse.getHeaders().isEmpty());
        assertArrayEquals(ResponseStatus.METHODNOTALLOWED.getStatusBody(),
                mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_GETRequest_MethodNotAllowedForLogs() {
        RequestResponder requestResponder = new RequestResponder();
        Request mockRequest = new Request(HTTPVerb.DELETE, "src/test/resources/logsDummy", emptyHeaders);

        Response mockResponse = requestResponder.respondTo(mockRequest);

        assertEquals(ResponseStatus.METHODNOTALLOWED, mockResponse.getStatus());
        assertTrue(mockResponse.getHeaders().isEmpty());
        assertArrayEquals(ResponseStatus.METHODNOTALLOWED.getStatusBody(),
                mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_OPTIONSRequest_ForLogFile() {
        RequestResponder requestResponder = new RequestResponder();
        Request mockRequest = new Request(HTTPVerb.OPTIONS,"src/test/resources/logs", emptyHeaders);

        Response mockResponse = requestResponder.respondTo(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals("GET, HEAD, OPTIONS".getBytes(),
                mockResponse.getHeaders().get(ResponseHeader.ALLOW));
        assertArrayEquals("".getBytes(), mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_GETRequest_PartialContent() {
//        RequestResponder requestResponder = new RequestResponder();
//        HashMap<String, String> rangeHeader = new HashMap<>();
//        rangeHeader.put("Range","bytes=0-4");
//        Request mockRequest = new Request(HTTPVerb.GET, mockFileURI, rangeHeader);
//
//        Response mockResponse = requestResponder.respondTo(mockRequest);
//
//        FileContentConverter fileContentConverter = new FileContentConverter();
//        File file = new File(mockFileURI);
//
//        byte[] fullContent = fileContentConverter.getContents(file);
//        byte[] expectedContent = Arrays.copyOfRange(fullContent, 0, 4);
//
//        assertEquals(ResponseStatus.PARTIALCONTENT, mockResponse.getStatus());
//        assertArrayEquals(expectedContent, mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_OPTIONSRequest_ignoreRange() {
        RequestResponder requestResponder = new RequestResponder();
        HashMap<String, String> rangeHeader = new HashMap<>();
        rangeHeader.put("Range","bytes=0-4");
        Request mockRequest = new Request(HTTPVerb.OPTIONS, mockFileURI, rangeHeader);

        Response mockResponse = requestResponder.respondTo(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals(HTTPVerb.getAllowedMethods().getBytes(),
                mockResponse.getHeaders().get(ResponseHeader.ALLOW));
        assertArrayEquals("".getBytes(), mockResponse.getBodyContent());
    }
}