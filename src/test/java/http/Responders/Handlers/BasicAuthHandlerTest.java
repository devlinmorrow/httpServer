package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseHeader;
import http.Responders.ResponseStatus;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class BasicAuthHandlerTest {

    private final String testRootPath = "src/test/resources";
    private final String logsPath = "/logs";

    @Test
    public void getResponseToGetLogs_unauthorised() {
        HashMap<String, String> emptyHeaders = new HashMap<>();
        Response response = getResponseToGetAuthFile(emptyHeaders);

        assertEquals(ResponseStatus.UNAUTHORISED, response.getStatus());
        assertNotNull(response.getHeaders().get(ResponseHeader.AUTHENTICATION));
        assertArrayEquals(ResponseStatus.UNAUTHORISED.getStatusBody(), response.getBodyContent());
    }

    @Test
    public void getResponseToGetLogs_authorised() throws IOException {
        String logsData = "logs data";
        writeDataToTestLogsFile(logsData);

        HashMap<String, String> authHeader = new HashMap<>();
        String authorization = "Basic " + new String(Base64.getEncoder().encode("admin:hunter2".getBytes()));
        authHeader.put("Authorization", authorization);

        Response response = getResponseToGetAuthFile(authHeader);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(logsData.getBytes(), response.getBodyContent());
    }

    private Response getResponseToGetAuthFile(Map<String, String> headers) {
        String emptyBody = "";
        Request request = new Request(HTTPVerb.GET, logsPath, headers, emptyBody);
        BasicAuthHandler basicAuthHandler = new BasicAuthHandler(testRootPath);
        return basicAuthHandler.getResponse(request);
    }

    private void writeDataToTestLogsFile(String data) throws IOException {
        String testFullRootPath = testRootPath + logsPath;
        Files.write(Paths.get(testFullRootPath), data.getBytes());
    }
}