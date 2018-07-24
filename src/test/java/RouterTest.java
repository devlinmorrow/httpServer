import org.junit.Test;

import static org.junit.Assert.*;

public class RouterTest {



    @Test
    public void runGET() {
        GETHandlerSpy getHandlerSpy = new GETHandlerSpy();
        Router router = new Router(getHandlerSpy);
        IOHelper socketIO = new IOHelper("GET / HTTP/1.1\n\nHost: localhost:5000");
        SocketStub socketStub = new SocketStub(socketIO.getIn(), socketIO.getOut());

        router.route(socketStub);

        assertTrue(getHandlerSpy.isHandleGETWasCalled());
    }

}