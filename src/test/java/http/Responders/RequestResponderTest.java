package http.Responders;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

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
}