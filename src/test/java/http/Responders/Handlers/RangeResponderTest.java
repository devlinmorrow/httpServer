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

    private final static String resourcePath = "/testFile1.txt";
    private byte[] fullTestFileContents = getFullTestFileContents();

    @Test
    public void performRangeRequest_onByteRange() {
        String byteRange = "bytes=0-4";
        Response response = getResponseToRangeRequest(byteRange);

        int rangeStart = 0;
        int rangeEnd = 5;
        byte[] expectedContent = getFileOfRange(rangeStart, rangeEnd);

        assertEquals(ResponseStatus.PARTIALCONTENT, response.getStatus());
        assertArrayEquals("bytes 0-4/15".getBytes(), response.getHeaders()
                .get(ResponseHeader.CONTENTRANGE));
        assertArrayEquals(expectedContent, response.getBodyContent());
    }

    @Test
    public void performRangeRequest_onlyRangeStart() {
        String byteRange = "bytes=11-";
        Response response = getResponseToRangeRequest(byteRange);

        int rangeStart = 11;
        int rangeEnd = fullTestFileContents.length;
        byte[] expectedContent = getFileOfRange(rangeStart, rangeEnd);

        assertEquals(ResponseStatus.PARTIALCONTENT, response.getStatus());
        assertArrayEquals("bytes 11-14/15".getBytes(), response.getHeaders()
                .get(ResponseHeader.CONTENTRANGE));
        assertArrayEquals(expectedContent, response.getBodyContent());
    }

    @Test
    public void performRangeRequest_onlyRangeEnd() {
        String byteRange = "bytes=-6";
        Response response = getResponseToRangeRequest(byteRange);

        int rangeStart = 9;
        int rangeEnd = fullTestFileContents.length;
        byte[] expectedContent = getFileOfRange(rangeStart, rangeEnd);

        assertEquals(ResponseStatus.PARTIALCONTENT, response.getStatus());
        assertArrayEquals("bytes 9-14/15".getBytes(), response.getHeaders()
                .get(ResponseHeader.CONTENTRANGE));
        assertArrayEquals(expectedContent, response.getBodyContent());
    }

    @Test
    public void performRangeRequest_rangeNotSatisfiable() {
        String byteRange = "bytes=10-20";
        Response response = getResponseToRangeRequest(byteRange);

        assertEquals(ResponseStatus.RANGENOTSATISFIABLE, response.getStatus());
        assertArrayEquals("bytes */15".getBytes(), response.getHeaders()
                .get(ResponseHeader.CONTENTRANGE));
    }

    private Response getResponseToRangeRequest(String byteRange) {
        HashMap<String, String> rangeHeader = new HashMap<>();
        rangeHeader.put("Range", byteRange);
        String emptyBody = "";
        Request request = new Request(HTTPVerb.GET, resourcePath, rangeHeader, emptyBody);

        RangeResponder rangeResponder = new RangeResponder(new FileContentConverter());

        return rangeResponder.performRangeRequest(request, fullTestFileContents);
    }

    private byte[] getFileOfRange(int rangeStart, int rangeEnd) {
        return Arrays.copyOfRange(fullTestFileContents, rangeStart, rangeEnd);
    }

    private byte[] getFullTestFileContents() {
        String testRootPath = "src/test/resources";
        String testFullPath = testRootPath + resourcePath;
        FileContentConverter fileContentConverter = new FileContentConverter();
        return fileContentConverter.getFullContents(new File(testFullPath));
    }
}