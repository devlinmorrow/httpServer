package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import org.junit.Test;

import java.util.Base64;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class AuthenticatorTest {

    private final static String testLogsPath = "src/test/resources/logs";
    private final static HashMap<String, String> emptyHeaders = new HashMap<>();
    private final static String emptyBody = "";
    private final static String authorizationHeader = "Authorization";

    @Test
    public void respondTo_HEADLogs_withMethodNotAllowed() {
        Request mockRequest = new Request(HTTPVerb.HEAD, testLogsPath, emptyHeaders, emptyBody);
        Authenticator authenticator = new Authenticator();

        assertEquals("NotAllowed", authenticator.handleLogs(mockRequest));
    }

    @Test
    public void respondTo_GETLogs_withUnauthorised() {
        Request mockRequest = new Request(HTTPVerb.GET, testLogsPath, emptyHeaders, emptyBody);
        Authenticator authenticator = new Authenticator();

        assertEquals("Unauthorised", authenticator.handleLogs(mockRequest));
    }

    @Test
    public void respondTo_GETLogs_whenAuthorised() {
        HashMap<String, String> authHeader = new HashMap<>();
        byte[] auth = Base64.getEncoder().encode("admin:hunter2".getBytes());
        String authorizationValue = "Basic " + new String(auth);
        authHeader.put(authorizationHeader, authorizationValue);
        Request mockRequest = new Request(HTTPVerb.GET, testLogsPath, authHeader, emptyBody);
        Authenticator authenticator = new Authenticator();

        assertEquals("GET", authenticator.handleLogs(mockRequest));
    }
}