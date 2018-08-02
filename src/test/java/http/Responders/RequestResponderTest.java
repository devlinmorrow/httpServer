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
    private String emptyBody;

    @Test
    public void respondTo_GETRequest_MethodNotAllowedForFile() {
        RequestResponder requestResponder = new RequestResponder();
        Request mockRequest = new Request(HTTPVerb.POST, mockFileURI, emptyHeaders, emptyBody);

        Response mockResponse = requestResponder.respondTo(mockRequest);

        assertEquals(ResponseStatus.METHODNOTALLOWED, mockResponse.getStatus());
        assertTrue(mockResponse.getHeaders().isEmpty());
        assertArrayEquals(ResponseStatus.METHODNOTALLOWED.getStatusBody(),
                mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_GETRequest_MethodNotAllowedForLogs() {
        RequestResponder requestResponder = new RequestResponder();
        Request mockRequest = new Request(HTTPVerb.DELETE, "src/test/resources/logs", emptyHeaders, emptyBody);

        Response mockResponse = requestResponder.respondTo(mockRequest);

        assertEquals(ResponseStatus.METHODNOTALLOWED, mockResponse.getStatus());
        assertTrue(mockResponse.getHeaders().isEmpty());
        assertArrayEquals(ResponseStatus.METHODNOTALLOWED.getStatusBody(),
                mockResponse.getBodyContent());
    }

}