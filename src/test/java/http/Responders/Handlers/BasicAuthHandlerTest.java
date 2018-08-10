package http.Responders.Handlers;

import http.Logger;
import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseHeader;
import http.Responders.ResponseStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class BasicAuthHandlerTest {

    private Logger logger;
    private final static String logsData = "logs data";

    @Before
    public void setUp() {
        logger = new Logger();
        logger.addLog(logsData);
    }

    @Test
    public void getResponseToGetLogs_unauthorised() {
        HashMap<String, String> emptyHeaders = new HashMap<>();
        Response response = getResponseToGetAuthFile(emptyHeaders);

        assertEquals(ResponseStatus.UNAUTHORISED, response.getStatus());
        assertNotNull(response.getHeaders().get(ResponseHeader.AUTHENTICATION));
        assertArrayEquals(ResponseStatus.UNAUTHORISED.getStatusBody(), response.getBodyContent());
    }

    @Test
    public void getResponseToGetLogs_authorised() {

        HashMap<String, String> authHeader = new HashMap<>();
        String authorizationData = "Basic " + new String
                (Base64.getEncoder().encode("admin:hunter2".getBytes()));
        authHeader.put("Authorization", authorizationData);

        Response response = getResponseToGetAuthFile(authHeader);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals((logsData + "\n").getBytes(), response.getBodyContent());
    }

    private Response getResponseToGetAuthFile(Map<String, String> headers) {
        String logsPath = "/logs";
        String emptyBody = "";
        Request request = new Request(HTTPVerb.GET, logsPath, headers, emptyBody);
        BasicAuthHandler basicAuthHandler = new BasicAuthHandler(logger);
        return basicAuthHandler.getResponse(request);
    }
}