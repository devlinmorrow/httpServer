import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ResponseBuilderTest {

    @Test
    public void buildsResponse_noHeaders() {
        String exFileContents = "file1 contents\n";
        Response exGETResponse = new Response();
        exGETResponse.setStatus(ResponseStatus.TWOHUNDRED);
        exGETResponse.setBodyContent(exFileContents.getBytes());

        ResponseBuilder responseBuilder = new ResponseBuilder();
        byte[] actualResponse = responseBuilder.makeResponse(exGETResponse);

        String expectedResponseS = HardcodedValues.HTTPVERSION.getS() + " " +
                new String(ResponseStatus.TWOHUNDRED.getPhrase()) + "\n\n" + exFileContents;
        byte[] expectedResponse = expectedResponseS.getBytes();

        assertEquals(new String(expectedResponse), new String(actualResponse));
    }

    @Test
    public void buildsResponse_Header_ContentType() {
        String exFileContents = "file1 contents\n";
        Response exGETResponse = new Response();
        exGETResponse.setStatus(ResponseStatus.TWOHUNDRED);
        exGETResponse.setContentType(ContentType.TXT);
        exGETResponse.setBodyContent(exFileContents.getBytes());

        ResponseBuilder responseBuilder = new ResponseBuilder();
        byte[] actualResponse = responseBuilder.makeResponse(exGETResponse);

        String expectedResponseS = HardcodedValues.HTTPVERSION.getS() + " " +
                new String(ResponseStatus.TWOHUNDRED.getPhrase()) + "\n" +
                "Content-Type: text/plain" + "\n\n" + exFileContents;
        byte[] expectedResponse = expectedResponseS.getBytes();

        assertEquals(new String(expectedResponse), new String(actualResponse));
    }
}