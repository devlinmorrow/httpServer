package http.Requesters;

import http.HardcodedValues;
import http.IOHelper;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class RequestParserTest {

    private String mockURI = "dummyFile1.txt";

    @Test
    public void parse_GETRequest_noHeaders() {
        String requestInput = "GET " + mockURI + " HTTP/1.1";
        RequestParser requestParser = new RequestParser();

        IOHelper clientIO = new IOHelper(requestInput);
        Request mockRequest = requestParser.parse(clientIO.getIn());

        assertEquals(HTTPVerb.GET, mockRequest.getHTTPVerb());
        assertEquals(HardcodedValues.RESOURCEPATH.getS() + mockURI, mockRequest.getURI());
    }

    @Test
    public void parse_GETRequest_withHeaders() {
        String requestInput = "GET " + mockURI + " HTTP/1.1\nHost: Localhost: 5000\nRange: bytes=0-4\n";
        RequestParser requestParser = new RequestParser();

        IOHelper clientIO = new IOHelper(requestInput);
        Request mockRequest = requestParser.parse(clientIO.getIn());

        assertEquals(HTTPVerb.GET, mockRequest.getHTTPVerb());
        assertEquals(HardcodedValues.RESOURCEPATH.getS() + mockURI, mockRequest.getURI());
        assertEquals("Localhost: 5000", mockRequest.getHeaders().get("Host"));
        assertEquals("bytes=0-4", mockRequest.getHeaders().get("Range"));
    }
}