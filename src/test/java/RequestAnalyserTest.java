import org.junit.Test;

import static org.junit.Assert.*;

public class RequestAnalyserTest {

    @Test
    public void findHTTPVerbTest_GET() {
        String simpleGETRequest = "GET / HTTP/1.1\n\nHost: localhost:5000";
        String expectedResult = "GET";

        RequestAnalyser requestAnalyser = new RequestAnalyser();

        String firstWordOfRequest = requestAnalyser
                .findHTTPVerb(simpleGETRequest);

        assertEquals(expectedResult, firstWordOfRequest);
    }

}