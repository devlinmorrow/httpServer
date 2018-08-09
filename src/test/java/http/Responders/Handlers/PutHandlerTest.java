package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseStatus;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.Assert.*;

public class PutHandlerTest {

    private String testRootPath = "src/test/resources";
    private String resourcePath = "/newFile.txt";
    private String fullTestPath = testRootPath + resourcePath;

    @Test
    public void getResponse_putRequest_newFile() throws IOException {
        deleteTestFileIfExists();

        String bodyContent = "first text";
        Response response = getResponseToPutRequest(bodyContent);

        assertEquals(ResponseStatus.CREATED, response.getStatus());
        assertTrue(Files.exists(Paths.get(fullTestPath)));
        assertArrayEquals(bodyContent.getBytes(), Files.readAllBytes(Paths.get(fullTestPath)));
    }

    @Test
    public void getResponse_putRequest_updateExistingFile() throws IOException {
        Files.write(Paths.get(fullTestPath), "some words".getBytes());

        String updatedContents = "Updated text";
        Response response = getResponseToPutRequest(updatedContents);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(updatedContents.getBytes(), Files.readAllBytes(Paths.get(fullTestPath)));
    }

    private Response getResponseToPutRequest(String bodyContent) {
        Request request = new Request(HTTPVerb.PUT, resourcePath, new HashMap<>(), bodyContent);
        PutHandler putHandler = new PutHandler(testRootPath);

        return putHandler.getResponse(request);
    }

    private void deleteTestFileIfExists() throws IOException {
        if (Files.exists(Paths.get(fullTestPath))) {
            Files.delete(Paths.get(fullTestPath));
        }
    }
}