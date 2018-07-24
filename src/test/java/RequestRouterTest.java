import org.junit.Test;

import static org.junit.Assert.*;

public class RequestRouterTest {

    @Test
    public void runGET() {
        String GETrequest = "GET / HTTP/1.1\n\nHost: localhost:5000";
        GETHandlerSpy getHandlerSpy = new GETHandlerSpy();
        RequestRouter requestRouter = new RequestRouter(getHandlerSpy);

        requestRouter.route(GETrequest);

        assertTrue(getHandlerSpy.isHandleGETWasCalled());
    }

}