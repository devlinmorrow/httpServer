package http.Responders.Handler;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Handlers.OPTIONSHandler;
import http.Responders.Response;
import http.Responders.ResponseHeader;
import http.Responders.ResponseStatus;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class OPTIONSHandlerTest {

    private String mockFileURI = "src/test/resources/dummyFile1.txt";
    private HashMap<String, String> emptyHeaders = new HashMap<>();
    private String emptyBody = "";

    @Test
    public void respondTo_OPTIONSRequest_NotLogs() {
        Request mockRequest = new Request(HTTPVerb.OPTIONS, mockFileURI, emptyHeaders, emptyBody);
        OPTIONSHandler optionsHandler = new OPTIONSHandler();

        Response mockResponse = optionsHandler.handle(mockRequest);

        Assert.assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals(HTTPVerb.getAllowedMethods().getBytes(),
                mockResponse.getHeaders().get(ResponseHeader.ALLOW));
        assertArrayEquals("".getBytes(), mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_OPTIONSRequest_ForLogs() {
        Request mockRequest = new Request(HTTPVerb.OPTIONS, "src/test/resources/Logs", emptyHeaders, emptyBody);
        OPTIONSHandler optionsHandler = new OPTIONSHandler();

        Response mockResponse = optionsHandler.handle(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals("GET, HEAD, OPTIONS".getBytes(),
                mockResponse.getHeaders().get(ResponseHeader.ALLOW));
        assertArrayEquals("".getBytes(), mockResponse.getBodyContent());
    }
}