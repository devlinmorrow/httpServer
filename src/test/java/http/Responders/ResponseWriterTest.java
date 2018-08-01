package http.Responders;

import http.HardcodedValues;
import http.IOHelper;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ResponseWriterTest {

    @Test
    public void buildsResponse_simpleGET_NoHeaders() {
        ResponseStatus exampleStatus = ResponseStatus.OK;
        ContentType exampleContentType = ContentType.TXT;
        String exampleContents = "file1 contents";

        Response mockResponse = makeResponse(exampleStatus,
                exampleContentType, exampleContents.getBytes());
        IOHelper clientIO = new IOHelper("");

        String expectedResponse = makeExpectedR(exampleStatus,
                exampleContentType, exampleContents);

        ResponseWriter responseWriter = new ResponseWriter();
        responseWriter.write(mockResponse, clientIO.getOut());

        assertEquals(expectedResponse, clientIO.getStringOutput());
    }

    private Response makeResponse(ResponseStatus status, ContentType contentType, byte[] bodyContents) {
        Response mockResponse = new Response();
        mockResponse.setStatus(status);
        mockResponse.setContentTypeHeader(contentType);
        mockResponse.setBodyContent(bodyContents);
        return mockResponse;
    }

    private String makeExpectedR(ResponseStatus status, ContentType contentType, String bodyContents) {
        return (HardcodedValues.HTTPVERSION.getS() + " " + status.getPhraseAsS()
                + "\r\n" + HardcodedValues.CONTENTTYPEHEADER.getS()
                + contentType.getValueAsS() + "\r\n\r\n" + bodyContents);
    }
}