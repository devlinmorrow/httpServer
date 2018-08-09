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
        String coffeePath = "/coffee";
        Response response = getResponseToGetRequestAt(coffeePath);

        assertEquals(ResponseStatus.IMATEAPOT, response.getStatus());
        assertArrayEquals(ResponseStatus.IMATEAPOT.getStatusBody(), response.getBodyContent());
    }

    @Test
    public void getTeaResponse() {
        String teaPath = "/tea";
        Response mockResponse = getResponseToGetRequestAt(teaPath);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals(teapotHandler.teaContent.getBytes(), mockResponse.getBodyContent());
    }

    private Response getResponseToGetRequestAt(String path) {
        HashMap<String, String> emptyHeaders = new HashMap<>();
        String emptyBody = "";
        Request request = new Request(HTTPVerb.GET, path, emptyHeaders, emptyBody);

        return teapotHandler.getResponse(request);
    }
}