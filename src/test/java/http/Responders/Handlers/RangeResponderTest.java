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

    @Test
    public void performRangeRequest_onByteRange() {
        FileContentConverter fileContentConverter = new FileContentConverter();
        RangeResponder rangeResponder = new RangeResponder(fileContentConverter);
        HashMap<String, String> rangeHeader = new HashMap<>();
        rangeHeader.put("Range","bytes=0-4");
        Request request = new Request(HTTPVerb.GET, resourcePath, rangeHeader, "");

        byte[] fullContent = fileContentConverter.getFullContents(new File(testFullPath));
        byte[] expectedContent = Arrays.copyOfRange(fullContent, 0, 5);

        Response response = rangeResponder.performRangeRequest(request, fullContent);

        assertEquals(ResponseStatus.PARTIALCONTENT, response.getStatus());
        assertArrayEquals("bytes 0-4/15".getBytes(), response.getHeaders()
                .get(ResponseHeader.CONTENTRANGE));
        assertArrayEquals(expectedContent, response.getBodyContent());
    }

    @Test
    public void respondTo_GETRequest_PartialContent_onlyStartIndex() {
        FileContentConverter fileContentConverter = new FileContentConverter();
        RangeResponder rangeResponder = new RangeResponder(fileContentConverter);
        HashMap<String, String> rangeHeader = new HashMap<>();
        rangeHeader.put("Range","bytes=11-");
        Request request = new Request(HTTPVerb.GET, resourcePath, rangeHeader, "");

        byte[] fullContent = fileContentConverter.getFullContents(new File(testFullPath));
        byte[] expectedContent = Arrays.copyOfRange(fullContent, 11, fullContent.length);

        Response response = rangeResponder.performRangeRequest(request, fullContent);

        assertEquals(ResponseStatus.PARTIALCONTENT, response.getStatus());
        assertArrayEquals("bytes 11-14/15".getBytes(), response.getHeaders()
                .get(ResponseHeader.CONTENTRANGE));
        assertArrayEquals(expectedContent, response.getBodyContent());
    }

    @Test
    public void respondTo_GETRequest_PartialContent_onlyEndNumber() {
        FileContentConverter fileContentConverter = new FileContentConverter();
        RangeResponder rangeResponder = new RangeResponder(fileContentConverter);
        HashMap<String, String> rangeHeader = new HashMap<>();
        rangeHeader.put("Range","bytes=-6");
        Request request = new Request(HTTPVerb.GET, resourcePath, rangeHeader, "");


        byte[] fullContent = fileContentConverter.getFullContents(new File(testFullPath));
        byte[] expectedContent = Arrays.copyOfRange(fullContent, 9, fullContent.length);

        Response response = rangeResponder.performRangeRequest(request, fullContent);

        assertEquals(ResponseStatus.PARTIALCONTENT, response.getStatus());
        assertArrayEquals("bytes 9-14/15".getBytes(), response.getHeaders()
                .get(ResponseHeader.CONTENTRANGE));
        assertArrayEquals(expectedContent, response.getBodyContent());
    }

    @Test
    public void respondTo_GETRequest_PartialContent_RangeNotSatisfiable() {
        FileContentConverter fileContentConverter = new FileContentConverter();
        RangeResponder rangeResponder = new RangeResponder(fileContentConverter);
        HashMap<String, String> rangeHeader = new HashMap<>();
        rangeHeader.put("Range","bytes=10-20");
        Request request = new Request(HTTPVerb.GET, resourcePath, rangeHeader, "");

        byte[] fullContent = fileContentConverter.getFullContents(new File(testFullPath));

        Response response = rangeResponder.performRangeRequest(request, fullContent);

        assertEquals(ResponseStatus.RANGENOTSATISFIABLE, response.getStatus());
        assertArrayEquals("bytes */15".getBytes(), response.getHeaders()
                .get(ResponseHeader.CONTENTRANGE));
    }
}