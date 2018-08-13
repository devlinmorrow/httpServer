package http.Handlers;

import http.Request.HTTPVerb;
import http.Request.Request;
import http.Response.Response;
import http.Response.ResponseStatus;
import http.util.Logger;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class RequestRouterTest {

    private final String testRootPath = "src/test/resources";
    private final HashMap<String, String> emptyHeaders = new HashMap<>();
    private final String emptyBody = "";
    private final String testFilePath = "/testFile1.txt";

    @Test
    public void whenGivenRequest_aHandlerHandlesIt() throws IOException {
        Request request = new Request(HTTPVerb.GET, testFilePath, emptyHeaders, emptyBody);
        RequestRouter requestRouter = new RequestRouter(testRootPath, new Logger());

        Response response = requestRouter.handle(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
    }

    @Test
    public void whenGivenRequestForWhichThereIsNoHandler_setNotAllowedResponse() throws IOException {
        Request request = new Request(HTTPVerb.POST, testFilePath, emptyHeaders, emptyBody);
        RequestRouter requestRouter = new RequestRouter(testRootPath, new Logger());

        Response response = requestRouter.handle(request);

        assertEquals(ResponseStatus.METHODNOTALLOWED, response.getStatus());
    }
}