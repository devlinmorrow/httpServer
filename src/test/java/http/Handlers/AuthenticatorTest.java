package http.Handlers;

import http.Request.HTTPVerb;
import http.Request.Request;
import org.junit.Test;

import java.util.Base64;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class AuthenticatorTest {

    private final Authenticator authenticator = new Authenticator();
    private final String testLogsPath = "/logs";
    private final HashMap<String, String> emptyHeaders = new HashMap<>();
    private final String emptyBody = "";

    @Test
    public void givenHeadRequestForLogs_respondWithNotAllowed() {
        Request request = new Request(HTTPVerb.HEAD, testLogsPath, emptyHeaders, emptyBody);

        assertEquals("NotAllowed", authenticator.handleLogs(request));
    }

    @Test
    public void givenUnauthorisedGetRequestForLogs_respondWithUnauthorised() {
        Request request = new Request(HTTPVerb.GET, testLogsPath, emptyHeaders, emptyBody);

        assertEquals("Unauthorised", authenticator.handleLogs(request));
    }

    @Test
    public void givenAuthorisedGetRequestForLogs_respondWithAuthorisedResponse() {
        HashMap<String, String> authHeader = new HashMap<>();
        byte[] auth = Base64.getEncoder().encode("admin:hunter2".getBytes());
        String authorizationValue = "Basic " + new String(auth);
        String authorizationHeader = "Authorization";
        authHeader.put(authorizationHeader, authorizationValue);

        Request request = new Request(HTTPVerb.GET, testLogsPath, authHeader, emptyBody);

        assertEquals("GET", authenticator.handleLogs(request));
    }
}