package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseStatus;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.Assert.*;

public class DeleteHandlerTest {

    private final static String testRootPath = "src/test/resources";
    private final static HashMap<String, String> emptyHeaders = new HashMap<>();
    private final static String emptyBody = "";


    @Test
    public void handle_deleteExistingResource() throws IOException {
        String fileToDeletePath = "/fileToDelete.txt";
        createFileIfDoesNotExist(testRootPath + fileToDeletePath);

        Response response = getResponseToDeleteRequestAt(fileToDeletePath);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertFalse(Files.exists(Paths.get(testRootPath + fileToDeletePath)));
    }

    @Test
    public void handle_deleteResourceNotFound() {
        String notFoundPath = "/noFileHere.txt";
        Response response = getResponseToDeleteRequestAt(notFoundPath);

        assertEquals(ResponseStatus.NOTFOUND, response.getStatus());
    }

    private void createFileIfDoesNotExist(String path) throws IOException {
        if (!Files.exists(Paths.get(path))) {
            File newFile = new File(path);
            newFile.createNewFile();
        }
    }

    private Response getResponseToDeleteRequestAt(String path) {
        Request request = new Request(HTTPVerb.DELETE, path, emptyHeaders, emptyBody);
        DeleteHandler deleteHandler = new DeleteHandler(testRootPath);

        return deleteHandler.getResponse(request);
    }
}