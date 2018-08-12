package http.Handlers;

import http.Request.HTTPVerb;
import http.Request.Request;
import http.Response.Response;
import http.Response.ResponseHeader;
import http.Response.ResponseStatus;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CookieHandlerTest {

    private final String eatCookiePath = "/eat_cookie";
    private final Map<String, String> emptyHeaders = new HashMap<>();
    private final String emptyBody = "";
    private final CookieHandler cookieHandler = new CookieHandler();

    @Test
    public void givenGetRequestWithSetCookiePath_setCookie() {
        String cookieParameterPath = "/cookie?type=mint";
        Request request = new Request(HTTPVerb.GET, cookieParameterPath, emptyHeaders, emptyBody);

        Response response = cookieHandler.getResponse(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals("mint".getBytes(), response.getHeaders().get(ResponseHeader.COOKIE));
        assertArrayEquals("Eat".getBytes(), response.getBodyContent());
    }

    @Test
    public void givenGetRequestForCookieWithoutCookieHeader_setNotFoundResponse() {
        Request request = new Request(HTTPVerb.GET, eatCookiePath, emptyHeaders, emptyBody);

        Response response = cookieHandler.getResponse(request);

        assertEquals(ResponseStatus.NOTFOUND, response.getStatus());
        assertArrayEquals(ResponseStatus.NOTFOUND.getStatusBody(), response.getBodyContent());
    }

    @Test
    public void givenGetRequestForCookieWithCookieHeader_setCookieResponse() {
        HashMap<String, String> cookieBananaHeader = new HashMap<>();
        String cookieFlavour = "banana";
        cookieBananaHeader.put("Cookie", cookieFlavour);
        Request request = new Request(HTTPVerb.GET, eatCookiePath, cookieBananaHeader, emptyBody);

        Response response = cookieHandler.getResponse(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(("mmmm " + cookieFlavour).getBytes(), response.getBodyContent());
    }
}