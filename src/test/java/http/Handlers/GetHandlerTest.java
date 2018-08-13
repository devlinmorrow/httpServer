package http.Handlers;

import http.Request.HTTPVerb;
import http.Request.Request;
import http.Response.Response;
import http.Response.ResponseHeader;
import http.Response.ResponseStatus;
import http.util.ContentType;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class GetHandlerTest {

    private final String testFileRoot = "src/test/resources";
    private final HashMap<String, String> emptyHeaders = new HashMap<>();
    private final String emptyBody = "";
    private final GetHandler getHandler = new GetHandler(testFileRoot);

    @Test
    public void givenGetRequestForExistingFile_getFileAndSetOKResponse() throws IOException {
        String filePath = "/testFile1.txt";
        Request request = new Request(HTTPVerb.GET, filePath, emptyHeaders, emptyBody);

        Response response = getHandler.getResponse(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(ContentType.TXT.getBytesValue(),
                response.getHeaders().get(ResponseHeader.CONTENTTYPE));
        assertArrayEquals("file1 contents\n".getBytes(), response.getBodyContent());
    }

    @Test
    public void givenGetRequestForNonExistentFile_setNotFoundResponse() throws IOException {
        String nonExistentFilePath = "/no-file.txt";
        Request request = new Request(HTTPVerb.GET, nonExistentFilePath, emptyHeaders, emptyBody);

        Response response = getHandler.getResponse(request);

        assertEquals(ResponseStatus.NOTFOUND, response.getStatus());
        assertArrayEquals(ResponseStatus.NOTFOUND.getStatusBody(), response.getBodyContent());
    }
}