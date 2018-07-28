package http;

import org.junit.Test;

import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;

public class ResponseWriterTest {

    @Test
    public void buildsResponse_noHeaders() {
        IOHelper clientIO = new IOHelper("");
        String exFileContents = "file1 contents\n";
        Response exGETResponse = new Response();
        exGETResponse.setStatus(ResponseStatus.OK);
        exGETResponse.setBodyContent(exFileContents.getBytes());
        ResponseWriter responseWriter = new ResponseWriter();

        String expectedResponse = HardcodedValues.HTTPVERSION.getS() + " " +
                new String(ResponseStatus.OK.getPhrase()) + "\n\n" + exFileContents;

        responseWriter.write(exGETResponse, clientIO.getOut());

        assertEquals(expectedResponse, clientIO.getStringOutput());
    }

    @Test
    public void buildsResponse_Header_ContentType() {
//        String exFileContents = "file1 contents\n";
//        Response exGETResponse = new Response();
//        exGETResponse.setStatus(ResponseStatus.OK);
//        exGETResponse.setContentType(ContentType.TXT);
//        exGETResponse.setBodyContent(exFileContents.getBytes());
//
//        ResponseWriter responseWriter = new ResponseWriter();
//        byte[] actualResponse = responseWriter.write(exGETResponse);
//
//        String expectedResponseS = HardcodedValues.HTTPVERSION.getS() + " " +
//                new String(ResponseStatus.OK.getPhrase()) + "\n" +
//                "Content-Type: text/plain" + "\n\n" + exFileContents;
//        byte[] expectedResponse = expectedResponseS.getBytes();
//
//        assertEquals(new String(expectedResponse), new String(actualResponse));
    }
}