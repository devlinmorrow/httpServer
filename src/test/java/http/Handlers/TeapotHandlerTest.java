package http.Handlers;

import http.Request.HTTPVerb;
import http.Request.Request;
import http.Response.Response;
import http.Response.ResponseStatus;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TeapotHandlerTest {

    private final HashMap<String, String> emptyHeaders = new HashMap<>();
    private final String emptyBody = "";
    private TeapotHandler teapotHandler = new TeapotHandler();

    @Test
    public void givenGetRequestAtCoffee_setImATeapotResponse() {
        String coffeePath = "/coffee";
        Request request = new Request(HTTPVerb.GET, coffeePath, emptyHeaders, emptyBody);

        Response response = teapotHandler.getResponse(request);

        assertEquals(ResponseStatus.IMATEAPOT, response.getStatus());
        assertArrayEquals(ResponseStatus.IMATEAPOT.getStatusBody(), response.getBodyContent());
    }

    @Test
    public void givenGetRequestAtTea_setOKResponse() {
        String teaPath = "/tea";
        Request request = new Request(HTTPVerb.GET, teaPath, emptyHeaders, emptyBody);

        Response response = teapotHandler.getResponse(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(teapotHandler.teaContent.getBytes(), response.getBodyContent());
    }
}