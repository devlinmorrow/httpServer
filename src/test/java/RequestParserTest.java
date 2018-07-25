import org.junit.Test;

import static org.junit.Assert.*;

public class RequestParserTest {
    private String URI1 = "/file1";
    private String exampleRequest = "GET " + URI1 + " HTTP/1.1\nHost: localhost:5000" +
            "\nConnection: keep-alive\nCache-Control: max-age=0\nUpgrade-Insecure-Requests: 1" +
            "\nUser-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36\nAccept: text/html,application/" +
            "xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\nAccept-Encoding: gzip," +
            " deflate, br\nAccept-Language: en-US,en;q=0.9,fr;q=0.8,de;q=0.7,es;q=0.6\nCookie: " +
            "textwrapon=false; wysiwyg=textarea";

    private String URI2 = "/bananas";
    private String exampleRequest2 = "GET " + URI2 + " HTTP/1.1\nHost: localhost:5000\n\n\n";

    @Test
    public void getURI() {
        RequestParser requestParser = new RequestParser();
        IOHelper clientSocket = new IOHelper(exampleRequest);

        assertEquals(URI1, requestParser.getURI(clientSocket.getIn()));
    }

    @Test
    public void getURI2() {
        RequestParser requestParser = new RequestParser();
        IOHelper clientSocket = new IOHelper(exampleRequest2);

        assertEquals(URI2, requestParser.getURI(clientSocket.getIn()));
    }

}