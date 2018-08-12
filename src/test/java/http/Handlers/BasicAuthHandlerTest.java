package http.Handlers;

import http.Request.HTTPVerb;
import http.Request.Request;
import http.Response.Response;
import http.Response.ResponseHeader;
import http.Response.ResponseStatus;
import http.util.Logger;
import org.junit.Test;

import java.util.Base64;
import java.util.HashMap;

import static org.junit.Assert.*;

public class BasicAuthHandlerTest {

    private final Logger logger = new Logger();
    private final String logsPath = "/logs";
    private final String emptyBody = "";
    private final BasicAuthHandler basicAuthHandler = new BasicAuthHandler(logger);

    @Test
    public void givenUnauthorisedGetRequestForLogs_getUnauthorisedResponse() {
        HashMap<String, String> emptyHeaders = new HashMap<>();
        Request request = new Request(HTTPVerb.GET, logsPath, emptyHeaders, emptyBody);

        Response response = basicAuthHandler.getResponse(request);

        assertEquals(ResponseStatus.UNAUTHORISED, response.getStatus());
        assertNotNull(response.getHeaders().get(ResponseHeader.AUTHENTICATION));
        assertArrayEquals(ResponseStatus.UNAUTHORISED.getStatusBody(), response.getBodyContent());
    }

    @Test
    public void getResponseToGetLogs_authorised() {
        String logsData = "logs data";
        logger.addLog(logsData);

        HashMap<String, String> authHeader = new HashMap<>();
        String authorizationData = "Basic " + new String
                (Base64.getEncoder().encode("admin:hunter2".getBytes()));
        authHeader.put("Authorization", authorizationData);

        Request request = new Request(HTTPVerb.GET, logsPath, authHeader, emptyBody);

        Response response = basicAuthHandler.getResponse(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals((logsData + "\n").getBytes(), response.getBodyContent());
    }
}