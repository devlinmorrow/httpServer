import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ConnectionAcceptorTest {

    @Test
    public void acceptsConnection() throws IOException {
        IOHelper serverIO = new IOHelper("");

        IOHelper socketIO = new IOHelper("");
        SocketStub socketStub = new SocketStub(socketIO.getIn(), socketIO.getOut());
        ServerSocketStub serverSocketStub = new ServerSocketStub(socketStub);

        Router router = new Router(new GETHandler());
        ConnectionAcceptor connectionAcceptor =
                new ConnectionAcceptor(serverSocketStub, serverIO.getPrintStream(), router);

        connectionAcceptor.start();

        assertEquals(Message.CONNECTED.getS(), serverIO.getStringOutput());
    }

    @Test
    public void callsRouterRoute() throws IOException {
        IOHelper serverIO = new IOHelper("");
        IOHelper socketIO = new IOHelper("");
        SocketStub socketStub = new SocketStub(socketIO.getIn(), socketIO.getOut());
        ServerSocketStub serverSocketStub = new ServerSocketStub(socketStub);
        RouterSpy routerSpy = new RouterSpy(new GETHandler());

        ConnectionAcceptor connectionAcceptor =
                new ConnectionAcceptor(serverSocketStub, serverIO.getPrintStream(), routerSpy);

        connectionAcceptor.start();

        assertTrue(routerSpy.routeWasCalled());
    }
}
