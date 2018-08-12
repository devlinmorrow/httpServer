package http.Handlers;

import http.Request.HTTPVerb;
import http.Request.Request;
import http.Response.Response;
import http.Response.ResponseHeader;
import http.Response.ResponseStatus;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class OptionsHandlerTest {

    private final HashMap<String, String> emptyHeaders = new HashMap<>();
    private final String emptyBody = "";
    private final OptionsHandler optionsHandler = new OptionsHandler();

    @Test
    public void givenOptionsRequestForNonLogsFile_setOptionsResponse() {
        String filePath = "/testFile1.txt";
        Request request = new Request(HTTPVerb.OPTIONS, filePath, emptyHeaders, emptyBody);

        Response response = optionsHandler.getResponse(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(HTTPVerb.getAllowedMethods().getBytes(),
                response.getHeaders().get(ResponseHeader.ALLOW));
        assertArrayEquals(emptyBody.getBytes(), response.getBodyContent());
    }

    @Test
    public void givenOptionsRequestForLogsFile_setLogsOptionsResponse() {
        String logsPath = "/logs";
        Request request = new Request(HTTPVerb.OPTIONS, logsPath, emptyHeaders, emptyBody);
        Response response = optionsHandler.getResponse(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(HTTPVerb.getAllowedForLogsMethods().getBytes(),
                response.getHeaders().get(ResponseHeader.ALLOW));
        assertArrayEquals(emptyBody.getBytes(), response.getBodyContent());
    }
}