package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseHeader;
import http.Responders.ResponseStatus;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class OptionsHandlerTest {

    private final static HashMap<String, String> emptyHeaders = new HashMap<>();
    private final static String emptyBody = "";

    @Test
    public void respondTo_OPTIONSRequest_NotLogs() {
        String resourcePath = "/testFile1.txt";
        Request request = new Request(HTTPVerb.OPTIONS,  resourcePath, emptyHeaders, emptyBody);
        OptionsHandler optionsHandler = new OptionsHandler();
        Response response = optionsHandler.getResponse(request);

                Assert.assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(HTTPVerb.getAllowedMethods().getBytes(),
                response.getHeaders().get(ResponseHeader.ALLOW));
        assertArrayEquals(emptyBody.getBytes(), response.getBodyContent());
    }

    @Test
    public void respondTo_OPTIONSRequest_ForLogs() {
        String resourcePath = "/logs";
        Request request = new Request(HTTPVerb.OPTIONS,  resourcePath, emptyHeaders, emptyBody);
        OptionsHandler optionsHandler = new OptionsHandler();
        Response response = optionsHandler.getResponse(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals("GET, HEAD, OPTIONS, PATCH".getBytes(),
                response.getHeaders().get(ResponseHeader.ALLOW));
        assertArrayEquals(emptyBody.getBytes(), response.getBodyContent());
    }
}