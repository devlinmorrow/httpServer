package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseStatus;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TeapotHandlerTest {

    @Test
    public void getCoffeeResponse() {
        TeapotHandler teapotHandler = new TeapotHandler();
        Request mockRequest = new Request(HTTPVerb.GET, "/coffee", new HashMap<>(), "");

        Response mockResponse = teapotHandler.getResponse(mockRequest);

        assertEquals(ResponseStatus.IMATEAPOT, mockResponse.getStatus());
        assertArrayEquals(ResponseStatus.IMATEAPOT.getStatusBody(), mockResponse.getBodyContent());
    }

    @Test
    public void getTeaResponse() {
        TeapotHandler teapotHandler = new TeapotHandler();
        Request mockRequest = new Request(HTTPVerb.GET, "/tea", new HashMap<>(), "");

        Response mockResponse = teapotHandler.getResponse(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals(teapotHandler.teaContent.getBytes(), mockResponse.getBodyContent());
    }
}