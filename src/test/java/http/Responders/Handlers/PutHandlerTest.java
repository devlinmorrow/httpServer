package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseStatus;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.Assert.*;

public class PutHandlerTest {

    private String mockRootPath = "src/test/resources";
    private String resourcePath = "/newFile.txt";
    private String mockFileURI = mockRootPath + resourcePath;
    private HashMap<String, String> emptyHeaders = new HashMap<>();
    String mockContents = "Entered text";

    @Test
    public void handlePUTRequest_newFile() throws IOException {
        if (Files.exists(Paths.get(mockFileURI))) {
            Files.delete(Paths.get(mockFileURI));
        }

        Request mockRequest = new Request(HTTPVerb.PUT, resourcePath, emptyHeaders, mockContents);
        PutHandler putHandler = new PutHandler(mockRootPath);

        Response mockResponse = putHandler.getResponse(mockRequest);

        Assert.assertEquals(ResponseStatus.CREATED, mockResponse.getStatus());
        assertTrue(Files.exists(Paths.get(mockFileURI)));
        assertArrayEquals(mockContents.getBytes(), Files.readAllBytes(Paths.get(mockFileURI)));
    }

    @Test
    public void handlePUTRequest_updateExistingFile() throws IOException {
        Files.write(Paths.get(mockFileURI), mockContents.getBytes());
        String updatedContents = "Updated text";

        Request mockRequest = new Request(HTTPVerb.PUT, resourcePath, emptyHeaders, updatedContents);
        PutHandler putHandler = new PutHandler(mockRootPath);

        Response mockResponse = putHandler.getResponse(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals(updatedContents.getBytes(), Files.readAllBytes(Paths.get(mockFileURI)));
    }
}