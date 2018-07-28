package http;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestParserTest {

    @Test
    public void parse_GETRequest_noHeaders() {
        String mockURI = "/no-file-here.txt";
        String GETRequest_NonExistentURI = "GET " + mockURI + " HTTP/1.1";
        RequestParser requestParser = new RequestParser();

        IOHelper clientIO = new IOHelper(GETRequest_NonExistentURI);
        Request mockRequest = requestParser.parse(clientIO.getIn());

        assertEquals(mockRequest.getHTTPVerb(), HTTPVerb.GET);
        assertEquals(mockRequest.getURI(), HardcodedValues.RESOURCEPATH.getS() + mockURI);
    }

}