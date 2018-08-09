package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TeapotHandlerTest {

    private TeapotHandler teapotHandler;

    @Before
    public void setUp() {
        teapotHandler = new TeapotHandler();
    }

    @Test
    public void getCoffeeResponse() {
        Response response = getResponseToGetRequestAt("/coffee");

        assertEquals(ResponseStatus.IMATEAPOT, response.getStatus());
        assertArrayEquals(ResponseStatus.IMATEAPOT.getStatusBody(), response.getBodyContent());
    }

    @Test
    public void getTeaResponse() {
        Response mockResponse = getResponseToGetRequestAt("/tea");

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals(teapotHandler.teaContent.getBytes(), mockResponse.getBodyContent());
    }

    private Response getResponseToGetRequestAt(String path) {
        Request request = new Request(HTTPVerb.GET, path, new HashMap<>(), "");

        return teapotHandler.getResponse(request);
    }
}