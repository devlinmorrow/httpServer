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

    @Test
    public void handlesPathsEndingInSlash() {
        DirectoryHandler directoryHandler = new DirectoryHandler("/");
        Request request = new Request(HTTPVerb.GET, "anything/", new HashMap<>(), "");

        assertTrue(directoryHandler.isHandledPathSegment(request));
    }

    @Test
    public void rejectsPathsNotEndingInSlash() {
        DirectoryHandler directoryHandler = new DirectoryHandler("/");
        Request request = new Request(HTTPVerb.GET, "anything", new HashMap<>(), "");

        assertFalse(directoryHandler.isHandledPathSegment(request));
    }

    @Test
    public void getResponse_getDir_exists() {
        DirectoryHandler directoryHandler = new DirectoryHandler("src/test/resources");
        Request request = new Request(HTTPVerb.GET, "/testDir/", new HashMap<>(), "");

        Response response = directoryHandler.getResponse(request);

        String expectedDirListing = "<html><head></head><body><a href=" +
                "'/testFile2.txt'>testFile2.txt</a><br></body></html>";

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(ContentType.HTML.getBytesValue(), response.getHeaders().get(ResponseHeader.CONTENTTYPE));
        assertArrayEquals(expectedDirListing.getBytes(), response.getBodyContent());
    }

    @Test
    public void getResponse_getDir_doesNotExist() {
        DirectoryHandler directoryHandler = new DirectoryHandler("/");
        Request request = new Request(HTTPVerb.GET, "/non-existent-dir/", new HashMap<>(), "");

        Response response = directoryHandler.getResponse(request);

        assertEquals(ResponseStatus.NOTFOUND, response.getStatus());
        assertArrayEquals(ResponseStatus.NOTFOUND.getStatusBody(), response.getBodyContent());
    }

    @Test
    public void getResponse_headDir_exists() {
        DirectoryHandler directoryHandler = new DirectoryHandler("src/test/resources");
        Request request = new Request(HTTPVerb.HEAD, "/", new HashMap<>(), "");

        Response response = directoryHandler.getResponse(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals("".getBytes(), response.getBodyContent());
    }

    @Test
    public void getResponse_headDir_doesNotExist() {
        DirectoryHandler directoryHandler = new DirectoryHandler("/");
        Request request = new Request(HTTPVerb.HEAD, "/non-existent-dir/", new HashMap<>(), "");

        Response response = directoryHandler.getResponse(request);

        assertEquals(ResponseStatus.NOTFOUND, response.getStatus());
        assertArrayEquals("".getBytes(), response.getBodyContent());
    }
}