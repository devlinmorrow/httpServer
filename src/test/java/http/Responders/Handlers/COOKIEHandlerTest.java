package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseHeader;
import http.Responders.ResponseStatus;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class COOKIEHandlerTest {

    @Test
    public void handle_requestToSetCookie() {
        Request mockRequest = new Request(HTTPVerb.GET, "/cookie?type=mint", new HashMap<>(), "");
        COOKIEHandler cookieHandler = new COOKIEHandler();

        Response mockResponse = cookieHandler.handle(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals("mint".getBytes(), mockResponse.getHeaders().get(ResponseHeader.COOKIE));
        assertArrayEquals("Eat".getBytes(), mockResponse.getBodyContent());
    }

    @Test
    public void handle_requestWithCookie() {
        HashMap<String, String> cookieHeader = new HashMap<>();
        cookieHeader.put("Cookie", "banana");
        Request mockRequest = new Request(HTTPVerb.GET, "/eat_cookie", cookieHeader, "");
        COOKIEHandler cookieHandler = new COOKIEHandler();

        Response mockResponse = cookieHandler.handle(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals("mmmm banana".getBytes(), mockResponse.getBodyContent());
    }
}