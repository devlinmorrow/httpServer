package http.Responders;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.Assert.*;

public class PUTHandlerTest {

    private String mockFileURI = "src/test/resources/newFile.txt";
    private HashMap<String, String> emptyHeaders = new HashMap<>();

    @Test
    public void handlePUTRequest() throws IOException {
        String mockContents = "Entered text\n";
        Request mockRequest = new Request(HTTPVerb.PUT, mockFileURI, emptyHeaders, mockContents);
        PUTHandler putHandler = new PUTHandler();
        File mockFile = new File(mockFileURI);

        Response mockResponse = putHandler.handle(mockRequest);

        assertEquals(ResponseStatus.CREATED, mockResponse.getStatus());
        assertTrue(mockFile.exists());
        assertArrayEquals(mockContents.getBytes(), Files.readAllBytes(Paths.get(mockFileURI)));
        mockFile.delete();
    }
}