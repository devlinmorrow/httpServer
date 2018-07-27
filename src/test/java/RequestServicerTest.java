import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;

public class RequestServicerTest {

    @Test
    public void simpleGET() throws IOException {
        Request exGETRequest = makeGETRequest("src/main/resources/dummyFile1.txt");
        RequestServicer requestServicer = new RequestServicer();

        ByteArrayOutputStream expectedResponseStream = new ByteArrayOutputStream();
        expectedResponseStream.write(HardcodedValues.HTTPVERSION.getS().getBytes());
        expectedResponseStream.write(" ".getBytes());
        expectedResponseStream.write(ResponseStatus.TWOHUNDRED.getPhrase());
        expectedResponseStream.write("\n\n".getBytes());
        expectedResponseStream.write("file1 contents\n".getBytes());

        byte[] expectedResponse = expectedResponseStream.toByteArray();

        byte[] actualResponse = requestServicer.respondTo(exGETRequest);

        assertArrayEquals(expectedResponse, actualResponse);
    }

    @Test
    public void attemptedGET_receives404() throws IOException {
        Request exGETRequest = makeGETRequest("src/main/resources/no-file-here.txt");
        RequestServicer requestServicer = new RequestServicer();

        ByteArrayOutputStream expectedResponseStream = new ByteArrayOutputStream();
        expectedResponseStream.write(HardcodedValues.HTTPVERSION.getS().getBytes());
        expectedResponseStream.write(" ".getBytes());
        expectedResponseStream.write(ResponseStatus.FOUROHFOUR.getPhrase());
        expectedResponseStream.write("\n\n".getBytes());
        expectedResponseStream.write(ResponseStatus.FOUROHFOUR.getStatusBody());
        expectedResponseStream.write("".getBytes());

        byte[] expectedResponse = expectedResponseStream.toByteArray();

        byte[] actualResponse = requestServicer.respondTo(exGETRequest);

        assertArrayEquals(expectedResponse, actualResponse);
    }

    private Request makeGETRequest(String URI) {
        Request request = new Request();
        request.setRequestMethod(RequestMethod.GET);
        request.setURI(URI);
        return request;
    }
}