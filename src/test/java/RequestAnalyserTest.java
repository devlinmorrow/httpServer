import org.junit.Test;

import static org.junit.Assert.*;

public class RequestAnalyserTest {

    @Test
    public void findHTTPMethodTest_GET() {
        RequestAnalyser requestAnalyser = new RequestAnalyser();

        HTTPMethod httpMethod = requestAnalyser
                .findHTTPMethod("GET / HTTP/1.1\n\nHost: localhost:5000");

        assertSame(httpMethod, HTTPMethod.GET);
    }

    @Test
    public void findHTTPMethodTest_NotValid() {
        RequestAnalyser requestAnalyser = new RequestAnalyser();

        HTTPMethod httpMethod = requestAnalyser
                .findHTTPMethod("blah / HTTP/1.1\n\nHost: localhost:5000");

        assertNull(httpMethod);
    }

}