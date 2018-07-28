package http;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class RequestResponderTest {

    @Test
    public void respondTo_GETRequest_dummyFile() {
        String URI = "src/test/resources/dummyFile1.txt";
        byte[] expectedContentType = ContentType.TXT.getBytesValue();
        byte[] dummyFileContents = "file1 contents\n".getBytes();
        RequestResponder requestResponder = new RequestResponder();
        Request mockRequest = new Request(HTTPVerb.GET, URI);

        Response mockResponse = requestResponder.respondTo(mockRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals(expectedContentType, mockResponse.getHeaders()
                .get(Header.CONTENTTYPE));
        assertArrayEquals(dummyFileContents, mockResponse.getBodyContent());
    }
}