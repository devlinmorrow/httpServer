package http.Request;

import http.util.IOHelper;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class RequestReaderTest {

    private final String requestLineInput = "GET testFile1.txt HTTP/1.1";
    private final String headersInput = "Host: localhost:5000\nRange: bytes=0-4\n";
    private final String bodyInput = "body content";
    private final String fullRequestInput = requestLineInput + "\n" +
            headersInput + "\n" + bodyInput;

    @Test
    public void givenRequestInput_extractAllRequestElements() throws IOException {
        IOHelper clientIO = new IOHelper(fullRequestInput);
        RequestReader requestReader = new RequestReader(clientIO.getIn());

        assertEquals(requestLineInput, requestReader.extractRequestLine());
        assertEquals(headersInput, requestReader.extractHeaders());
        assertEquals(bodyInput, requestReader.extractBodyContent(bodyInput.length()));
    }
}