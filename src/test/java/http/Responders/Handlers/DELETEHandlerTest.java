package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseStatus;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.Assert.*;

public class DELETEHandlerTest {

    private String mockFileURI = "src/test/resources/fileToDelete.txt";
    private HashMap<String, String> emptyHeaders = new HashMap<>();

    @Test
    public void handle_deleteExistingResource() throws IOException {
        if (!Files.exists(Paths.get(mockFileURI))) {
            File newFile = new File(mockFileURI);
            newFile.createNewFile();
        }

        Request mockRequest = new Request(HTTPVerb.DELETE, mockFileURI, emptyHeaders, "");
        DELETEHandler deleteHandler = new DELETEHandler();

        Response mockResponse = deleteHandler.handle(mockRequest);

        Assert.assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertFalse(Files.exists(Paths.get(mockFileURI)));
    }

    @Test
    public void handle_deleteResourceNotFound() throws IOException {
        if (Files.exists(Paths.get(mockFileURI))) {
            Files.delete(Paths.get(mockFileURI));
        }

        Request mockRequest = new Request(HTTPVerb.DELETE, mockFileURI, emptyHeaders, "");
        DELETEHandler deleteHandler = new DELETEHandler();

        Response mockResponse = deleteHandler.handle(mockRequest);

        Assert.assertEquals(ResponseStatus.NOTFOUND, mockResponse.getStatus());
    }
}