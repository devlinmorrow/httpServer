package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.FileContentConverter;
import http.Responders.Response;
import http.Responders.ResponseHeader;
import http.Responders.ResponseStatus;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;

import static org.junit.Assert.*;

public class BasicAuthHandlerTest {

    private final String testRootPath = "src/test/resources";
    private final String logsPath = "/logs";
    private final String testFullRootPath = testRootPath + "/logs";

    @Test
    public void getResponseToGetLogs_unauthorised() {
        Request request = new Request(HTTPVerb.GET, logsPath, new HashMap<>(), "");

        BasicAuthHandler basicAuthHandler = new BasicAuthHandler(testRootPath);

        Response response = basicAuthHandler.getResponse(request);

        assertEquals(ResponseStatus.UNAUTHORISED, response.getStatus());
        assertNotNull(response.getHeaders().get(ResponseHeader.AUTHENTICATION));
        assertArrayEquals(ResponseStatus.UNAUTHORISED.getStatusBody(), response.getBodyContent());
    }

    @Test
    public void respondTo_GETLogs_whenAuthorised() {
        if (!Files.exists(Paths.get(testFullRootPath))) {
            try {
                Files.createFile(Paths.get(testFullRootPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File logFile = new File(testFullRootPath);

        HashMap<String, String> authHeader = new HashMap<>();
        byte[] auth = Base64.getEncoder().encode("admin:hunter2".getBytes());
        String authorizationValue = "Basic " + new String(auth);
        authHeader.put("Authorization", authorizationValue);
        Request request = new Request(HTTPVerb.GET, logsPath, authHeader, "");

        BasicAuthHandler basicAuthHandler = new BasicAuthHandler(testRootPath);

        Response response = basicAuthHandler.getResponse(request);

        FileContentConverter fileContentConverter = new FileContentConverter();
        byte[] logsData = fileContentConverter.getFullContents(logFile);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(logsData, response.getBodyContent());
    }
}