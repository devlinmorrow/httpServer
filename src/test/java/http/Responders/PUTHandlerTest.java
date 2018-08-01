package http.Responders;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.Assert.*;

public class PUTHandlerTest {

    private String mockFileURI = "src/test/resources/newFile.txt";
    private HashMap<String, String> emptyHeaders = new HashMap<>();
    String mockContents = "Entered text";

    @Test
    public void handlePUTRequest_newFile() throws IOException {
        if (Files.exists(Paths.get(mockFileURI))) {
            Files.delete(Paths.get(mockFileURI));
        }

        Request mockRequest = new Request(HTTPVerb.PUT, mockFileURI, emptyHeaders, mockContents);
        PUTHandler putHandler = new PUTHandler();

        Response mockResponse = putHandler.handle(mockRequest);

        assertEquals(ResponseStatus.CREATED, mockResponse.getStatus());
        assertTrue(Files.exists(Paths.get(mockFileURI)));
        assertArrayEquals(mockContents.getBytes(), Files.readAllBytes(Paths.get(mockFileURI)));
    }

    @Test
    public void handlePUTRequest_updateExistingFile() throws IOException {
        Files.write(Paths.get(mockFileURI), mockContents.getBytes());
        String updatedContents = "Updated text";

        Request mockRequest = new Request(HTTPVerb.PUT, mockFileURI, emptyHeaders, updatedContents);
        PUTHandler putHandler = new PUTHandler();

        Response mockResponse = putHandler.handle(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals(updatedContents.getBytes(), Files.readAllBytes(Paths.get(mockFileURI)));
    }
}