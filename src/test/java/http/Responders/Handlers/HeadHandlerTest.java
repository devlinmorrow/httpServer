package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.ContentType;
import http.Responders.Response;
import http.Responders.ResponseHeader;
import http.Responders.ResponseStatus;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class HeadHandlerTest {

    private final static String testRootPath = "src/test/resources";
    private final static HashMap<String, String> emptyHeaders = new HashMap<>();
    private final static String emptyBody = "";

    @Test
    public void getResponse_headRequest_existingFile() {
        String resourcePath = "/testFile1.txt";
        Request request = new Request(HTTPVerb.HEAD, resourcePath,
                emptyHeaders, emptyBody);

        HeadHandler headHandler = new HeadHandler(testRootPath);
        Response response = headHandler.getResponse(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(ContentType.TXT.getBytesValue(),
                response.getHeaders().get(ResponseHeader.CONTENTTYPE));
        assertArrayEquals(emptyBody.getBytes(), response.getBodyContent());
    }

    @Test
    public void getResponse_headRequest_existingDir() {
        String resourcePath = "/testDir/";
        Request request = new Request(HTTPVerb.HEAD, resourcePath,
                emptyHeaders, emptyBody);

        HeadHandler headHandler = new HeadHandler(testRootPath);
        Response response = headHandler.getResponse(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(ContentType.HTML.getBytesValue(),
                response.getHeaders().get(ResponseHeader.CONTENTTYPE));
        assertArrayEquals(emptyBody.getBytes(), response.getBodyContent());
    }

    @Test
    public void getResponse_headRequest_notFoundFile() {
        String resourcePath = "/no_file_exists.txt";
        Request request = new Request(HTTPVerb.HEAD, resourcePath,
                emptyHeaders, emptyBody);

        HeadHandler headHandler = new HeadHandler(testRootPath);
        Response response = headHandler.getResponse(request);

        assertEquals(ResponseStatus.NOTFOUND, response.getStatus());
        assertArrayEquals(emptyBody.getBytes(), response.getBodyContent());
    }
}