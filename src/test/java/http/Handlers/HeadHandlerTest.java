package http.Handlers;

import http.Request.HTTPVerb;
import http.Request.Request;
import http.Response.Response;
import http.Response.ResponseHeader;
import http.Response.ResponseStatus;
import http.util.ContentType;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

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