package http.Responders;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class RequestRouterTest {

    private final String testRootPath = "src/test/resources";

    @Test
    public void handlesRequest() {
        RequestRouter requestRouter = new RequestRouter(testRootPath);
        Request request = new Request(HTTPVerb.GET, "/testFile1.txt", new HashMap<>(), "");

        Response response = requestRouter.handle(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
    }

    @Test
    public void methodNotAllowedDefaultIfRequestNotHandled() {
        RequestRouter requestRouter = new RequestRouter(testRootPath);
        Request request = new Request(HTTPVerb.GET, "/non-existent", new HashMap<>(), "");

        Response response = requestRouter.handle(request);

        assertEquals(ResponseStatus.METHODNOTALLOWED, response.getStatus());
    }
}