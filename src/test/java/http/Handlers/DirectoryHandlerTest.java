package http.Handlers;

import http.Request.HTTPVerb;
import http.Request.Request;
import http.Response.Response;
import http.Response.ResponseHeader;
import http.Response.ResponseStatus;
import http.util.ContentType;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class DirectoryHandlerTest {

    private final String testRootPath = "src/test/resources";
    private final HashMap<String, String> emptyHeaders = new HashMap<>();
    private final String emptyBody = "";
    private final DirectoryHandler directoryHandler = new DirectoryHandler(testRootPath);

    @Test
    public void pathEndingInSlashIsHandledPathSegment() {
        String pathEndingInSlash = "/endsInSlash/";
        Request request = new Request(HTTPVerb.GET, pathEndingInSlash, emptyHeaders, emptyBody);

        assertTrue(directoryHandler.isHandledPathSegment(request));
    }

    @Test
    public void rejectsPathsNotEndingInSlash() {
        String pathNotEndingInSlash = "/doesNotEndInSlash";
        Request request = new Request(HTTPVerb.GET, pathNotEndingInSlash, emptyHeaders, emptyBody);

        assertFalse(directoryHandler.isHandledPathSegment(request));
    }

    @Test
    public void givenGetRequestForExistingDir_getDirListingAndSetOKResponse() {
        String testDirPath = "/testDir/";
        Request request = new Request(HTTPVerb.GET, testDirPath, emptyHeaders, emptyBody);

        Response response = directoryHandler.getResponse(request);

        String expectedDirListing = "<html><head></head><body><a href=" +
                "'/testFile2.txt'>testFile2.txt</a><br></body></html>";

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(ContentType.HTML.getBytesValue(), response.getHeaders().get(ResponseHeader.CONTENTTYPE));
        assertArrayEquals(expectedDirListing.getBytes(), response.getBodyContent());
    }

    @Test
    public void givenGetRequestForNonExistentDir_setNotFoundResponse() {
        String falseDirPath = "/non-existent-dir/";
        Request request = new Request(HTTPVerb.GET, falseDirPath, emptyHeaders, emptyBody);

        Response response = directoryHandler.getResponse(request);

        assertEquals(ResponseStatus.NOTFOUND, response.getStatus());
        assertArrayEquals(ResponseStatus.NOTFOUND.getStatusBody(), response.getBodyContent());
    }
}