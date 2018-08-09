package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseHeader;
import http.Responders.ResponseStatus;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CookieHandlerTest {

    private final static Map<String, String> emptyHeaders = new HashMap<>();

    @Test
    public void getResponse_getRequestSettingCookie() {
        String cookieParameterPath = "/cookie?type=mint";

        Response response = getResponseToGetRequestAt(cookieParameterPath, emptyHeaders);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals("mint".getBytes(), response.getHeaders().get(ResponseHeader.COOKIE));
        assertArrayEquals("Eat".getBytes(), response.getBodyContent());
    }

    @Test
    public void getResponse_getRequestWithCookie() {
        String eatCookiePath = "/eat_cookie";
        HashMap<String, String> cookieBananaHeader = new HashMap<>();
        String cookieFlavour = "banana";
        cookieBananaHeader.put("Cookie", cookieFlavour);

        Response response = getResponseToGetRequestAt(eatCookiePath, cookieBananaHeader);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(("mmmm " + cookieFlavour).getBytes(), response.getBodyContent());
    }

    @Test
    public void getResponse_getRequestNoCookie() {
        String eatCookiePath = "/eat_cookie";

        Response response = getResponseToGetRequestAt(eatCookiePath, emptyHeaders);

        assertEquals(ResponseStatus.NOTFOUND, response.getStatus());
        assertArrayEquals(ResponseStatus.NOTFOUND.getStatusBody(), response.getBodyContent());
    }

    private Response getResponseToGetRequestAt(String path, Map<String, String> headers) {
        String emptyBody = "";
        Request request = new Request(HTTPVerb.GET, path, headers, emptyBody);
        CookieHandler cookieHandler = new CookieHandler();

        return cookieHandler.getResponse(request);
    }
}