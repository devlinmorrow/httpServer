package http.Responders.Handlers;

import http.HardcodedValues;
import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class AuthenticatorTest {

    private String mockLogsURI = "src/test/resources/logs";
    private HashMap<String, String> emptyHeaders = new HashMap<>();
    private String emptyBody = "";

    @Test
    public void respondTo_HEADLogs_withMethodNotAllowed() {
        Request mockRequest = new Request(HTTPVerb.HEAD,mockLogsURI, emptyHeaders, emptyBody);
        Authenticator authenticator = new Authenticator();

        assertEquals("NotAllowed", authenticator.handleLogs(mockRequest, new Response()));
    }

    @Test
    public void respondTo_GETLogs_withUnauthorised() {
        Request mockRequest = new Request(HTTPVerb.GET,mockLogsURI, emptyHeaders, emptyBody);
        Authenticator authenticator = new Authenticator();

        assertEquals("Unauthorised", authenticator.handleLogs(mockRequest, new Response()));
    }

    @Test
    public void respondTo_GETLogs_whenAuthorised() throws IOException {
        HashMap<String, String> authHeader = new HashMap<>();
        byte[] auth = Base64.getEncoder().encode("admin:hunter2".getBytes());
        String authorizationValue = "Basic " + new String(auth);
        authHeader.put(HardcodedValues.AUTHORIZATIONHEADER.getS(), authorizationValue);
        Request mockRequest = new Request(HTTPVerb.GET, mockLogsURI, authHeader, emptyBody);
        Authenticator authenticator = new Authenticator();

        assertEquals("GET", authenticator.handleLogs(mockRequest, new Response()));
    }
}