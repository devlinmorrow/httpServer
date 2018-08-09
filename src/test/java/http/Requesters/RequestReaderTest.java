package http.Requesters;

import http.IOHelper;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RequestReaderTest {

    private String requestLine = "GET testFile1.txt HTTP/1.1";
    private String requestHeaders = "Host: localhost:5000\nRange: bytes=0-4\n";
    private String requestBodyContent = "requestBodyContent";
    private String mockRequestInput = requestLine + "\n" +
            requestHeaders + "\n" + requestBodyContent;

    @Test
    public void extractRequestLine() {
        RequestReader requestReader = setUpRequestReader();

        assertEquals(requestLine, requestReader.extractRequestLine());
    }

    @Test
    public void extractHeaders() {
        RequestReader requestReader = setUpRequestReader();
        requestReader.extractRequestLine();

        assertEquals(requestHeaders, requestReader.extractHeaders());
    }

    @Test
    public void extractBodyContent() {
        RequestReader requestReader = setUpRequestReader();
        requestReader.extractRequestLine();
        requestReader.extractHeaders();

        assertEquals(requestBodyContent, requestReader.extractBodyContent(requestBodyContent.length()));
    }

    private RequestReader setUpRequestReader() {
        IOHelper clientIO = new IOHelper(mockRequestInput);
        return new RequestReader(clientIO.getIn());
    }
}