import org.junit.Test;

import static org.junit.Assert.*;

public class StringAnalyserTest {

    @Test
    public void findHTTPVerbTest_GET() {
        String simpleGETRequest = "GET / HTTP/1.1\n\nHost: localhost:5000";
        String expectedResult = "GET";

        StringAnalyser stringAnalyser = new StringAnalyser();

        String firstWordOfRequest = stringAnalyser
                .findHTTPVerb(simpleGETRequest);

        assertEquals(expectedResult, firstWordOfRequest);
    }

}