package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.*;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class GetHandlerTest {

    private String testFileRoot = "src/test/resources";

    @Test
    public void getResponse_getFile() {
        byte[] testFileContents = "file1 contents\n".getBytes();
        Request request = new Request(HTTPVerb.GET, "/testFile1.txt", new HashMap<>(), "");
        GetHandler getHandler = new GetHandler(testFileRoot);

        Response response = getHandler.getResponse(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(ContentType.TXT.getBytesValue(),
                response.getHeaders().get(ResponseHeader.CONTENTTYPE));
        assertArrayEquals(testFileContents, response.getBodyContent());
    }

    @Test
    public void getResponse_getNotFound() {
        Request request = new Request(HTTPVerb.GET,"/no-file.txt", new HashMap<>(), "");
        GetHandler getHandler = new GetHandler(testFileRoot);

        Response response = getHandler.getResponse(request);

        assertEquals(ResponseStatus.NOTFOUND, response.getStatus());
        assertArrayEquals(ResponseStatus.NOTFOUND.getStatusBody(), response.getBodyContent());
    }
}