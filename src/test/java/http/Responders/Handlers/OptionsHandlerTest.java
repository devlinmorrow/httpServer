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

    @Test
    public void respondTo_OPTIONSRequest_NotLogs() {
        Response response = getOptionsResponseTo("/testFile1.txt");

                Assert.assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(HTTPVerb.getAllowedMethods().getBytes(),
                response.getHeaders().get(ResponseHeader.ALLOW));
        assertArrayEquals("".getBytes(), response.getBodyContent());
    }

    @Test
    public void respondTo_OPTIONSRequest_ForLogs() {
        Response response = getOptionsResponseTo("/logs");

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals("GET, HEAD, OPTIONS, PATCH".getBytes(),
                response.getHeaders().get(ResponseHeader.ALLOW));
        assertArrayEquals("".getBytes(), response.getBodyContent());
    }

    private Response getOptionsResponseTo(String path) {
        HashMap<String, String> emptyHeaders = new HashMap<>();
        String emptyBody = "";
        Request request = new Request(HTTPVerb.OPTIONS, "src/test/resources" + path, emptyHeaders, emptyBody);
        OptionsHandler optionsHandler = new OptionsHandler();
        return optionsHandler.getResponse(request);
    }
}