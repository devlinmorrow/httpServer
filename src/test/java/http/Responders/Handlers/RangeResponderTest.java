package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.FileContentConverter;
import http.Responders.Response;
import http.Responders.ResponseHeader;
import http.Responders.ResponseStatus;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class RangeResponderTest {

    private String testRootPath = "src/test/resources";
    private String resourcePath = "/testFile1.txt";
    private String testFullPath = testRootPath + resourcePath;
    private byte[] fullFileContents = getFullFileContents(testFullPath);

    @Test
    public void performRangeRequest_onByteRange() {
        Response response = getResponseToRangeRequest("bytes=0-4");
        byte[] expectedContent = getContentRange(0, 5);

        assertEquals(ResponseStatus.PARTIALCONTENT, response.getStatus());
        assertArrayEquals("bytes 0-4/15".getBytes(), response.getHeaders()
                .get(ResponseHeader.CONTENTRANGE));
        assertArrayEquals(expectedContent, response.getBodyContent());
    }

    @Test
    public void respondTo_GETRequest_PartialContent_onlyStartIndex() {
        Response response = getResponseToRangeRequest("bytes=11-");
        byte[] expectedContent = getContentRange(11, fullFileContents.length);

        assertEquals(ResponseStatus.PARTIALCONTENT, response.getStatus());
        assertArrayEquals("bytes 11-14/15".getBytes(), response.getHeaders()
                .get(ResponseHeader.CONTENTRANGE));
        assertArrayEquals(expectedContent, response.getBodyContent());
    }

    @Test
    public void respondTo_GETRequest_PartialContent_onlyEndNumber() {
        Response response = getResponseToRangeRequest("bytes=-6");
        byte[] expectedContent = getContentRange(9, fullFileContents.length);

        assertEquals(ResponseStatus.PARTIALCONTENT, response.getStatus());
        assertArrayEquals("bytes 9-14/15".getBytes(), response.getHeaders()
                .get(ResponseHeader.CONTENTRANGE));
        assertArrayEquals(expectedContent, response.getBodyContent());
    }

    @Test
    public void respondTo_GETRequest_PartialContent_RangeNotSatisfiable() {
        Response response = getResponseToRangeRequest("bytes=10-20");

        assertEquals(ResponseStatus.RANGENOTSATISFIABLE, response.getStatus());
        assertArrayEquals("bytes */15".getBytes(), response.getHeaders()
                .get(ResponseHeader.CONTENTRANGE));
    }

    private Response getResponseToRangeRequest(String byteRange) {
        RangeResponder rangeResponder = new RangeResponder(new FileContentConverter());
        HashMap<String, String> rangeHeader = new HashMap<>();
        rangeHeader.put("Range", byteRange);
        Request request = new Request(HTTPVerb.GET, resourcePath, rangeHeader, "");
        return rangeResponder.performRangeRequest(request, fullFileContents);
    }

    private byte[] getContentRange(int start, int end) {
        return Arrays.copyOfRange(fullFileContents, start, end);
    }

    private byte[] getFullFileContents(String path) {
        FileContentConverter fileContentConverter = new FileContentConverter();
        return fileContentConverter.getFullContents(new File(path));
    }
}