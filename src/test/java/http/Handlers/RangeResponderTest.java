package http.Handlers;

import http.Request.HTTPVerb;
import http.Request.Request;
import http.Response.Response;
import http.Response.ResponseHeader;
import http.Response.ResponseStatus;
import http.util.FileContentConverter;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class RangeResponderTest {

    private final String testRootPath = "src/test/resources";
    private final String filePath = "/testFile1.txt";
    private final String testFullPath = testRootPath + filePath;
    private final FileContentConverter fileContentConverter = new FileContentConverter();
    private final RangeResponder rangeResponder = new RangeResponder(new FileContentConverter());
    private final String emptyBody = "";
    byte[] fullTestFileContents;

    @Before
    public void setUp() throws IOException {
         fullTestFileContents = fileContentConverter.getFullContents(new File(testFullPath));
    }

    @Test
    public void givenRangeRequestWithFullByteRange_setRangeResponse() {
        String byteRange = "bytes=0-4";
        HashMap<String, String> rangeHeader = new HashMap<>();
        rangeHeader.put("Range", byteRange);
        Request request = new Request(HTTPVerb.GET, filePath, rangeHeader, emptyBody);

        Response response = rangeResponder.performRangeRequest(request, fullTestFileContents);

        int rangeStart = 0;
        int rangeEnd = 5;
        byte[] expectedContent = getFileOfRange(rangeStart, rangeEnd);

        assertEquals(ResponseStatus.PARTIALCONTENT, response.getStatus());
        assertArrayEquals("bytes 0-4/15".getBytes(), response.getHeaders()
                .get(ResponseHeader.CONTENTRANGE));
        assertArrayEquals(expectedContent, response.getBodyContent());
    }

    @Test
    public void givenRangeRequestWithStartByte_setRangeResponse() {
        String byteRange = "bytes=11-";
        HashMap<String, String> rangeHeader = new HashMap<>();
        rangeHeader.put("Range", byteRange);
        Request request = new Request(HTTPVerb.GET, filePath, rangeHeader, emptyBody);

        Response response = rangeResponder.performRangeRequest(request, fullTestFileContents);

        int rangeStart = 11;
        int rangeEnd = fullTestFileContents.length;
        byte[] expectedContent = getFileOfRange(rangeStart, rangeEnd);

        assertEquals(ResponseStatus.PARTIALCONTENT, response.getStatus());
        assertArrayEquals("bytes 11-14/15".getBytes(), response.getHeaders()
                .get(ResponseHeader.CONTENTRANGE));
        assertArrayEquals(expectedContent, response.getBodyContent());
    }

    @Test
    public void givenRangeRequestWithEndByte_setRangeResponse() {
        String byteRange = "bytes=-6";
        HashMap<String, String> rangeHeader = new HashMap<>();
        rangeHeader.put("Range", byteRange);
        Request request = new Request(HTTPVerb.GET, filePath, rangeHeader, emptyBody);

        Response response = rangeResponder.performRangeRequest(request, fullTestFileContents);

        int rangeStart = 9;
        int rangeEnd = fullTestFileContents.length;
        byte[] expectedContent = getFileOfRange(rangeStart, rangeEnd);

        assertEquals(ResponseStatus.PARTIALCONTENT, response.getStatus());
        assertArrayEquals("bytes 9-14/15".getBytes(), response.getHeaders()
                .get(ResponseHeader.CONTENTRANGE));
        assertArrayEquals(expectedContent, response.getBodyContent());
    }

    @Test
    public void givenRangeRequestWhichIsNotSatisfiable_setNotSatisfiableResponse() {
        String byteRange = "bytes=10-20";
        HashMap<String, String> rangeHeader = new HashMap<>();
        rangeHeader.put("Range", byteRange);
        Request request = new Request(HTTPVerb.GET, filePath, rangeHeader, emptyBody);

        Response response = rangeResponder.performRangeRequest(request, fullTestFileContents);

        assertEquals(ResponseStatus.RANGENOTSATISFIABLE, response.getStatus());
        assertArrayEquals("bytes */15".getBytes(), response.getHeaders()
                .get(ResponseHeader.CONTENTRANGE));
    }

    private byte[] getFileOfRange(int rangeStart, int rangeEnd) {
        return Arrays.copyOfRange(fullTestFileContents, rangeStart, rangeEnd);
    }
}