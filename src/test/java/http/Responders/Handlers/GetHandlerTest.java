package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.*;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class GetHandlerTest {

    private final static String testFileRoot = "src/test/resources";

    @Test
    public void getResponse_getFile() {
        Response response = getResponseToGetRequestAt("/testFile1.txt");
        byte[] testFileContents = "file1 contents\n".getBytes();

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(ContentType.TXT.getBytesValue(),
                response.getHeaders().get(ResponseHeader.CONTENTTYPE));
        assertArrayEquals(testFileContents, response.getBodyContent());
    }

    @Test
    public void getResponse_getNotFound() {
        Response response = getResponseToGetRequestAt("/no-file.txt");

        assertEquals(ResponseStatus.NOTFOUND, response.getStatus());
        assertArrayEquals(ResponseStatus.NOTFOUND.getStatusBody(), response.getBodyContent());
    }

    private Response getResponseToGetRequestAt(String path) {
        HashMap<String, String> emptyHeaders = new HashMap<>();
        String emptyBody = "";
        Request request = new Request(HTTPVerb.GET, path, emptyHeaders, emptyBody);
        GetHandler getHandler = new GetHandler(testFileRoot);

        return getHandler.getResponse(request);
    }
}