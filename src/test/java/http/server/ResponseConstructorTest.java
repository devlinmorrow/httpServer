package http.server;

import http.Response.Response;
import http.Response.ResponseStatus;
import http.util.ContentType;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;

public class ResponseConstructorTest {

    @Test
    public void constructsResponse() throws IOException {
        ResponseStatus exampleStatus = ResponseStatus.OK;
        ContentType exampleContentType = ContentType.TXT;
        String exampleContents = "any contents";

        Response mockResponse = new Response();
        mockResponse.setStatus(exampleStatus);
        mockResponse.setContentTypeHeader(exampleContentType);
        mockResponse.setBodyContent(exampleContents.getBytes());

        String expectedResponse = ("HTTP/1.1" + " " + exampleStatus.getPhraseAsString()
                + "\r\n" + "Content-Type: " + exampleContentType.getValueAsString()
                + "\r\n\r\n" + exampleContents);

        ResponseConstructor responseConstructor = new ResponseConstructor();
        byte[] actualResponse = responseConstructor.construct(mockResponse);

        assertArrayEquals(expectedResponse.getBytes(), actualResponse);
    }
}