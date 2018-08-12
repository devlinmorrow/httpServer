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

public class DirectoryHandlerTest {

    private final static String testRootPath = "src/test/resources";
    private final static HashMap<String, String> emptyHeaders = new HashMap<>();
    private final static String emptyBody = "";

    @Test
    public void handlesPathsEndingInSlash() {
        DirectoryHandler directoryHandler = new DirectoryHandler(testRootPath);
        Request request = new Request(HTTPVerb.GET, "/endsInSlash/", emptyHeaders, emptyBody);

        assertTrue(directoryHandler.isHandledPathSegment(request));
    }

    @Test
    public void rejectsPathsNotEndingInSlash() {
        DirectoryHandler directoryHandler = new DirectoryHandler(testRootPath);
        Request request = new Request(HTTPVerb.GET, "/doesNotEndInSlash", emptyHeaders, emptyBody);

        assertFalse(directoryHandler.isHandledPathSegment(request));
    }

    @Test
    public void getResponse_getDir_exists() {
        String testDirPath = "/testDir/";
        Request request = new Request(HTTPVerb.GET, testDirPath, emptyHeaders, emptyBody);
        DirectoryHandler directoryHandler = new DirectoryHandler(testRootPath);

        Response response = directoryHandler.getResponse(request);

        String expectedDirListing = "<html><head></head><body><a href=" +
                "'/testFile2.txt'>testFile2.txt</a><br></body></html>";

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(ContentType.HTML.getBytesValue(), response.getHeaders().get(ResponseHeader.CONTENTTYPE));
        assertArrayEquals(expectedDirListing.getBytes(), response.getBodyContent());
    }

    @Test
    public void getResponse_getDir_doesNotExist() {
        String falseDirPath = "/non-existent-dir/";
        Request request = new Request(HTTPVerb.GET, falseDirPath, emptyHeaders, emptyBody);
        DirectoryHandler directoryHandler = new DirectoryHandler(testRootPath);

        Response response = directoryHandler.getResponse(request);

        assertEquals(ResponseStatus.NOTFOUND, response.getStatus());
        assertArrayEquals(ResponseStatus.NOTFOUND.getStatusBody(), response.getBodyContent());
    }
}