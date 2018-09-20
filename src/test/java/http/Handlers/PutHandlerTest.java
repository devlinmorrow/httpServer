package http.Handlers;

import http.Request.HTTPVerb;
import http.Request.Request;
import http.Response.Response;
import http.Response.ResponseStatus;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.Assert.*;

public class PutHandlerTest {

    private final String testRootPath = "src/test/resources";
    private final String resourcePath = "/newFile.txt";
    private final String fullTestPath = testRootPath + resourcePath;
    private final HashMap<String, String> emptyHeaders = new HashMap<>();
    private final PutHandler putHandler = new PutHandler(testRootPath);

    @Test
    public void givenPutRequestForNonExistentFile_createFileAndSetCreatedResponse() throws IOException {
        deleteTestFileIfExists();

        String bodyContent = "first text";
        Request request = new Request(HTTPVerb.PUT, resourcePath, emptyHeaders, bodyContent);

        Response response = putHandler.getResponse(request);

        assertEquals(ResponseStatus.CREATED, response.getStatus());
        assertTrue(Files.exists(Paths.get(fullTestPath)));
        assertArrayEquals(bodyContent.getBytes(), Files.readAllBytes(Paths.get(fullTestPath)));
    }

    @Test
    public void givenPutRequestForExistingFile_updateFileAndSetOKResponse() throws IOException {
        overwriteDataToFile("some words".getBytes());

        String updatedContents = "Updated text";
        Request request = new Request(HTTPVerb.PUT, resourcePath, emptyHeaders, updatedContents);

        Response response = putHandler.getResponse(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(updatedContents.getBytes(), Files.readAllBytes(Paths.get(fullTestPath)));
    }

    private void deleteTestFileIfExists() throws IOException {
        if (Files.exists(Paths.get(fullTestPath))) {
            Files.delete(Paths.get(fullTestPath));
        }
    }

    private void overwriteDataToFile(byte[] content) throws IOException {
        Files.write(Paths.get("src/test/resources/newFile.txt"), content);
    }
}