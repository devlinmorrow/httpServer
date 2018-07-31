package http.Responders;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class OPTIONSHandlerTest {

    private String mockFileURI = "src/test/resources/dummyFile1.txt";
    private HashMap<String, String> emptyHeaders = new HashMap<>();

    @Test
    public void respondTo_OPTIONSRequest_NotLogs() {
        Request mockRequest = new Request(HTTPVerb.OPTIONS, mockFileURI, emptyHeaders);
        OPTIONSHandler optionsHandler = new OPTIONSHandler();

        Response mockResponse = optionsHandler.handleRequest(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals(HTTPVerb.getAllowedMethods().getBytes(),
                mockResponse.getHeaders().get(ResponseHeader.ALLOW));
        assertArrayEquals("".getBytes(), mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_OPTIONSRequest_ForLogs() {
        Request mockRequest = new Request(HTTPVerb.OPTIONS, "src/test/resources/Logs", emptyHeaders);
        OPTIONSHandler optionsHandler = new OPTIONSHandler();

        Response mockResponse = optionsHandler.handleRequest(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals("GET, HEAD, OPTIONS".getBytes(),
                mockResponse.getHeaders().get(ResponseHeader.ALLOW));
        assertArrayEquals("".getBytes(), mockResponse.getBodyContent());
    }
}