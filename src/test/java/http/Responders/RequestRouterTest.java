package http.Responders;

import http.Logger;
import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class RequestRouterTest {

    private final String testRootPath = "src/test/resources";
    private final HashMap<String, String> emptyHeaders = new HashMap<>();
    private final String emptyBody = "";
    private final String testFilePath = "/testFile1.txt";

    @Test
    public void whenGivenRequest_aHandlerHandlesIt() {
        Request request = new Request(HTTPVerb.GET, testFilePath, emptyHeaders, emptyBody);
        RequestRouter requestRouter = new RequestRouter(testRootPath, new Logger());

        Response response = requestRouter.handle(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
    }

    @Test
    public void whenGivenRequestForWhichThereIsNoHandler_makesNotAllowedResponse() {
        Request request = new Request(HTTPVerb.POST, testFilePath, emptyHeaders, emptyBody);
        RequestRouter requestRouter = new RequestRouter(testRootPath, new Logger());

        Response response = requestRouter.handle(request);

        assertEquals(ResponseStatus.METHODNOTALLOWED, response.getStatus());
    }
}