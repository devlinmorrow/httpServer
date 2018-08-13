package http.Handlers;

import http.Request.HTTPVerb;
import http.Request.Request;
import http.Response.Response;
import http.Response.ResponseStatus;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DeleteHandlerTest {

    private final String rootPath = "src/test/resources";
    private final HashMap<String, String> emptyHeaders = new HashMap<>();
    private final String emptyBody = "";
    private final DeleteHandler deleteHandler = new DeleteHandler(rootPath);

    @Test
    public void givenDeleteRequestForExistingFile_deleteFileAndSetOKResponse() throws IOException {
        String fileToDeletePath = "/fileToDelete.txt";
        createFileAt(rootPath + fileToDeletePath);
        Request request = new Request(HTTPVerb.DELETE, fileToDeletePath, emptyHeaders, emptyBody);

        Response response = deleteHandler.getResponse(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertFalse(Files.exists(Paths.get(rootPath + fileToDeletePath)));
    }

    @Test
    public void givenDeleteRequestForNonExistentFile_setNotFoundResponse() {
        String noFilePath = "/noFileHere.txt";
        Request request = new Request(HTTPVerb.DELETE, noFilePath, emptyHeaders, emptyBody);

        Response response = deleteHandler.getResponse(request);

        assertEquals(ResponseStatus.NOTFOUND, response.getStatus());
    }

    private void createFileAt(String path) throws IOException {
        if (!Files.exists(Paths.get(path))) {
            File newFile = new File(path);
            newFile.createNewFile();
        }
    }
}