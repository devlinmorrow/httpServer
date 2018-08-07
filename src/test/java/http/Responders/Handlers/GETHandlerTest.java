package http.Responders.Handlers;

import http.HardcodedValues;
import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;

import static org.junit.Assert.*;

public class GETHandlerTest {

    private String mockFileURI = "src/test/resources/dummyFile1.txt";
    private String mockLogsURI = "src/test/resources/logs";
    private HashMap<String, String> emptyHeaders = new HashMap<>();
    private String emptyBody = "";

    @Test
    public void respondTo_GETRequest_withFileResource() {
        byte[] dummyFileContents = "file1 contents\n".getBytes();
        Request mockRequest = new Request(HTTPVerb.GET, mockFileURI, emptyHeaders, emptyBody);
        GETHandler getHandler = new GETHandler();

        Response mockResponse = getHandler.handle(mockRequest);

        Assert.assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        Assert.assertArrayEquals(ContentType.TXT.getBytesValue(),
                mockResponse.getHeaders().get(ResponseHeader.CONTENTTYPE));
        assertArrayEquals(dummyFileContents, mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_GETRequest_withNotFound() {
        Request mockRequest = new Request(HTTPVerb.GET,"src/test/resources/no-file.txt", emptyHeaders, emptyBody);
        GETHandler getHandler = new GETHandler();

        Response mockResponse = getHandler.handle(mockRequest);

        assertEquals(ResponseStatus.NOTFOUND, mockResponse.getStatus());
        assertTrue(mockResponse.getHeaders().isEmpty());
        assertArrayEquals(ResponseStatus.NOTFOUND.getStatusBody(), mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_HEADRequest_withFileResource() {
        Request mockRequest = new Request(HTTPVerb.HEAD, mockFileURI, emptyHeaders, emptyBody);
        GETHandler getHandler = new GETHandler();

        Response mockResponse = getHandler.handle(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertTrue(mockResponse.getHeaders().isEmpty());
        assertArrayEquals("".getBytes(), mockResponse.getBodyContent());

    }

    @Test
    public void respondTo_GETDirectoryRequest() {
        byte[] dummyDirectoryContents = ("<html><head></head><body>" +
                "<a href='/dummyFile2.txt'>dummyFile2.txt</a><br>" +
                "</body></html>").getBytes();
        Request mockRequest = new Request(HTTPVerb.GET,"src/test/resources/dummyDirectory", emptyHeaders, emptyBody);

        GETHandler getHandler = new GETHandler();

        Response mockResponse = getHandler.handle(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals(ContentType.HTML.getBytesValue(),
                mockResponse.getHeaders().get(ResponseHeader.CONTENTTYPE));
        assertArrayEquals(dummyDirectoryContents, mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_HEADRequest_withDirectoryResource() {
        Request mockRequest = new Request(HTTPVerb.HEAD,"src/test/resources/dummyDirectory", emptyHeaders, emptyBody);
        GETHandler getHandler = new GETHandler();

        Response mockResponse = getHandler.handle(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertTrue(mockResponse.getHeaders().isEmpty());
        assertArrayEquals("".getBytes(), mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_HEADRequest_forLogs_withMethodNotAllowed() {
        Request mockRequest = new Request(HTTPVerb.HEAD,"src/test/resources/logs", emptyHeaders, emptyBody);
        GETHandler getHandler = new GETHandler();

        Response mockResponse = getHandler.handle(mockRequest);

        assertEquals(ResponseStatus.METHODNOTALLOWED, mockResponse.getStatus());
    }

    @Test
    public void respondTo_HEADLogs_withMethodNotAllowed() {
        Request mockRequest = new Request(HTTPVerb.HEAD, mockLogsURI, emptyHeaders, emptyBody);

        GETHandler getHandler = new GETHandler();

        Response mockResponse = getHandler.handle(mockRequest);

        assertEquals(ResponseStatus.METHODNOTALLOWED, mockResponse.getStatus());
    }

    @Test
    public void respondTo_GETLogs_withUnauthorised() {
        Request mockRequest = new Request(HTTPVerb.GET, mockLogsURI, emptyHeaders, emptyBody);

        GETHandler getHandler = new GETHandler();

        Response mockResponse = getHandler.handle(mockRequest);

        assertEquals(ResponseStatus.UNAUTHORISED, mockResponse.getStatus());
        assertNotNull(mockResponse.getHeaders().get(ResponseHeader.AUTHENTICATE));
        assertArrayEquals(ResponseStatus.UNAUTHORISED.getStatusBody(), mockResponse.getBodyContent());
    }

    @Test
    public void respondTo_GETLogs_whenAuthorised() {
        File logFile = new File(mockLogsURI);
        if (!Files.exists(Paths.get(mockLogsURI))) {
            try {
                Files.createFile(Paths.get(mockLogsURI));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        HashMap<String, String> authHeader = new HashMap<>();
        byte[] auth = Base64.getEncoder().encode("admin:hunter2".getBytes());
        String authorizationValue = "Basic " + new String(auth);
        authHeader.put(HardcodedValues.AUTHORIZATIONHEADER.getS(), authorizationValue);
        Request mockRequest = new Request(HTTPVerb.GET, mockLogsURI, authHeader, emptyBody);
        FileContentConverter fileContentConverter = new FileContentConverter();
        byte[] logsData = fileContentConverter.getFullContents(logFile);
        GETHandler getHandler = new GETHandler();

        Response mockResponse = getHandler.handle(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals(logsData, mockResponse.getBodyContent());
    }
}